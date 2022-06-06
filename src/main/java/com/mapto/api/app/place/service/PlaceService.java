package com.mapto.api.app.place.service;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.app.file.entity.File;
import com.mapto.api.app.file.repository.FileRepository;
import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.place.entity.PlaceFile;
import com.mapto.api.app.place.repository.PlaceFileRepository;
import com.mapto.api.app.place.repository.PlaceRepository;
import com.mapto.api.app.placecategory.entity.PlaceCategory;
import com.mapto.api.app.placecategory.repository.PlaceCategoryRepository;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.file.FileUploader;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceCategoryRepository placeCategoryRepository;
    private final PlaceFileRepository placeFileRepository;
    private final FileRepository fileRepository;
    private final FileUploader fileUploader;

    @Transactional
    public PlaceDTO.Basic createPlace(UserPrincipal userPrincipal, PlaceDTO.Create placeInfo, List<MultipartFile> files) throws CustomException, IOException {
        if(CheckUtil.isEmptyString(placeInfo.getName())) {
            throw new CustomException(StatusCode.CODE_753);
        } else if(CheckUtil.isEmptyString(placeInfo.getAddress())) {
            throw new CustomException(StatusCode.CODE_754);
        } else if(placeInfo.getCategoryIdx() == -1) {
            throw new CustomException(StatusCode.CODE_755);
        }

        User user = userRepository.findByIdx(userPrincipal.getUser().getIdx());
        PlaceCategory placeCategory = placeCategoryRepository.findByIdx(placeInfo.getCategoryIdx());
        Place place = Place.builder()
                .user(user)
                .category(placeCategory)
                .name(placeInfo.getName())
                .address(placeInfo.getAddress())
                .description(placeInfo.getDescription())
                .lat(placeInfo.getLat())
                .lng(placeInfo.getLng())
                .build();
        Place placeEntity = placeRepository.save(place);

        if(files != null && files.size() != 0) {
            List<FileDTO.Basic> fileList = fileUploader.upload(files, "place");
            List<File> fileEntities = new ArrayList<>();
            List<PlaceFile> placeFileEntities = new ArrayList<>();
            for (FileDTO.Basic basic : fileList) {
                File file = File.builder()
                        .name(basic.getName())
                        .type(basic.getType())
                        .url(basic.getUrl())
                        .build();
                PlaceFile placeFile = PlaceFile.builder()
                        .place(placeEntity)
                        .file(file)
                        .build();
                fileEntities.add(file);
                placeFileEntities.add(placeFile);
            }
            fileRepository.saveAll(fileEntities);
            placeFileRepository.saveAll(placeFileEntities);
        }
        return placeEntity.toPlaceBasicDTO();
    }
}

