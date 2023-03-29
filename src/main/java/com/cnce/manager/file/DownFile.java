package com.cnce.manager.file;


import ch.ethz.ssh2.*;
import com.cnce.common.jenkins.JenkinsConstant;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class DownFile {
    public void downfiles(HttpServletResponse response, String filePath, String fileName){
        try{
            Connection conn = new Connection(JenkinsConstant.SERVER_IP,22);
            conn.connect();
            conn.authenticateWithPassword(JenkinsConstant.LOGIN_USERNAME, JenkinsConstant.LOGIN_PASSWORD);
            SCPClient sc = conn.createSCPClient();
            SCPInputStream is = sc.get(filePath);


            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            String filename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            byte[] b = new byte[4096];
            int i;
            while ((i = is.read(b)) != -1) {
                ouputStream.write(b, 0, i);
            }
            ouputStream.flush();
            ouputStream.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

 }