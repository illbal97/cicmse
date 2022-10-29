package com.modern_inf.management.service;
import com.asana.Client;
import com.asana.models.Project;
import com.asana.models.Section;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.google.gson.JsonElement;
import com.modern_inf.management.model.Dto.*;
import com.modern_inf.management.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AsanaApiService {

    public Client client;
    public List<Workspace> getWorkspaces(Optional<User> user) throws IOException {
        client = getClient(user);
        return client.workspaces.getWorkspaces()
                .option("pretty", true)
                .execute();
    }

    public List<Project> getProjectsByWorkspace(Optional<User> user, String workspaceGid, boolean archived ) throws IOException {
        client = getClient(user);
        return client.projects.getProjectsForWorkspace(workspaceGid, archived)
                .option("pretty", true)
                .execute();
    }

    public List<com.asana.models.User> getAsanaUsers(UserAndWorkspaceGidDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.users.getUsersForWorkspace(dto.getWorkspaceGid())
                .option("pretty", true)
                .execute();
    }

    public Project createProjectForWorkspace(AsanaProjectDto asanaProjectDto) throws IOException {
        client = getClient(Optional.ofNullable(asanaProjectDto.getUser()));
        return client.projects.createProjectForWorkspace(asanaProjectDto.getWorkspaceGid())
                .data("name", asanaProjectDto.getAsanaProject().getName())
                .data("color", asanaProjectDto.getAsanaProject().getColor())
                .data("due_on",asanaProjectDto.getAsanaProject().getDueOn())
                .data("notes", asanaProjectDto.getAsanaProject().getNotes())
                .data("owner", asanaProjectDto.getAsanaProject().getOwner())
                .option("pretty", true)
                .execute();

    }

    public List<Task> getTasksFromSection(AsanaProjectTaskSectionDto asanaProjectTaskSectionDto) throws IOException {
        client = getClient(Optional.ofNullable(asanaProjectTaskSectionDto.getUser()));
        return client.tasks.getTasksForSection(asanaProjectTaskSectionDto.getSectionGid())
                .option("pretty", true)
                .execute();

    }

    public List<Section> getSectionFromProject(AsanaProjectTasksDto asanaProjectDto) throws IOException {
        client = getClient(Optional.ofNullable(asanaProjectDto.getUser()));
        return client.sections.getSectionsForProject(asanaProjectDto.getProjectGid() )
                .option("pretty", true)
                .execute();

    }

    public Section createSectionForProject(AsanaUserAndProjectDto asanaUserAndProjectDto, String sectionName) throws IOException {
        client = getClient(Optional.ofNullable(asanaUserAndProjectDto.getUser()));
        return client.sections.createSectionForProject(asanaUserAndProjectDto.getProjectGid() )
                .option("pretty", true)
                .data("name", sectionName)
                .execute();

    }

    public JsonElement addTaskToSection(AsanaTaskAndSectionDto asanaTaskAndSectionDto) throws IOException {
        client = getClient(Optional.ofNullable(asanaTaskAndSectionDto.getUser()));
        return client.sections.addTaskForSection(asanaTaskAndSectionDto.getAsanaSectionGid() )
                .data("task", asanaTaskAndSectionDto.getAsanaTaskGid())
                .option("pretty", true)
                .execute();
    }

    private Client getClient(Optional<User> user) {
        return Client.accessToken(user.get().getAsanaPersonalAccessToken());
    }
}
