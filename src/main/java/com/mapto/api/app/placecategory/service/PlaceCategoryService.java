package com.mapto.api.app.placecategory.service;

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
    private final UserRepository userRepository;

    @Transactional
    public void createPlaceCategory(UserPrincipal userPrincipal, PlaceCategoryDTO.Create placeCategoryInfo) throws CustomException {
        if(CheckUtil.isEmptyString(placeCategoryInfo.getName())) {
            throw new CustomException(StatusCode.CODE_751);
        } else if(CheckUtil.isNullObject(placeCategoryInfo.getType())) {
            throw new CustomException(StatusCode.CODE_752);
        }

        PlaceCategory placeCategory = PlaceCategory.builder()
                .user(userPrincipal.getUser())
                .name(placeCategoryInfo.getName())
                .type(placeCategoryInfo.getType())
                .build();
        placeCategoryRepository.save(placeCategory);
    }

    @Transactional(readOnly = true)
    public PlaceCategoryDTO.Simples getPlaceCategories(UserPrincipal userPrincipal) {
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

}
