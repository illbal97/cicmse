package com.modern_inf.management.service;
import com.asana.models.Project;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.modern_inf.management.model.*;
import com.modern_inf.management.model.Dto.AsanaProjectDto;
import com.modern_inf.management.model.Dto.AsanaProjectTasksDto;
import com.modern_inf.management.model.Dto.UserAndWorkspaceGidDto;
import com.modern_inf.management.repository.AsanaDao;
import com.modern_inf.management.repository.AsanaProjectsDao;
import com.modern_inf.management.repository.AsanaTasksDao;
import com.modern_inf.management.repository.AsanaWorkspacesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AsanaServiceImpl implements AsanaService{
    private final AsanaApiService asanaApiService;
    private final AsanaDao asanaDao;
    private final AsanaProjectsDao asanaProjectsDao;

    private final AsanaTasksDao asanaTasksDao;
    private final AsanaWorkspacesDao asanaWorkspacesDao;

    private final static ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());


    @Value("${app.asana.expiration-in-ms}")
    private Long ASANA_EXPIRATION_TIME_IN_MS ;

    @Autowired
    public AsanaServiceImpl(AsanaApiService asanaApiService, AsanaDao asanaDao, AsanaProjectsDao asanaProjectsDao, AsanaTasksDao asanaTasksDao, AsanaWorkspacesDao asanaWorkspacesDao) {
        this.asanaApiService = asanaApiService;
        this.asanaDao = asanaDao;
        this.asanaProjectsDao = asanaProjectsDao;
        this.asanaTasksDao = asanaTasksDao;
        this.asanaWorkspacesDao = asanaWorkspacesDao;
    }

    public List<Workspace> getAsanaWorkspaces(Optional<User> user) throws IOException {

        return this.asanaApiService.getWorkspaces(user);

    }

    public List<AsanaProjects> getAllAsanaProjects() {
        return this.asanaProjectsDao.findAll();
    }

    public List<AsanaWorkspaces> getAllAsanaWorkspaces() {
        return this.asanaWorkspacesDao.findAll();
    }


    public void saveAsanaProject(AsanaProjects asanaProjects) {
        this.asanaProjectsDao.save(asanaProjects);
    }

    public List<Project> getAsanaProjectsByWorkspaces(Optional<User> user, String gid) throws IOException {

        return this.asanaApiService.getProjectsByWorkspace(user, gid, false);

    }

    public Asana getAsanaAccountIdByUser(Long userId) {
        return this.asanaDao.findByUserId(userId);
    }

    public void setAsanaAccountForUser(User user) {
        Asana a = new Asana();
        List<Asana> asana = this.asanaDao.findAll();
        var alreadyExistAsanaAccount = asana.stream().filter(as -> Objects.equals(as.getUser().getId(), user.getId())).toList();
        if (alreadyExistAsanaAccount.isEmpty()) {
            a.setUser(user);
            a.setTokenLastTimeUsed(LocalDateTime.now());
            long milliseconds = convertLocalDateTimeToMilliSecond(a.getTokenLastTimeUsed());
            a.setTokenExpirationTime(convertMilliSecondToLocalDateTime(milliseconds));
            this.asanaDao.save(a);
        }

    }

    public AsanaWorkspaces getAsanaWorkspaceByWorkspaceGid(String gid) {
        return this.asanaWorkspacesDao.findByGid(gid);
    }

    public List<Task> getAsanaTasksFromProject(AsanaProjectTasksDto asanaProjectDto) throws IOException {
        return this.asanaApiService.getTasksFromProject(asanaProjectDto);
    }

    public void updateAsanaTokenExpirationTime(Asana a) {
        a.setTokenLastTimeUsed(LocalDateTime.now());
        long milliseconds = convertLocalDateTimeToMilliSecond(a.getTokenLastTimeUsed());
        a.setTokenExpirationTime(convertMilliSecondToLocalDateTime(milliseconds));
        this.asanaDao.save(a);
    }

    private LocalDateTime convertMilliSecondToLocalDateTime(long milliSecond) {
      return Instant.ofEpochMilli(milliSecond).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private long convertLocalDateTimeToMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(zoneOffset) * 1000 + ASANA_EXPIRATION_TIME_IN_MS;
    }

    public void saveAsanaWorkspace(AsanaWorkspaces asanaWorkspace) {
        this.asanaWorkspacesDao.save(asanaWorkspace);
    }
    public void saveAsanaTask(AsanaTasks asanaTasks) {
        this.asanaTasksDao.save(asanaTasks);
    }

    public Project createAsanaProject(AsanaProjectDto asanaProjectDto) throws IOException {
        return this.asanaApiService.createProjectForWorkspace(asanaProjectDto);

    }

    public AsanaProjects getAsanaProjectByProjectGid(String projectGid) {
        return this.asanaProjectsDao.findByGid(projectGid);
    }

    public List<com.asana.models.User> getAsanaUsers(UserAndWorkspaceGidDto userAndWorkspaceGidDto) throws IOException {
        return this.asanaApiService.getAsanaUsers(userAndWorkspaceGidDto);
    }
}

