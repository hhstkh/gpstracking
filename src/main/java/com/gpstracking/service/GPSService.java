package com.gpstracking.service;

import com.gpstracking.dto.GPSResponseDTO;
import com.gpstracking.dto.ResponseDTO;
import com.gpstracking.dto.UserGPSResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface GPSService {
    UserGPSResponseDTO viewGPSByUserId(Integer userId);
    GPSResponseDTO viewUserGPSFile(Integer gpsId, Integer userId);

    void saveGPSFile(String originalFilename, Integer userId);

    ResponseDTO uploadGPXFile(MultipartFile file, Integer userId);
}
