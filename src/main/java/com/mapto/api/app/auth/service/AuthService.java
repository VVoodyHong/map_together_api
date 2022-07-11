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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JavaMailSender javaMailSender;

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

    @Transactional
    public AuthDTO.Email authEmail(AuthDTO.Email authEmailDTO) throws MessagingException {
        String authCode = createAuthCode();
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, authEmailDTO.getEmail());
        message.setSubject("맵투게더 회원가입 이메일 인증입니다.");
        String msg = "";
        msg+= "<div>";
        msg+= "<h2>안녕하세요. 맵투게더입니다.</h2>";
        msg+= "<p>회원가입 인증번호는 다음과 같습니다.<p>";
        msg+= "<p>" + authCode + "<p>";
        msg+= "<p>감사합니다.<p>";
        msg+= "</div>";
        message.setContent(msg, "text/html; charset=utf-8");
        javaMailSender.send(message);
        authEmailDTO.setCode(authCode);
        authEmailDTO.setCode(authCode);
        return authEmailDTO;
    }

    public static String createAuthCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
            switch (index) {
                case 0:
                    code.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    code.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return code.toString();
    }
}
