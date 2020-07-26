package com.gpstracking.controller;

import com.gpstracking.dto.GPSResponseDTO;
import com.gpstracking.dto.ResponseDTO;
import com.gpstracking.dto.UserGPSResponseDTO;
import com.gpstracking.service.GPSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GPSTrackingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GPSTrackingController.class);

    @Autowired
    private GPSService mGPSService;

    @GetMapping("/gps/user/{userId}")
    public ResponseEntity<ResponseDTO> viewGPSByUserId(@PathVariable(name = "userId") Integer userId) {
        LOGGER.info("Start viewGPSByUserId, user id: {}", userId);
        UserGPSResponseDTO responseDTO = mGPSService.viewGPSByUserId(userId);
        return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
    }

    @GetMapping("/gps/{gpsId}/user/{userId}")
    public ResponseEntity<ResponseDTO> viewUserGPSFile(@PathVariable(name = "gpsId") Integer gpsId,
                                                       @PathVariable(name = "userId") Integer userId) {
        LOGGER.info("viewUserGPSFile gps id {}, user id: {}", gpsId, userId);
        GPSResponseDTO responseDTO = mGPSService.viewUserGPSFile(gpsId, userId);
        return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
    }

    @PostMapping("gps/upload-file")
    public ResponseEntity<ResponseDTO> uploadGPXFile(@RequestParam(name = "file") MultipartFile file,
                                                     @RequestParam(name = "userId") Integer userId) {
        LOGGER.info("Upload gpx file, user id {}", userId);
        ResponseDTO responseDTO = mGPSService.uploadGPXFile(file, userId);
        return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
    }
}
