package com.modern_inf.management.model.Dto;

import com.modern_inf.management.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsanaDto {
    private User user;
    private String workspaceGid;
    private TempAsanaProject asanaProject;
    private String projectGid;
    private boolean isImmediate;

}
