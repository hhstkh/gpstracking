package com.gpstracking.service.impl;

import com.gpstracking.domain.GPSEntity;
import com.gpstracking.dto.GPSResponseDTO;
import com.gpstracking.dto.ResponseDTO;
import com.gpstracking.dto.UserGPSItemResponseDTO;
import com.gpstracking.dto.UserGPSResponseDTO;
import com.gpstracking.gpx.GpxType;
import com.gpstracking.repository.GPSRepository;
import com.gpstracking.service.GPSService;
import com.gpstracking.utils.DateTimeUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPSServiceImpl implements GPSService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GPSServiceImpl.class);

    @Value("${gpx.folder.path}")
    private String mGPXFolderPath;

    @Autowired
    private GPSRepository mGPSRepository;

    @Override
    public UserGPSResponseDTO viewGPSByUserId(Integer userId) {
        LOGGER.info("Start get GPSEntity file, user id: {}", userId);
        UserGPSResponseDTO gpsDTO = new UserGPSResponseDTO();
        gpsDTO.setHttpStatus(HttpStatus.OK);
        try {
            List<GPSEntity> gpsEntityList = mGPSRepository.findByUserId(userId);
            if (gpsEntityList == null) {
                LOGGER.info("No gps file");
                return gpsDTO;
            }

            List<UserGPSItemResponseDTO> itemResponseDTOList = new ArrayList<>();
            for (GPSEntity gpsEntity : gpsEntityList) {
                UserGPSItemResponseDTO itemResponseDTO = new UserGPSItemResponseDTO();
                itemResponseDTO.setGpsId(gpsEntity.getId());
                itemResponseDTO.setUserId(gpsEntity.getUserId());
                itemResponseDTO.setCreatedDate(DateTimeUtil.convertDateToString(gpsEntity.getCreatedDate(), DateTimeUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));

                itemResponseDTOList.add(itemResponseDTO);
            }

            gpsDTO.setItems(itemResponseDTOList);
            LOGGER.info("End get GPSEntity file");
            return gpsDTO;
        } catch (Exception e) {
            LOGGER.error("Get GPSEntity file has error", e);
            gpsDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return gpsDTO;
        }
    }

    @Override
    public GPSResponseDTO viewUserGPSFile(Integer gpsId, Integer userId) {
        LOGGER.info("Start view latest GPSEntity file, gps id {} user id: {}", gpsId, userId);
        GPSResponseDTO gpsDTO = new GPSResponseDTO();
        gpsDTO.setHttpStatus(HttpStatus.OK);
        try {
            GPSEntity gpsEntity = mGPSRepository.findByIdAndUserId(gpsId, userId);
            if (gpsEntity == null) {
                LOGGER.info("No gps file");
                return gpsDTO;
            }
            GpxType gpxType = parseGPXFile(gpsEntity.getGpsFileName());
            gpsDTO.setGpsInfo(gpxType);
            LOGGER.info("End view latest GPSEntity file");
            return gpsDTO;
        } catch (Exception e) {
            LOGGER.error("End view latest GPSEntity file", e);
            gpsDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return gpsDTO;
        }
    }

    @Override
    public void saveGPSFile(String originalFilename, Integer userId) {
        LOGGER.info("Start save gpx upload file, file name {}, user {}", originalFilename, userId);
        GPSEntity gpsEntity = new GPSEntity();
        gpsEntity.setGpsFileName(originalFilename);
        gpsEntity.setUserId(userId);
        mGPSRepository.save(gpsEntity);
        LOGGER.info("End save gpx upload file.");
    }

    @Override
    public ResponseDTO uploadGPXFile(MultipartFile file, Integer userId) {
        LOGGER.info("Start upload gpx file {}", file.getName());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setHttpStatus(HttpStatus.OK);
        try {
            File gpxFolder = new File(mGPXFolderPath);
            if (!gpxFolder.exists()) {
                gpxFolder.mkdir();
            }

            Long currentTime = Instant.now().toEpochMilli();
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = FilenameUtils.getBaseName(file.getOriginalFilename());
            String gpxFilePath = gpxFolder + File.separator + fileName + "_" + currentTime + "." + fileExtension;
            Files.write(Paths.get(gpxFilePath), file.getBytes());
            this.saveGPSFile(file.getOriginalFilename(), userId);
        } catch (Exception e) {
            LOGGER.error("upload gpx file has error", e);
            responseDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("End upload gpx file");
        return responseDTO;
    }

    private GpxType parseGPXFile(String gpxFileName) throws Exception {
        String gpxFilePath = mGPXFolderPath + File.separator + gpxFileName;
        JAXBContext context = JAXBContext.newInstance(GpxType.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Source source = new StreamSource(new FileInputStream(gpxFilePath));
        JAXBElement<GpxType> root = unmarshaller.unmarshal(source, GpxType.class);
        return root.getValue();
    }
}
