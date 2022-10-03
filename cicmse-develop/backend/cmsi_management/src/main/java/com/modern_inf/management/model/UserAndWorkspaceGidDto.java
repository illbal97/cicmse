package com.modern_inf.management.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAndWorkspaceGidDto {

    private User user;
    private String workspacesGid;
}
