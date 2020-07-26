package com.gpstracking.dto;

import com.gpstracking.gpx.GpxType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GPSResponseDTO extends ResponseDTO {
    private GpxType gpsInfo;

    public GpxType getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(GpxType gpsInfo) {
        this.gpsInfo = gpsInfo;
    }
}
