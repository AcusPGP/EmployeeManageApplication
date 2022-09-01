package employee.pojo.utils;

public final class EmployeeConstants {
    /**
     * Special
     *
     * @return
     */
    static String objectPath() {
        String objectPath = System.getProperty("object.list");
        return objectPath;
    }

    public static final String CHOOSE_FUNCTION = "Please choose an option: ";
    public static final String LIST_PATH = "/Users/macbook/OOPProjects/Employee_Data/list.txt";
    public static final String INVALID_OUTPUT = "Invalid! Please choose an option in the below menu: ";

    public static final String ABSOLUTE_OBJECT_PATH = objectPath();

    /**
     * Main
     */
    //Menu
    public static final String ADD = "1. Add an employee.";
    public static final String SHOW = "2. Show the list of employees.";
    public static final String EDIT_UPDATE = "3. Edit and Update the employee's information.";
    public static final String DELETE = "4. Delete the employee's information.";
    public static final String EXIT = "5. Exit.";

    /**
     * Manage
     */
    // showOptionAdd
    public static final String JOB_POSTIONS = "Job Positions";
    public static final String WORKER = "1. worker";
    public static final String ENGINEER = "2. engineer";
    // showOptionShow
    public static final String SHOW_OPTIONS = "Show options";
    public static final String SHOW_WORKER_LIST = "1. Show worker list";
    public static final String SHOW_ENGINEER_LIST = "2. Show engineer list";
    public static final String SHOW_ALL = "3. Show full list";
    public static final String SHOW_OBJECT = "4. Show object.list";
    // showOptionEdit
    public static final String EDIT_OPTIONS = "Edit Options";
    public static final String EDIT_ID_WORKER = "1. Change the worker's info by id";
    public static final String EDIT_ID_ENGINEER = "2. Change the engineer's info by id";
    // showDeleteOption
    public static final String DELETE_OPTIONS = "Delete Options";
    public static final String DELETE_ID_WORKER = "1. Delete worker by id";
    public static final String DELETE_ID_ENGINEER = "2. Delete engineer by id";

    /**
     * WorkerServiceImpl and EngineerServiceImpl
     */
    public static final String INPUT_ID = "Please enter to get this ID or input a new ID: ";
    public static final String INPUT_NAME = "Input the name: ";
    public static final String INPUT_AGE = "Input the age: ";
    public static final String INPUT_ADDRESS = "Input the address: ";

    /**
     * BasicServiceImpl
     */
    // edit
    public static final String INPUT_ID_TO_EDIT = "Please choose an Id to edit: ";
    public static final String INPUT_NEWNAME = "Input a new name: ";
    public static final String INPUT_NEWAGE = "Input a new age: ";
    public static final String INPUT_NEWADDRESS = "Input a new address: ";
    public static final String INPUT_NEWTYPE = "Input a new type: ";
    // edit_functions
    public static final String EDIT_FUNCTIONS = "Edit functions";
    public static final String CHANGE_NAME = "1. Change the name.";
    public static final String CHANGE_AGE = "1. Change the age.";
    public static final String CHANGE_ADDRESS = "1. Change the address.";
    public static final String CHANGE_TYPE = "1. Change the type.";
    // delete
    public static final String INPUT_ID_TO_DELETE = "Please choose an Id to delete: ";
}
