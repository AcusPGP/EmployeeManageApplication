module pham.phuc.employee_data {
    requires javafx.controls;
    requires javafx.fxml;

    opens pham.phuc.employee_data to javafx.fxml;
    opens employee.pojo to javafx.base;
    exports pham.phuc.employee_data;
}