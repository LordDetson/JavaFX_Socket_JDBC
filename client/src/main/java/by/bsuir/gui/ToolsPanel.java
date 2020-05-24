package by.bsuir.gui;

import by.bsuir.converter.FacultyConverter;
import by.bsuir.event.Client;
import by.bsuir.event.ComponentType;
import by.bsuir.event.Event;
import by.bsuir.property.FacultyProperty;
import by.bsuir.util.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class ToolsPanel extends ToolBar implements Initializable {
    private final Stage owner;
    private final FacultyTableView table;

    private Button newButton;
    private Button editButton;
    private Button deleteButton;

    public ToolsPanel(Stage owner, FacultyTableView table) {
        this.owner = owner;
        this.table = table;
    }

    @Override
    public void init() {
        initComponents();
        initActions();
        placeComponents();
    }

    private void initComponents() {
        newButton = new Button("New");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        editButton.disableProperty().set(true);
        deleteButton.disableProperty().set(true);
    }

    private void initActions() {
        initNewButtonAction();
        initEditButtonAction();
        initDeleteButtonAction();
    }

    private void initNewButtonAction() {
        newButton.setOnAction(event -> {
            FacultyEditorDialogPane createFacultyDialogPane = new FacultyEditorDialogPane(table.getItems());
            createFacultyDialogPane.init();
            DialogStage dialogStage = new DialogStage(owner, createFacultyDialogPane, "Create faculty");
            createFacultyDialogPane.setOkAction(newEvent -> {
                FacultyProperty newFaculty = createFacultyDialogPane.getNewFaculty();
                Client.getSender().create()
                        .component(FacultyConverter.convertToDomain(newFaculty))
                        .componentType(ComponentType.FACULTY)
                        .crudType(Event.EventType.CREATED)
                        .send();
                table.setCashedSelectedItem(getItems().size());
                dialogStage.close();
            });
            createFacultyDialogPane.setCancelAction(cancelEvent -> dialogStage.close());
            dialogStage.show();
        });
    }

    private void initEditButtonAction() {
        editButton.setOnAction(event -> {
            FacultyProperty facultyToEdit = table.getSelectionModel().getSelectedItem();
            FacultyEditorDialogPane editFacultyDialogPane = new FacultyEditorDialogPane(table.getItems(), facultyToEdit);
            editFacultyDialogPane.init();
            DialogStage dialogStage = new DialogStage(owner, editFacultyDialogPane, "Edit faculty");
            editFacultyDialogPane.setOkAction(editEvent -> {
                FacultyProperty newFaculty = editFacultyDialogPane.getNewFaculty();
                Client.getSender().create()
                        .component(FacultyConverter.convertToDomain(newFaculty))
                        .componentType(ComponentType.FACULTY)
                        .crudType(Event.EventType.UPDATED)
                        .send();
                table.cashSelectedItem();
                dialogStage.close();
            });
            editFacultyDialogPane.setCancelAction(cancelEvent -> dialogStage.close());
            dialogStage.show();
        });
    }

    private void initDeleteButtonAction() {
        deleteButton.setOnAction(event -> {
            ObservableList<FacultyProperty> facultiesToDelete = table.getSelectionModel().getSelectedItems();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning delete");
            alert.setHeaderText("Are you sure to delete?");
            StringBuilder warningMsgBuilder = new StringBuilder("The following items will be deleted:");
            for (FacultyProperty property : facultiesToDelete) {
                warningMsgBuilder.append("\n").append(property.getName());
            }
            alert.setContentText(warningMsgBuilder.toString());

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    Client.getSender().create()
                            .components(FacultyConverter.convertToDomains(facultiesToDelete))
                            .componentType(ComponentType.FACULTY)
                            .crudType(Event.EventType.DELETED)
                            .send();
                }
            });
        });
    }

    private void placeComponents() {
        getItems().addAll(
                newButton,
                editButton,
                deleteButton
        );
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
