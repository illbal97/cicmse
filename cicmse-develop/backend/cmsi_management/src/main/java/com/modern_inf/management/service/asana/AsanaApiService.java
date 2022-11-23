package com.modern_inf.management.service.asana;

import com.asana.Client;
import com.asana.models.Project;
import com.asana.models.Section;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.google.gson.JsonElement;
import com.modern_inf.management.model.dto.asana.AsanaDto;
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

    public List<Project> getProjectsByWorkspace(Optional<User> user, String workspaceGid, boolean archived) throws IOException {
        client = getClient(user);
        return client.projects.getProjectsForWorkspace(workspaceGid, archived)
                .option("pretty", true)
                .execute();
    }

    public List<com.asana.models.User> getAsanaUsers(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.users.getUsersForWorkspace(dto.getWorkspaceGid())
                .option("pretty", true)
                .execute();
    }

    public Project createProjectForWorkspace(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.projects.createProjectForWorkspace(dto.getWorkspaceGid())
                .data("name", dto.getAsanaProject().getName())
                .data("color", dto.getAsanaProject().getColor())
                .data("due_on", dto.getAsanaProject().getDueOn())
                .data("notes", dto.getAsanaProject().getNotes())
                .data("owner", dto.getAsanaProject().getOwner())
                .option("pretty", true)
                .execute();

    }

    public List<Task> getTasksFromSection(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.tasks.getTasksForSection(dto.getSectionGid())
                .option("pretty", true)
                .execute();

    }

    public List<Section> getSectionFromProject(AsanaDto asanaDto) throws IOException {
        client = getClient(Optional.ofNullable(asanaDto.getUser()));
        return client.sections.getSectionsForProject(asanaDto.getProjectGid())
                .option("pretty", true)
                .execute();

    }

    public Task getTask(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.tasks.getTask(dto.getTaskGid())
                .option("pretty", true)
                .execute();

    }

    public List<Task> getTaskByWorkspace(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));

        return  client.tasks.getTasks(null, null, null, null, dto.getProjectGid(), null)
                .option("pretty", true)
                .execute();

    }

    public Project getProject(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.projects.getProject(dto.getProjectGid())
                .option("pretty", true)
                .execute();
    }

    public Section createSectionForProject(AsanaDto dto, String sectionName) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.sections.createSectionForProject(dto.getProjectGid())
                .option("pretty", true)
                .data("name", sectionName)
                .execute();

    }

    public JsonElement addTaskToSection(AsanaDto dto) throws IOException {
        client = getClient(Optional.ofNullable(dto.getUser()));
        return client.sections.addTaskForSection(dto.getSectionGid())
                .data("task", dto.getTaskGid())
                .option("pretty", true)
                .execute();
    }

    private Client getClient(Optional<User> user) {
        return Client.accessToken(user.get().getAsanaPersonalAccessToken());
    }
}
