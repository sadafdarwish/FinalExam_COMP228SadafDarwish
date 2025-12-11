package application;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormController {

    public FormController() {
        // default public constructor required by FXMLLoader
    }

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtContact;

    @FXML
    private ComboBox<String> cmbEducation;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField txtSalary;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnSubmit;

    // runs automatically after FXML loads
    @FXML
    private void initialize() {
        cmbEducation.getItems().addAll("Masters", "Bachelors", "College Diploma");
    }

    @FXML
    private void handleSubmit() {
        String fullName = txtFullName.getText().trim();
        String contact = txtContact.getText().trim();
        String education = cmbEducation.getValue();
        LocalDate date = dpDate.getValue();
        String salaryStr = txtSalary.getText().trim();

        String error = validateForm(fullName, contact, education, date, salaryStr);

        if (!error.isEmpty()) {
            lblMessage.setText(error);
            return;
        }

        double salary = Double.parseDouble(salaryStr);

        // save to database
        boolean success = DBConnection.insertApplication(fullName, contact, education, date, salary);

        if (success) {
            lblMessage.setText("Application saved to database successfully.");

            // clear form
            txtFullName.clear();
            txtContact.clear();
            cmbEducation.getSelectionModel().clearSelection();
            dpDate.setValue(null);
            txtSalary.clear();
        } else {
            lblMessage.setText("Error saving application to database.");
        }
    }

    private String validateForm(String fullName, String contact, String education,
                                LocalDate date, String salaryStr) {

        // full name: letters + spaces, max 50 chars
        if (fullName.isEmpty() || fullName.length() > 50 || !fullName.matches("[A-Za-z ]+")) {
            return "Full name: letters only, max 50 characters.";
        }

        // contact: exactly 10 digits
        if (!contact.matches("\\d{10}")) {
            return "Contact number must be exactly 10 digits.";
        }

        // education: must be selected
        if (education == null) {
            return "Please select highest education.";
        }

        // date: must be selected
        if (date == null) {
            return "Please select a date.";
        }

        // salary: up to 8 digits + dot + 2 decimals (e.g. 12345678.50)
        if (!salaryStr.matches("\\d{1,8}\\.\\d{2}")) {
            return "Salary format: up to 8 digits and 2 decimals, e.g. 12345678.50";
        }

        return "";
    }
}
