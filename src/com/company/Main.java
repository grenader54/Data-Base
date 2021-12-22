package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataBase database = DataBase.initializedSingleton();

        try {
            while (true) {
                System.out.println("Select an action:\n1) Create User;\n2) Edit User;\n3) Delete User;\n4) Viewing User;\n5) Viewing Users;\n6) Loading from a file;\n7) Add users to the database;\n8) Create a database from users");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        database.createUser();
                        break;
                    case 2:
                        database.editUser();
                        break;
                    case 3:
                        database.deleteUser();
                        break;
                    case 4:
                        System.out.println("Select the user whose information you want to view");
                        int numberOfUser = scanner.nextInt();
                        scanner.nextLine();
                        database.printUser(numberOfUser);
                        break;
                    case 5:
                        database.printUsers();
                        break;
                    case 6:
                        database.readingFromFile();
                        break;
                    case 7:
                        database.writingToFileAllUsers(true);
                        break;
                    case 8:
                        database.writingToFileAllUsers(false);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



