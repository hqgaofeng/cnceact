package com.cnce.manager.tools.service;

import com.cnce.system.domain.UserDO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface CameraStressTestingService {

    List<String> getModelNameByModelSort(String modelSort);

    List<String> getResoNameByModelSort(String modelSort);

    String startTask(Map<String, String> params, UserDO user);


}
