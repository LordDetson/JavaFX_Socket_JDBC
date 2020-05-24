package by.bsuir.gui;

import by.bsuir.property.FacultyProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class FacultyEditorDialogPane extends OkCancelDialogPane {
    private final ObservableList<FacultyProperty> propertiesFromTable;
    private FacultyProperty facultyToEdit;

    private Label nameLabel;
    private TextField nameField;

    private Label phoneLabel;
    private TextField phoneField;

    private Label emailLabel;
    private TextField emailField;

    private Label deansAddressLabel;
    private TextField deansAddressField;

    private Label studentCountLabel;
    private TextField studentCountField;

    private Label descriptionLabel;
    private TextArea descriptionField;

    public FacultyEditorDialogPane(ObservableList<FacultyProperty> propertiesFromTable) {
        this(propertiesFromTable, null);
    }

    public FacultyEditorDialogPane(ObservableList<FacultyProperty> propertiesFromTable, FacultyProperty facultyToEdit) {
        this.propertiesFromTable = propertiesFromTable;
        this.facultyToEdit = facultyToEdit;
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        nameLabel = new Label("Name:");
        phoneLabel = new Label("Phone:");
        emailLabel = new Label("Email:");
        deansAddressLabel = new Label("Dean's address:");
        studentCountLabel = new Label("Students count:");
        descriptionLabel = new Label("Description:");

        nameField = new TextField();
        phoneField = new TextField();
        emailField = new TextField();
        deansAddressField = new TextField();
        studentCountField = new TextField();
        descriptionField = new TextArea();
        descriptionField.setPrefColumnCount(15);
        descriptionField.setPrefRowCount(3);

        if (facultyToEdit != null) {
            setEditData(facultyToEdit);
            disabledOkButton(false);
        }
    }

    private void setEditData(FacultyProperty edit) {
        nameField.setText(edit.getName());
        phoneField.setText(edit.getPhone());
        emailField.setText(edit.getEmail());
        deansAddressField.setText(edit.getDeansAddress());
        studentCountField.setText(String.valueOf(edit.getStudentCount()));
        descriptionField.setText(edit.getDescription());
    }

    @Override
    protected void initActions() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            String oldName = facultyToEdit != null ? facultyToEdit.getName() : null;
            disabledOkButton(newValue.isEmpty() || !checkNotExistingName(newValue, oldName));
        });
    }

    private boolean checkNotExistingName(String newName, String oldName) {
        for (FacultyProperty property : propertiesFromTable) {
            if (property.getName().equalsIgnoreCase(newName)) {
                return oldName != null && property.getName().equalsIgnoreCase(oldName);
            }
        }
        return true;
    }

    @Override
    protected void placeComponents() {
        GridPane form = new GridPane();

        form.addColumn(0,
                nameLabel,
                phoneLabel,
                emailLabel,
                deansAddressLabel,
                studentCountLabel,
                descriptionLabel);

        form.addColumn(1,
                nameField,
                phoneField,
                emailField,
                deansAddressField,
                studentCountField,
                descriptionField);

        form.setVgap(10);
        form.getColumnConstraints().addAll(
                new ColumnConstraints(130),
                new ColumnConstraints(450));
        form.getRowConstraints().addAll(
                new RowConstraints(30),
                new RowConstraints(30),
                new RowConstraints(30),
                new RowConstraints(30),
                new RowConstraints(30),
                new RowConstraints(100)
        );
        setContent(form);
    }

    public FacultyProperty getNewFaculty() {
        FacultyProperty faculty = new FacultyProperty();
        if (facultyToEdit != null) {
            faculty.setId(facultyToEdit.getId());
        }
        faculty.setName(nameField.getText());
        faculty.setPhone(phoneField.getText());
        faculty.setEmail(emailField.getText());
        faculty.setDeansAddress(deansAddressField.getText());
        String studentCount = studentCountField.getText();
        faculty.setStudentCount(studentCount.isEmpty() ? 0 : Integer.parseInt(studentCount));
        faculty.setDescription(descriptionField.getText());
        return faculty;
    }
}
