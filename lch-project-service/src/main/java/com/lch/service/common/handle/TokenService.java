package com.lch.service.common.handle;

import com.lch.entity.common.bo.Token;

public interface TokenService {

    /**
     * 查询当前用户
     * @param token
     * @return
     */
    Token findUserByToken(String token);

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    int logout(String token);

    /**
     * 登录
     * @param uid
     * @return
     */
    String login(Long uid);
}
