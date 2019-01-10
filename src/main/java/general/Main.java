package general;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        DatabaseSource.establishDatasource();
        UsersRepository usersRepository = new UsersRepositoryImpl();

        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to do?");
        System.out.println("create, search, update, delete");
        String command = scanner.nextLine();

        switch (command) {
            case "create":
                System.out.println("Enter first and last name of record to add");
                String firstName = scanner.nextLine();
                String lastName = scanner.nextLine();
                usersRepository.createUser(firstName, lastName);
                break;
            case "search":
                search(usersRepository);
                break;
            case "update":
                System.out.println("Enter id of record you wish to update");
                int updateId = scanner.nextInt();
                usersRepository.findUserById(updateId);
                System.out.println("Enter the new first name and last name");
                String updateFirstName = scanner.nextLine();
                String updateLastName = scanner.nextLine();
                usersRepository.updateUser(updateId, updateFirstName, updateLastName);
                break;
            case "delete":
                System.out.println("Enter id of record you wish to delete");
                int deleteId = scanner.nextInt();
                usersRepository.deleteUser(deleteId);
                break;
            default:
                System.out.println("Invalid command");
        }
    }


    public void search(UsersRepository usersRepository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have selected search. Would you like to search by id or by name?");
        String command = scanner.nextLine();

        switch (command) {
            case "id":
                System.out.println("Enter id of record you wish to retrieve");
                int id = scanner.nextInt();
                User user = usersRepository.findUserById(id);
                break;
            case "name":
                System.out.println("Not implemented");
                break;
            default:
                System.out.println("Invalid command");
                break;
        }

    }



}
