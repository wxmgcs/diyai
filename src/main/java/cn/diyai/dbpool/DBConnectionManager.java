package cn.diyai.dbpool;

import cn.diyai.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class DBConnectionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnectionManager.class);
    static private DBConnectionManager instance;//唯一数据库连接池管理实例类
    static private int clients;                 //客户连接数
    private Vector drivers  = new Vector();//驱动信息
    private Hashtable pools=new Hashtable();//连接池

    /**
     * 实例化管理类
     */
    public DBConnectionManager() {
        this.init();
    }

    /**
     * 得到唯一实例管理类
     * @return
     */
    static synchronized public DBConnectionManager getInstance()
    {
        if(instance==null)
        {
            instance=new DBConnectionManager();
        }
        return instance;

    }

    /**
     * 释放连接
     * @param name
     * @param con
     */
    public void freeConnection(String name, Connection con)
    {
        DBConnectionPool pool=(DBConnectionPool)pools.get(name);//根据关键名字得到连接池
        if(pool!=null)
            pool.freeConnection(con);//释放连接
    }

    /**
     * 得到一个连接根据连接池的名字name
     * @param name
     * @return
     */
    public Connection getConnection(String name)
    {
        DBConnectionPool pool=null;
        Connection con=null;
        pool=(DBConnectionPool)pools.get(name);//从名字中获取连接池
        con=pool.getConnection();//从选定的连接池中获得连接

        if(con==null){
            LOGGER.error("数据库连接池中没有可用的数据库连接");
        }
        return con;
    }

    /**
     * 得到一个连接，根据连接池的名字和等待时间
     * @param name
     * @param timeout
     * @return
     */
    public Connection getConnection(String name, long timeout)
    {
        DBConnectionPool pool=null;
        Connection con=null;
        pool=(DBConnectionPool)pools.get(name);//从名字中获取连接池
        con=pool.getConnection(timeout);//从选定的连接池中获得连接
        return con;
    }

    /**
     * 释放所有连接
     */
    public synchronized void release()
    {
        Enumeration allpools=pools.elements();
        while(allpools.hasMoreElements())
        {
            DBConnectionPool pool=(DBConnectionPool)allpools.nextElement();
            if(pool!=null)pool.release();
        }
        pools.clear();
    }

    /**
     * 创建连接池
     * @param dsb
     */
    private void createPools(DSConfigBean dsb)
    {
        DBConnectionPool dbpool=new DBConnectionPool();
        dbpool.setName(dsb.getName());
        dbpool.setDriver(dsb.getDriver());
        dbpool.setUrl(dsb.getUrl());
        dbpool.setUser(dsb.getUsername());
        dbpool.setPassword(dsb.getPassword());
        dbpool.setMaxConn(dsb.getMaxconn());
        LOGGER.info("数据库连接池配置:"+dbpool.toString());
        pools.put(dsb.getName(), dbpool);
    }

    /**
     * 初始化连接池的参数
     */
    private void init()
    {
        //加载驱动程序
        this.loadDrivers();
        //创建连接池
        Iterator alldriver=drivers.iterator();
        while(alldriver.hasNext())
        {
            this.createPools((DSConfigBean)alldriver.next());

        }
        LOGGER.info("创建连接池完毕。。。");
    }

    /**
     * 加载驱动程序
     */
    private void loadDrivers()
    {
        Vector dsConfig=new Vector();
        DSConfigBean dscBean=new DSConfigBean();
        dscBean.setType( PropertiesUtils.getproperties("DB_TYPE","mysql"));
        dscBean.setName(PropertiesUtils.getproperties("DB_NAME","ctu800617"));
        dscBean.setDriver(PropertiesUtils.getproperties("DB_DRIVER","com.mysql.cj.jdbc.Driver"));
        dscBean.setUrl(PropertiesUtils.getproperties("DB_URL","jdbc:mysql://127.0.0.1:3306/ctu_cmpp?useUnicode=true&characterEncoding=UTF8&autoReconnect=true"));
        dscBean.setUsername(PropertiesUtils.getproperties("DB_USERNAME","root"));
        dscBean.setPassword(PropertiesUtils.getproperties("DB_PWD","123456"));
        dscBean.setMaxconn(Integer.parseInt(PropertiesUtils.getproperties("DB_MAX_CONNECTION","200")));
        dsConfig.add(dscBean);
        drivers=dsConfig;
        LOGGER.info("加载驱动程序。。。");
    }
}