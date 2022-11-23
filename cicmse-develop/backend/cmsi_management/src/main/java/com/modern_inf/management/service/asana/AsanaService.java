package com.modern_inf.management.service.asana;

import com.asana.models.Project;
import com.asana.models.Section;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.google.gson.JsonElement;
import com.modern_inf.management.model.*;
import com.modern_inf.management.model.dto.asana.AsanaDto;
import com.modern_inf.management.model.asana.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AsanaService {
    List<Workspace> getAsanaWorkspaces(Optional<User> user) throws IOException;

    List<AsanaProject> getAllAsanaProjects();

    List<AsanaWorkspace> getAllAsanaWorkspaces();

    void saveAsanaProject(AsanaProject asanaProjects);

    List<Project> getAsanaProjectsByWorkspaces(Optional<User> user, String gid) throws IOException;

    List<Section> getSectionFromProject(AsanaDto dto) throws IOException;

    Asana getAsanaAccountIdByUser(Long userId);

    void setAsanaAccountForUser(User user);

    AsanaWorkspace getAsanaWorkspaceByWorkspaceGid(String gid);

    List<Task> getTasksFromSection(AsanaDto asanaDto) throws IOException;

    List<Task> getTaskByWorkspace(AsanaDto dto) throws IOException;

    void updateAsanaTokenExpirationTime(Asana a);

    void saveAsanaWorkspace(AsanaWorkspace asanaWorkspace);

    void saveAsanaTask(AsanaTask asanaTasks);

    Project createAsanaProject(AsanaDto dto) throws IOException;

    Task getTask(AsanaDto asanaDto) throws IOException;

    Project getProject(AsanaDto dto) throws IOException;

    Section createSectionForProject(AsanaDto asanaDto, String sectionName) throws IOException;

    AsanaProject getAsanaProjectByProjectGid(String projectGid);

    List<com.asana.models.User> getAsanaUsers(AsanaDto asanaDto) throws IOException;

    void saveAsanaSection(AsanaSection asanaSection);

    AsanaTask getAsanaTaskByTaskGid(String taskGid);

    AsanaSection getAsanaSectionBySectionGid(String asanaSectionGid);

    JsonElement addTaskToSection(AsanaDto asanaDto) throws IOException;
}
