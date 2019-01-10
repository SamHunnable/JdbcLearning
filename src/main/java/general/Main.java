package general;

import java.util.List;
import java.util.Scanner;

public class Main {

    UsersRepository usersRepository;
    Scanner scanner;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        DatabaseSource.establishDatasource();
        usersRepository = new UsersRepositoryImpl();
        boolean repeat = true;
        String command;

        scanner = new Scanner(System.in);


        while (repeat) {
            System.out.println("What would you like to do?");
            System.out.println("create, search, update, delete, exit");
            command = scanner.nextLine();
            switch (command) {
                case "create":
                    create();
                    break;
                case "search":
                    search();
                    break;
                case "update":
                    update();
                    break;
                case "delete":
                    delete();
                    break;
                case "exit":
                    repeat = false;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }


        scanner.close();
    }

    public void create() {
        System.out.println("Enter first and last name of record to add");
        String firstName = scanner.nextLine();
        String lastName = scanner.nextLine();
        usersRepository.createUser(firstName, lastName);
    }

    public void search() {
        System.out.println("You have selected search. Would you like to search by id or by name?");
        String command = scanner.nextLine();

        switch (command) {
            case "id":
                System.out.println("Enter id of record you wish to retrieve");
                int id = scanner.nextInt();
                User user = usersRepository.findUserById(id);
                break;
            case "name":
                System.out.println("Enter the las name you wish to search for:");
                String lastNameSearch = scanner.nextLine();
                List<User> users = usersRepository.searchByLastName(lastNameSearch);
                if (users == null || users.size() < 1) System.out.println("No users found");
                else {
                    for (User foundUser : users) {
                        System.out.println(foundUser.getId() + " " + foundUser.getFirstName() + " " + foundUser.getLastName());
                    }
                }
                break;
            default:
                System.out.println("Invalid command");
                break;
        }

    }

    public void update() {
        System.out.println("Enter id of record you wish to update");
        int updateId = scanner.nextInt();
        usersRepository.findUserById(updateId);
        System.out.println("Enter the new first name");
        String updateFirstName = scanner.nextLine();
        System.out.println("Enter the new last name");
        String updateLastName = scanner.nextLine();
        usersRepository.updateUser(updateId, updateFirstName, updateLastName);
    }

    public void delete() {
        System.out.println("Enter id of record you wish to delete");
        int deleteId = scanner.nextInt();
        usersRepository.deleteUser(deleteId);
    }

}
