package repository;


import domain.Foo;



import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by hammer on 2018/4/29.
 */


public class Operation {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Operation.class);

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8";

    private static final String DB_USERNAME = "root";

    private static final String DB_PASSWORD = "123456";

    private static final String SELECT_SQL = "select * from foo where id = ?";

    private static final String SELECT_FOR_UPDATE_SQL = "select * from foo where id = ? for update";

    private static final String UPDATE_SQL = "update foo set value = ? , version = ? + 1 where version = ? and id = ?";

    public static AtomicInteger failCounter = new AtomicInteger(0);



    private static Connection getDbConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

//            connection.setAutoCommit(false);
            return connection;
        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }

    public static void executeQuery(int recordId, int threadId) {

        /**
         * no connection pool, every time new connection
         */
//        Connection connection = getDbConnection();
        /**
         * todo using c3p0 connection pool
         */
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
//            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED );
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED );
//            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (Exception e ) {
            e.printStackTrace();
            throw new RuntimeException("isolation level setting failed");
        }

        if(connection == null) {
            throw new RuntimeException("no connection ");
        }

        try {
            int count = 0;
            do {
                Foo foo = queryForFoo(connection, SELECT_SQL, recordId);
                count = updateForFoo(connection, UPDATE_SQL, foo, threadId);
                if (count == 0) {
                    failCounter.addAndGet(1);
//                log.info("thread [{}] value [{}] version [{}] attempt [{}]", threadId, foo.getValue() , foo.getVersion(), "failed");
                } else {
//                log.info("thread [{}] value [{}] version [{}] attempt [{}]", threadId, foo.getValue() , foo.getVersion(), "success");
                }
            } while (count == 0);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            throw new RuntimeException("unknown exception");
        } finally {

            try {

                connection.close();
            } catch (Exception e) {

            }
        }
    }

    private static Foo queryForFoo(Connection connection, String sql, int recordId) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, recordId);

        ResultSet resultSet = preparedStatement.executeQuery();
        Foo foo = new Foo();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String value = resultSet.getString("value");
            int version = resultSet.getInt("version");

            foo.setId(id);
            foo.setValue(value);
            foo.setVersion(version);
        }
        resultSet.close();
        preparedStatement.close();
        return foo;


    }

    private static int updateForFoo(Connection connection, String sql, Foo foo, int threadId) throws SQLException{
        String value = foo.getValue();
        String[] values = value.split(",");
        int originValue = Integer.parseInt(values[threadId]);
        values[threadId] = String.valueOf(Integer.parseInt(values[threadId]) + 1);
        StringBuffer sb = new StringBuffer();
        for(String single : values) {
            sb.append(single);
            sb.append(",");
        }
        String finalValue = sb.toString();
        int version = foo.getVersion();
        int id = foo.getId();

        PreparedStatement updateStatement = connection.prepareStatement(sql);
        updateStatement.setString(1, finalValue);
        updateStatement.setInt(2, version);
        updateStatement.setInt(3, version);
        updateStatement.setInt(4, id);

        updateStatement.execute();
        int count = updateStatement.getUpdateCount();
        updateStatement.close();
        return count;


    }

    public static void executeQueryForUpdate(int recordId, int threadId) {

        /**
         * no connection pool, every time new connection
         */
//        Connection connection = getDbConnection();
        /**
         * todo using c3p0 connection pool
         */
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
//            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED );
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED );
//            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (Exception e ) {
            e.printStackTrace();
            throw new RuntimeException("isolation level setting failed");
        }

        if(connection == null) {
            throw new RuntimeException("no connection");
        }



        try {
            Foo foo = queryForFoo(connection, SELECT_FOR_UPDATE_SQL, recordId);


            int count = updateForFoo(connection, UPDATE_SQL, foo, threadId);
            connection.commit();
            if (count == 0) {
                failCounter.addAndGet(1);
//                log.info("thread [{}] value [{}] version [{}] attempt [{}]", threadId, foo.getValue() , foo.getVersion(), "failed");

            } else {
//                log.info("thread [{}] value [{}] version [{}] attempt [{}]", threadId, foo.getValue() , foo.getVersion(), "success");

            }

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("rollback encounter exception");
            }
            e.printStackTrace();
            throw new RuntimeException("unknown exception");
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                throw new RuntimeException("close resource encounter exception");
            }
        }

    }

    private static final String INIT_UPDATE_SQL = "update foo set value = ?, version = ?";

    private static final String initialValue = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,";

    public static void initValues() {
        Connection connection = getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INIT_UPDATE_SQL);
            preparedStatement.setString(1, initialValue);
            preparedStatement.setInt(2, 1);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        initValues();
        executeQuery(2,1);
    }
}
