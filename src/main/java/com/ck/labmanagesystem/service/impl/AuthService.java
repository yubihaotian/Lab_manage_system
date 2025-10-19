package com.ck.labmanagesystem.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ck.labmanagesystem.dto.LoginDTO;
import com.ck.labmanagesystem.dto.RegisterDTO;
import com.ck.labmanagesystem.entity.User;
import com.ck.labmanagesystem.mapper.UserMapper;
import com.ck.labmanagesystem.service.IAuthService;
import com.ck.labmanagesystem.utils.JwtUtil;
import com.ck.labmanagesystem.vo.ResultVO;
import com.ck.labmanagesystem.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResultVO<Map<String, Object>> login(LoginDTO loginRequest) {
        User user = userMapper.selectByUsername(loginRequest.getUsername());
        if (user == null || !BCrypt.verifyer().verify(loginRequest.getPassword().toCharArray(), user.getPassword()).verified) {
            return ResultVO.error("用户名或密码错误");
        }

        // 生成 Token
        String token = jwtUtil.generateToken(user.getUsername());

        // 构造返回信息
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        userInfoVO.setUserId(user.getUserId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", userInfoVO);

        return ResultVO.success(result);
    }

    @Override
    public boolean register(RegisterDTO registerRequest) {
        if (userMapper.selectByUsername(registerRequest.getUsername()) != null) {
            throw new RuntimeException("用户名已被占用");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setRealName(registerRequest.getRealName());
        user.setPassword(BCrypt.withDefaults().hashToString(12, registerRequest.getPassword().toCharArray()));
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setUserType(registerRequest.getUserType());
        user.setStatus(1); // 正常状态

        return userMapper.insert(user) > 0;
    }
}
