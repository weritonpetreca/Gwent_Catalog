package com.petreca.Gwent_Catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDetails {

    private String response;
    private String version;
    private String language;
}
