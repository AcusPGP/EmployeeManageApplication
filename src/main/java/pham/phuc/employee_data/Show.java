package pham.phuc.employee_data;

import employee.pojo.Person;
import employee.pojo.utils.EmployeeConstants;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Show extends Application {

    @FXML
    private TableView<Person> listTableView;
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
    @FXML
    private TextField idSearch;
    @FXML
    private ComboBox<String> showOption;

    ObservableList<String> showOptionList = FXCollections.observableArrayList("Show all", "Show worker list", "Show engineer list");
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
}
