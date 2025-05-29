package com.petreca.Gwent_Catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GwentApiResponse {

    private RequestInfo request;
    private Map<String, CardDTO> response;
}
