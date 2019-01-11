package general;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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
        String sql = "INSERT INTO users (firstName, lastName) VALUES ('" + firstName + "', '" + lastName + "')";

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

    public User deleteUser(int id) {
        User user = null;

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
            System.out.println("Deleted user " + user.getFirstName() + " " + user.getLastName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;

    }

    public User findUserById(int id) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DatabaseSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                user.setId(result.getInt("id"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
            }
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public List<User> searchByLastName(String lastNameSearch) {
        List<User> users = new LinkedList<>();
        String sql = "SELECT * FROM users WHERE lastName = ?";

        try (Connection connection = DatabaseSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, lastNameSearch);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                User user = new User();
                int returnedId = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");

                user.setId(returnedId);
                user.setFirstName(firstName);
                user.setLastName(lastName);

                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }


    public List<User> getAllRecords() {
        List<User> users = new LinkedList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = DatabaseSource.getConnection();
             Statement statement = prepareStatement(connection)) {

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

}
