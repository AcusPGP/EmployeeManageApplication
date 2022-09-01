package pham.phuc.employee_data;

import employee.pojo.Engineer;
import employee.pojo.Person;
import employee.pojo.Worker;
import employee.service.BaseEmployeeServiceImpl;
import employee.service.engineer.EngineerServiceImpl;
import employee.service.worker.WorkerServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Add extends BaseEmployeeServiceImpl {
    @FXML
    private TextField ID;
    @FXML
    private TextField NAME;
    @FXML
    private TextField AGE;
    @FXML
    private TextField ADDRESS;
    @FXML
    private Label INFO;
    @FXML
    private Label ID_ADVICE;
    @FXML
    private ComboBox<String> comboBoxLevelDegree;
    @FXML
    private ComboBox<String> comboBoxType;
    private String DATA;
    private String MISSING_DATA = "";
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, Integer> idColumn;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, Integer> ageColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, String> typeColumn;
    @FXML
    private TableColumn<Person, String> lv_degreeColumn;


    // ObservableList
    ObservableList<String> typeList = FXCollections.observableArrayList("Worker", "Engineer");
    ObservableList<String> levelList = FXCollections.observableArrayList("Assistant", "Manager");
    ObservableList<String> degreeList = FXCollections.observableArrayList("Back-end", "Front-end", "Full-stack");
    ObservableList<Person> addList;

    /**
     * STEP 1: GET INFORMATION
     */
    // Getting input information from the user: Lấy thông tin người dùng nhập vào
    public void getInfo() {
        String id, name, age, address, level_degree, type;
        id = checkInfo(ID.getText().trim());
        name = checkInfo(NAME.getText().trim());
        age = checkInfo(AGE.getText().trim());
        address = checkInfo(ADDRESS.getText().trim());
        type = comboBoxType.getValue();
        level_degree = comboBoxLevelDegree.getValue();
        DATA = id + "@" + name + "@" + age + "@" + address + "@" + type + "@" + level_degree;
    }

    /**
     * STEP 2: EXCEPTION SITUATION
     */
    // 2.0: Additional feature
    // Suggest an ID for the user
    public void idSuggestion() throws IOException {
        int suggestID = suggestId();
        ID.setText(String.valueOf(suggestID));
        idAdviceOff();
    }

    // 2.1 Checking if there is any empty Text Field in the stage - filling the empty Text Field with "null"
    // Kiểm tra thông tin còn trống - thêm vào các thông tin còn trống "null"
    public String checkInfo(String info) {
        String temp = "";
        if (info.equals(temp)) {
            info = "null";
        }
        return info;
    }

    // 2.2 Checking if ID or AGE is an integer
    public int checkIfIsInteger() {
        int temp = 0;
        if (checkIfIsInteger(ID.getText()) == -1) {
            Alert checkText = new Alert(Alert.AlertType.ERROR, "Wrong information", ButtonType.OK);
            checkText.setContentText("Please type the right integer/number for ID!");
            checkText.initModality(Modality.APPLICATION_MODAL);
            checkText.showAndWait();
            if (checkText.getResult() == ButtonType.OK) {
                checkText.close();
                ID.clear();
                ID.setPromptText("Please input your ID");
            }
            temp = -1;
            return temp;
        }
        if (checkIfIsInteger(AGE.getText()) == -1) {
            Alert checkText = new Alert(Alert.AlertType.ERROR, "Wrong information", ButtonType.OK);
            checkText.setTitle("Wrong Information Error");
            checkText.setContentText("Please type the right integer/number for AGE!");
            checkText.initModality(Modality.APPLICATION_MODAL);
            checkText.showAndWait();
            if (checkText.getResult() == ButtonType.OK) {
                checkText.close();
                AGE.clear();
                AGE.setPromptText("Please input your age");
            }
            temp = -1;
            return temp;
        } else {
            if (checkAgeRange(AGE.getText()) == -1) {
                Alert checkAge = new Alert(Alert.AlertType.ERROR, "Wrong information", ButtonType.OK);
                checkAge.setTitle("Invalid Age Error");
                checkAge.setContentText("Please type the valid AGE!");
                checkAge.initModality(Modality.APPLICATION_MODAL);
                checkAge.showAndWait();
                if (checkAge.getResult() == ButtonType.OK) {
                    checkAge.close();
                    AGE.clear();
                    AGE.setPromptText("Please input your age");
                }
                temp = -1;
                return temp;
            }
        }
        return temp;
    }

    // 2.3 Checking if the ID is already had in the list - if it is true, alert the user to input a new ID again.
    public int checkIDTextField() throws IOException {
        String id = ID.getText();
        int temp = 0;
        String nothing = "";
        idAdviceOff();
        if (id.equals(nothing)) {
            return temp;
        } else {
            int result = checkId(id);
            if (result == -1) {
                Alert duplicateIDError = new Alert(Alert.AlertType.ERROR, "Duplicated ID Error", ButtonType.OK);
                duplicateIDError.setTitle("Duplicated ID Error");
                duplicateIDError.setContentText("This ID " + id + " is already had. Please try another id.");
                duplicateIDError.initModality(Modality.APPLICATION_MODAL);
                duplicateIDError.showAndWait();
                if (duplicateIDError.getResult() == ButtonType.OK) {
                    ID.clear();
                    ID.setPromptText("Please input your ID");
                }
                idAdviceOn();
                temp = -1;
                return temp;
            }
        }
        return temp;
    }

    // 2.4 Checking if theres is a string "null" in the final output of user's information
    // Kiểm tra xem còn thông tin nào thiếu trong thông tin cuối cùng của người dùng hay không
    public String checkEmptyText() {
        String isEmpty = "true";
        MISSING_DATA = "";
        String[] checkEmptyText = DATA.split("@");
        for (int i = 0; i < checkEmptyText.length; i++) {
            if (checkEmptyText[i].equals("null")) {
                isEmpty = "false";
                MISSING_DATA = missingFeature(i);
            }
        }
        if (MISSING_DATA.endsWith(", ")) {
            MISSING_DATA = MISSING_DATA.substring(0, MISSING_DATA.length() - 2);
        }
        System.out.println(MISSING_DATA);
        return isEmpty;
    }

    public String missingFeature(int i) {
        String missingFeature;
        Map<Integer, String> mapFeature = new HashMap<>();
        mapFeature.put(0, "ID");
        mapFeature.put(1, "NAME");
        mapFeature.put(2, "AGE");
        mapFeature.put(3, "ADDRESS");
        mapFeature.put(4, "TYPE");
        mapFeature.put(5, "LEVEL/DEGREE");
        String temp = MISSING_DATA;
        missingFeature = temp + mapFeature.get(i) + ", ";
        return missingFeature;
    }

    /**
     * STEP 3: CONFIRMATION
     */
    // Confirming the user's information, display and output the final adding information text for user before adding section
    // Đưa ra thông tin cuối cùng của người dùng trước khi thêm vào danh sách
    public void onConfirmButtonClick() throws IOException {
        getInfo();
        if (checkIfIsInteger() == 0) {
            if (checkIDTextField() == 0) {
                if (checkEmptyText().equals("true")) {
                    String[] arrayData = DATA.split("@");
                    String confirmInfo = arrayData[0] + " " + arrayData[1] + " " + arrayData[2] + " " + arrayData[3] + " " + arrayData[4] + " " + arrayData[5];
                    tableAddView();
                    INFO.setText(confirmInfo);
                } else {
                    Alert emptyText = new Alert(Alert.AlertType.ERROR, "Empty Information Error", ButtonType.OK);
                    emptyText.setTitle("Empty Information Error");
                    emptyText.setContentText(MISSING_DATA + " is empty. Please fill properly.");
                    emptyText.initModality(Modality.APPLICATION_MODAL);
                    emptyText.showAndWait();
                    if (emptyText.getResult() == ButtonType.OK) {
                        emptyText.close();
                    }
                }
            }
        }
    }

    // Display a table of adding information for user
    public void tableAddView() {
        String[] arrayData = DATA.split("@");
        addList = FXCollections.observableArrayList(new Person(Integer.parseInt(arrayData[0]), arrayData[1], Integer.parseInt(arrayData[2]), arrayData[3], arrayData[4], arrayData[5]));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        lv_degreeColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        table.setItems(addList);
    }

    /**
     * STEP 4: ADDING
     */
    // Adding user's information to the list - Checking if final output user's information is empty at the beginning (exception)
    // Lưu thông tin người dùng - Kiểm tra ngay lúc vào chương trình nhập thông tin nếu người dùng có bấm Add ngay lúc đầu thì báo lỗi bắt người dùng phải
    // nhập thông tin hoặc bấm Confirm để tiếp tục bước kiểm tra các trường hợp ngoại lệ tiếp theo.
    public void onAddInfoButtonClick(ActionEvent event) throws IOException {
        if (DATA == null) {
            Alert emptyText = new Alert(Alert.AlertType.ERROR, "Missing Steps", ButtonType.OK);
            emptyText.setContentText("Please press confirm button to see the final output!");
            emptyText.initModality(Modality.APPLICATION_MODAL);
            emptyText.showAndWait();
            if (emptyText.getResult() == ButtonType.OK) {
                emptyText.close();
            }
        } else {
            if (checkEmptyText().equals("true")) {
                Alert addSuccess = new Alert(Alert.AlertType.NONE, "Notification", ButtonType.OK, ButtonType.CANCEL);
                Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addSuccess.setContentText("Add Employee's Information successfully!");
                addSuccess.initModality(Modality.APPLICATION_MODAL);
                addSuccess.initOwner(stage1);
                addSuccess.showAndWait();

                if (addSuccess.getResult() == ButtonType.CANCEL) {
                    addSuccess.close();
                } else {
                    addInfo();
                    Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(Controller.class.getResource("add-view.fxml"));
                    Parent addParent = loader.load();
                    Scene addScene = new Scene(addParent);
                    stage2.setScene(addScene);
                }
            } else {
                Alert emptyText = new Alert(Alert.AlertType.ERROR, "Missing Steps", ButtonType.OK);
                emptyText.setContentText("Please press confirm button to see the final output!");
                emptyText.initModality(Modality.APPLICATION_MODAL);
                emptyText.showAndWait();
                if (emptyText.getResult() == ButtonType.OK) {
                    emptyText.close();
                }
            }
        }
    }

    public void addInfo() {
        String[] arrayData = DATA.split("@");
        if (arrayData[4].equals("Worker")) {
            WorkerServiceImpl worker = new WorkerServiceImpl();
            Worker newWorker = new Worker(Integer.parseInt(arrayData[0]), arrayData[1], Integer.parseInt(arrayData[2]), arrayData[3], arrayData[4], arrayData[5]);
            worker.addToF(newWorker);
        } else {
            EngineerServiceImpl engineer = new EngineerServiceImpl();
            Engineer newEngineer = new Engineer(Integer.parseInt(arrayData[0]), arrayData[1], Integer.parseInt(arrayData[2]), arrayData[3], arrayData[4], arrayData[5]);
            engineer.addToF(newEngineer);
        }
    }

    /**
     * FUNCTIONS
     */
    // Exit
    public void onExitButtonCLick(ActionEvent event) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmation", ButtonType.OK, ButtonType.CANCEL);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        exitAlert.setContentText("Are you sure you want to exit?");
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.initOwner(stage);
        exitAlert.showAndWait();

        if (exitAlert.getResult() == ButtonType.OK) {
            Platform.exit();
        } else {
            exitAlert.close();
        }
    }

    // Return
    public void onReturnButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("hello-view.fxml"));
        Parent addParent = loader.load();
        Scene addScene = new Scene(addParent);
        stage.setScene(addScene);
    }

    // Menu
    public void onMenuButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("hello-view.fxml"));
        Parent addParent = loader.load();
        Scene addScene = new Scene(addParent);
        stage.setScene(addScene);
    }

    // Comment
    public void onCommentButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("note-view.fxml"));
        Parent addParent = loader.load();
        Scene addScene = new Scene(addParent);
        stage.setScene(addScene);
    }

    /**
     * COMBOBOX (Java)
     */
    public void comboBoxType(MouseEvent event) throws IOException {
        comboBoxType.setItems(typeList);
        String id = ID.getText();
        int result = checkId(id);
        if (result == -1) {
            Alert duplicateIDError = new Alert(Alert.AlertType.ERROR, "Duplicated ID Error", ButtonType.OK);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            duplicateIDError.setContentText("This ID " + id + " is already had. Please try another id.");
            duplicateIDError.initModality(Modality.APPLICATION_MODAL);
            duplicateIDError.initOwner(stage);
            duplicateIDError.showAndWait();
            if (duplicateIDError.getResult() == ButtonType.OK) {
                ID.clear();
                ID.setPromptText("Please input your ID");
            }
        }
    }

    public void comboBoxLevelDegree(MouseEvent event) throws IOException {
        String temp = comboBoxType.getValue().trim();
        if (temp.equals("Worker")) {
            comboBoxLevelDegree.setItems(levelList);
        } else if (temp.equals("Engineer")) {
            comboBoxLevelDegree.setItems(degreeList);
        }
        String id = ID.getText();
        int result = checkId(id);
        if (result == -1) {
            Alert duplicateIDError = new Alert(Alert.AlertType.ERROR, "Duplicated ID Error", ButtonType.OK);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            duplicateIDError.setContentText("This ID " + id + " is already had. Please try another id.");
            duplicateIDError.initModality(Modality.APPLICATION_MODAL);
            duplicateIDError.initOwner(stage);
            duplicateIDError.showAndWait();
            if (duplicateIDError.getResult() == ButtonType.OK) {
                ID.clear();
                ID.setPromptText("Please input your ID");
            }
        }
    }

    public void idAdviceOn() {
        ID_ADVICE.setText("Recommend using Suggesting ID button");
    }

    public void idAdviceOff() {
        ID_ADVICE.setText("");
    }

    /**
     * TRASH
     */
    @Override
    public void add() throws IOException {

    }

    @Override
    public void show() throws IOException {

    }

    @Override
    public void delete() throws IOException {

    }
}
