package com.modern_inf.management.model.Dto;

import com.modern_inf.management.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AsanaProjectDto {

    private User user;
    private String workspaceGid;
    private TempAsanaProject asanaProject;
}
