package com.ck.labmanagesystem.service;

import com.ck.labmanagesystem.dto.LoginDTO;
import com.ck.labmanagesystem.dto.RegisterDTO;
import com.ck.labmanagesystem.vo.ResultVO;

import java.util.Map;

public interface IAuthService {
    ResultVO<Map<String, Object>> login(LoginDTO loginRequest);

    boolean register(RegisterDTO registerRequest);
}
