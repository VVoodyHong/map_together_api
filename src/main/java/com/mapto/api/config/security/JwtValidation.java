package com.mapto.api.config.security;

import com.mapto.api.common.model.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtValidation {
    private boolean success = false;
    private StatusCode code = StatusCode.CODE_652;
    private boolean accessToken = false;
    private boolean refreshToken = false;
}
