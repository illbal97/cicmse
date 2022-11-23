package com.modern_inf.management.service.gitlab;

import com.google.api.client.json.Json;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.dto.gitlab.GitlabDto;
import com.modern_inf.management.model.gitlab.GitlabBranch;
import com.modern_inf.management.model.gitlab.GitlabProject;
import com.modern_inf.management.model.postData.GitlabBranchCreation;
import com.modern_inf.management.model.postData.GitlabProjectCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class GitlabApiService {
    private final String GITLAB_PROJECT_URL = "https://gitlab.com/api/v4/projects?visibility=private&simple=true";

    private final String GITLAB_PROJECT_CREATION_URL = "https://gitlab.com/api/v4/projects/";

    private final String GITLAB_BRANCH_CREATION_URL = "https://gitlab.com/api/v4/projects/id/repository/branches";

    private HttpHeaders headers;

    @Autowired
    private SymmetricEncryption symmetricEncryption;


    public ResponseEntity<GitlabProject[]> getGitlabProjects(GitlabDto dto) throws Exception {
        headers = setHttpHeader(dto);
        HttpEntity<String> entity = new HttpEntity <> (headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(GITLAB_PROJECT_URL, HttpMethod.GET, entity, GitlabProject[].class);
    }


    public ResponseEntity<GitlabProject> createGitlabProject(GitlabDto dto) throws Exception {
        headers = setHttpHeader(dto);
        HttpEntity<GitlabProjectCreation> entity = new HttpEntity <> (dto.getData(),headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(GITLAB_PROJECT_CREATION_URL, entity, GitlabProject.class);

    }

    public ResponseEntity<GitlabBranch> createGitlabBranch(GitlabDto dto) throws Exception {
        headers = setHttpHeader(dto);
        HttpEntity<GitlabBranchCreation> entity = new HttpEntity <> (dto.getDataBranch(), headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(GITLAB_BRANCH_CREATION_URL.replace("id", dto.getGitlabProjectId().toString()), entity, GitlabBranch.class);

    }

    private HttpHeaders setHttpHeader(GitlabDto dto) throws Exception {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + this.symmetricEncryption.decrypt(dto.getUser().getGitlabPersonalAccessToken()) ); //accessToken can be the secret key you generate.
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }


}
