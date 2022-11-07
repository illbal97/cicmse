package com.modern_inf.management.model.Dto.asana;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TempAsanaProject {
    private String name;
    private String currentStatus;
    private String color;
    private String dueOn;
    private String notes;
    private String owner;
}
