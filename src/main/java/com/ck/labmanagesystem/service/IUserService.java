package com.ck.labmanagesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ck.labmanagesystem.entity.User;
import com.ck.labmanagesystem.dto.UserDTO;
import com.ck.labmanagesystem.dto.UserQueryDTO;
import com.ck.labmanagesystem.vo.PageVO;

public interface IUserService extends IService<User> {

    /**
     * 创建用户
     */
    boolean createUser(UserDTO userDTO);

    /**
     * 更新用户
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 删除用户
     */
    boolean deleteUser(Integer userId);

    /**
     * 根据用户名获取用户
     */
    User getUserByUsername(String username);

    /**
     * 分页查询用户
     */
    PageVO<User> getUserPage(UserQueryDTO queryDTO);

    /**
     * 修改密码
     */
    boolean updatePassword(Integer userId, String newPassword);
}
