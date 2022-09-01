package employee;

import employee.pojo.Person;
import employee.pojo.utils.EmployeeConstants;
import employee.service.BaseService;
import employee.service.engineer.EngineerServiceImpl;
import employee.service.worker.WorkerServiceImpl;

import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public class Manage {

    Scanner sc = new Scanner(System.in);

    public void add() throws IOException {
        showOptionAdd();
        String option = sc.nextLine().trim();
        switch (option) {
            case "1" -> {
                BaseService worker = new WorkerServiceImpl();
                worker.add();
            }
            case "2" -> {
                BaseService engineer = new EngineerServiceImpl();
                engineer.add();
            }
            default -> System.out.println("\n" + EmployeeConstants.INVALID_OUTPUT);
        }
    }

    public void show() throws Exception {
        showOptionShow();
        String option = sc.nextLine().trim();
        switch (option) {
            case "1" -> {
                BaseService worker = new WorkerServiceImpl();
                worker.show();
            }
            case "2" -> {
                BaseService engineer = new EngineerServiceImpl();
                engineer.show();
            }
            case "3" -> showAll();

            //case "4" -> showObject();
        }
    }

    public void edit() throws IOException {
        showOptionEdit();
        String option = sc.nextLine().trim();
        switch (option) {
            case "1" -> {
                BaseService worker = new WorkerServiceImpl();
                worker.edit();
            }
            case "2" -> {
                BaseService engineer = new EngineerServiceImpl();
                engineer.edit();
            }
            default -> System.out.println("\n" + EmployeeConstants.INVALID_OUTPUT);
        }
    }

    public void delete() throws IOException {
        showOptionDelete();
        String option = sc.nextLine().trim();
        switch (option) {
            case "1" -> {
                BaseService worker = new WorkerServiceImpl();
                worker.delete();
            }
            case "2" -> {
                BaseService engineer = new EngineerServiceImpl();
                engineer.delete();
            }
            default -> System.out.println("\n" + EmployeeConstants.INVALID_OUTPUT);
        }
    }

    static void showOptionAdd() {
        System.out.println(EmployeeConstants.JOB_POSTIONS);
        System.out.println(EmployeeConstants.WORKER);
        System.out.println(EmployeeConstants.ENGINEER);
        System.out.print(EmployeeConstants.CHOOSE_FUNCTION);
    }

    static void showOptionShow() {
        System.out.println(EmployeeConstants.SHOW_OPTIONS);
        System.out.println(EmployeeConstants.SHOW_WORKER_LIST);
        System.out.println(EmployeeConstants.SHOW_ENGINEER_LIST);
        System.out.println(EmployeeConstants.SHOW_ALL);
        //System.out.println(EmployeeConstants.SHOW_OBJECT);
        System.out.print(EmployeeConstants.CHOOSE_FUNCTION);
    }

    static void showOptionEdit() {
        System.out.println(EmployeeConstants.EDIT_OPTIONS);
        System.out.println(EmployeeConstants.EDIT_ID_WORKER);
        System.out.println(EmployeeConstants.EDIT_ID_ENGINEER);
        System.out.print(EmployeeConstants.CHOOSE_FUNCTION);
    }

    static void showOptionDelete() {
        System.out.println(EmployeeConstants.DELETE_OPTIONS);
        System.out.println(EmployeeConstants.DELETE_ID_WORKER);
        System.out.println(EmployeeConstants.DELETE_ID_ENGINEER);
        System.out.print(EmployeeConstants.CHOOSE_FUNCTION);
    }

    public static void showAll() throws IOException {
        System.out.println(" ");
        File file = new File(EmployeeConstants.LIST_PATH);
        if (file.length() == 0) {
            System.out.println("No data in the list. Please insert a new employee in the list.");
        } else {
            System.out.println("-------------------------------------------------------------------------------------------------");
            Formatter fmt = new Formatter();
            fmt.format("%15s %15s %15s %15s %15s %15s\n", "EMPLOYEE ID", "NAME", "AGE", "ADDRESS", "TYPE", "LEVEL/DEGREE");
            BufferedReader reader = new BufferedReader(new FileReader(EmployeeConstants.LIST_PATH));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] array = currentLine.split("@");
                fmt.format("%10s %20s %15s %15s %15s %14s\n", array[0], array[1], array[2], array[3], array[4], array[5]);
            }
            System.out.print(fmt);
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println();
            reader.close();
        }
    }
}

    /**
     *
    public static void showObject() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EmployeeConstants.ABSOLUTE_OBJECT_PATH));
            // Read object from file
            Person per = (Person) ois.readObject();
            // Print object
            System.out.println(per);
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     */

