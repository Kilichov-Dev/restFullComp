package uz.pdp.task_1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull(message = "Name should not be empty!")
    private String name;

    @NotNull(message = "Phone number should not be empty!")
    private String phoneNumber;

    @NotNull(message = "Street should not be empty!")
    private String street;

    @NotNull(message = " Home number not be empty!")
    private String homeNumber;

    @NotNull(message = "Department id should not be empty!")
    private Integer department;
}
