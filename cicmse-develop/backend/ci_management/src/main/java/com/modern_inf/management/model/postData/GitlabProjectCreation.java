package com.modern_inf.management.model.postData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabProjectCreation {

    private String name;

    private String visibility;

    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("initialize_with_readme")
    private String initializeWithReadme;

    private String description;
}
