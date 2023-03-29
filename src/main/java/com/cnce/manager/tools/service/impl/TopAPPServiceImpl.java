package com.cnce.manager.tools.service.impl;

import com.cnce.common.constant.ToolsConstant;
import com.cnce.common.domain.JenkinsJobInfo;
import com.cnce.common.jenkins.JenkinsConstant;
import com.cnce.common.jenkins.JenkinsFilter;
import com.cnce.common.jenkins.JenkinsUtil;
import com.cnce.common.utils.DateUtils;
import com.cnce.common.utils.TimeUtils;
import com.cnce.manager.logs.dao.LogsDao;
import com.cnce.manager.logs.domain.GenLogsDO;
import com.cnce.manager.logs.domain.LogsDO;
import com.cnce.manager.manpower.dao.ManpowerDao;
import com.cnce.manager.manpower.domain.ManpowerDO;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.report.domain.GenReportDO;
import com.cnce.manager.report.domain.ReportDO;
import com.cnce.manager.tools.dao.TopAPPDao;
import com.cnce.manager.tools.domain.TopAPPDO;
import com.cnce.manager.tools.service.TopAPPService;
import com.cnce.system.domain.UserDO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;


@Service
public class TopAPPServiceImpl implements TopAPPService {

	private String jobName = JenkinsConstant.TOP_APPLICATION;

	JenkinsUtil jenkins = new JenkinsUtil();

	@Autowired
	private TopAPPDao topAppMapper;
	@Autowired
	private LogsDao logMapper;
	@Autowired
	private ReportDao reportMapper;
	@Autowired
	private ManpowerDao manpowerMapper;

	public List<TopAPPDO> getAllAppInfo(Map<String, Object> map) {
		// 开始使用时间
		Date statDate = new Date();
		String type= (String) map.get("type");
		String appField= (String) map.get("appField");
		if (type.equals("null")){
			map.remove("type");
		}
		if (appField.equals("null")){
			map.remove("appField");
		}
		if(!type.equals("null")){
			// 工具的人力数据入库
			List<ManpowerDO> manList = new ArrayList<>();
			for(int i=0; i<3; i++){
				ManpowerDO manpower = new ManpowerDO();
				manpower.setToolId(ToolsConstant.TOP_APP_ID);
				manpower.setToolName(ToolsConstant.TOP_APP_NAME);
				manpower.setUseDept(ToolsConstant.sDept);
				manpower.setStartUseTime(statDate);
				manpower.setEndUseTime(new Date());
				int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
				DecimalFormat df = new DecimalFormat("0.00");
				manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
				manpower.setCreateTime(new Date());
				manpower.setFunName("查询");
				manList.add(manpower);
				manpower.setFunName("选择应用类别");
				manList.add(manpower);
				manpower.setFunName("选择细分领域");
				manList.add(manpower);
			}
			manpowerMapper.insertList(manList);
		}
		List<TopAPPDO> list = topAppMapper.getAllAppInfo(map);
		return list;
	}

	public int count(Map<String, Object> map){
		return topAppMapper.count(map);
	}


