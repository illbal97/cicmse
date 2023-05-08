package com.modern_inf.management.service.asana;

import com.asana.models.Project;
import com.asana.models.Section;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.google.gson.JsonElement;
import com.modern_inf.management.model.*;
import com.modern_inf.management.model.dto.asana.AsanaDto;
import com.modern_inf.management.model.asana.*;

import java.util.List;
import java.util.Optional;

public interface AsanaService {
    List<Workspace> getAsanaWorkspaces(Optional<User> user) throws Exception;

    List<AsanaProject> getAllAsanaProjects();

    List<AsanaWorkspace> getAllAsanaWorkspaces();

    void saveAsanaProject(AsanaProject asanaProjects);

    List<Project> getAsanaProjectsByWorkspaces(Optional<User> user, String gid) throws Exception;

    List<Section> getSectionFromProject(AsanaDto dto) throws Exception;

    Asana getAsanaAccountIdByUser(Long userId);

    void setAsanaAccountForUser(User user);

    AsanaWorkspace getAsanaWorkspaceByWorkspaceGid(String gid);

    List<Task> getTasksFromSection(AsanaDto asanaDto) throws Exception;

    List<Task> getTaskByWorkspace(AsanaDto dto) throws Exception;

    void updateCacheExpirationTime(Asana a);

    void saveAsanaWorkspace(AsanaWorkspace asanaWorkspace);

    void saveAsanaTask(AsanaTask asanaTasks);

    Project createAsanaProject(AsanaDto dto) throws Exception;

    Task getTask(AsanaDto asanaDto) throws Exception;

    Project getProject(AsanaDto dto) throws Exception;

    Section createSectionForProject(AsanaDto asanaDto, String sectionName) throws Exception;

    AsanaProject getAsanaProjectByProjectGid(String projectGid);

    List<com.asana.models.User> getAsanaUsers(AsanaDto asanaDto) throws Exception;

    void saveAsanaSection(AsanaSection asanaSection);

    AsanaTask getAsanaTaskByTaskGid(String taskGid);

    AsanaSection getAsanaSectionBySectionGid(String asanaSectionGid);

    JsonElement addTaskToSection(AsanaDto asanaDto) throws Exception;
}
