package com.cnce.manager.tools.dao;

import com.cnce.manager.tools.domain.CameraStressTestingDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-10-03 15:35:39
 */
@Mapper
public interface CameraStressTestingDao {

    List<CameraStressTestingDO> getModelNameByModelSort(String modelSort);

    List<CameraStressTestingDO> getResoNameByModelSort(String modelSort);

}
