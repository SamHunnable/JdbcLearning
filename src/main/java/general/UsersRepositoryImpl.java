package general;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersRepositoryImpl implements UsersRepository {

//    Connection connection;
//    Statement statement;

    public Statement prepareStatement(Connection connection) {
        connection = DatabaseSource.getConnection();
        Statement statement = null;
        try {
            System.out.println("Connection autocommit=" + connection.getAutoCommit());
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }


    public User createUser(String firstName, String lastName) {

        try (Connection connection = DatabaseSource.getConnection();
             Statement statement = prepareStatement(connection)) {
            statement.execute("INSERT INTO users (firstName,Lastname) VALUES ('" + firstName + "', '" + lastName + "')");
            ResultSet idResultSet = statement.getGeneratedKeys();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public User updateUser(String firstName, String lastName) {
        return null;
    }

    public void deleteUser(int id) {

    }

    public User findUserById(int id) {
        User user = new User();

        try (Connection connection = DatabaseSource.getConnection();
             Statement statement = prepareStatement(connection)) {

            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE id=" + String.valueOf(id));

            while (result.next()) {
                int returnedId = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");

                user.setId(returnedId);
                user.setFirstName(firstName);
                user.setLastName(lastName);

                System.out.println(firstName + " " + lastName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }
}
