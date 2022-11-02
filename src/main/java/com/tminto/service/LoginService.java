package com.tminto.service;

import com.tminto.domain.ResponseResult;
import com.tminto.domain.User;

/**
 * @author 吴员外
 * @date 2022/10/24 20:14
 */
public interface LoginService {
    ResponseResult login(User user);

    void logout();

}
