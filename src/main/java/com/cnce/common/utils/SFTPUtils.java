/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cnce.common.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import com.cnce.common.jenkins.JenkinsConstant;
import com.jcraft.jsch.ChannelSftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 远程服务工具类
 * chenfei
 */
public class SFTPUtils {

    public static Connection conn = null;

    public static boolean login(){
        conn = new Connection(JenkinsConstant.SERVER_IP);
        try {
            conn.connect();
            return conn.authenticateWithPassword(JenkinsConstant.LOGIN_USERNAME, JenkinsConstant.LOGIN_PASSWORD);
        } catch (IOException e) {
            System.out.println("用户密码登录服务器失败！");
            e.printStackTrace();
        }
        return false;
    }

    public static String getFileProperties(Connection conn, String remotePath){
        List<String> fileList = new ArrayList<>();
        try {
            if(remotePath != null && !remotePath.equals("")){
                SFTPv3Client sft = new SFTPv3Client(conn);
//                Vector<?> v = (Vector<?>) sft.ls(remotePath);
                List<?> v = (List<?>) sft.ls(remotePath);
                for (Object o : v) {
                    SFTPv3DirectoryEntry s = new SFTPv3DirectoryEntry();
                    s = (SFTPv3DirectoryEntry) o;
                    String filename = s.filename;
                    if (!filename.equals("..") && !filename.equals(".")) {
                        fileList.add(filename);
                    }
                }
                if(fileList.size() > 0){
                    Object a = Collections.max(fileList);
                    String newPath = remotePath + a.toString() + "/";
                    sft.close();
                    return newPath;
                }else {
                    return null;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        boolean b = login();
        System.out.println(b);
        String p1 = getFileProperties(conn, "/cnce/reports/CameraObjective/");
        System.out.println(p1);
        String p2 = getFileProperties(conn, p1);
        System.out.println(p2);
    }
}