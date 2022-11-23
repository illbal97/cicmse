package com.modern_inf.management.service.asana;

import com.asana.models.Project;
import com.asana.models.Section;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.google.gson.JsonElement;
import com.modern_inf.management.model.*;
import com.modern_inf.management.model.dto.asana.AsanaDto;
import com.modern_inf.management.model.asana.*;
import com.modern_inf.management.repository.asana.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AsanaServiceImpl implements AsanaService {
    private final  ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
    private final AsanaApiService asanaApiService;
    private final AsanaDao asanaDao;
    private final AsanaProjectsDao asanaProjectsDao;
    private final AsanaSectionDao asanaSectionDao;
    private final AsanaTasksDao asanaTasksDao;
    private final AsanaWorkspacesDao asanaWorkspacesDao;
    @Value("${app.asana.expiration-in-ms}")
    private Long ASANA_EXPIRATION_TIME_IN_MS;

    @Autowired
    public AsanaServiceImpl(AsanaApiService asanaApiService, AsanaDao asanaDao, AsanaProjectsDao asanaProjectsDao, AsanaSectionDao asanaSectionDao, AsanaTasksDao asanaTasksDao, AsanaWorkspacesDao asanaWorkspacesDao) {
        this.asanaApiService = asanaApiService;
        this.asanaDao = asanaDao;
        this.asanaProjectsDao = asanaProjectsDao;
        this.asanaSectionDao = asanaSectionDao;
        this.asanaTasksDao = asanaTasksDao;
        this.asanaWorkspacesDao = asanaWorkspacesDao;
    }

    @Override
    public List<Workspace> getAsanaWorkspaces(Optional<User> user) throws IOException {

        return this.asanaApiService.getWorkspaces(user);

    }

    @Override
    public List<AsanaProject> getAllAsanaProjects() {
        return this.asanaProjectsDao.findAll();
    }

    @Override
    public List<AsanaWorkspace> getAllAsanaWorkspaces() {
        return this.asanaWorkspacesDao.findAll();
    }


    @Override
    public void saveAsanaProject(AsanaProject asanaProjects) {
        this.asanaProjectsDao.save(asanaProjects);
    }

    @Override
    public List<Project> getAsanaProjectsByWorkspaces(Optional<User> user, String gid) throws IOException {

        return this.asanaApiService.getProjectsByWorkspace(user, gid, false);

    }

    @Override
    public List<Section> getSectionFromProject(AsanaDto dto) throws IOException {
        return this.asanaApiService.getSectionFromProject(dto);
    }

    @Override
    public Asana getAsanaAccountIdByUser(Long userId) {
        return this.asanaDao.findByUserId(userId);
    }

    @Override
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

    @Override
    public AsanaWorkspace getAsanaWorkspaceByWorkspaceGid(String gid) {
        return this.asanaWorkspacesDao.findByGid(gid);
    }

    @Override
    public List<Task> getTasksFromSection(AsanaDto asanaDto) throws IOException {
        return this.asanaApiService.getTasksFromSection(asanaDto);
    }

    @Override
    public List<Task> getTaskByWorkspace(AsanaDto dto) throws IOException {
        return this.asanaApiService.getTaskByWorkspace(dto);
    }

    @Override
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

    @Override
    public void saveAsanaWorkspace(AsanaWorkspace asanaWorkspace) {
        this.asanaWorkspacesDao.save(asanaWorkspace);
    }

    @Override
    public void saveAsanaTask(AsanaTask asanaTasks) {
        this.asanaTasksDao.save(asanaTasks);
    }

    @Override
    public Project createAsanaProject(AsanaDto dto) throws IOException {
        return this.asanaApiService.createProjectForWorkspace(dto);

    }

    @Override
    public Task getTask(AsanaDto asanaDto) throws IOException {
        return this.asanaApiService.getTask(asanaDto);
    }

    @Override
    public Project getProject(AsanaDto dto) throws IOException {
        return this.asanaApiService.getProject(dto);
    }

    @Override
    public Section createSectionForProject(AsanaDto asanaDto, String sectionName) throws IOException {
        return this.asanaApiService.createSectionForProject(asanaDto, sectionName);
    }

    @Override
    public AsanaProject getAsanaProjectByProjectGid(String projectGid) {
        return this.asanaProjectsDao.findByGid(projectGid);
    }

    @Override
    public List<com.asana.models.User> getAsanaUsers(AsanaDto asanaDto) throws IOException {
        return this.asanaApiService.getAsanaUsers(asanaDto);
    }

    @Override
    public void saveAsanaSection(AsanaSection asanaSection) {
        this.asanaSectionDao.save(asanaSection);
    }

    @Override
    public AsanaTask getAsanaTaskByTaskGid(String taskGid) {
        return this.asanaTasksDao.findByGid(taskGid);
    }

    @Override
    public AsanaSection getAsanaSectionBySectionGid(String asanaSectionGid) {
        return this.asanaSectionDao.findByGid(asanaSectionGid);
    }

    @Override
    public JsonElement addTaskToSection(AsanaDto asanaDto) throws IOException {
        return this.asanaApiService.addTaskToSection(asanaDto);
    }
}

