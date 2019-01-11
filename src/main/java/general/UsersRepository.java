package general;

import java.util.List;

public interface UsersRepository {

    User createUser(String firstName, String lastName);
    User updateUser(int id, String firstName, String lastName);
    User deleteUser(int id);
    User findUserById(int id);
    List<User> searchByLastName(String lastNameSearch);
    List<User> getAllRecords();

}
