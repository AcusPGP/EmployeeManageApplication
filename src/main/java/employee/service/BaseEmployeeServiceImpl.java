package employee.service;

import employee.pojo.utils.EmployeeConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class BaseEmployeeServiceImpl implements BaseService {

    Scanner sc = new Scanner(System.in);

    public void edit() throws IOException {
        System.out.print(EmployeeConstants.INPUT_ID_TO_EDIT);
        String id = sc.nextLine();
        File file = new File(EmployeeConstants.LIST_PATH);
        File temp = new File(EmployeeConstants.LIST_PATH);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        String curentLine;
        while ((curentLine = reader.readLine()) != null) {
            String[] array = curentLine.split("@");
            if (array[0].equals(id)) {
                showEditFunctions();
                String option = sc.nextLine().trim();
                String nAME, aDDRESS, tYPE, ld;
                int iD, aGE;
                switch (option) {
                    case "1" -> {
                        System.out.println("The current name is " + array[1]);
                        iD = Integer.parseInt(array[0]);
                        aGE = Integer.parseInt(array[2]);
                        aDDRESS = array[3];
                        tYPE = array[4];
                        ld = array[5];
                        System.out.print(EmployeeConstants.INPUT_NEWNAME);
                        nAME = sc.nextLine().trim();
                        writer.write(iD + "@" + nAME + "@" + aGE + "@" + aDDRESS + "@" + tYPE + "@" + ld);
                        writer.close();
                    }
                    case "2" -> {
                        System.out.println("The current age is " + array[2]);
                        iD = Integer.parseInt(array[0]);
                        nAME = array[1];
                        aDDRESS = array[3];
                        tYPE = array[4];
                        ld = array[5];
                        System.out.print(EmployeeConstants.INPUT_NEWAGE);
                        aGE = Integer.parseInt(sc.nextLine().trim());
                        writer.write(iD + "@" + nAME + "@" + aGE + "@" + aDDRESS + "@" + tYPE + "@" + ld);
                        writer.close();
                    }
                    case "3" -> {
                        System.out.println("The current address is " + array[3]);
                        iD = Integer.parseInt(array[0]);
                        nAME = array[1];
                        aGE = Integer.parseInt(array[2]);
                        tYPE = array[4];
                        ld = array[5];
                        System.out.print(EmployeeConstants.INPUT_NEWADDRESS);
                        aDDRESS = sc.nextLine();
                        writer.write(iD + "@" + nAME + "@" + aGE + "@" + aDDRESS + "@" + tYPE + "@" + ld);
                        writer.close();
                    }
                    case "4" -> {
                        System.out.println("The current type is " + array[4]);
                        iD = Integer.parseInt(array[0]);
                        nAME = array[1];
                        aGE = Integer.parseInt(array[2]);
                        aDDRESS = array[3];
                        System.out.print(EmployeeConstants.INPUT_NEWTYPE);
                        tYPE = sc.nextLine();
                        if (tYPE.equals("worker")) {
                            ld = showWorkerLevel();
                        } else {
                            ld = showEngineerDegree();
                        }
                        writer.write(iD + "@" + nAME + "@" + aGE + "@" + aDDRESS + "@" + tYPE + "@" + ld);
                        writer.close();
                    }
                    default -> System.out.println("\n" + "Invalid! Please choose an option in the below menu: ");
                }
            } else {
                writer.write(curentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        temp.renameTo(file);
        System.out.println("Change successful!" + "\n");
    }


    public void delete(String id) throws IOException {
        File inputF = new File(EmployeeConstants.LIST_PATH);
        File tempF = new File("temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputF));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempF));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            String[] array = trimmedLine.split("@");
            if (array[0].equals(id)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        tempF.renameTo(inputF);
        System.out.println("Delete successful!" + "\n");
    }

    static void showEditFunctions() {
        System.out.println(EmployeeConstants.EDIT_FUNCTIONS);
        System.out.println(EmployeeConstants.CHANGE_NAME);
        System.out.println(EmployeeConstants.CHANGE_AGE);
        System.out.println(EmployeeConstants.CHANGE_ADDRESS);
        System.out.println(EmployeeConstants.CHANGE_TYPE);
        System.out.print(EmployeeConstants.CHOOSE_FUNCTION);
    }

    public String showWorkerLevel() {
        System.out.println("Choose level for the worker.");
        System.out.println("1. Employee");
        System.out.println("2. Manager");
        System.out.print("Please choose an option: ");
        String option = sc.nextLine().trim();
        String level = null;
        switch (option) {
            case "1" -> {
                level = "employee";
                break;
            }
            case "2" -> {
                level = "engineer";
                break;
            }
            default -> System.out.println("\n" + "Invalid! Please choose an option in the below menu. ");
        }
        return level;
    }

    public String showEngineerDegree() {
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
            default -> System.out.println("\n" + "Invalid! Please choose an option in the below menu. ");
        }
        return degree;
    }

    public int suggestId() throws IOException {
        int num = 0;
        File file = new File(EmployeeConstants.LIST_PATH);
        String currentLine;
        if (file.length() == 0) {
            num = 1;
        } else {
            ArrayList<Integer> ar = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(EmployeeConstants.LIST_PATH));
            while ((currentLine = reader.readLine()) != null) {
                String[] array = currentLine.split("@");
                ar.add(Integer.parseInt(array[0]));
            }
            reader.close();
            int length = Collections.max(ar);
            if (length == ar.size()) {
                num = length + 1;
            } else {
                for (int i = 1; i <= length; i++) {
                    if (ar.contains(i)) {
                        continue;
                    } else {
                        num = i;
                        return num;
                    }
                }
            }
        }
        return num;
    }

    public int checkId(String id) throws IOException {
        int result = 0;
        File check = new File(EmployeeConstants.LIST_PATH);
        if (check.length() == 0) {
            return result;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(check));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                String[] array = trimmedLine.split("@");
                if (array[0].equals(id)) {
                    result = -1;
                    System.out.println("This ID " + "'" + id + "'" + " is aLready had. Please try another id." + "\n");
                    break;
                }
            }
            reader.close();
            return result;
        }
    }

    public int checkIfIsInteger(String num) {
        int temp = 0;
        String nothing = "";
        if (num.equals(nothing)) {
            return temp;
        } else {
            try {
                Integer.parseInt(num);
                return temp;
            } catch (NumberFormatException e) {
                temp = -1;
                return temp;
            }
        }
    }

    public int checkAgeRange(String age) {
        int temp = 0;
        String nothing = "";
        if (age.equals(nothing)) {
            return temp;
        } else {
            int checkAge = Integer.parseInt(age);
            if (checkAge >= 0 && checkAge <= 100) {
                return temp;
            } else {
                temp = -1;
                return temp;
            }
        }
    }
}
