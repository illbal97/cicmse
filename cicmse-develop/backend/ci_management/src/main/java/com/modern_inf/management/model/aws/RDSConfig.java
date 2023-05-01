package com.modern_inf.management.model.aws;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RDSConfig {
    private String dbIdentifier;
    private String dbInstanceType;
    private String engine;
    private String password;
    private String username;
    private String dbName;
    private String storageType;
    private int allocatedStorage;

}
