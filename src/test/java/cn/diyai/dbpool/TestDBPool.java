package cn.diyai.dbpool;

import cn.diyai.util.PropertiesUtils;

import java.sql.CallableStatement;
import java.sql.Connection;

public class TestDBPool {
    private static String dbName = PropertiesUtils.getproperties("DB_NAME","ctu_cmpp");
    
    public static boolean insert(String id){
        Connection conn = null;
        DBConnectionManager connectionMan = null;
        try{
            //得到唯一实例
            connectionMan = DBConnectionManager.getInstance();
            conn = connectionMan.getConnection(dbName);
            if(conn == null){
                return false;
            }

            CallableStatement cs = conn.prepareCall("{call produre(?)}");
            cs.setString(1,id);
            cs.execute();
            cs.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(connectionMan != null){
                connectionMan.freeConnection(dbName, conn);
            }
        }
        return false;
    }

    public void test(){
        insert("1");
    }
}
