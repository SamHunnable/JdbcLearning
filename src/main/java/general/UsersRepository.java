package general;

public interface UsersRepository {

    public User createUser(String firstName, String lastName);
    public User updateUser(String firstName, String lastName);
    public void deleteUser(int id);
    public User findUserById(int id);

}
