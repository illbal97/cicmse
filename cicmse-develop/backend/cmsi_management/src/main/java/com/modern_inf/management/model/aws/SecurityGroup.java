package com.modern_inf.management.model.aws;

import com.amazonaws.services.ec2.model.IpRange;

public class SecurityGroup {
    private String groupName;
    private String description;
    private IpRange[] ipRanges;
    private String protocol;
    private int fromPort;
    private int toPort;
    private String key;
}
