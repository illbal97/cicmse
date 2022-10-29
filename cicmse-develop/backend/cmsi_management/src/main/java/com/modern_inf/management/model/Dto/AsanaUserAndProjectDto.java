package com.modern_inf.management.model.Dto;

import com.modern_inf.management.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AsanaUserAndProjectDto {
    private User user;
    private String projectGid;
}
