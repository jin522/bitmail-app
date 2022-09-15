package cn.dayutec.pigeon.db.ver100;

import cn.dayutec.pigeon.utils.PlatformConfUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class DruidPoolUtil {

    DataSource ds;

    //    druid连接池
    public Connection getConn() throws Exception {
        Properties prop = new Properties();
        prop.put("driverClassName", PlatformConfUtil.getString("driverClassName"));
        prop.put("url", PlatformConfUtil.getString("url"));
        prop.put("username", PlatformConfUtil.getString("username"));
        prop.put("password", PlatformConfUtil.getString("password"));
        prop.put("maxActive", PlatformConfUtil.getString("maxActive")); //maximum number of connection in the pool
        prop.put("initialSize", PlatformConfUtil.getString("initialSize"));//initial number of connection
        prop.put("maxWait", PlatformConfUtil.getString("maxWait"));//maximum wait milliseconds for get connection from pool
        prop.put("minIdle", PlatformConfUtil.getString("minIdle"));//minimum number of connection in the pool
        prop.put("timeBetweenEvictionRunsMillis", PlatformConfUtil.getString("timeBetweenEvictionRunsMillis"));// the interval milliseconds to test connection
        prop.put("minEvictableIdleTimeMillis", PlatformConfUtil.getString("minEvictableIdleTimeMillis"));//the minimum milliseconds to keep idle
        prop.put("maxEvictableIdleTimeMillis", PlatformConfUtil.getString("maxEvictableIdleTimeMillis"));//the maximum milliseconds to keep idle
//        prop.put("validationQuery", "describe train_realtime_status.dn"); //validation query
        prop.put("testWhileIdle", PlatformConfUtil.getString("testWhileIdle")); // test connection while idle
        prop.put("testOnBorrow", PlatformConfUtil.getString("testOnBorrow")); // don't need while testWhileIdle is true
        prop.put("testOnReturn", PlatformConfUtil.getString("testOnReturn")); // don't need while testWhileIdle is true

        //create druid datasource
        ds = DruidDataSourceFactory.createDataSource(prop);

        Connection connection = ds.getConnection(); // get connection
        return connection;
    }
}
