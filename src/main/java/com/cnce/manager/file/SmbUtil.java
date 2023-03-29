package com.cnce.manager.file;

import com.cnce.common.jenkins.JenkinsConstant;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;

public class SmbUtil {

    private static String host = JenkinsConstant.SERVER_IP;

    private static Integer port = 22;

    private static String user = JenkinsConstant.LOGIN_USERNAME;

    private static String password = JenkinsConstant.LOGIN_PASSWORD;


//    private static String basePath = "/cnce/";

    // 4.上传文件到服务器
    public int uploadFile(MultipartFile file,String basePath) {
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(now.get(Calendar.MINUTE));
        String second = String.valueOf(now.get(Calendar.SECOND));
        String newFolder = year + month + day + hour + minute + second;
        String fileName = file.getOriginalFilename();
        String fileNames = fileName.substring(0, fileName.indexOf("."));
        String end = fileName.substring(fileName.indexOf("."));
        fileName = fileNames + newFolder + end;
        InputStream input = null;
        String filePath="";
        try {
            input = file.getInputStream();
            uploadFile(host, port, user, password, basePath, filePath, fileName, input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }



    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        File file=null;
        try {
            JSch jsch = new JSch();
            //获取sshSession  账号-ip-端口
            Session sshSession = jsch.getSession(username, host, port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            file=new File(basePath);
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            //进入到要上传的目录  然后上传文件
            sftp.cd(basePath);
            sftp.put(input,filename);
            input.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}

