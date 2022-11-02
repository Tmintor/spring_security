package com.tminto.service.impl;

import com.tminto.domain.LoginUser;
import com.tminto.domain.ResponseResult;
import com.tminto.domain.User;
import com.tminto.service.LoginService;
import com.tminto.utils.JwtUtil;
import com.tminto.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author 吴员外
 * @date 2022/10/24 20:14
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        //认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断认证成功还是失败
        if (authenticate == null) {
            throw new RuntimeException("登录失败");
        }

        //认证成功生成token，存放redis
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        redisCache.setCacheObject(userId,loginUser);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200,"登录成功",map);
    }

    @Override
    public void logout() {

        //从SecurityContextHolder拿到认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //删除redis中的token
        String id = loginUser.getUser().getId().toString();
        redisCache.deleteObject(id);
    }

}
