package cn.jeeweb.web.modules.sso.service.impl;

import cn.jeeweb.web.security.shiro.realm.UserRealm;
import cn.jeeweb.web.config.autoconfigure.ShiroConfigProperties;
import cn.jeeweb.web.modules.sso.service.IOAuthService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.gzst.gov.cn
 *
 * @version V1.0
 * @package cn.gov.gzst.sso.server.service.impl
 * @title:
 * @description: 单点登录
 * @author: 王存见
 * @date: 2018/3/29 9:30
 * @copyright: 2017 www.gzst.gov.cn Inc. All rights reserved.
 */
@Service("oAuthService")
public class OAuthServiceImpl implements IOAuthService {


    //@Autowired
    //private AppFeign appFeign;
    
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShiroConfigProperties shiroConfigProperties;

    private final String ACCESS_TOKEN_KEY="ACCESS_TOKEN_KEY";
    private final String AUTH_CODE_PRE="AUTH_CODE_PRE_";
    private final String ACCESS_TOKEN_PRE="ACCESS_TOKEN_PRE_";
    private final String REFRESH_TOKEN_PRE="REFRESH_TOKEN_PRE";

    @Override
    public void addAuthCode(String authCode, UserRealm.Principal principal) {
        redisTemplate.opsForValue().set(AUTH_CODE_PRE+authCode, principal, shiroConfigProperties.getCodeExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public void addAccessToken(String accessToken, UserRealm.Principal principal) {
        redisTemplate.opsForValue().set(ACCESS_TOKEN_PRE+accessToken, principal,getExpireIn(),TimeUnit.SECONDS);
        redisTemplate.opsForSet().add(ACCESS_TOKEN_KEY,accessToken);
        redisTemplate.expire(ACCESS_TOKEN_KEY,getExpireIn(), TimeUnit.SECONDS);//设置过期时间

    }

    @Override
    public void addRefreshToken(String refreshToken, UserRealm.Principal principal) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PRE+refreshToken, principal,shiroConfigProperties.getRefreshTokenExpiresIn(),TimeUnit.SECONDS);
    }

    @Override
    public UserRealm.Principal getPrincipalByAuthCode(String authCode) {
        return (UserRealm.Principal)redisTemplate.opsForValue().get(AUTH_CODE_PRE+authCode);
    }

    @Override
    public UserRealm.Principal getPrincipalByAccessToken(String accessToken) {
        return (UserRealm.Principal)redisTemplate.opsForValue().get(ACCESS_TOKEN_PRE+accessToken);
    }
    @Override
    public UserRealm.Principal getPrincipalByRefreshToken(String refreshToken){
        return (UserRealm.Principal)redisTemplate.opsForValue().get(REFRESH_TOKEN_PRE+refreshToken);
    }
    @Override
    public boolean checkAuthCode(String authCode) {
        try {
            return redisTemplate.opsForValue().get(AUTH_CODE_PRE+authCode) != null;
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        try {
            return redisTemplate.opsForValue().get(ACCESS_TOKEN_PRE+accessToken) != null;
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean checkRefreshToken(String refreshToken) {
        try {
            return redisTemplate.opsForValue().get(REFRESH_TOKEN_PRE+refreshToken) != null;
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }

    @Override
    public void revokeToken(String accessToken) {
        redisTemplate.delete(ACCESS_TOKEN_PRE+accessToken);
        redisTemplate.opsForSet().add(ACCESS_TOKEN_KEY,accessToken);
    }

    @Override
    public boolean checkClientId(String clientId) {
        //return   appFeign.checkClientId(clientId);
        return Boolean.TRUE;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        //return appFeign.checkClientSecret(clientSecret);
        return Boolean.TRUE;
    }

    @Override
    public Integer getExpireIn() {
        return shiroConfigProperties.getAccesTokenExpiresIn();
    }

    @Override
    public List<UserRealm.Principal> activePrincipal() {
        List<UserRealm.Principal> principalList = new ArrayList<>();
       Set<String> accessTokenList= redisTemplate.opsForSet().members(ACCESS_TOKEN_KEY);
        for (String accessToken: accessTokenList) {
            UserRealm.Principal  principal = getPrincipalByAccessToken(accessToken);
            if (principal!=null){
                principalList.add(principal);
            }
        }
        return principalList;
    }

    @Override
    public Page<UserRealm.Principal> activePrincipal(Page page) {
        List<UserRealm.Principal> activePrincipalList=activePrincipal();
        int currentRow = page.getOffset() * page.getSize();
        List<UserRealm.Principal> dataList=new ArrayList<>();
        for (int i = currentRow; i < currentRow+page.getSize()&& i < activePrincipalList.size(); i++) {
            dataList.add(activePrincipalList.get(i));
        }
        page.setRecords(dataList);
        page.setTotal(activePrincipalList.size());
        return page;
    }
}
