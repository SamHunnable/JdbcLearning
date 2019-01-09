package general;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;

public class DatabaseSource {


    static JdbcDataSource dataSource;
    static Connection connection;


    public static void establishDatasource() {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        try {
            connection = dataSource.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static Connection getConnection(){


        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
