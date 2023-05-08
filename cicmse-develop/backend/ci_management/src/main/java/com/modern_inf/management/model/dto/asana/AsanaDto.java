package com.modern_inf.management.model.dto.asana;

import com.modern_inf.management.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsanaDto {
    private User user;
    private String workspaceGid;
    private String userGid;
    private TempAsanaProject asanaProject;
    private String projectGid;
    private String sectionGid;
    private String taskGid;
    private boolean isImmediately;

}
