package com.modern_inf.management.service.gitlab;

import com.modern_inf.management.model.Dto.gitlab.GitlabDto;
import com.modern_inf.management.model.gitlab.GitlabProject;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Project;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GitlabApiService {
    // glpat-q7fBukD8uxvfP-ivh9yK
    private final String GITLAB_PROJECT_URL = "https://gitlab.com/api/v4/projects?visibility=private&simple=true";


    public ResponseEntity<GitlabProject[]> getGitlabProjects(GitlabDto dto) throws GitLabApiException {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + "glpat-VMycG2JNKL37YzEBVzpZ"); //accessToken can be the secret key you generate.
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity <> (headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(GITLAB_PROJECT_URL, HttpMethod.GET, entity, GitlabProject[].class);
    }


}
