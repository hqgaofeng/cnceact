package com.cnce.common.utils;

import com.cnce.project.template.domain.TempSyncDO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;

public class PGSQLUtil {
    //pgsql配置
    private static String url = "jdbc:postgresql://192.168.3.162:5433/jiradb";
//    private static String url = "jdbc:postgresql://192.168.100.203:5433/jiradb";
    private static String userName ="jiradbuser";
    private static String userPwd = "jiradbuser";
    private static String driverClass = "org.postgresql.Driver";

    //获取数据库的连接
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, userName, userPwd);
            conn.setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public static List<HashMap<String,Object>> loadUserData(String sql){
        Statement stmt = null;
        List<HashMap<String ,Object>> list = new ArrayList<>();
        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> map=new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    //getColumnName获取列名
                    String name = metaData.getColumnName(i);
                    Object object = rs.getObject(i);
                    map.put(name, object);
                }
                list.add(map);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return list;
    }


    // 获取jira数据库项目数据
    public static List<TempSyncDO> loadProjectData() {
        List<TempSyncDO> list = new ArrayList<>();
        String sql = "(SELECT ID, customvalue, disabled, 'XA' AS region FROM customfieldoption WHERE customfield = '10801' ) \n" +
                "UNION\n" +
                "(SELECT ID, customvalue, disabled, 'BJ' AS region FROM customfieldoption WHERE customfield = '11101')";
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                TempSyncDO temp = new TempSyncDO();
                temp.setJiraId(rs.getInt("id"));
                temp.setpName(rs.getString("customvalue"));
                String disabled = rs.getString("disabled");
                temp.setpStatus("N".equals(disabled)?1:0);
                String region = rs.getString("region");
                temp.setRegion(region);
                list.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 获取jira数据库部门数据
    /*public static List<ProjectDeptDO> loadDeptData() {
        List<ProjectDeptDO> list = new ArrayList<>();
        String sql = "SELECT region, \"部门\", \"domain\"\n" +
                "FROM public.\"人员信息\"\n" +
                "WHERE 部门 NOT IN ('EMS管理部','人力资源部','销售三部','产品市场部','交付一部','总经办','IT部','财务部','商务一部','商务二部','行政部','商务部','交付部','HMD PDT部','荣耀PDT部','业务拓展部','HMD部','H1/运营商PDT部','销售部','MOTO PDT部','市场部','None')\n" +
                "GROUP BY region, \"部门\", \"domain\"";
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ProjectDeptDO deptDO = new ProjectDeptDO();
                list.add(deptDO);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }*/

}
