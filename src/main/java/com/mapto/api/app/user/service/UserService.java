package com.mapto.api.app.user.service;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.file.FileUploader;
import com.mapto.api.common.model.type.ExistType;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileUploader fileUploader;

    @Transactional
    public void createUser(UserDTO.Create userInfo) throws CustomException {
        if(CheckUtil.isEmptyString(userInfo.getLoginId())) {
            throw new CustomException(StatusCode.CODE_601);
        } else if(CheckUtil.isEmptyString(userInfo.getPassword())) {
            throw new CustomException(StatusCode.CODE_603);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .loginId(userInfo.getLoginId())
                .nickname("")
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .build();
        userRepository.save(user).toUserBasicDTO();
    }

    @Transactional
    public UserDTO.Basic updateUser(Long userIdx, UserDTO.Update userInfo, MultipartFile file) throws CustomException, IOException {
        User user = userRepository.findByIdx(userIdx);
        user.setNickname(userInfo.getNickname());
        if(!CheckUtil.isNullObject(userInfo.getName())) user.setName(userInfo.getName());
        if(!CheckUtil.isNullObject(userInfo.getIntroduce())) user.setIntroduce(userInfo.getIntroduce());
        if(!CheckUtil.isNullObject(userInfo.getLat())) user.setLat(userInfo.getLat());
        if(!CheckUtil.isNullObject(userInfo.getLng())) user.setLng(userInfo.getLng());
        if(!CheckUtil.isNullObject(userInfo.getZoom())) user.setZoom(userInfo.getZoom());
        // set default image
        if(!CheckUtil.isNullObject(userInfo.getProfileImg()) && userInfo.getProfileImg().equals("default")) {
            user.setProfileImg(null);
        }
        // set image
        if(!CheckUtil.isNullObject(file)) {
            if(!CheckUtil.isNullObject(userInfo.getIntroduce())) {
                boolean deleted = fileUploader.delete(user.getProfileImg());
                if(deleted) {
                    FileDTO.Basic profileImgFile = fileUploader.upload(file, "profile");
                    user.setProfileImg(profileImgFile.getUrl());
                } else {
                    throw new CustomException(StatusCode.CODE_703);
                }
            } else {
                FileDTO.Basic profileImgFile = fileUploader.upload(file, "profile");
                user.setProfileImg(profileImgFile.getUrl());
            }
        }
        return userRepository.save(user).toUserBasicDTO();
    }

    // find me
    @Transactional(readOnly = true)
    public UserDTO.Basic readUser(UserPrincipal userPrincipal) throws CustomException {
        Long userIdx = userPrincipal.getUser().getIdx();
        User user = userRepository.findByIdx(userIdx);
        return user.toUserBasicDTO();
    }

    // find other user
    @Transactional(readOnly = true)
    public UserDTO.Basic readUser(Long userIdx) throws CustomException {
        if(CheckUtil.isNullObject(userIdx)) {
            throw new CustomException(StatusCode.CODE_701);
        } else {
            User user = userRepository.findByIdx(userIdx);
            return user.toUserBasicDTO();
        }
    }

    @Transactional(readOnly = true)
    public UserDTO.Simples searchUser(Long userIdx, UserDTO.Search search) throws CustomException {
        if(CheckUtil.isEmptyString(search.getKeyword())) {
            throw new CustomException(StatusCode.CODE_704);
        } else {
            Pageable pageable = search.getRequestPage().of();
            Page<UserDTO.Simple> userPage = userRepository.findByKeyword(userIdx, pageable, search.getKeyword());
            UserDTO.Simples result = new UserDTO.Simples();
            result.setList(userPage.getContent());
            result.setTotalCount(userPage.getTotalElements());
            result.setLast(userPage.isLast());
            return result;
        }
    }

    @Transactional(readOnly = true)
    public StatusCode checkExistUser(String value, ExistType type) throws CustomException {
        if(type == ExistType.LOGINID) {
            if(CheckUtil.isEmptyString(value)) {
                throw new CustomException(StatusCode.CODE_601);
            }
            User user = userRepository.findByLoginId(value);
            if(user != null) {
                throw new CustomException(StatusCode.CODE_607);
            } else {
                return StatusCode.CODE_200;
            }
        } else if(type == ExistType.NICKNAME) {
            if(CheckUtil.isEmptyString(value)) {
                throw new CustomException(StatusCode.CODE_602);
            }
            User user = userRepository.findByNickname(value);
            if(user != null) {
                throw new CustomException(StatusCode.CODE_608);
            } else {
                return StatusCode.CODE_200;
            }
        } else {
            throw new CustomException(StatusCode.CODE_609);
        }
    }
}
