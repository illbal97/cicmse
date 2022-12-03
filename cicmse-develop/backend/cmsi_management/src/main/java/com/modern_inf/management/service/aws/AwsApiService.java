package com.modern_inf.management.service.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2AsyncClientBuilder;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.CreateDBInstanceRequest;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.dto.aws.AwsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AwsApiService {

    @Value("${aws.region}")
    private String REGION;

    @Autowired
    private SymmetricEncryption symmetricEncryption;

    public List<Instance> getEC2Instances(AwsDto awsDto) throws Exception {
        BasicAWSCredentials basicAWSCredentials = createAWSCredential(awsDto) ;
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).withRegion(Regions.EU_CENTRAL_1).build();
        boolean done = false;
        List<Instance> instances = new ArrayList<>();
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for(Reservation reservation : response.getReservations()) {
                for(Instance instance : reservation.getInstances()) {
                    if(!instance.getState().getName().equals("terminated") ) {
                        instances.add(instance);
                    }

                }
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
        }
        return instances;
    }
    public void startEC2Instance(AwsDto awsDto) throws Exception {
        AmazonEC2 ec2Client = AmazonEC2AsyncClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(createAWSCredential(awsDto)))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        StartInstancesRequest startInstancesRequest = new StartInstancesRequest()
                .withInstanceIds(awsDto.getInstanceId());
        ec2Client.startInstances(startInstancesRequest);

        DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest().withInstanceIds(awsDto.getInstanceId());
        DescribeInstancesResult response ;
         do {
             response = ec2Client.describeInstances(describeInstancesRequest);

         }while(response.getReservations().get(0).getInstances().get(0).getState().getName().equals("running"));


    }

    public void stopEC2Instance(AwsDto awsDto) throws Exception {
        AmazonEC2 ec2Client = AmazonEC2AsyncClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(createAWSCredential(awsDto)))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        StopInstancesRequest stopInstancesRequest = new StopInstancesRequest()
                .withInstanceIds(awsDto.getInstanceId());


        ec2Client.stopInstances(stopInstancesRequest);

        DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest().withInstanceIds(awsDto.getInstanceId());
        DescribeInstancesResult response;
        do {
            response = ec2Client.describeInstances(describeInstancesRequest);

        }while(response.getReservations().get(0).getInstances().get(0).getState().getName().equals("stopped"));
    }

    public Instance createEC2Instance(AwsDto awsDto) throws Exception {
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(createAWSCredential(awsDto)))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
        /*IpRange ipRange = new IpRange().withCidrIp("0.0.0.0/0");
        IpPermission ipPermission = new IpPermission()
                .withIpv4Ranges(Arrays.asList(new IpRange[] { ipRange }))
                .withIpProtocol("tcp")
                .withFromPort(80)
                .withToPort(80);

        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest
                = new AuthorizeSecurityGroupIngressRequest()
                .withGroupName("BaeldungSecurityGroup")
                .withIpPermissions(ipPermission);
        ec2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);*/

        CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest()
                .withKeyName(awsDto.getKeyName());
        CreateKeyPairResult createKeyPairResult = ec2Client.createKeyPair(createKeyPairRequest);
        createKeyPairResult.getKeyPair().getKeyMaterial();
        Tag tag = new Tag("Name", awsDto.getTagName());
        TagSpecification tagSpecification =  new TagSpecification().withResourceType(ResourceType.Instance);
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                .withTagSpecifications(tagSpecification.withTags(tag))
                .withImageId("ami-076309742d466ad69") // ami-076309742d466ad69 Amazon , ami-0caef02b518350c8b Ubuntu ami-05a60358d5cda31c5
                .withInstanceType("t2.micro")
                .withKeyName(awsDto.getKeyName())
                .withMinCount(1)
                .withMaxCount(1)
                .withSecurityGroups("default");

        var response = ec2Client.runInstances(runInstancesRequest);

        return response.getReservation().getInstances().get(0);

    }

    public void createS3(AwsDto dto) throws Exception {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(createAWSCredential(dto)))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        s3client.createBucket(dto.getBucketName());

    }

    public DBInstance createRDS(AwsDto dto) throws Exception {
        AmazonRDS amazonRDS = AmazonRDSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(createAWSCredential(dto)))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        CreateDBInstanceRequest request = new CreateDBInstanceRequest();
        request.setDBInstanceIdentifier(dto.getRdsConfig().getDbIdentifier());
        request.setDBInstanceClass(dto.getRdsConfig().getDbInstanceType());
        request.setEngine(dto.getRdsConfig().getEngine());
        request.setMasterUsername(dto.getRdsConfig().getUsername());
        request.setMasterUserPassword(dto.getRdsConfig().getPassword());
        request.setDBName(dto.getRdsConfig().getDbName());
        request.setStorageType("gp2");
        request.setAllocatedStorage(dto.getRdsConfig().getAllocatedStorage());

       return amazonRDS.createDBInstance(request);

    }



    private BasicAWSCredentials createAWSCredential(AwsDto awsDto) throws Exception {
        return new BasicAWSCredentials(this.symmetricEncryption.decrypt(awsDto.getUser().getAwsAccessKey()) , this.symmetricEncryption.decrypt(awsDto.getUser().getAwsAccessSecretKey()) );
    }
}
