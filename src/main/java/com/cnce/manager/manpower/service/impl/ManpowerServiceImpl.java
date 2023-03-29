package com.cnce.manager.manpower.service.impl;

import com.cnce.common.constant.ToolsConstant;
import com.cnce.manager.manpower.dao.ManpowerDao;
import com.cnce.manager.manpower.domain.ManPowerVO;
import com.cnce.manager.manpower.domain.ManpowerDO;
import com.cnce.manager.manpower.service.ManpowerService;
import com.cnce.manager.profit.domain.ProfitFilter;
import com.cnce.manager.tools.dao.ToolsDao;
import com.cnce.manager.tools.domain.ToolsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ManpowerServiceImpl implements ManpowerService {

    @Autowired
    private ManpowerDao powerMapper;
    @Autowired
    private ToolsDao toolMapper;

    @Override
    public ManPowerVO getAll() {
        ManPowerVO manPower = new ManPowerVO();

        final SoftInfo softInfo = getSoftInfo();
        // 软测使用次数
        manPower.setsUseTimes(softInfo.sUseTimes);
        manPower.setsEffort(softInfo.softSum.toString());

        final HardInfo hardInfo = getHardInfo();
        // 硬测使用次数
        manPower.sethUseTimes(hardInfo.hUseTimes);
        manPower.sethEffort(hardInfo.hardSum.toString());

        // 使用次数总计
        int useTotal = softInfo.sUseTimes + hardInfo.hUseTimes;
        manPower.setUseTotal(String.valueOf(useTotal));
        // 节省人力总计
        BigDecimal bd = softInfo.softSum.add(hardInfo.hardSum);
        manPower.setEffTotal(bd.toString());

        return manPower;
    }

    private static class SoftInfo {
        public int sUseTimes; // 软测使用次数
        public BigDecimal softSum; // 软测总人力
    }

    private SoftInfo getSoftInfo() {
        List<ToolsDO> sTools = toolMapper.getToolsByDept(ToolsConstant.sDept);
        // 软测
        List<BigDecimal> sList = new ArrayList<>(sTools.size() + 3);
        for (ToolsDO t : sTools) {
            List<ManpowerDO> pfList = powerMapper.loadTools(new HashMap<>());
            List<BigDecimal> sli = new ArrayList<>(pfList.size());
            for (ManpowerDO pf : pfList) {
                final Integer toolId = pf.getToolId();
                final String funName = pf.getFunName();
                final BigDecimal bigDecimal = getSwBigDecimal(toolId, funName);
                if (bigDecimal != null) sli.add(bigDecimal);
            }
            BigDecimal sum = sli.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            sList.add(sum);
            sli.clear();
        }

        Iterator<BigDecimal> itr = sList.iterator();
        while (itr.hasNext()) {
            if (itr.next().compareTo(BigDecimal.ZERO) == 0) {
                itr.remove();
            }
        }
        final SoftInfo softInfo = new SoftInfo();
        // 软测使用次数
        softInfo.sUseTimes = sList.size();
        // 多机升级原始已有收益
        BigDecimal updOrg = new BigDecimal("-1.091");
        sList.add(updOrg);
        // camera压测原始已有收益
        BigDecimal cmaStressOrg = new BigDecimal("-0.023");
        sList.add(cmaStressOrg);
        // TOP3000原始已有收益
        BigDecimal topOrg = new BigDecimal("0.364");
        sList.add(topOrg);
        // 软测总人力
        softInfo.softSum = sList.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
        return softInfo;
    }

    private BigDecimal getSwBigDecimal(final Integer toolId, final String funName) {
        if (ToolsConstant.B_UPAPP_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getMuUpdVal(funName));
        }
        if (ToolsConstant.F_UPAPP_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getFreeMuUpdVal(funName));
        }
        if(ToolsConstant.H_7502_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getBHeartVal(funName));
        }
        if (ToolsConstant.H_7505_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getHeartVal(funName));
        }
        if (ToolsConstant.CMASTR_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getCMAStressVal(funName));
        }
        if (ToolsConstant.TOP_APP_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getTopAppVal(funName));
        }
        return null;
    }

    private static class HardInfo {
        public int hUseTimes; // 软测使用次数
        public BigDecimal hardSum; // 硬测总人力
    }

    private HardInfo getHardInfo() {
        List<ToolsDO> hTools = toolMapper.getToolsByDept(ToolsConstant.hDept);
        // 硬测
        List<BigDecimal> hList = new ArrayList<>(hTools.size() + 4);
        for (ToolsDO t : hTools) {
            List<ManpowerDO> pfList = powerMapper.loadTools(new HashMap<>());
            List<BigDecimal> hli = new ArrayList<>(pfList.size());
            for(ManpowerDO pf : pfList){
                final Integer toolId = pf.getToolId();
                final String funName = pf.getFunName();
                BigDecimal bigDecimal = getHwBigDecimal(toolId, funName);
                if (bigDecimal != null) hli.add(bigDecimal);
            }
            BigDecimal sum = hli.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
            hList.add(sum);
            hli.clear();
        }
        Iterator<BigDecimal> ith = hList.iterator();
        while (ith.hasNext()) {
            if(ith.next().compareTo(BigDecimal.ZERO) == 0){
                ith.remove();
            }
        }
        final HardInfo hardInfo = new HardInfo();
        // 硬测使用次数
        hardInfo.hUseTimes = hList.size();
        // cma主观导出原始已有收益
        BigDecimal cmaSubOrg = new BigDecimal("-0.193");
        hList.add(cmaSubOrg);
        // cma3A原始已有收益
        BigDecimal cma3AOrg = new BigDecimal("-0.023");
        hList.add(cma3AOrg);
        // Flare图片导出原始已有收益
        BigDecimal flareOrg = new BigDecimal("-0.273");
        hList.add(flareOrg);
        // wifi干扰原始已有收益
        BigDecimal wifiOrg = new BigDecimal("-1.364");
        hList.add(wifiOrg);
        // 硬测总人力
        hardInfo.hardSum = hList.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
        return hardInfo;
    }

    private BigDecimal getHwBigDecimal(final Integer toolId, final String funName) {
        if (ToolsConstant.CMASUB_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getCMASubVal(funName));
        }
        if (ToolsConstant.CMA3A_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getCMA3AVal(funName));
        }
        if (ToolsConstant.FLARE_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getFlareVal(funName));
        }
        if (ToolsConstant.WIFI_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getWifiVal(funName));
        }
        return null;
    }

    @Override
    public List<ManpowerDO> loadTools(Map<String,Object> map){
        List<ManpowerDO> manPower = powerMapper.loadTools(map);
        for (ManpowerDO mp : manPower) {
            final Integer toolId = mp.getToolId();
            final String funName = mp.getFunName();
            BigDecimal manpowerBd = getManpowerBigDecimal(toolId, funName);
            if (manpowerBd == null) continue;
            BigDecimal mDay = manpowerBd.divide(new BigDecimal("22"), 5, BigDecimal.ROUND_HALF_UP);
            mp.setManPower(mDay.toString());
        }
        return manPower;
    }

    private BigDecimal getManpowerBigDecimal(final Integer toolId, final String funName) {
        if (ToolsConstant.CMASUB_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getCMASubVal(funName));
        }
        if (ToolsConstant.CMA3A_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getCMA3AVal(funName));
        }
        if (ToolsConstant.B_UPAPP_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getMuUpdVal(funName));
        }
        if (ToolsConstant.F_UPAPP_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getFreeMuUpdVal(funName));
        }
        if (ToolsConstant.H_7502_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getBHeartVal(funName));
        }
        if (ToolsConstant.H_7505_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getHeartVal(funName));
        }
        if (ToolsConstant.CMASTR_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getCMAStressVal(funName));
        }
        if (ToolsConstant.TOP_APP_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getTopAppVal(funName));
        }
        if (ToolsConstant.FLARE_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getFlareVal(funName));
        }
        if (ToolsConstant.WIFI_ID.equals(toolId)) {
            return new BigDecimal(ProfitFilter.getWifiVal(funName));
        }
        return null;
    }

    @Override
    public int count(Map<String, Object> map) {
        return powerMapper.count(map);
    }

}
