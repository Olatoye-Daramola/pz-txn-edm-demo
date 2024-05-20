package dev.olatoye.walletservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitiateTransactionRequest {

    private String sessionId;
    private String paymentReference;
    private String sourceAccountName;
    private String sourceAccountNumber;
    private String paymentChannel;
    private BigDecimal amount;
    private String destinationUniqueId;
    private String initiationTransactionReference;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
