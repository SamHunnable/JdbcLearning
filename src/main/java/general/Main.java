package general;

public class Main {

    public static void main(String[] args) {
        DatabaseSource.establishDatasource();
        UsersRepository usersRepository = new UsersRepositoryImpl();
//        usersRepository.createUser("Sam", "Hunnable2");
        User user = usersRepository.findUserById(2);
        DatabaseSource.closeConnection();
    }

}
