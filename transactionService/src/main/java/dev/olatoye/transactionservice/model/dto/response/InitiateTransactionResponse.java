package dev.olatoye.transactionservice.model.dto.response;

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
public class InitiateTransactionResponse {

    private String initiationTransactionReference;
    private String message = "Transaction has been initiated. Contact business team with your " +
            "initiationTransactionReference and/or paymentReference if you do not get a " +
            "confirmation email in 5 minutes";

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
