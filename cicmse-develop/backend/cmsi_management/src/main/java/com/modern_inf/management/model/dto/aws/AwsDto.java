package com.modern_inf.management.model.dto.aws;

import com.modern_inf.management.model.User;
import com.modern_inf.management.model.aws.RDSConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsDto {
    private User user;

    private String keyName;

    private String tagName;

    private String imageIds;

    private String instanceId;

    private String bucketName;

    private RDSConfig rdsConfig;

    private boolean statusChanged;


}
