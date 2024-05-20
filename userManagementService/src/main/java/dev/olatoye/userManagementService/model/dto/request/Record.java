package dev.olatoye.userManagementService.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    private String messageId;
    private String receiptHandle;
    private String body;
    private Map<String, String> attributes = new HashMap<>();
    private Map<String, String> messageAttributes = new HashMap<>();
    private String md5OfBody;
    private String eventSource;
    private String eventSourceARN;
    private String awsRegion;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
