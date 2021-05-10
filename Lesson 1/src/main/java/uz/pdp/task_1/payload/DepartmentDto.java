package uz.pdp.task_1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "Name is not empty!")
    private String name;

    @NotNull(message = "Company id is not empty!")
    private Integer companyId;
}
