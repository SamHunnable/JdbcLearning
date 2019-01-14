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
//            connection.setAutoCommit(false);
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
                user.setUserId(idResultSet.getInt(1));
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public User updateUser(int id, String firstName, String lastName) {
        String sql = "UPDATE users SET firstName = '" + firstName + "', lastname = '" + lastName + "' " + "WHERE UserId = " + id;
        User user = new User();

        try (Connection connection = DatabaseSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("User was:");
            findUserById(id);
            statement.execute();
//            ResultSet resultSet = statement.getGeneratedKeys();
//
//            if (resultSet.next()) {
//                user.setUserId(resultSet.getInt(1));
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

        String sql = "DELETE FROM users WHERE UserId=" + id;

        try (Connection connection = DatabaseSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            user = findUserById(id);
            statement.execute();

            statement.close();
            System.out.println("Deleted user " + user.getFirstName() + " " + user.getLastName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;

    }

    public User findUserById(int id) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE UserId = ?";

        try (Connection connection = DatabaseSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                user.setUserId(result.getInt("UserId"));
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
                int returnedId = result.getInt("UserId");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");

                user.setUserId(returnedId);
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
                user.setUserId(result.getInt("UserId"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public void getUserandPermissionById(int id) {

        String sql = "SELECT * FROM users INNER JOIN permissions ON users.id=permissions.id WHERE users.id=?;";

        try (Connection connection = DatabaseSource.getConnection()) {

            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            connection.commit();

            while (resultSet.next()) {
//                resultSet.getMetaData().
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("firstName"));
                System.out.println(resultSet.getString("lastName"));
                System.out.println(resultSet.getString("permission"));
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseSource.establishDatasource();
        UsersRepositoryImpl usersRepository = new UsersRepositoryImpl();
        usersRepository.createUserAndPermission("test", " permission test", "a permission");
    }

    public void createUserAndPermission(String firstName, String lastName, String permission) {

        String userSql = "INSERT INTO users (firstName, lastName) VALUES (?, ?)";
        String permissionSql = "INSERT INTO permissions (permission, id) VALUES (?, ?)";


        try (Connection connection = DatabaseSource.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement userStatement = connection.prepareStatement(userSql);
            userStatement.setString(1, firstName);
            userStatement.setString(2, lastName);


            userStatement.execute();
            ResultSet userResult = userStatement.getGeneratedKeys();
            int userId = userResult.getInt("id");

            PreparedStatement permissionStatement = connection.prepareStatement(permissionSql);
            permissionStatement.setString(1, permission);
            permissionStatement.setInt(2, userId);

            permissionStatement.execute();

            connection.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
