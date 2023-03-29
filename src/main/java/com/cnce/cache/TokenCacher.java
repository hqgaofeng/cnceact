package com.cnce.cache;

import com.cnce.common.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 *cache token
 */
public class TokenCacher {

    private TokenCacher(){}
    private static TokenCacher connCacher;

    private static ExpiringMap<String,String> connMap ;

    public static TokenCacher getInstance(){
        if(null==connMap){
            //default,30d
            connMap = ExpiringMap.builder()
                    .variableExpiration()
                    .expiration(30,TimeUnit.DAYS).build();
        }
        if(null==connCacher){
            connCacher = new TokenCacher();
            return connCacher;
        }
        return connCacher;
    }

    public void putToken(String user,String token,long duration){
        connMap.put(user,token);//滑动窗口
        connMap.setExpiration(user,duration,TimeUnit.DAYS);
    }

    public String getToken(String user){
        return connMap.get(user);
    }

    public void removeToken(String user){
        connMap.remove(user);
    }

}
