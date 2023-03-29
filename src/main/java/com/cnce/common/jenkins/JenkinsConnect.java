package com.cnce.common.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 连接 Jenkins
 */
public class JenkinsConnect {

    private JenkinsConnect(){}

    public static JenkinsHttpClient getClient(){
        JenkinsHttpClient jenkinsHttpClient = null;
        try {
            jenkinsHttpClient = new JenkinsHttpClient(new URI(JenkinsConstant.JENKINS_URL),
                    JenkinsConstant.JENKINS_USERNAME, JenkinsConstant.JENKINS_PASSWORD);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jenkinsHttpClient;
    }

    /**
     * 建立连接
     */
    public static JenkinsServer connection() {
        JenkinsServer jenkinsServer = null;
        try {
            jenkinsServer = new JenkinsServer(new URI(JenkinsConstant.JENKINS_URL),
                    JenkinsConstant.JENKINS_USERNAME, JenkinsConstant.JENKINS_PASSWORD);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jenkinsServer;
    }

    public static Map<Object, Object> getUrlInfo(String jenkinsUrl){
        Map<Object, Object> infoMap = new HashMap();
        try {
            URL url = new URL(jenkinsUrl);
            String host = url.getHost();
            infoMap.put("host", host);
            int port = url.getPort();
            infoMap.put("port", port);
        }catch (Exception e){
            e.printStackTrace();
        }
        return infoMap;
    }

}