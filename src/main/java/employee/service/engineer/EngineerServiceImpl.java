package employee.service.engineer;

import employee.pojo.Engineer;
import employee.pojo.utils.EmployeeConstants;
import employee.service.BaseEmployeeServiceImpl;

import java.io.*;
import java.util.*;

public class EngineerServiceImpl extends BaseEmployeeServiceImpl implements EngineerService {

    Scanner sc = new Scanner(System.in);

    @Override
    public void add() throws IOException {
        System.out.println(" ");
        int id = inputId();
        String name = inputName();
        int age = inputAge();
        String address = inputAddress();
        String degree = chooseDegree();
        System.out.println(" ");
        String type = "engineer";
        Engineer en = new Engineer(id, name, age, address, type, degree);
        addToF(en);
        addToObject(en);
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
                if (array[4].equals("engineer")) {
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

    public void addToF(Engineer en) {
        FileWriter myList = null;
        try {
            if (checkFileExist()) {
                myList = new FileWriter(EmployeeConstants.LIST_PATH, true);
                myList.write(en.toString() + "\n");
            } else {
                myList = new FileWriter(EmployeeConstants.LIST_PATH);
                myList.write(en.toString() + "\n");
            }
            myList.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addToObject(Engineer en) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.list"));
            // Write object to file
            oos.writeObject(en.toString() + "\n");
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

    public String chooseDegree() {
        System.out.println("Choose degree for the engineer.");
        System.out.println("1. Front-end");
        System.out.println("2. Back-end");
        System.out.println("3. Full-stack");
        System.out.print("Please choose an option: ");
        String option = sc.nextLine().trim();
        String degree = null;
        switch (option) {
            case "1" -> {
                degree = "front-end";
                break;
            }
            case "2" -> {
                degree = "back-end";
                break;
            }
            case "3" -> {
                degree = "full-stack";
                break;
            }
            default -> System.out.println("\n" + EmployeeConstants.INVALID_OUTPUT);
        }
        return degree;
    }

    @Override
    public void showAllDegree(Engineer engineer) {
            System.out.print("computerCertificate: " + engineer.getComputerCertificate() + " & Specialized: " + engineer.getSpecialized());
    }


}
