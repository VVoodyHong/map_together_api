package com.mapto.api.app.place.service;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.app.place.entity.Place;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceCategoryRepository placeCategoryRepository;
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
        // TODO
        // N:N 관계 매핑 테이블 생성 후 추가 로직
//        if(files != null && files.size() != 0) {
//            fileUploader.upload(files, "place");
//        }
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
        return placeRepository.save(place).toPlaceBasicDTO();
    }

}

