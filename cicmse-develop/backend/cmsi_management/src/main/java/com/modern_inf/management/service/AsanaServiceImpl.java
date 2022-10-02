package com.modern_inf.management.service;

import com.asana.models.Workspace;
import com.asana.resources.Workspaces;
import com.modern_inf.management.model.Asana;
import com.modern_inf.management.model.User;
import com.modern_inf.management.repository.AsanaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsanaServiceImpl implements AsanaService{
    private AsanaApiService asanaApiService;
    private AsanaDao asanaDao;

    private final static ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());


    @Value("${app.asana.expiration-in-ms}")
    private Long ASANA_EXPIRATION_TIME_IN_MS ;

    @Autowired
    public AsanaServiceImpl(AsanaApiService asanaApiService, AsanaDao asanaDao) {
        this.asanaApiService = asanaApiService;
        this.asanaDao = asanaDao;
    }

    public List<Workspace> getAsanaWorkspaces(Optional<User> user) throws IOException {

        return this.asanaApiService.getWorkspaces(user);

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
}

