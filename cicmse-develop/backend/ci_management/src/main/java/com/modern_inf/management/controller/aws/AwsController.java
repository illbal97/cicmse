package com.modern_inf.management.controller.aws;

import com.amazonaws.services.ec2.model.AmazonEC2Exception;
import com.amazonaws.services.ec2.model.Instance;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.aws.AwsAccount;
import com.modern_inf.management.model.aws.EC2instance;
import com.modern_inf.management.model.dto.aws.AwsDto;
import com.modern_inf.management.service.aws.AwsService;
import com.modern_inf.management.service.userService.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/aws")
public class AwsController {

    private final AwsService awsService;

    private final UserService userService;
    private final SymmetricEncryption symmetricEncryption;

    private List<String> errors = new ArrayList<>();

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AwsController(AwsService awsService, UserService userService, SymmetricEncryption symmetricEncryption) {
        this.awsService = awsService;
        this.userService = userService;
        this.symmetricEncryption = symmetricEncryption;
    }

    @PostMapping("ec2-instance")
    public ResponseEntity<?> getEc2Instances(@RequestBody AwsDto awsDto) {
        List<Instance> instances;
        errors.clear();
        var awsAccount = this.awsService.getAwsAccountByUser(awsDto.getUser().getId());
        var ec2instancesFromDb = awsAccount.getEc2instance();
        if(awsAccount == null) {
            errors.add("There are not any AWS account");
            LOGGER.error(errors.get(0));
            return ResponseEntity.ok(errors);
        }
        try{
            if(!ec2instancesFromDb.isEmpty() && LocalDateTime.now().isBefore(awsAccount.getTokenExpirationTime()) && !awsDto.isStatusChanged()) {
                return ResponseEntity.ok(awsAccount.getEc2instance());
            }else {
                instances = awsService.getEC2Instances(awsDto);
                for(Instance instance: instances) {
                   var ec2instances = ec2instancesFromDb.stream()
                           .filter(ec2 -> !Objects.equals(ec2.getState(), instance.getState().getName()) && ec2.getInstanceId().equals(instance.getInstanceId()))
                           .toList();
                   if(!ec2instances.isEmpty()) {
                       this.awsService.updateEC2InstanceState(ec2instances, instance);
                   }
                    if(ec2instancesFromDb.stream().noneMatch(ec2 -> ec2.getInstanceId().equals(instance.getInstanceId()))) {
                        this.awsService.saveEC2Instance(EC2InstanceBuilder(instance, awsAccount));
                    }
                }
                this.awsService.updateAwsUserTokenExpirationTime(awsAccount);
                return ResponseEntity.ok(awsAccount.getEc2instance());
            }

        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            errors.add("Bad AWS access keys");
            return ResponseEntity.ok(errors);
        }

    }

    @PostMapping("S3-creation")
    public ResponseEntity<?> creationS3(@RequestBody AwsDto awsDto) {
        errors.clear();
        try{
            this.awsService.createS3(awsDto);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            errors.add(e.getMessage());
            return ResponseEntity.ok(errors);
        }
        return ResponseEntity.ok(Arrays.asList("Successfully S3 creation"));

    }

    @PostMapping("RDS-creation")
    public ResponseEntity<?> RDSCreation(@RequestBody AwsDto awsDto) {
        errors.clear();
        try{
            this.awsService.RDSCreation(awsDto);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            errors.add(e.getMessage());
            return ResponseEntity.ok(errors);
        }
        return ResponseEntity.ok(Arrays.asList("Successfully RDS creation"));

    }

    @PostMapping("ec2-instance-start")
    public ResponseEntity<?> startEC2Instance(@RequestBody AwsDto dto) {
        try{
            this.awsService.startEC2Instance(dto);

            return ResponseEntity.ok(Arrays.asList("Start was Successfully"));
        }catch (AmazonEC2Exception e){
            if(e.getStatusCode() == 400 || e.getMessage().equals("InvalidInstanceID.NotFound")) {
                this.awsService.deleteEC2Instance(dto);
                return ResponseEntity.ok(Arrays.asList("Terminated instance deleted from database Successfully"));
            }
            errors.add(e.getMessage());

            return ResponseEntity.ok(errors);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());

            return ResponseEntity.ok(Arrays.asList(e.getMessage()));
        }

    }

    @PostMapping("ec2-instance-stop")
    public ResponseEntity<?> stopEC2Instance(@RequestBody AwsDto dto) {
        errors.clear();
        try{
            this.awsService.stopEC2Instance(dto);

            return ResponseEntity.ok(List.of("Stop was Successfully"));
        }catch (AmazonEC2Exception e){
            if(e.getStatusCode() == 400 || e.getMessage().equals("InvalidInstanceID.NotFound")) {
                this.awsService.deleteEC2Instance(dto);
                return ResponseEntity.ok(List.of("Terminated instance deleted from database Successfully"));
            }
            errors.add(e.getMessage());

            return ResponseEntity.ok(errors);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());

            return ResponseEntity.ok(List.of(e.getMessage()));
        }
    }

    @PostMapping("ec2-instance-creation")
    public ResponseEntity<?> createEC2Instance(@RequestBody AwsDto awsDto) {
        errors.clear();
        var awsAccount = this.awsService.getAwsAccountByUser(awsDto.getUser().getId());
        try{
            var instance = this.awsService.createEC2Instance(awsDto);
            var dbInstance = EC2InstanceBuilder(instance, awsAccount);
            this.awsService.saveEC2Instance(dbInstance);
            return ResponseEntity.ok(dbInstance);
        }catch (Exception e) {
            errors.add(e.getMessage());
            LOGGER.error(errors.get(0));
            return ResponseEntity.ok(errors);
        }
    }

    @PostMapping("add-access-token")
    public ResponseEntity<?> setUserPersonalAccessToken(@RequestBody AwsDto dto) {
        var user = this.userService.findByUsername(dto.getUser().getUsername());
        try {
            user.get().setAwsAccessKey(this.symmetricEncryption.encrypt(dto.getUser().getAwsAccessKey()));
            user.get().setAwsAccessSecretKey(this.symmetricEncryption.encrypt(dto.getUser().getAwsAccessSecretKey()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        var us = this.userService.setAccessKeyAndSecretAccessKeyForAws(user.get());
        this.awsService.setAwsAccountForUser(user.get());

        return ResponseEntity.ok(us);
    }

    public EC2instance EC2InstanceBuilder(Instance instance, AwsAccount awsAccount) {
        return EC2instance.builder()
                .awsAccount(awsAccount)
                .keyName(instance.getKeyName())
                .instanceId(instance.getInstanceId())
                .tagName(instance.getTags().get(0).getValue())
                .instanceType(instance.getInstanceType())
                .state(instance.getState().getName())
                .securityGroupId(instance.getSecurityGroups().get(0).getGroupId())
                .securityGroupName(instance.getSecurityGroups().get(0).getGroupName())
                .build();
    }
}
