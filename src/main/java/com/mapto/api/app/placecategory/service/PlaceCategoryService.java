package com.mapto.api.app.placecategory.service;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.place.repository.PlaceRepository;
import com.mapto.api.app.placecategory.dto.PlaceCategoryDTO;
import com.mapto.api.app.placecategory.entity.PlaceCategory;
import com.mapto.api.app.placecategory.repository.PlaceCategoryRepository;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceCategoryService {
    private final PlaceCategoryRepository placeCategoryRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlaceCategoryDTO.Basic createPlaceCategory(UserPrincipal userPrincipal, PlaceCategoryDTO.Create placeCategory) throws CustomException {
        if(CheckUtil.isEmptyString(placeCategory.getName())) {
            throw new CustomException(StatusCode.CODE_751);
        } else if(CheckUtil.isNullObject(placeCategory.getType())) {
            throw new CustomException(StatusCode.CODE_752);
        }
        User user = userRepository.findByIdx(userPrincipal.getUser().getIdx());
        PlaceCategory placeCategoryEntity = PlaceCategory.builder()
                .user(user)
                .name(placeCategory.getName())
                .type(placeCategory.getType())
                .build();
        return placeCategoryRepository.save(placeCategoryEntity).toPlaceCategoryBasicDTO();
    }

    @Transactional(readOnly = true)
    public PlaceCategoryDTO.Simples getPlaceCategory(UserPrincipal userPrincipal) {
        User user = userRepository.findByIdx(userPrincipal.getUser().getIdx());
        PlaceCategoryDTO.Simples result = new PlaceCategoryDTO.Simples();
        List<PlaceCategoryDTO.Simple> list = new ArrayList<>();
        for(PlaceCategory placeCategory : user.getPlaceCategories()) {
            list.add(placeCategory.toPlaceCategorySimpleDTO());
        }
        result.setList(list);
        System.out.println(result);
        return result;
    }

    @Transactional
    public void deletePlaceCategory(UserPrincipal userPrincipal, PlaceCategoryDTO.Simples placeCategory) throws CustomException{
        List<Place> placeList = placeRepository.findByUser(userPrincipal.getUser());
        List<PlaceCategoryDTO.Simple> dtoList = placeCategory.getList();
        for(PlaceCategoryDTO.Simple dto : dtoList) {
            for(Place place : placeList) {
                if(place.getCategory().getIdx().equals(dto.getIdx())) {
                    throw new CustomException(StatusCode.CODE_756);
                }
            }
        }
        List<PlaceCategory> entityList = new ArrayList<>();
        for(PlaceCategoryDTO.Simple dto : placeCategory.getList()) {
            entityList.add(dto.toEntity());
        }
        placeCategoryRepository.deleteAll(entityList);
    }

}
