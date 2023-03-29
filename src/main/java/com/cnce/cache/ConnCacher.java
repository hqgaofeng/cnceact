package com.cnce.cache;

import com.cnce.common.expiringmap.ExpiringMap;

import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 *
 */
//@Component
//@CacheConfig(cacheNames = "conn")
public class ConnCacher {

    private ConnCacher (){}
    private static ConnCacher connCacher;

    private static ExpiringMap<String,SocketChannel> connMap  ;

    public static ConnCacher getInstance(){
        if(null==connMap){
            //default,1min
            connMap = ExpiringMap.builder()
                    .variableExpiration()
                    .expiration(60,TimeUnit.SECONDS).build();
        }
        if(null==connCacher){
            connCacher = new ConnCacher();
            return connCacher;
        }
        return connCacher;
    }

    public void putConn(String gprsMsg,long connDuration,SocketChannel sc){
        connMap.put(gprsMsg,sc);//滑动窗口
        connMap.setExpiration(gprsMsg,connDuration,TimeUnit.SECONDS);
    }

    public synchronized SocketChannel getCacheConn(String gprsMsg){
        return connMap.get(gprsMsg);
    }

    public void removeConn(String gprsMsg){
        connMap.remove(gprsMsg);
    }

}
