package com.petreca.gwent_catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDetails {

    private String response;
    private String version;
    private String language;
}
