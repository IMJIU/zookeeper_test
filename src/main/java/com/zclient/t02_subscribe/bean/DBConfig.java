package com.zclient.t02_subscribe.bean;
 
/** 
 * 用于记录WorkServer（工作服务器）的配置信息 
 */  
public class DBConfig {  
    private String dbUrl;  
    private String dbPwd;  
    private String dbUser;  
    public String getDbUrl() {  
        return dbUrl;  
    }  
    public void setDbUrl(String dbUrl) {  
        this.dbUrl = dbUrl;  
    }  
    public String getDbPwd() {  
        return dbPwd;  
    }  
    public void setDbPwd(String dbPwd) {  
        this.dbPwd = dbPwd;  
    }  
    public String getDbUser() {  
        return dbUser;  
    }  
    public void setDbUser(String dbUser) {  
        this.dbUser = dbUser;  
    }  
    @Override  
    public String toString() {  
        return "ServerConfig [dbUrl=" + dbUrl + ", dbPwd=" + dbPwd + ", dbUser=" + dbUser + "]";  
    }  
      
      
}  