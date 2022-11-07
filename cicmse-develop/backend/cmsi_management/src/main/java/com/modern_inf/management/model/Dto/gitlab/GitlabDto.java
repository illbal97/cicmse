package com.modern_inf.management.model.Dto.gitlab;

import com.modern_inf.management.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitlabDto {

    private String userId;
    private User user;
}
