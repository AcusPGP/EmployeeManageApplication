package employee.service.worker;

import employee.pojo.Worker;
import employee.pojo.utils.EmployeeConstants;
import employee.service.BaseEmployeeServiceImpl;

import java.io.*;
import java.util.*;

public class WorkerServiceImpl extends BaseEmployeeServiceImpl implements WorkerService<Worker> {

    Scanner sc = new Scanner(System.in);

    @Override
    public void add() throws IOException {
        System.out.println();
        int id = inputId();
        String name = inputName();
        int age = inputAge();
        String address = inputAddress();
        String level = chooseLevel();
        System.out.println();
        String type = "worker";
        Worker worker = new Worker(id, name, age, address, type, level);
        addToF(worker);
        addToObject(worker);
    }

    @Override
    public void show() throws IOException {
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
                if (array[4].equals("worker")) {
                    fmt.format("%10s %20s %15s %15s %15s %14s\n", array[0], array[1], array[2], array[3], array[4], array[5]);
                }
            }
            System.out.print(fmt);
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("");
            reader.close();
        }
    }

    @Override
    public void delete() throws IOException {

    }

    private boolean checkFileExist() {
        File file = new File(EmployeeConstants.LIST_PATH);
        return file.exists();
    }

    public void addToF(Worker worker) {
        FileWriter myList = null;
        try {
            if (checkFileExist()) {
                myList = new FileWriter(EmployeeConstants.LIST_PATH, true);
                myList.write(worker.toString() + "\n");
            } else {
                myList = new FileWriter(EmployeeConstants.LIST_PATH);
                myList.write(worker.toString() + "\n");
            }
            myList.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addToObject(Worker wo) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.list"));
            // Write object to file
            oos.writeObject(wo.toString() + "\n");
            // Close resources
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int inputId() throws IOException {
        while (true) {
            int suggestId = suggestId();
            System.out.println("Suggest ID: " + suggestId);
            System.out.print(EmployeeConstants.INPUT_ID);
            String sgId = sc.nextLine().trim();
            String empty = "";
            if (sgId.equals(empty)) {
                return suggestId;
            } else {
                try {
                    String id = sgId;
                    int result = checkId(id);
                    if (result == 0) {
                        return Integer.parseInt(id);
                    }
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Invalid! Please input the id again. ");
                    return inputId();
                }
            }
        }
    }


    public String inputName() {
        System.out.print(EmployeeConstants.INPUT_NAME);
        return sc.nextLine();
    }

    public int inputAge() {
        System.out.print(EmployeeConstants.INPUT_AGE);
        try {
            int age = Integer.parseInt(sc.nextLine().trim());
            if (age >= 1 && age <= 100) {
                return age;
            } else {
                System.out.println("Invalid! Please input the age again. ");
                return inputAge();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid! Please input the age again. ");
            return inputAge();
        }
    }


    public String inputAddress() {
        System.out.print(EmployeeConstants.INPUT_ADDRESS);
        return sc.nextLine();
    }

    public String chooseLevel() {
        System.out.println("Choose level for the worker.");
        System.out.println("1. Assistant");
        System.out.println("2. Manager");
        System.out.print("Please choose an option: ");
        String option = sc.nextLine().trim();
        String level = null;
        switch (option) {
            case "1" -> level = "employee";
            case "2" -> level = "manager";
            default -> System.out.println("\n" + "Invalid! Please choose an option in the below menu. ");
        }
        return level;
    }

    @Override
    public void showSkills(Worker worker) {

    }
}
