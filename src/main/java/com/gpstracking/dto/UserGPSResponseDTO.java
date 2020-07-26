package com.gpstracking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGPSResponseDTO extends ResponseDTO {
    private List<UserGPSItemResponseDTO> items;

    public List<UserGPSItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<UserGPSItemResponseDTO> items) {
        this.items = items;
    }
}
