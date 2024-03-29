package com.modern_inf.management.service.gitlab;

import com.modern_inf.management.model.dto.gitlab.GitlabDto;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.gitlab.GitlabAccount;
import com.modern_inf.management.model.gitlab.GitlabBranch;
import com.modern_inf.management.model.gitlab.GitlabProject;
import com.modern_inf.management.repository.gitlab.GitlabAccountDao;
import com.modern_inf.management.repository.gitlab.GitlabBranchDao;
import com.modern_inf.management.repository.gitlab.GitlabProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GitlabServiceImpl implements GitlabService {


    private final GitlabApiService gitlabApiService;
    private final GitlabProjectDao gitlabProjectDao;
    private final GitlabBranchDao gitlabBranchDao;
    private final GitlabAccountDao gitlabAccountDao;

    @Value("${app.gitlab.expiration-in-ms}")
    private Long GITLAB_EXPIRATION_TIME_IN_MS;

    private final ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());


    @Autowired
    public GitlabServiceImpl(GitlabApiService gitlabApiService, GitlabProjectDao gitlabProjectDao, GitlabBranchDao gitlabBranchDao, GitlabAccountDao gitlabAccountDao) {
        this.gitlabApiService = gitlabApiService;
        this.gitlabProjectDao = gitlabProjectDao;
        this.gitlabBranchDao = gitlabBranchDao;
        this.gitlabAccountDao = gitlabAccountDao;
    }

    @Override
    public ResponseEntity<GitlabProject[]> getGitlabProjects(GitlabDto dto) throws Exception {
        return this.gitlabApiService.getGitlabProjects(dto);
    }

    @Override
    public List<GitlabProject> getAllGitlabProjects() {
        return this.gitlabProjectDao.findAll();
    }
    @Override
    public void saveGitlabProject(GitlabProject gitlabProject) {
        this.gitlabProjectDao.save(gitlabProject);
    }

    @Override
    public void setGitLabAccountForUser(User user) {
        GitlabAccount gitlabAccount = new GitlabAccount();
        List<GitlabAccount> gitlab = this.gitlabAccountDao.findAll();
        var alreadyExistGitlabAccount = gitlab.stream().filter(as -> Objects.equals(as.getUser().getId(), user.getId())).toList();
        if (alreadyExistGitlabAccount.isEmpty()) {
            gitlabAccount.setUser(user);
            gitlabAccount.setLastActivityTime(LocalDateTime.now());
            long milliseconds = convertLocalDateTimeToMilliSecond(gitlabAccount.getLastActivityTime());
            gitlabAccount.setCacheExpirationTime(convertMilliSecondToLocalDateTime(milliseconds));
            this.gitlabAccountDao.save(gitlabAccount);
        }
    }

    @Override
    public void saveGitlabBranch(GitlabBranch branch) {
        this.gitlabBranchDao.save(branch);
    }

    @Override
    public Optional<GitlabProject> getGitlabProjectById(Long projectId) {
        return this.gitlabProjectDao.findById(projectId);
    }

    @Override
    public ResponseEntity<GitlabProject> gitlabProjectCreation(GitlabDto dto) throws Exception {
        return this.gitlabApiService.createGitlabProject(dto);
    }

    @Override
    public ResponseEntity<GitlabBranch> gitlabBranchCreation(GitlabDto dto) throws Exception {
        return this.gitlabApiService.createGitlabBranch(dto);
    }

    @Override
    public void updateGitlabTokenExpirationTime(GitlabAccount g) {
        g.setLastActivityTime(LocalDateTime.now());
        long milliseconds = convertLocalDateTimeToMilliSecond(g.getLastActivityTime());
        g.setCacheExpirationTime(convertMilliSecondToLocalDateTime(milliseconds));
        this.gitlabAccountDao.save(g);
    }

    private LocalDateTime convertMilliSecondToLocalDateTime(long milliSecond) {
        return Instant.ofEpochMilli(milliSecond).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private long convertLocalDateTimeToMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(zoneOffset) * 1000 + GITLAB_EXPIRATION_TIME_IN_MS;
    }
}
