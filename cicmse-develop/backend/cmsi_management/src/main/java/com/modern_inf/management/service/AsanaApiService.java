package com.modern_inf.management.service;
import com.asana.Client;
import com.asana.models.Project;
import com.asana.models.Workspace;
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

    private Client getClient(Optional<User> user) {
        return Client.accessToken(user.get().getAsanaPersonalAccessToken());
    }
}
