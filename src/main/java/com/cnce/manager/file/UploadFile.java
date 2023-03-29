package com.cnce.manager.file;

import com.cnce.common.jenkins.JenkinsConstant;
import com.jcraft.jsch.*;

import java.io.*;
import java.util.Calendar;

public class UploadFile {

    private static String host = JenkinsConstant.SERVER_IP;

    private static Integer port = 22;

    private static String user = JenkinsConstant.LOGIN_USERNAME;

    private static String password = JenkinsConstant.LOGIN_PASSWORD;



    private static String basePath="/cnce/require/";

    public static String  sshSftp(byte[] bytes, String fileName) throws Exception {
        Calendar now = Calendar.getInstance();
        String year= String.valueOf(now.get(Calendar.YEAR));
        String month= String.valueOf(now.get(Calendar.MONTH)+1);
        String day= String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour= String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String minute= String.valueOf(now.get(Calendar.MINUTE));
        String second= String.valueOf(now.get(Calendar.SECOND));
        String newFolder=year+month+day+hour+minute+second;
        basePath=basePath+newFolder+"/";
        Session session = null;
        Channel channel = null;
        JSch jsch = new JSch();
        if (port<= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, host);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, host, port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }
        //设置登陆主机的密码
        session.setPassword(password);
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);
        OutputStream outstream = null;
        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.mkdir(basePath);
            sftp.cd(basePath);
            outstream = sftp.put(fileName);
            outstream.write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关流操作
            if (outstream != null) {
                outstream.flush();
                outstream.close();
            }
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return basePath;
    }
}
