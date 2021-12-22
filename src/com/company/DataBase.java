package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class DataBase {
    private Vector<User> users;
    private Scanner scanner;
    private File file;
    private PrintWriter fl;
    private static DataBase instance;

    private DataBase() {
        users = new Vector<>();
        scanner = new Scanner(System.in);
    }

    public static DataBase initializedSingleton() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    private int validationUserNumber(int numberOfUser) {
        numberOfUser--;
        while (numberOfUser < 0 || numberOfUser >= users.size()) {
            System.out.println("The user with this number doesn't exist");
            System.out.println("Enter a number of user again!");
            numberOfUser = scanner.nextInt();
            scanner.nextLine();
            numberOfUser--;
        }
        return numberOfUser;
    }

    private int validationRoleNumber(int roleNumber) {
        while (roleNumber < 1 || roleNumber > 5) {
            System.out.println("The role with this number doesn't exist");
            System.out.println("Enter a number of role again!");
            roleNumber = scanner.nextInt();
            scanner.nextLine();
        }
        return roleNumber;
    }

    public void writingToFileAllUsers(boolean append) throws IOException {

        file = new File("database");
        fl = new PrintWriter(new FileWriter(file, append));
        for (int numberOfUser = 0; numberOfUser < users.size(); numberOfUser++) {
            fl.write(users.get(numberOfUser).getName() + "\n");
            fl.write(users.get(numberOfUser).getSurname() + "\n");
            fl.write(users.get(numberOfUser).getEmail() + "\n");
            fl.write(users.get(numberOfUser).getFirstRole() + "\n");
            fl.write(users.get(numberOfUser).getSecondRole() + "\n");
            Vector<String> numbers = users.get(numberOfUser).getPhoneNumber();
            int count = users.get(numberOfUser).getPhoneNumber().size();
            int number = 0;
            while (number != count) {
                fl.write(numbers.get(number) + "\n");
                number++;
            }
            while (count < 3) {
                fl.write("" + "\n");
                count++;
            }
        }
        fl.close();
    }

    public void readingFromFile() throws FileNotFoundException {
        Scanner s = new Scanner(new FileInputStream("database"));
        users.clear();
        while (s.hasNextLine()) {
            User newUser = new User();
            newUser.setName(s.nextLine());
            newUser.setSurname(s.nextLine());
            newUser.setEmail(s.nextLine());
            newUser.setFirstRole(s.nextLine());
            newUser.setSecondRole(s.nextLine());
            try {
                for (int count = 0; count < 3; count++) {
                    String number = s.nextLine();
                    if (!number.equals(""))
                        newUser.addNewPhoneNumber(number, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            users.add(newUser);
        }
    }

    public void createUser() {
        User newUser = new User();
        System.out.println("Enter your name:");
        newUser.setName(scanner.nextLine());
        System.out.println("Enter your surname:");
        newUser.setSurname(scanner.nextLine());
        System.out.println("Enter your email:");
        newUser.setEmail(scanner.nextLine());
        System.out.println("Enter number of roles (from 1 to 2)");
        int numberOfRoles = scanner.nextInt();
        scanner.nextLine();
        while (numberOfRoles < 1 || numberOfRoles > 2) {
            System.out.println("Incorrect number of roles");
            numberOfRoles = scanner.nextInt();
            scanner.nextLine();
        }
        for (int i = 0; i < numberOfRoles; i++)
            newUser.addNewRole();
        System.out.println("Enter the number of phones you want to add:");
        int count = scanner.nextInt();
        scanner.nextLine();
        while (count > 3 || count < 1) {
            System.out.println("Incorrect number of phones!");
            System.out.println("Enter another number of phones:");
            count = scanner.nextInt();
            scanner.nextLine();
        }
        for (int i = 0; i < count; i++) {
            System.out.println("Enter " + (i + 1) + " number:");
            String number = scanner.nextLine();
            newUser.addNewPhoneNumber(number, i);
        }
        users.add(newUser);
    }

    public void deleteUser() {
        printUsers();
        System.out.println("Enter a number of user you want to delete");
        int numberOfUser = scanner.nextInt();
        scanner.nextLine();
        numberOfUser = validationUserNumber(numberOfUser);
        users.remove(numberOfUser);
    }

    public void editUser() {
        printUsers();
        System.out.println("Enter a number of user you want to change:");
        int numberOfUser = scanner.nextInt();
        scanner.nextLine();
        numberOfUser = validationUserNumber(numberOfUser);
        printUser(numberOfUser + 1);
        System.out.println("Select the number of the parameter you want to change:");
        int numberOfParameter = scanner.nextInt();
        scanner.nextLine();
        while (numberOfParameter <= 0 || numberOfParameter > 6) {
            System.out.println("Incorrect number of parameter!");
            System.out.println("Select the number of the parameter you want to change:");
            numberOfParameter = scanner.nextInt();
            scanner.nextLine();
        }
        switch (numberOfParameter) {
            case 1:
                System.out.println("Enter your name");
                users.get(numberOfUser).setName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Enter your surname");
                users.get(numberOfUser).setSurname(scanner.nextLine());
                break;
            case 3:
                System.out.println("Enter your email");
                users.get(numberOfUser).setSurname(scanner.nextLine());
                break;
            case 4:
                users.get(numberOfUser).setFirstRole("");
                users.get(numberOfUser).addNewRole();
                break;
            case 5:
                users.get(numberOfUser).setSecondRole("");
                if (users.get(numberOfUser).getFirstRole().equals("")) {
                    System.out.println("Choose your role in company:\n1) USER (1 level);\n2) CUSTOMER (1 level);\n3) ADMIN (2 level);\n4) PROVIDER (2 level);\n5) SUPER_ADMIN (3 level).\nAt the same time, you can have one role at the first and the second level.\nIf you choose a role on the third level, you will not be able to choose a role on another level");
                    int choiceSecondRole = scanner.nextInt();
                    scanner.nextLine();
                    choiceSecondRole = validationRoleNumber(choiceSecondRole);
                    users.get(numberOfUser).setSecondRole(getRoleByNumber(choiceSecondRole));
                } else
                    users.get(numberOfUser).addNewRole();
                break;
            case 6:
                for (int count = 0; count < users.get(numberOfUser).getPhoneNumber().size(); count++) {
                    System.out.println((count + 1) + " - " + users.get(numberOfUser).getPhoneNumber().get(count));
                }
                System.out.println("Select a number of phone you want to edit");
                int number = scanner.nextInt();
                scanner.nextLine();
                while (number < 0 || number > 3) {
                    System.out.println("Incorrect number of phone. You have to choose from 1 to 3");
                    number = scanner.nextInt();
                    scanner.nextLine();
                }
                users.get(numberOfUser).editPhoneNumber(number - 1);
                break;
        }
    }

    private String getRoleByNumber(int numberOfRole) {
        switch (numberOfRole) {
            case 1:
                return "USER";
            case 2:
                return "CUSTOMER";
            case 3:
                return "ADMIN";
            case 4:
                return "PROVIDER";
            case 5:
                return "SUPER_ADMIN";
        }
        return "";
    }

    public void printUsers() {
        if (users.size() == 0) {
            System.out.println("Users are missing from the database");
            return;
        }
        for (int userNumber = 1; userNumber <= users.size(); userNumber++) {
            System.out.println((userNumber) + " User");
            printUser(userNumber);
        }
    }

    public void printUser(int numberOfUser) {
        if (users.size() == 0) {
            System.out.println("Users are missing from the database");
            return;
        }
        numberOfUser = validationUserNumber(numberOfUser);
        System.out.println("1) Name: " + users.get(numberOfUser).getName());
        System.out.println("2) Surname: " + users.get(numberOfUser).getSurname());
        System.out.println("3) Email: " + users.get(numberOfUser).getEmail());
        System.out.println("4) FirstRole: " + users.get(numberOfUser).getFirstRole());
        System.out.println("5) SecondRole: " + users.get(numberOfUser).getSecondRole());
        System.out.println("6) Phone numbers:");
        Vector<String> phoneNumbers = users.get(numberOfUser).getPhoneNumber();
        for (int count = 0; count < phoneNumbers.size(); count++) {
            System.out.println((count + 1) + " - " + phoneNumbers.get(count));
        }
    }
}