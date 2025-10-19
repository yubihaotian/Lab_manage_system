package com.ck.labmanagesystem.controller;

import com.ck.labmanagesystem.dto.LoginDTO;
import com.ck.labmanagesystem.dto.RegisterDTO;
import com.ck.labmanagesystem.entity.User;
import com.ck.labmanagesystem.service.IAuthService;
import com.ck.labmanagesystem.vo.ResultVO;
import com.ck.labmanagesystem.vo.UserInfoVO;
import com.ck.labmanagesystem.mapper.UserMapper;
import com.ck.labmanagesystem.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "认证中心", description = "登录、注册接口")
public class AuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResultVO<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ResultVO<Void> register(@Valid @RequestBody RegisterDTO registerRequest) {
        boolean success = authService.register(registerRequest);
        return success ? ResultVO.success() : ResultVO.error("注册失败");
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息")
    public ResultVO<UserInfoVO> getCurrentUserInfo(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                // 先提取用户名，再用于验证 token
                String username = jwtUtil.extractUsername(token);
                if (jwtUtil.validateToken(token, username)) {  // 修改为两个参数
                    User user = userMapper.selectByUsername(username);
                    if (user != null) {
                        UserInfoVO userInfoVO = new UserInfoVO();
                        BeanUtils.copyProperties(user, userInfoVO);
                        userInfoVO.setUserId(user.getUserId());
                        return ResultVO.success(userInfoVO);
                    }
                }
            }
            return ResultVO.error("无效的认证信息");
        } catch (Exception e) {
            return ResultVO.error("获取用户信息失败：" + e.getMessage());
        }
    }

}
