package com.modern_inf.management.model.Dto.asana;

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
    private TempAsanaProject asanaProject;
    private String projectGid;
    private String sectionGid;
    private String taskGid;
    private boolean isImmediate;

}
