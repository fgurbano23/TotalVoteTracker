package com.cgts.services.model.service;

import com.cgts.services.db.*;
import com.cgts.services.tool.properties.PropertiesLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class UserStatusService {

    @Autowired
    private DataSource dataSource;



    public Object getStatus (Integer userId) {
//        String sql= "SELECT ACCT_STATUS, USR_PRF_STATUS " +
//                "FROM USR_ACCOUNT " +
//                "JOIN USR_USER USER2 " +
//                "ON USR_ACCOUNT.ACCT_USR_ID = USER2.USR_ID " +
//                "JOIN USR_PROFILE UP " +
//                "ON USER2.USR_USER_PROFILE_ID = UP.USR_PROFILE_ID " +
//                "WHERE ACCT_USR_ID = ?";
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        Map<String, Object> map = jdbcTemplate.queryForMap(sql, userId);
//        Gson gson = new Gson();
//        String json = gson.toJson(map, new TypeToken<Map<String, Object>>() {}.getType());
        SQLRunner db = null;

        try{

            db = new SQLRunner(dataSource.getConnection());
         //   String sql = PropertiesLoader.getProperty("userPrivileges");
            String sql= "SELECT ACCT_STATUS, USR_PRF_STATUS " +
                        "FROM USR_ACCOUNT " +
                        "JOIN USR_USER USER2 " +
                        "ON USR_ACCOUNT.ACCT_USR_ID = USER2.USR_ID " +
                        "JOIN USR_PROFILE UP " +
                        "ON USER2.USR_USER_PROFILE_ID = UP.USR_PROFILE_ID " +
                        "WHERE ACCT_USR_ID = prm:USER_ID";

            List<AttributeSql> attrs = new ArrayList<AttributeSql>();
            AttributeSql sqlTest = new AttributeSql("USER_ID",userId,0);
            attrs.add(sqlTest);
            Recordset rs = db.runSQL(db.prepareSQL(attrs, sql));
            Gson gson = new Gson();
            String json = gson.toJson(rs.getData(), new TypeToken<ArrayList<Record>>() {}.getType());
            return json;

        } catch (Throwable e) {
            e.printStackTrace();
            return "[]";
        }finally{
            try{
                if(db!=null){
                    db.closeConnection();
                }
            } catch (Throwable e) {

            }
        }
    }

    public Object getPermm(Integer userId) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,userId);
        SQLRunner db = null;
        try{

            db = new SQLRunner(dataSource.getConnection());
            //   String sql = PropertiesLoader.getProperty("userPrivileges");
            String sql= "SELECT SM.SYS_MOD_NAME, listagg(USP.USR_PRIVILEGE_ID,',' ) within group (order by usp.USR_PRIV_NAME) AS PRIVILEGES " +
                    "FROM USR_ACCOUNT UA " +
                    "JOIN USR_USER UU " +
                    "ON UA.ACCT_USR_ID = UU.USR_ID " +
                    "JOIN USR_PROFILE UP " +
                    "ON UP.USR_PROFILE_ID = UU.USR_USER_PROFILE_ID " +
                    "JOIN USR_SYS_MODULE USM " +
                    "ON UP.USR_PROFILE_ID = USM.SYS_USR_PROFILE_ID " +
                    "JOIN SYS_MODULE SM " +
                    "ON USM.SYS_MODULE_ID = SM.SYS_MODULE_ID " +
                    "JOIN USER_PRIVILEGE USP " +
                    "ON USM.USR_PRIVILEGE_ID = USP.USR_PRIVILEGE_ID " +
                    "WHERE UA.ACCT_USR_ID= prm:USER_ID " +
                    "GROUP BY SM.SYS_MOD_NAME";

            List<AttributeSql> attrs = new ArrayList<AttributeSql>();
            AttributeSql sqlTest = new AttributeSql("USER_ID",userId,0);
            attrs.add(sqlTest);
            Recordset rs = db.runSQL(db.prepareSQL(attrs, sql));
            Gson gson = new Gson();
            String json = gson.toJson(rs.getData(), new TypeToken<ArrayList<Record>>() {}.getType());
            return json;

        } catch (Throwable e) {
            e.printStackTrace();
            return "[]";
        }finally{
            try{
                if(db!=null){
                    db.closeConnection();
                }
            } catch (Throwable e) {

            }
        }
    }
}