package general;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;

public final class DatabaseSource {

    static JdbcDataSource dataSource;

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

}
