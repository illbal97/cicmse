package com.modern_inf.management.service;
import com.asana.models.Project;
import com.asana.models.Section;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.google.gson.JsonElement;
import com.modern_inf.management.model.*;
import com.modern_inf.management.model.Dto.*;
import com.modern_inf.management.repository.*;
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

    private final AsanaSectionDao asanaSectionDao;

    private final AsanaTasksDao asanaTasksDao;
    private final AsanaWorkspacesDao asanaWorkspacesDao;

    private final static ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());


    @Value("${app.asana.expiration-in-ms}")
    private Long ASANA_EXPIRATION_TIME_IN_MS ;

    @Autowired
    public AsanaServiceImpl(AsanaApiService asanaApiService, AsanaDao asanaDao, AsanaProjectsDao asanaProjectsDao, AsanaSectionDao asanaSectionDao, AsanaTasksDao asanaTasksDao, AsanaWorkspacesDao asanaWorkspacesDao) {
        this.asanaApiService = asanaApiService;
        this.asanaDao = asanaDao;
        this.asanaProjectsDao = asanaProjectsDao;
        this.asanaSectionDao = asanaSectionDao;
        this.asanaTasksDao = asanaTasksDao;
        this.asanaWorkspacesDao = asanaWorkspacesDao;
    }

    public List<Workspace> getAsanaWorkspaces(Optional<User> user) throws IOException {

        return this.asanaApiService.getWorkspaces(user);

    }

    public List<AsanaProject> getAllAsanaProjects() {
        return this.asanaProjectsDao.findAll();
    }

    public List<AsanaWorkspace> getAllAsanaWorkspaces() {
        return this.asanaWorkspacesDao.findAll();
    }


    public void saveAsanaProject(AsanaProject asanaProjects) {
        this.asanaProjectsDao.save(asanaProjects);
    }

    public List<Project> getAsanaProjectsByWorkspaces(Optional<User> user, String gid) throws IOException {

        return this.asanaApiService.getProjectsByWorkspace(user, gid, false);

    }
    public List<Section> getSectionFromProject(AsanaProjectTasksDto dto) throws IOException {
        return this.asanaApiService.getSectionFromProject(dto);
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

    public AsanaWorkspace getAsanaWorkspaceByWorkspaceGid(String gid) {
        return this.asanaWorkspacesDao.findByGid(gid);
    }

    public List<Task> getTasksFromSection(AsanaProjectTaskSectionDto asanaProjectTaskSectionDto) throws IOException {
        return this.asanaApiService.getTasksFromSection(asanaProjectTaskSectionDto);
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

    public void saveAsanaWorkspace(AsanaWorkspace asanaWorkspace) {
        this.asanaWorkspacesDao.save(asanaWorkspace);
    }
    public void saveAsanaTask(AsanaTask asanaTasks) {
        this.asanaTasksDao.save(asanaTasks);
    }

    public Project createAsanaProject(AsanaProjectDto asanaProjectDto) throws IOException {
        return this.asanaApiService.createProjectForWorkspace(asanaProjectDto);

    }

    public Task getTask(AsanaTaskAndSectionDto asanaTaskAndSectionDto) throws IOException {
        return this.asanaApiService.getTask(asanaTaskAndSectionDto);
    }

    public Section createSectionForProject(AsanaUserAndProjectDto asanaUserAndProjectDto, String sectionName) throws IOException {
        return this.asanaApiService.createSectionForProject(asanaUserAndProjectDto, sectionName);
    }

    public AsanaProject getAsanaProjectByProjectGid(String projectGid) {
        return this.asanaProjectsDao.findByGid(projectGid);
    }

    public List<com.asana.models.User> getAsanaUsers(UserAndWorkspaceGidDto userAndWorkspaceGidDto) throws IOException {
        return this.asanaApiService.getAsanaUsers(userAndWorkspaceGidDto);
    }

    public void saveAsanaSection(AsanaSection asanaSection) {
        this.asanaSectionDao.save(asanaSection);
    }

    public AsanaTask getAsanaTaskByTaskGid(String taskGid) {
        return this.asanaTasksDao.findByGid(taskGid);
    }

    public AsanaSection getAsanaSectionBySectionGid(String asanaSectionGid) {
        return this.asanaSectionDao.findByGid(asanaSectionGid);
    }

    public JsonElement addTaskToSection(AsanaTaskAndSectionDto asanaTaskAndSectionDto) throws IOException {
        return this.asanaApiService.addTaskToSection(asanaTaskAndSectionDto);
    }
}

