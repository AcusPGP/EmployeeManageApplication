package pham.phuc.employee_data;

import employee.pojo.Person;
import employee.pojo.utils.EmployeeConstants;
import employee.service.BaseEmployeeServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Edit extends BaseEmployeeServiceImpl {

    @FXML
    private TextField ID;
    @FXML
    private TextField NAME;
    @FXML
    private TextField AGE;
    @FXML
    private TextField ADDRESS;
    @FXML
    private TextField newLine;
    @FXML
    private ComboBox<String> comboBoxLevelDegree;
    @FXML
    private ComboBox<String> comboBoxType;
    @FXML
    private TableView<Person> listTableView;
    @FXML
    private TableColumn<Person, String> idColumn;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> ageColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, String> typeColumn;
    @FXML
    private TableColumn<Person, String> lv_degreeColumn;
    @FXML
    private TextField idSearch;
    @FXML
    private TextField editLine;
    @FXML
    private ComboBox<String> showOption;
    private String newDATA;
    private String MISSING_DATA = "";
    private String[] oldData;
    private String newId;
    private String newName;
    private String newAge;
    private String newAddress;
    private String newType;
    private String newLevel_degree;


    ObservableList<String> showOptionList = FXCollections.observableArrayList("Show all", "Show worker list", "Show engineer list");
    ObservableList<String> typeList = FXCollections.observableArrayList("Worker", "Engineer");
    ObservableList<String> levelList = FXCollections.observableArrayList("Assistant", "Manager");
    ObservableList<String> degreeList = FXCollections.observableArrayList("Back-end", "Front-end", "Full-stack");
    ObservableList<Person> dataList = FXCollections.observableArrayList();

    /**
     * Show list of the employee - list of workers - list of engineers
     */
    public void onShowOptionButtonClick() throws IOException {
        String temp = showOption.getValue();
        if (temp == null) {
            Alert emptyShowOption = new Alert(Alert.AlertType.ERROR, "Empty Show Option", ButtonType.OK);
            emptyShowOption.setTitle("Option Error");
            emptyShowOption.setContentText("Please choose an option to show!");
            emptyShowOption.initModality(Modality.APPLICATION_MODAL);
            emptyShowOption.showAndWait();
            if (emptyShowOption.getResult() == ButtonType.OK) {
                emptyShowOption.close();
            }
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(EmployeeConstants.LIST_PATH));
            String currentLine;
            listTableView.getItems().clear();
            clearText();
            if (temp.equals("Show all")) {
                while ((currentLine = reader.readLine()) != null) {
                    String[] data = currentLine.split("@");
                    showTableView(data);
                }
                reader.close();
            } else if (temp.equals("Show worker list")) {
                while ((currentLine = reader.readLine()) != null) {
                    String[] data = currentLine.split("@");
                    if (data[4].equals("Worker")) {
                        showTableView(data);
                    }
                }
                reader.close();
            } else {
                while ((currentLine = reader.readLine()) != null) {
                    String[] data = currentLine.split("@");
                    if (data[4].equals("Engineer")) {
                        showTableView(data);
                    }
                }
                reader.close();
            }
            reader.close();
        }
    }

    /**
     * Search employee's information by id
     */
    public void onSearchIDButtonClick() throws IOException {
        if (idSearch.getText().equals("")) {
            Alert emptyShowOption = new Alert(Alert.AlertType.ERROR, "Empty Show Option", ButtonType.OK);
            emptyShowOption.setTitle("Option Error");
            emptyShowOption.setContentText("Please choose an ID to search!");
            emptyShowOption.initModality(Modality.APPLICATION_MODAL);
            emptyShowOption.showAndWait();
            if (emptyShowOption.getResult() == ButtonType.OK) {
                emptyShowOption.close();
            }
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(EmployeeConstants.LIST_PATH));
            String currentLine;
            listTableView.getItems().clear();
            while ((currentLine = reader.readLine()) != null) {
                String[] searchData = currentLine.split("@");
                if (searchData[0].equals(idSearch.getText())) {
                    showTableView(searchData);
                }
            }
            reader.close();
        }
    }

    public void showTableView(String[] data) {
        dataList.add(new Person(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        lv_degreeColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        listTableView.setItems(dataList);
    }

    /**
     * Edit employee's information
     */

    public void editableRow() {
        listTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String temp = String.valueOf(listTableView.getSelectionModel().getSelectedItems());
                String arr = temp.substring(1, temp.length() - 1);
                String[] selectedRow = arr.split("@");
                oldData = selectedRow;
                editLine.setText(selectedRow[0] + " - " + selectedRow[1] + " - " + selectedRow[2] + " - " + selectedRow[3] + " - " + selectedRow[4] + " - " + selectedRow[5]);
            }
        });
    }

    /**
     * STEP 1: GET NEW INFORMATION
     */
    public void getNewInformation() {
        newId = checkInfo(ID.getText().trim());
        newName = checkInfo(NAME.getText().trim());
        newAge = checkInfo(AGE.getText().trim());
        newAddress = checkInfo(ADDRESS.getText().trim());
        newType = comboBoxType.getValue();
        newLevel_degree = comboBoxLevelDegree.getValue();
        newDATA = newId + "@" + newName + "@" + newAge + "@" + newAddress + "@" + newType + "@" + newLevel_degree;
    }

    /**
     * STEP 2: EXCEPTION SITUATION
     */
    // 2.0: Additional feature
    // Suggest an ID for the user
    public void idSuggestion() throws IOException {
        int suggestID = suggestId();
        ID.setText(String.valueOf(suggestID));
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
        String[] checkEmptyText = newDATA.split("@");
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
        String nothing = "";
        if (editLine.getText().equals(nothing)) {
            Alert emptyEditLine = new Alert(Alert.AlertType.ERROR, "Empty Editing Information Error", ButtonType.OK);
            emptyEditLine.setTitle("Empty Editing Information Error");
            emptyEditLine.setContentText("Please choose a line to edit!");
            emptyEditLine.show();
            if (emptyEditLine.getResult() == ButtonType.OK) {
                emptyEditLine.close();
            }
        } else {
            getNewInformation();
            if (checkIfIsInteger() == 0) {
                if (newId.equals(oldData[0])) {
                    if (checkEmptyText().equals("true")) {
                        String[] arrayData = newDATA.split("@");
                        String confirmInfo = arrayData[0] + " " + arrayData[1] + " " + arrayData[2] + " " + arrayData[3] + " " + arrayData[4] + " " + arrayData[5];
                        newLine.setText(confirmInfo);
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
                } else {
                    if (checkIDTextField() == 0) {
                        if (checkEmptyText().equals("true")) {
                            String[] arrayData = newDATA.split("@");
                            String confirmInfo = arrayData[0] + " " + arrayData[1] + " " + arrayData[2] + " " + arrayData[3] + " " + arrayData[4] + " " + arrayData[5];
                            newLine.setText(confirmInfo);
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
        }
    }

    /**
     * STEP 4: CHANGE AND ADD NEW INFORMAITON
     */
    public void onEditAndAddButtonClick(ActionEvent event) throws IOException {
        String nothing = "";
        if (newLine.getText().equals(nothing)) {
            Alert emptyEditLine = new Alert(Alert.AlertType.ERROR, "Empty Editing Information Error", ButtonType.OK);
            emptyEditLine.setTitle("Empty Editing Information Error");
            emptyEditLine.setContentText("Please choose a line to edit!");
            emptyEditLine.initModality(Modality.APPLICATION_MODAL);
            emptyEditLine.show();
            if (emptyEditLine.getResult() == ButtonType.OK) {
                emptyEditLine.close();
            }
        } else {
            if (checkEmptyText().equals("true")) {
                Alert addSuccess = new Alert(Alert.AlertType.NONE, "Notification", ButtonType.OK, ButtonType.CANCEL);
                addSuccess.setContentText("Add Employee's Information successfully!");
                addSuccess.initModality(Modality.APPLICATION_MODAL);
                addSuccess.showAndWait();
                if (addSuccess.getResult() == ButtonType.CANCEL) {
                    addSuccess.close();
                } else {
                    editAndAdd(oldData[0]);
                    listTableView.getItems().clear();
                    BufferedReader reader = new BufferedReader(new FileReader(EmployeeConstants.LIST_PATH));
                    String currentLine;
                    while ((currentLine = reader.readLine()) != null) {
                        String[] data = currentLine.split("@");
                        showTableView(data);
                    }
                    reader.close();
                    clearText();
                }
            }
        }
    }

    public void editAndAdd(String oldId) throws IOException {
        File file = new File(EmployeeConstants.LIST_PATH);
        File temp = new File("temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        String curentLine;
        while ((curentLine = reader.readLine()) != null) {
            String[] array = curentLine.split("@");
            if (array[0].equals(oldId)) {
                writer.write(newId + "@" + newName + "@" + newAge + "@" + newAddress + "@" + newType + "@" + newLevel_degree + "\n");
            } else {
                writer.write(curentLine + "\n");
            }
        }
        writer.close();
        reader.close();
        temp.renameTo(file);
        System.out.println("Change successful!");
    }

    public void clearText() {
        editLine.setText("");
        ID.setText("");
        NAME.setText("");
        AGE.setText("");
        ADDRESS.setText("");
        comboBoxType.getItems().clear();
        comboBoxLevelDegree.getItems().clear();
    }

    /**
     * SPECIAL
     */
    public void chooseEditLine() {
        Alert emptyText = new Alert(Alert.AlertType.ERROR, "Empty Information Error", ButtonType.OK);
        emptyText.setTitle("Empty Information Error");
        emptyText.setContentText("Please choose a line to edit!");
        emptyText.initModality(Modality.APPLICATION_MODAL);
        emptyText.showAndWait();
        if (emptyText.getResult() == ButtonType.OK) {
            emptyText.close();
        }
    }

    public void onIDButtonClick() {
        String nothing = "";
        if (editLine.getText().equals(nothing)) {
            chooseEditLine();
        } else {
            ID.setText(oldData[0]);
        }
    }

    public void onNameButtonClick() {
        String nothing = "";
        if (editLine.getText().equals(nothing)) {
            chooseEditLine();
        } else {
            NAME.setText(oldData[1]);
        }
    }

    public void onAgeButtonClick() {
        String nothing = "";
        if (editLine.getText().equals(nothing)) {
            chooseEditLine();
        } else {
            AGE.setText(oldData[2]);
        }
    }

    public void onAddressButtonClick() {
        String nothing = "";
        if (editLine.getText().equals(nothing)) {
            chooseEditLine();
        } else {
            ADDRESS.setText(oldData[3]);
        }
    }

    public void onSpecialButtonClick() {
        String nothing = "";
        if (editLine.getText().equals(nothing)) {
            chooseEditLine();
        } else {
            ID.setText(oldData[0]);
            NAME.setText(oldData[1]);
            AGE.setText(oldData[2]);
            ADDRESS.setText(oldData[3]);
        }
    }

    /**
     * COMBOBOX
     */
    public void comboBoxShowOption() {
        showOption.setItems(showOptionList);
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

    // Menu
    public void onMenuButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("hello-view.fxml"));
        Parent addParent = loader.load();
        Scene addScene = new Scene(addParent);
        stage.setScene(addScene);
    }

    // Return
    public void onAddReturnClick(ActionEvent event) throws IOException {
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
    public void comboBoxType() {
        comboBoxType.setItems(typeList);
    }

    public void comboBoxLevelDegree() {
        String temp = comboBoxType.getValue().trim();
        if (temp.equals("Worker")) {
            comboBoxLevelDegree.setItems(levelList);
        } else if (temp.equals("Engineer")) {
            comboBoxLevelDegree.setItems(degreeList);
        }
    }

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
