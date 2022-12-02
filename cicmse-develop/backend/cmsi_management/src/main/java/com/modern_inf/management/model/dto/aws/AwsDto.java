package com.modern_inf.management.model.dto.aws;

import com.modern_inf.management.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsDto {
    private User user;

    private String keyName;

    private String instanceId;

    private boolean statusChanged;


}
