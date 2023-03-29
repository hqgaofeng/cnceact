package com.cnce.manager.tools.service;

import com.cnce.manager.tools.domain.TopAPPDO;
import com.cnce.system.domain.UserDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface TopAPPService {

    List<TopAPPDO> getAllAppInfo(Map<String, Object> map);

    int count(Map<String, Object> map);

    String batchUninstall(String[] appPackages, String equipName, UserDO user);

    String batchInstall(String[] names, String equipName, UserDO user);

    List<String> getAppType();

    List<String> getAppField(String appType);

    List<TopAPPDO> getInfoByTypeAndFeild(Map<String, Object> map);


    int importFiles(MultipartFile file) throws Exception;
}
