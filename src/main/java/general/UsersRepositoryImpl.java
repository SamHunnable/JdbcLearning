package general;

import java.sql.*;

public class UsersRepositoryImpl implements UsersRepository {


    private Statement prepareStatement(Connection connection) {
//        connection = DatabaseSource.getConnection();
        Statement statement = null;
        try {
//            System.out.println("Connection autocommit=" + connection.getAutoCommit());
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }


    public User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        String sql = "INSERT INTO users (firstName,Lastname) VALUES ('" + firstName + "', '" + lastName + "')";

        try (Connection connection = DatabaseSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.execute();
            ResultSet idResultSet = statement.getGeneratedKeys();
            if (idResultSet.next()) {
                user.setId(idResultSet.getInt(1));
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Added user with id " + user.getId() + ", " + firstName + " " + lastName);
        return user;
    }

    public User updateUser(int id, String firstName, String lastName) {
        String sql = "UPDATE users SET firstName = '" + firstName + "', lastname = '" + lastName + "' " + "WHERE id = " + id;
        User user = new User();

        try (Connection connection = DatabaseSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("User was:");
            findUserById(id);
            statement.execute();
//            ResultSet resultSet = statement.getGeneratedKeys();
//
//            if (resultSet.next()) {
//                user.setId(resultSet.getInt(1));
//                user.setFirstName(resultSet.getString("firstName"));
//                user.setLastName(resultSet.getString("lastName"));
//            }
            System.out.println("and is now:");
            user = findUserById(id);

//            System.out.println("updated");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return null;
    }

    public void deleteUser(int id) {
        User user = new User();
        String sql = "DELETE FROM users WHERE id=" + id;

        try (Connection connection = DatabaseSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            user = findUserById(id);
            statement.execute();
//            ResultSet resultSet = statement.getGeneratedKeys();

//            while (resultSet.next()) {
//                user.setFirstName(resultSet.getString("firstName"));
//                user.setLastName(resultSet.getString("lastName"));
//            }

            statement.close();
            System.out.println("Deleted user " +  user.getFirstName() + " " + user.getLastName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
