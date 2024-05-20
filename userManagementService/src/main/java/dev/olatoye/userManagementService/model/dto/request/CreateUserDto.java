package dev.olatoye.userManagementService.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String institution;
    private String country;
    private String uniqueId;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
