package com.mapto.api.app.auth.service;

import com.mapto.api.app.auth.dto.AuthDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.model.type.LoginType;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.common.util.JwtUtil;
import com.mapto.api.config.security.JwtProvider;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.config.security.JwtAuthenticationResponse;
import com.mapto.api.config.security.JwtValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtAuthenticationResponse signIn(AuthDTO.Create signInInfo) throws CustomException {
        if(signInInfo.getLoginType() == LoginType.DEFAULT) {
            if (CheckUtil.isEmptyString(signInInfo.getLoginId())) {
                throw new CustomException(StatusCode.CODE_601);
            } else if (CheckUtil.isEmptyString(signInInfo.getPassword())) {
                throw new CustomException(StatusCode.CODE_603);
            }
            User user = userRepository.findByLoginId(signInInfo.getLoginId());
            if (user == null) {
                throw new CustomException(StatusCode.CODE_605);
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                boolean isMatch = passwordEncoder.matches(signInInfo.getPassword(), user.getPassword());
                if (isMatch) {
                    return getJwtAuthenticationResponse(signInInfo, user);
                } else {
                    throw new CustomException(StatusCode.CODE_605);
                }
            }
        } else if(signInInfo.getLoginType() == LoginType.KAKAO) {
            if (CheckUtil.isEmptyString(signInInfo.getLoginId())) {
                throw new CustomException(StatusCode.CODE_601);
            }
            User user = userRepository.findByLoginId(signInInfo.getLoginId());
            if(user == null) {
                User createUser = User.builder()
                    .loginId(signInInfo.getLoginId())
                    .nickname("")
                    .userType(signInInfo.getUserType())
                    .loginType(signInInfo.getLoginType())
                    .osType(signInInfo.getOsType())
                    .appVersion(signInInfo.getAppVersion())
                    .osVersion(signInInfo.getOsVersion())
                    .deviceId(signInInfo.getDeviceId())
                    .build();
                User newUser = userRepository.save(createUser);
                String accessToken = jwtProvider.createAccessToken(String.valueOf(newUser.getIdx()));
                String refreshToken = jwtProvider.createRefreshToken(String.valueOf(newUser.getIdx()));
                return new JwtAuthenticationResponse(accessToken, refreshToken);
            } else {
                return getJwtAuthenticationResponse(signInInfo, user);
            }
        } else {
            throw new CustomException(StatusCode.CODE_606);
        }
    }

    private JwtAuthenticationResponse getJwtAuthenticationResponse(AuthDTO.Create signInInfo, User user) {
        Long userIdx = user.getIdx();
        user.setUserType(signInInfo.getUserType());
        user.setLoginType(signInInfo.getLoginType());
        user.setOsType(signInInfo.getOsType());
        user.setAppVersion(signInInfo.getAppVersion());
        user.setOsVersion(signInInfo.getOsVersion());
        user.setDeviceId(signInInfo.getDeviceId());
        userRepository.save(user);
        String accessToken = jwtProvider.createAccessToken(String.valueOf(userIdx));
        String refreshToken = jwtProvider.createRefreshToken(String.valueOf(userIdx));
        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public JwtAuthenticationResponse getNewAccessToken(HttpServletRequest request) throws CustomException {
        String jwt = JwtUtil.getJwtFromRequest(request);
        JwtValidation jwtValidation = jwtProvider.validateToken(jwt);
        if(jwt == null) {
            throw new CustomException(StatusCode.CODE_652);
        } else if(jwtValidation.isSuccess()) {
            if(jwtValidation.isRefreshToken()) {
                Long userIdx = jwtProvider.getUserIdxFromJwt(jwt);
                String newAccessToken = jwtProvider.createAccessToken(String.valueOf(userIdx));
                return new JwtAuthenticationResponse(newAccessToken);
            } else {
                throw new CustomException(StatusCode.CODE_654);
            }
        } else {
            throw new CustomException(StatusCode.CODE_652);
        }
    }

    @Transactional
    public JwtAuthenticationResponse refreshJwt(HttpServletRequest request) throws CustomException {
        String jwt = JwtUtil.getJwtFromRequest(request);
        JwtValidation jwtValidation = jwtProvider.validateToken(jwt);
        if(jwt == null) {
            throw new CustomException(StatusCode.CODE_652);
        } else if(jwtValidation.isSuccess()) {
            Long userIdx = jwtProvider.getUserIdxFromJwt(jwt);
            String newAccessToken = jwtProvider.createAccessToken(String.valueOf(userIdx));
            String newRefreshToken = jwtProvider.createRefreshToken(String.valueOf(userIdx));
            return new JwtAuthenticationResponse(newAccessToken, newRefreshToken);
        } else {
            throw new CustomException(StatusCode.CODE_652);
        }
    }
}
