package com.modern_inf.management.service;
import com.asana.Client;
import com.asana.models.Workspace;
import com.modern_inf.management.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AsanaApiService {


    public List<Workspace> getWorkspaces(Optional<User> user) throws IOException {
        Client client = Client.accessToken(user.get().getAsanaPersonalAccessToken());
        return client.workspaces.getWorkspaces()
                .option("pretty", true)
                .execute();
    }
}
