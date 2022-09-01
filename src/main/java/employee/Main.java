package employee;

import employee.pojo.utils.EmployeeConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        getInput();
    }

    public static void getInput() throws Exception {
        Scanner sc = new Scanner(System.in);
        String input;
        boolean exit = false;
        Manage employee = new Manage();
        showMenu();
        while (true) {
            input = sc.nextLine().trim();
            switch (input) {
                case "1" -> employee.add();
                case "2" -> employee.show();
                case "3" -> {
                    employee.show();
                    employee.edit();
                }
                case "4" -> {
                    employee.show();
                    employee.delete();
                }
                case "5" -> {
                    System.out.println("EXIT THE PROGRAM");
                    exit = true;
                }
                default -> {
                    System.out.println("");
                    System.out.println(EmployeeConstants.INVALID_OUTPUT);
                }
            }
            if (exit) {
                break;
            } else {
                showMenu();
            }
        }
    }


    static void showMenu() {
        System.out.println("----------Menu----------");
        System.out.println(EmployeeConstants.ADD);
        System.out.println(EmployeeConstants.SHOW);
        System.out.println(EmployeeConstants.EDIT_UPDATE);
        System.out.println(EmployeeConstants.DELETE);
        System.out.println(EmployeeConstants.EXIT);
        System.out.println("------------------------");
        System.out.print(EmployeeConstants.CHOOSE_FUNCTION);
    }
}
