package com.mapto.api.app.placereply.service;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.place.repository.PlaceRepository;
import com.mapto.api.app.placereply.dto.PlaceReplyDTO;
import com.mapto.api.app.placereply.entity.PlaceReply;
import com.mapto.api.app.placereply.repository.PlaceReplyRepository;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.RequestPage;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceReplyService {
    private final PlaceReplyRepository placeReplyRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceReplyDTO.Simple createPlaceReply(UserPrincipal userPrincipal, PlaceReplyDTO.Create placeReply) throws CustomException {
        if(CheckUtil.isEmptyString(placeReply.getReply())) {
            throw new CustomException(StatusCode.CODE_758);
        } else if(CheckUtil.isNullObject(placeReply.getPlaceIdx())) {
            throw new CustomException(StatusCode.CODE_759);
        }
        User user = userRepository.findByIdx(userPrincipal.getUser().getIdx());
        Place place = placeRepository.findByIdx(placeReply.getPlaceIdx());
        PlaceReply placeReplyEntity = PlaceReply.builder()
                .reply(placeReply.getReply())
                .place(place)
                .user(user)
                .build();
        return placeReplyRepository.save(placeReplyEntity).toPlaceReplySimpleDTO();
    }

    @Transactional(readOnly = true)
    public PlaceReplyDTO.Simples getPlaceReply(RequestPage requestPage, Long placeIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeIdx)) {
            throw new CustomException(StatusCode.CODE_759);
        }
        Pageable pageable = requestPage.of();
        Page<PlaceReplyDTO.Simple> placeReplyPage = placeReplyRepository.findByPlaceIdx(pageable, placeIdx);
        PlaceReplyDTO.Simples result = new PlaceReplyDTO.Simples();
        result.setList(placeReplyPage.getContent());
        result.setTotalCount(placeReplyPage.getTotalElements());
        result.setLast(placeReplyPage.isLast());
        return result;
    }

    @Transactional
    public void deletePlaceReply(Long placeReplyIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeReplyIdx)) {
            throw new CustomException(StatusCode.CODE_760);
        }
        placeReplyRepository.deleteByIdx(placeReplyIdx);
    }

}
