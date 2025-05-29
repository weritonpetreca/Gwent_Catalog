package com.petreca.Gwent_Catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestInfo {

    private String message;
    private int status;
    @JsonProperty("REQUEST")
    private RequestDetails REQUEST;
}
