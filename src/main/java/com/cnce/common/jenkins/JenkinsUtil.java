package com.cnce.common.jenkins;

import com.alibaba.fastjson.JSONObject;
import com.cnce.common.domain.CrumbDO;
import com.cnce.common.utils.JSONUtils;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.*;
import com.offbytwo.jenkins.model.Job;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
//import net.sf.json.JSONObject;


import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * Jenkins工具类
 *
 */
public class JenkinsUtil {

    private static Logger logger = LoggerFactory.getLogger(JenkinsUtil.class);

    private JenkinsServer jenkinsServer;

    private JenkinsHttpClient jenkinsHttpClient;

    private String username;

    private String password;

    private String host;

    private int port;

    private CloseableHttpClient httpClient = HttpClientPool.getHttpClient();


    public JenkinsUtil() {

        jenkinsServer = JenkinsConnect.connection();

        jenkinsHttpClient = JenkinsConnect.getClient();

        username = JenkinsConstant.JENKINS_USERNAME;

        password = JenkinsConstant.JENKINS_PASSWORD;

        Map<Object, Object> urlInfo = JenkinsConnect.getUrlInfo(JenkinsConstant.JENKINS_URL);

        host = urlInfo.get("host").toString();

        port = 8080;

    }

    /**
     * 获取 Job 列表
     */
    public List<String> getJobList(){
        List<String> jobList = new ArrayList<>();
        try {
            Map<String,Job> jobs = jenkinsServer.getJobs();
            for (Job job:jobs.values()){
                jobList.add(job.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobList;

    }

    /**
     * 查看 Job XML 信息
     */
    public String getJobConfig(String jobName){
        try {
            String xml = jenkinsServer.getJobXml(jobName);
            return xml;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行无参Job
     */
    public void buildJob(String jobName){
        try {
            jenkinsServer.getJob(jobName).build(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行有参Job
     */
    public void buildJobWithParam(String jobName, Map<String,String> params){
        try {
            jenkinsServer.getJob(jobName).build(params, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 删除job
    public void deleteJob(String jobName){
        try {
            jenkinsServer.deleteJob(jobName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止最后构建的Job
     */
    public void stopLastJobBuild(String jobName){
        try {
            Build build = jenkinsServer.getJob(jobName).getLastBuild();
            build.Stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Build 编号获取编译信息
     */
    public void getJobByNumber(String jobName){
        try {
            JobWithDetails job = jenkinsServer.getJob(jobName);
            Build numberBuild = job.getBuildByNumber(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全部Job Build列表
     */
    public List<Integer> getJobBuildListAll(String jobName){
        List<Integer> buildNums = new ArrayList<>();
        try {
            JobWithDetails job = jenkinsServer.getJob(jobName);
            List<Build> builds = job.getAllBuilds();
            for (Build build:builds){
                buildNums.add(build.getNumber());
            }
            return buildNums;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取正在执行构建任务的日志信息
     */
    public void getBuildActiveLog(String jobName){
        try {
            BuildWithDetails build = jenkinsServer.getJob(jobName).getLastBuild().details();
            ConsoleLog currentLog = build.getConsoleOutputText(0);
            while (currentLog.getHasMoreData()){
                ConsoleLog newLog = build.getConsoleOutputText(currentLog.getCurrentBufferSize());
                currentLog = newLog;
                System.out.println(newLog);
                Thread.sleep(1000);
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断 Jenkins 是否运行
     */
    public boolean isRunning() {
        return jenkinsServer.isRunning();
    }

    /**
     * 根据 Label 查找代理节点信息
     */
    public String getLabelNodeInfo(String labelName) {
        try {
            LabelWithDetails labelWithDetails = jenkinsServer.getLabel(labelName);
            String nodeName = labelWithDetails.getName();
            return nodeName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 Label 查找代理节点信息
     */
    public List<Object> getLabelNodeTiedJobs(String labelName) {
        try {
            LabelWithDetails labelWithDetails = jenkinsServer.getLabel(labelName);
            List<Object> tiedJobs = labelWithDetails.getTiedJobs();
            return tiedJobs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 Label 查找代理节点信息
     */
    public List<Object> getLabelNodeParams(String labelName) {
        try {
            LabelWithDetails labelWithDetails = jenkinsServer.getLabel(labelName);
            List<Object> params = labelWithDetails.getPropertiesList();
            return params;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行有参Job
     * @param jobName
     * @param apiToken
     * @param map
     */
    public void buildParamJob(String jobName, String apiToken, Map<String, String> map) {
        String format = "http://%s:%s/job/%s/buildWithParameters?token=" + apiToken;
        CrumbDO crumbEntity = getCrumb();
        HttpPost httpPost = new HttpPost(String.format(format, host, port, jobName));
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        for (String key : map.keySet()) {
            data.add(new BasicNameValuePair(key, map.get(key)));
        }
        UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(data, Consts.UTF_8);
        CloseableHttpResponse rsp = null;

        try {
            httpPost.setEntity(urlEntity);
            httpPost.addHeader(crumbEntity.getCrumbRequestField(), crumbEntity.getCrumb());
            rsp = httpClient.execute(httpPost, this.getHttpClientContext());
            System.out.println(rsp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpUtil.close(rsp);
            format = null;
            crumbEntity = null;
            httpPost = null;
            data.clear();
            map.clear();
            data = null;
            map = null;
        }
    }

    public CrumbDO getCrumb() {
        String format = "http://%s:%s/crumbIssuer/api/json";
        CloseableHttpResponse rsp = null;
        HttpGet httpGet = new HttpGet(String.format(format, host, port));
        try {
            rsp = httpClient.execute(httpGet, this.getHttpClientContext());
            String jsonResult = EntityUtils.toString(rsp.getEntity());
            CrumbDO crumbEntity = JSONObject.parseObject(jsonResult, CrumbDO.class);
            return crumbEntity;
        } catch (Exception e) {
            logger.error(null, e);
        } finally {
            HttpUtil.close(rsp);
            format = null;
            httpGet = null;
        }
        return null;
    }

    private HttpHost getHttpHost() {
        return new HttpHost(host, port);
    }

    private CredentialsProvider getCredentialProvider() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(this.getHttpHost().getHostName(), this.getHttpHost().getPort()),
                new UsernamePasswordCredentials(username, password)
        );
        return credentialsProvider;
    }

    private AuthCache getAuthCache() {
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicScheme = new BasicScheme();
        authCache.put(getHttpHost(), basicScheme);
        return authCache;
    }

    private HttpClientContext getHttpClientContext() {
        HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setCredentialsProvider(this.getCredentialProvider());
        httpClientContext.setAuthCache(this.getAuthCache());
        return httpClientContext;
    }

    // 获取最后一次构建是否成功
    public boolean isSuccess(String jobName, int number) {
        try {
            Map<String, Job> jobs = jenkinsServer.getJobs();
            JobWithDetails job = jobs.get(jobName).details();
            int LastSuccessfulNumber = job.getLastSuccessfulBuild().getNumber();
            int LastUnsuccessfulNumber = job.getLastUnsuccessfulBuild().getNumber();
            boolean flag = false;
            if (LastSuccessfulNumber == number) {
                flag = true;
            }
            if (LastUnsuccessfulNumber == number) {
                flag = false;
            }
            return flag;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    // 检查job是否build结束
    public boolean isFinished(String jobName, int number) {
        boolean isBuilding = false;
        if (number <= 0) {
            throw new IllegalArgumentException("job number必须大于0!");
        }
        try {
            Map<String, Job> jobs = jenkinsServer.getJobs();
            JobWithDetails job = jobs.get(jobName).details();
            Build buildByNumber = job.getBuildByNumber(number);
            if (null != buildByNumber) {
                BuildWithDetails details = buildByNumber.details();
                if (null != details) {
                    isBuilding = details.isBuilding();
                } else {
                    isBuilding = true;
                }
            } else {
                isBuilding = true;
            }
            return !isBuilding;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    public String getBuildResult(Integer number, String jobName) {
        BuildWithDetails buildWithDetails = this.getBuildDetails(number, jobName);
        BuildResult result = buildWithDetails.getResult();
        return result.name();
    }

    private BuildWithDetails getBuildDetails(Integer number, String jobName){
        JobWithDetails job = null;
        Build build=null;
        BuildWithDetails buildWithDetails=null;
        try{
            job = jenkinsServer.getJob(jobName);
            build = job.getBuildByNumber(number);
            buildWithDetails = build.details();
        }catch (Exception e){
            e.printStackTrace();
        }
        return buildWithDetails;
    }

    public boolean isJenkinsJobExist(String jobName) {
        HttpGet httpGet = new HttpGet(JenkinsConstant.JENKINS_URL + "/api/json");
        CloseableHttpResponse rsp = null;
        try {
            rsp = httpClient.execute(httpGet, this.getHttpClientContext());
            HttpEntity entity = rsp.getEntity();
            String result = EntityUtils.toString(entity);
            List<String> jobList = with(result).getList("jobs.name");
            for (String job : jobList) {
                if (jobName.equals(job)) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error(null, e);
            return false;
        }
        return false;
    }

    // 复制一个Jenkins job
    public void copyJenkinsJob(String jobName, String copyJob) {
        String format = "http://%s:%s/createItem?name="+jobName+"&mode=copy&from="+copyJob+"";
        CrumbDO crumbEntity = getCrumb();
        HttpPost httpPost = new HttpPost(String.format(format, host, port));
        CloseableHttpResponse rsp = null;
        try {
            httpPost.addHeader(crumbEntity.getCrumbRequestField(), crumbEntity.getCrumb());
            rsp = httpClient.execute(httpPost, this.getHttpClientContext());
            System.out.println(rsp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpUtil.close(rsp);
            format = null;
            crumbEntity = null;
            httpPost = null;
        }
    }


    public boolean stopJenkinsJob(String jobName) {
        String format = "http://%s:%s/job/%s//api/json";
        HttpPost httpPost = new HttpPost(String.format(format, host, port, jobName));
        CrumbDO crumbEntity = getCrumb();
        CloseableHttpResponse resp = null;
        try {
            httpPost.addHeader(crumbEntity.getCrumbRequestField(), crumbEntity.getCrumb());
            resp = httpClient.execute(httpPost, this.getHttpClientContext());
            HttpEntity entity = resp.getEntity();
            String result = EntityUtils.toString(entity);
            int buildNumber = with(result).get("lastBuild.number");
            HttpPost stopRequest = new HttpPost(
                    JenkinsConstant.JENKINS_URL + "/job/" + jobName + "/" + buildNumber + "/stop");
            stopRequest.addHeader(crumbEntity.getCrumbRequestField(), crumbEntity.getCrumb());
            httpClient.execute(stopRequest, this.getHttpClientContext());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
            return true;
    }

    // 获取building job信息
    public Map<String, Object> reqLastBuild(String jobName){
        Map<String, Object> resMap = new HashMap<>();
        String format = "http://%s:%s/job/%s/lastCompletedBuild/api/json";
        try {
            URL url=new URL(String.format(format, host, port, jobName));
            StringBuffer document = new StringBuffer();
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null){
                document.append(line);
            }
            reader.close();
            JSONObject json = JSONObject.parseObject(document.toString());
            Object building = json.get("building");
            resMap.put("building", building);
            Object duration = json.get("duration");
            resMap.put("duration", duration);
            Object estimatedDuration = json.get("estimatedDuration");
            resMap.put("estimatedDuration", estimatedDuration);
            Object timestamp = json.get("timestamp");
            resMap.put("timestamp", timestamp);
            Object number = json.get("number");
            resMap.put("number", number);
            Object result = json.get("result");
            resMap.put("result", result);
            System.out.println(resMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resMap;
    }

    // 检查节点是否在线
    public boolean checkNodeOnline(String nodeName){
        String format = "http://%s:%s/computer/%s/api/json?pretty=true";
        try {
            URL url = new URL(String.format(format, host, port, nodeName));
            StringBuffer document = new StringBuffer();
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                document.append(line);
            }
            reader.close();
            JSONObject json = JSONObject.parseObject(document.toString());
            System.out.println(json);
            Object offline = json.get("offline");
            if("false".equals(offline.toString())){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
    }
        return false;
    }

    public boolean jobNotBuild(String jobName) {
        String format = "http://%s:%s/job/%s/api/json";
        try {
            URL url = new URL(String.format(format, host, port, jobName));
            StringBuffer dm = new StringBuffer();
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                dm.append(line);
            }
            reader.close();
            JSONObject json = JSONObject.parseObject(dm.toString());
            Object lastBuild = json.get("lastBuild");
            Object lastCompletedBuild = json.get("lastCompletedBuild");
            JSONObject js = JSONObject.parseObject(lastBuild.toString());
            JSONObject jsb = JSONObject.parseObject(lastCompletedBuild.toString());
            int number1 = (Integer) js.get("number");
            int number2 = (Integer) jsb.get("number");
            if (number1 == number2) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CAMERA3A JOB
    public void createCM3AJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/CameraObjective&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("projectName")+" -v2=" +map.get("cameraLens")+ " -v3=" + map.get("cameraMode")+ " -v4=" +map.get("cameraTime")+ " -v5=" +map.get("cameraTouch")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCM3AJob(String labelName, String jobName){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +labelName+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/CameraObjective&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=%projectName% -v2=%cameraLens% -v3=%cameraMode% -v4=%cameraTime% -v5=%cameraTouch%&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <actions>\n" +
                    "    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin=\"pipeline-model-definition@2.2114.v2654ca_721309\"/>\n" +
                    "    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin=\"pipeline-model-definition@2.2114.v2654ca_721309\">\n" +
                    "      <jobProperties/>\n" +
                    "      <triggers/>\n" +
                    "      <parameters/>\n" +
                    "      <options/>\n" +
                    "    </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>\n" +
                    "  </actions>\n" +
                    "  <description>Camera客观3A自动拍照</description>\n" +
                    "  <keepDependencies>false</keepDependencies>\n" +
                    "  <properties>\n" +
                    "    <hudson.model.ParametersDefinitionProperty>\n" +
                    "      <parameterDefinitions>\n" +
                    "        <hudson.model.StringParameterDefinition>\n" +
                    "          <name>labelName</name>\n" +
                    "          <description>要执行的Node节点名称</description>\n" +
                    "          <defaultValue></defaultValue>\n" +
                    "          <trim>false</trim>\n" +
                    "        </hudson.model.StringParameterDefinition>\n" +
                    "        <hudson.model.StringParameterDefinition>\n" +
                    "          <name>projectName</name>\n" +
                    "          <description>测试项目：Q6803、Q7503、M3322</description>\n" +
                    "          <defaultValue></defaultValue>\n" +
                    "          <trim>false</trim>\n" +
                    "        </hudson.model.StringParameterDefinition>\n" +
                    "        <hudson.model.StringParameterDefinition>\n" +
                    "          <name>cameraLens</name>\n" +
                    "          <description>相机镜头：前置front、后摄rear、广角wide、微距macro</description>\n" +
                    "          <defaultValue></defaultValue>\n" +
                    "          <trim>false</trim>\n" +
                    "        </hudson.model.StringParameterDefinition>\n" +
                    "        <hudson.model.StringParameterDefinition>\n" +
                    "          <name>cameraMode</name>\n" +
                    "          <description>相机模式：单次连拍 1、循环进入2</description>\n" +
                    "          <defaultValue></defaultValue>\n" +
                    "          <trim>false</trim>\n" +
                    "        </hudson.model.StringParameterDefinition>\n" +
                    "        <hudson.model.StringParameterDefinition>\n" +
                    "          <name>cameraTime</name>\n" +
                    "          <description>间隔时间</description>\n" +
                    "          <defaultValue></defaultValue>\n" +
                    "          <trim>false</trim>\n" +
                    "        </hudson.model.StringParameterDefinition>\n" +
                    "        <hudson.model.StringParameterDefinition>\n" +
                    "          <name>cameraTouch</name>\n" +
                    "          <description>对焦模式: TouchAF、UnTouchAF</description>\n" +
                    "          <defaultValue></defaultValue>\n" +
                    "          <trim>false</trim>\n" +
                    "        </hudson.model.StringParameterDefinition>\n" +
                    "      </parameterDefinitions>\n" +
                    "    </hudson.model.ParametersDefinitionProperty>\n" +
                    "  </properties>\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "    <script>" + script + "</script>\n" +
                    "    <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>CameraObjective</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.updateJob(jobName, xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CameraSub JOB
    public void createCMSubJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/CameraSubjectivetool&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("projectName")+" -v2=" +map.get("proofType")+ " -v3=" + map.get("localPath")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // CmaStress JOB
    public void createCmaStressJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/blue/CameraStress&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("cameraFeatures")+" -v2=" +map.get("cameraLens")+ " -v3=" + map.get("cameraResolution")+ " -v4=" +map.get("testNum")+ " -v5=" +map.get("cameraMode")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Flare JOB
    public void createFlareJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/Flare&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("projectName")+" -v2=" +map.get("cameraLens")+ " -v3=" + map.get("scenes")+ " -v4=" +map.get("directory")+ " -v5=" +map.get("excelPath")+ " -v6=" +map.get("deviceNo")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // BlueHeartBeat JOB
    public void createBlueHeartBeatJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/blue/XinTiaoTools&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("testCase")+" -v2=" +map.get("runTime")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TopApp JOB
    public void createTopAppJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/TopUsage&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("installApp")+" -v2=" +map.get("unInstallApp")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Wifi干扰 JOB
    public void createWifiDisturbJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/wifiInterfer&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("projectName")+" -v2=" +map.get("testStage")+ " -v3=" + map.get("testPlan")+ " -v4=" +map.get("engineer")+ " -v5=" +map.get("device")+ " -v6=" +map.get("testTime")+ " -v7=" +map.get("initLevel")+ " -v8=" +map.get("power")+ " -v9=" +map.get("lineLoss")+ " -v10=" +map.get("scenario")+ " -v11=" +map.get("network")+ " -v12=" +map.get("spectrum")+ " -v13=" +map.get("channel")+ "&apos;\n" +
                    "}\n" +
                    "}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 蓝区多机升级 JOB
    public void createBlueUpgradeJob(Map<String, String> map){
        try {
            String script = "pipeline {\n" +
                    "agent {\n" +
                    "node {\n" +
                    "label &quot;" +map.get("labelName")+ "&quot;\n" +
                    "}}\n" +
                    "stages{\n" +
                    "stage(&apos;Init&apos;){\n" +
                    "steps {\n" +
                    "script {\n" +
                    "switch(&quot;" +map.get("platformType")+ "&quot;){\n" +
                    "case &apos;Qualcomm&apos;:\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/blue/brushToolflash-Q&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "break;\n" +
                    "case &apos;MTK&apos;:\n" +
                    "checkout([$class: &apos;SubversionSCM&apos;, additionalCredentials: [], excludedCommitMessages: &apos;&apos;, excludedRegions: &apos;&apos;, excludedRevprop: &apos;&apos;, excludedUsers: &apos;&apos;, filterChangelog: false, ignoreDirPropChanges: false, includedRegions: &apos;&apos;, locations: [[cancelProcessOnExternalsFail: true, credentialsId: &apos;25722b62-ef36-436e-893d-85b94bc55ec2&apos;, depthOption: &apos;infinity&apos;, ignoreExternalsOption: true, local: &apos;.&apos;, remote: &apos;http://svn.ontim.cn:8081/svn/ontim/test_tools/02 code/blue/brushToolflash-M&apos;]], quietOperation: true, workspaceUpdater: [$class: &apos;UpdateUpdater&apos;]])\n" +
                    "break;\n" +
                    "default:\n" +
                    "println(&apos;error platformType&apos;)\n" +
                    "}}}}\n" +
                    "stage(&apos;Excute&apos;){\n" +
                    "steps{\n" +
                    "withAnt{\n" +
                    "bat &apos;python %WORKSPACE%/prog.py -v1=" +map.get("reqModel")+" -v2=" +map.get("localPath")+ " -v3=" + map.get("versionPath")+ " -v4=" +map.get("executeType")+ " -v5=" +map.get("platformType")+ "&apos;\n" +
                    "}}}}}";

            String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                    "<flow-definition plugin=\"workflow-job@1207.ve6191ff089f8\">\n" +
                    "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2759.v87459c4eea_ca_\">\n" +
                    "  <script>" + script + "</script>\n" +
                    "  <sandbox>true</sandbox>\n" +
                    "  </definition>\n" +
                    "  <triggers/>\n" +
                    "  <authToken>" +map.get("token")+ "</authToken>\n" +
                    "  <disabled>false</disabled>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob(map.get("jobName"), xml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        JenkinsUtil ju = new JenkinsUtil();
//        String jobName = "CameraObjective";
//        String apiToken = "CameraObjective";

//        System.out.println(ju.getJobConfig("10037974_CameraObjective"));

//        System.out.println(ju.getJobConfig(jobName));
//        System.out.println(ju.checkNodeOnline("hard-test-node2"));

//        Map<String, String> cmaMap = new HashMap();
//        cmaMap.put("labelName", "test_node_chen");
//        cmaMap.put("jobName", "chenfei-test");
//        cmaMap.put("projectName", "Q7503");
//        cmaMap.put("cameraLens", "后摄");
//        cmaMap.put("cameraMode", "2");
//        cmaMap.put("cameraTime", "6");
//        cmaMap.put("cameraTouch", "UnTouchAF");

//        ju.copyJenkinsJob("bbb", jobName);

//        if(!(ju.isJenkinsJobExist("chenfei-test"))){
//            ju.createCM3AJob(cmaMap);
//        }
//        ju.buildJob("chenfei-test");

//        ju.buildJobWithParam("chenfei-test2", cmaMap);
//        ju.deleteJob("chenfei-test");


//        ju.checkNodeOnline("hard-test-node2");
//        String xml = ju.getJobConfig(jobName);
//        System.out.println(xml);
//        ju.reqLastBuild(jobName);
//        List<Integer> builds = ju.getJobBuildListAll(jobName);
//        System.out.println(builds);
//        String sss = ju.getBuildResult(builds.get(0), jobName);
//        System.out.println(sss);
//        System.out.println(ju.isFinished(jobName, builds.get(0)));
//        boolean stop = ju.stopJenkinsJob(jobName);
//        System.out.println(stop);
//        ju.stopLastJobBuild(jobName);

        Map<String, String> map = new HashMap<>();
        map.put("labelName", "test_node_chen");
        map.put("projectName", "Q6803");
        map.put("cameraLens", "rear");
        map.put("model", "1");
        map.put("intervals", "6");
        map.put("focusType", "TAF");
        map.put("number", "20");
        System.out.println(map);
        ju.buildParamJob("PowerOnOff", "PowerOnOff", map);

//        List<Integer> list = ju.getJobBuildListAll(jobName);
//        System.out.println(list);
//        try {
//            boolean isSuccess = ju.isSuccess(jobName, list.get(0));
//            System.out.println(isSuccess);
//            boolean isFinished = ju.isFinished(jobName, list.get(0));
//            System.out.println(isFinished);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }

}