	public String  batchUninstall(String[] appPackages, String equipName, UserDO user){
		Map<String,String> params =new HashMap<String,String>();
		if (appPackages.length > 0) {
			params.put("unInstallApp", StringUtils.join(appPackages, ","));
			params.put("labelName", JenkinsFilter.getSoftNode(equipName));
			String token = JenkinsJobInfo.JobName.JOB6.toString();

			// 开始使用时间
			Date statDate = new Date();
			jenkins.buildParamJob(jobName, token, params);
			TimeUtils.timeSleep(10);
			while (true){
				List<Integer> builds = jenkins.getJobBuildListAll(jobName);
				boolean hasEnd = jenkins.isFinished(jobName, builds.get(0));
				if(hasEnd){
					break;
				}
				TimeUtils.timeSleep(10);
			}

			// 检查测试结果是否有效
			List<Integer> bs = jenkins.getJobBuildListAll(jobName);
			String result = jenkins.getBuildResult(bs.get(0), jobName);
			Integer status = JenkinsFilter.getResCode(result);
			if(status == 1){
				// 工具的人力数据入库
				List<ManpowerDO> manList = new ArrayList<>();
				for(int i=0; i<2; i++){
					ManpowerDO manpower = new ManpowerDO();
					manpower.setToolId(ToolsConstant.TOP_APP_ID);
					manpower.setToolName(ToolsConstant.TOP_APP_NAME);
					manpower.setUseDept(ToolsConstant.sDept);
					manpower.setStartUseTime(statDate);
					manpower.setEndUseTime(new Date());
					int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
					DecimalFormat df = new DecimalFormat("0.00");
					manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
					manpower.setCreateTime(new Date());
					manpower.setFunName("选择应用");
					manList.add(manpower);
					manpower.setFunName("卸载应用");
					manList.add(manpower);
				}
				manpowerMapper.insertList(manList);
			}
			// 测试结果入库
			LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.TOP_APP_ID, status, ToolsConstant.TOP_APP_NAME,
					ToolsConstant.TOPAPP_LOG_PATH);
			logMapper.save(logDO);
			ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.TOP_APP_ID, ToolsConstant.TOP_APP_NAME,
					ToolsConstant.TOPAPP_REPORT_PATH);
			reportMapper.save(reportDO);
		}
		return "SUCCESS";
	}

	public String batchInstall(String[] names, String equipName, UserDO user){
		Map<String,String> params =new HashMap<>();
		if (names.length > 0) {
			params.put("installApp", StringUtils.join(names, ","));
			params.put("labelName", JenkinsFilter.getSoftNode(equipName));
			String token = JenkinsJobInfo.JobName.JOB6.toString();

			// 开始使用时间
			Date statDate = new Date();
			jenkins.buildParamJob(jobName, token, params);
			TimeUtils.timeSleep(10);
			while (true){
				List<Integer> builds = jenkins.getJobBuildListAll(jobName);
				boolean hasEnd = jenkins.isFinished(jobName, builds.get(0));
				if(hasEnd){
					break;
				}
				TimeUtils.timeSleep(10);
			}

			// 检查测试结果是否有效
			List<Integer> bs = jenkins.getJobBuildListAll(jobName);
			String result = jenkins.getBuildResult(bs.get(0), jobName);
			Integer status = JenkinsFilter.getResCode(result);
			if(status == 1){
				// 工具的人力数据入库
				List<ManpowerDO> manList = new ArrayList<>();
				for(int i=0; i<2; i++){
					ManpowerDO manpower = new ManpowerDO();
					manpower.setToolId(ToolsConstant.TOP_APP_ID);
					manpower.setToolName(ToolsConstant.TOP_APP_NAME);
					manpower.setUseDept(ToolsConstant.sDept);
					manpower.setStartUseTime(statDate);
					manpower.setEndUseTime(new Date());
					int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
					DecimalFormat df = new DecimalFormat("0.00");
					manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
					manpower.setCreateTime(new Date());
					manpower.setFunName("选择应用");
					manList.add(manpower);
					manpower.setFunName("安装应用");
					manList.add(manpower);
				}
				manpowerMapper.insertList(manList);
			}
			// 测试结果入库
			LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.TOP_APP_ID, status, ToolsConstant.TOP_APP_NAME,
					ToolsConstant.TOPAPP_LOG_PATH);
			logMapper.save(logDO);
			ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.TOP_APP_ID, ToolsConstant.TOP_APP_NAME,
					ToolsConstant.TOPAPP_REPORT_PATH);
			reportMapper.save(reportDO);
		}
		return "SUCCESS";
	}

	public List<String> getAppType() {
		List<String> list = topAppMapper.getAppType();
		return list;
	}

	public List<String> getAppField(String appType) {
		List<String> list = topAppMapper.getAppField(appType);
		return list;
	}


	public List<TopAPPDO> getInfoByTypeAndFeild(Map<String, Object> map) {
		List<TopAPPDO> list = topAppMapper.getInfoByTypeAndFeild(map);
		return list;
	}

	@Override
	public int importFiles(MultipartFile file) throws Exception {
		topAppMapper.deleteAllData();
		int result = 0;
		//存放excel表中所有user数据
		List<TopAPPDO> topAppList = new ArrayList<>();
		//file.getOriginalFilename()方法 得到上传时的文件名
		String fileName = file.getOriginalFilename();
		//截取文件名的后缀
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		//file.getInputStream()方法  返回InputStream对象 读取文件的内容
		InputStream ins = file.getInputStream();
		Workbook wb = null;
        /*判断文件后缀
            XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
            HSSF － 提供读写Microsoft Excel XLS格式档案的功能。*/
		if(suffix.equals("xlsx")){
			wb = new XSSFWorkbook(ins);
		}else{
			wb = new HSSFWorkbook(ins);
		}
		//获取excel表单的sheet对象
		Sheet sheet = wb.getSheetAt(0);
		//如果sheet不为空，就开始遍历表中的数据
		if(null != sheet){
			//line = 2 :从表的第三行开始获取记录
			for(int line = 1; line <= sheet.getLastRowNum();line++){
				//excel表单的sheet的行对象
				Row row = sheet.getRow(line);
				//如果某行为空，跳出本行
				if(null == row){
					continue;
				}
				//获取第一个单元格的内容
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				String pakageName=row.getCell(0).getStringCellValue();
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String appName=row.getCell(1).getStringCellValue();
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				String appType=row.getCell(2).getStringCellValue();
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				String appField=row.getCell(3).getStringCellValue();
				row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				String appRank=row.getCell(4).getStringCellValue();
				TopAPPDO topApp = new TopAPPDO();
				topApp.setToolId(8);
				topApp.setAppPackage(pakageName);
				topApp.setName(appName);
				topApp.setType(appType);
				topApp.setAppField(appField);
				topApp.setAppRank(appRank);
				topAppList.add(topApp);
			}
			for(TopAPPDO topApp:topAppList){
				result = topAppMapper.insertAllData(topApp);
			}
		}
		return result;
	}
}