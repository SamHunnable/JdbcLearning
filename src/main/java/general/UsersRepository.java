package general;

public interface UsersRepository {

    User createUser(String firstName, String lastName);
    User updateUser(int id, String firstName, String lastName);
    void deleteUser(int id);
    User findUserById(int id);

}
