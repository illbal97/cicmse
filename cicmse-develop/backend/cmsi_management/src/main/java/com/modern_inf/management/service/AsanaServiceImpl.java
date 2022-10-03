package com.modern_inf.management.service;

import com.asana.models.Project;
import com.asana.models.Workspace;
import com.modern_inf.management.model.Asana;
import com.modern_inf.management.model.AsanaProjects;
import com.modern_inf.management.model.AsanaWorkspaces;
import com.modern_inf.management.model.User;
import com.modern_inf.management.repository.AsanaDao;
import com.modern_inf.management.repository.AsanaProjectsDao;
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
    private final AsanaWorkspacesDao asanaWorkspacesDao;

    private final static ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());


    @Value("${app.asana.expiration-in-ms}")
    private Long ASANA_EXPIRATION_TIME_IN_MS ;

    @Autowired
    public AsanaServiceImpl(AsanaApiService asanaApiService, AsanaDao asanaDao, AsanaProjectsDao asanaProjectsDao, AsanaWorkspacesDao asanaWorkspacesDao) {
        this.asanaApiService = asanaApiService;
        this.asanaDao = asanaDao;
        this.asanaProjectsDao = asanaProjectsDao;
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

    public List<Project> getAsanaProjectsByWorkspaces(Optional<User> user, String grid) throws IOException {

        return this.asanaApiService.getProjectsByWorkspace(user, grid, true);

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
}

