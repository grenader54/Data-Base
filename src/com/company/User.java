package com.company;

import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class User {
    private Scanner scanner;
    private String name;
    private String surname;
    private String email;
    private String firstRole = "";
    private String secondRole = "";
    private Vector<String> phoneNumbers;

    public User() {
        phoneNumbers = new Vector<>(3);
        for (int i = 0; i < 3; i++)
            phoneNumbers.add("");
        scanner = new Scanner(System.in);
    }

    public void addRole(String role) {
        if (!getFirstRole().equals("") && !getSecondRole().equals(""))
            System.out.println("All roles are taken!");
        else if (getFirstRole().equals(""))
            setFirstRole(role);
        else
            setSecondRole(role);
    }

    public void addNewRole() {
        if (this.getFirstRole().equals("SUPER_ADMIN") || this.getSecondRole().equals("SUPER_ADMIN"))
            return;
        while (true) {
            System.out.println("Choose your role in company:\n1) USER (1 level);\n2) CUSTOMER (1 level);\n3) ADMIN (2 level);\n4) PROVIDER (2 level);\n5) SUPER_ADMIN (3 level).\nAt the same time, you can have one role at the first and the second level.\nIf you choose a role on the third level, you will not be able to choose a role on another level");
            int choiceRole = scanner.nextInt();
            scanner.nextLine();
            if (choiceRole < 1 || choiceRole > 6) {
                System.out.println("The role with this number doesn't exist");
                continue;
            }
            if (choiceRole == 5) {
                if (!this.getFirstRole().equals("") || !this.getSecondRole().equals("")) {
                    System.out.println("You cannot assign a second role SUPER_ADMIN to a user if he already has another role");
                    continue;
                }
                addRole("SUPER_ADMIN");
                break;
            }
            if (choiceRole == 1 || choiceRole == 2) {
                if (this.getFirstRole().equals("USER") || this.getFirstRole().equals("CUSTOMER") || this.getSecondRole().equals("USER") || Objects.equals(this.getSecondRole(), "CUSTOMER")) {
                    System.out.println("You already have a role with the same level (level 1). Choose your role again (level 2)");
                    continue;
                }
                if (choiceRole == 1)
                    addRole("USER");
                else
                    addRole("CUSTOMER");
                break;
            } else if (choiceRole == 3 || choiceRole == 4) {
                if (this.getFirstRole().equals("ADMIN") || this.getFirstRole().equals("PROVIDER") || this.getSecondRole().equals("ADMIN") || Objects.equals(this.getSecondRole(), "PROVIDER")) {
                    System.out.println("You already have a role with the same level (level 2). Choose your role again (level 1)");
                    continue;
                }
                if (choiceRole == 3)
                    addRole("ADMIN");
                else
                    addRole("PROVIDER");
                break;
            }
        }
    }

    public void addNewPhoneNumber(String number, int numberOfPosition) {
        number = validationNumber(number);
        phoneNumbers.setElementAt(number, numberOfPosition);
    }

    private String validationEmail(String emailName) {
        while (!emailName.matches("(\\w+)@(\\w+)\\.(\\w+)")) {
            System.out.println("Incorrect email was entered!");
            System.out.println("Enter your email:");
            emailName = scanner.nextLine();
        }
        return emailName;
    }

    private String validationNumber(String number) {
        while (!number.matches("375([0-9]+)")) {
            System.out.println("Incorrect phone number was entered!");
            System.out.println("Enter phone number:");
            number = scanner.nextLine();
        }
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = validationEmail(email);
    }

    public void setFirstRole(String firstRole) {
        this.firstRole = firstRole;
    }

    public void setSecondRole(String secondRole) {
        this.secondRole = secondRole;
    }
    public void editPhoneNumber(int recordNumber) {
        System.out.println("Enter your phone number");
        String newPhoneNumber = validationNumber(scanner.nextLine());
        this.phoneNumbers.remove(recordNumber);
        this.phoneNumbers.insertElementAt(newPhoneNumber, recordNumber);
    }

    public String getFirstRole() {
        return this.firstRole;
    }

    public String getSecondRole() {
        return this.secondRole;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public Vector<String> getPhoneNumber() {
        return this.phoneNumbers;
    }

}
