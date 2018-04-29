package repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hammer on 2018/4/29.
 */
public class ConnectionPool {

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        cpds = getCpds();
    }

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8";

    private static final String DB_USERNAME = "root";

    private static final String DB_PASSWORD = "123456";

    private ComboPooledDataSource cpds;

    public Connection getConnection() {
        try {
            return this.cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("connection can not be retrieved");
        }

    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            synchronized (ConnectionPool.class) {
                if(connectionPool == null) {
                    connectionPool = new ConnectionPool();
                }
            }
        }
        return connectionPool;
    }

    private ComboPooledDataSource getCpds() {
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(DB_DRIVER); //loads the jdbc driver
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            throw new RuntimeException("load driver failed");
        }
        cpds.setJdbcUrl(DB_URL);
        cpds.setUser(DB_USERNAME);
        cpds.setPassword(DB_PASSWORD);
        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);
        return cpds;
    }
}
