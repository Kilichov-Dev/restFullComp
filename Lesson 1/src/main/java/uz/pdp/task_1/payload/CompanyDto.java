package uz.pdp.task_1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    @NotNull(message = "Corp name is not empty!")
    private String corpName;

    @NotNull(message = "Street is not empty!")
    private String street;

    @NotNull(message = "Home number is not empty!")
    private String homeNumber;
}
