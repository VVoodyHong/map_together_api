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
        userRepository.save(user).toUserSimpleDTO();
    }

    @Transactional
    public UserDTO.Simple updateUser(Long userIdx, UserDTO.Update userInfo, MultipartFile file) throws CustomException, IOException {
        User user = userRepository.findByIdx(userIdx);
        user.setNickname(userInfo.getNickname());
        user.setName(userInfo.getName());
        user.setIntroduce(userInfo.getIntroduce());
        if(file != null) {
            FileDTO.Simple profileImgFile = fileUploader.upload(file, "image/profile");
            user.setProfileImg(profileImgFile.getUrl());
        }
        return userRepository.save(user).toUserSimpleDTO();
    }

    @Transactional(readOnly = true)
    public UserDTO.Simple readUser(UserPrincipal userPrincipal) throws CustomException {
        Long userIdx = userPrincipal.getUser().getIdx();
        User user = userRepository.findByIdx(userIdx);
        return user.toUserSimpleDTO();
    }

    @Transactional(readOnly = true)
    public StatusCode checkExistUser(String value, ExistType type) throws CustomException {
        if(type == ExistType.LOGINID) {
            if(CheckUtil.isEmptyString(value)) {
                throw new CustomException(StatusCode.CODE_601);
            }
            User user = userRepository.findByLoginId(value);
            if(user != null) {
                return StatusCode.CODE_607;
            } else {
                return StatusCode.CODE_200;
            }
        } else if(type == ExistType.NICKNAME) {
            if(CheckUtil.isEmptyString(value)) {
                throw new CustomException(StatusCode.CODE_602);
            }
            User user = userRepository.findByNickname(value);
            if(user != null) {
                return StatusCode.CODE_608;
            } else {
                return StatusCode.CODE_200;
            }
        } else {
            return StatusCode.CODE_609;
        }
    }
}
