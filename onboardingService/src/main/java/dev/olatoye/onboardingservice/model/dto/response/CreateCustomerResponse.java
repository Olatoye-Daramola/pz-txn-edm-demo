package dev.olatoye.onboardingservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerResponse {

    private String uniqueId;
    private String customer = "Onboarding in progress. Contact business team if you" +
            " do not receive confirmation email in 5 minutes";

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
