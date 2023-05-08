package com.modern_inf.management.model.dto.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.postData.GitlabBranchCreation;
import com.modern_inf.management.model.postData.GitlabProjectCreation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitlabDto {

    private String userId;

    private GitlabProjectCreation data;

    @JsonProperty("data_branch")
    private GitlabBranchCreation dataBranch;

    @JsonProperty("project_id")
    private Long gitlabProjectId;

    private User user;

    private boolean isImmediately;

}
