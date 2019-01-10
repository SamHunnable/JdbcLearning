package general;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;

public final class DatabaseSource {


    static JdbcDataSource dataSource;
//    static Connection connection;

//    private DatabaseSource() {}


    public static void establishDatasource() {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");

    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return connection;
    }

//    public static void closeConnection(){
//        try {
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

}
