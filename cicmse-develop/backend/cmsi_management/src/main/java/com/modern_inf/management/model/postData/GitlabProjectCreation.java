package com.modern_inf.management.model.postData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabProjectCreation {

    private String name;

    private String path;

    @JsonProperty("default_branch")
    private String defaultBranch;

    private String visibility;

    private String description;
}
