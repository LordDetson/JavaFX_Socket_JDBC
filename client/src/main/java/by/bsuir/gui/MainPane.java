package by.bsuir.gui;

import by.bsuir.event.Client;
import by.bsuir.event.ComponentType;
import by.bsuir.event.Event;
import by.bsuir.property.FacultyProperty;
import by.bsuir.util.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPane extends BorderPane implements Initializable {
    private static final Logger log = LogManager.getLogger(MainPane.class);
    private final Stage owner;

    private ToolsPanel toolsPanel;
    private FacultyTableView table;

    public MainPane(Stage owner) {
        this.owner = owner;
    }

    @Override
    public void init() {
        initComponents();
        initActions();
        placeComponents();
        Client.getReceiver().addObserver(table);
        Client.getSender().create()
                .crudType(Event.EventType.LOAD_DATA)
                .componentType(ComponentType.FACULTY)
                .send();
        log.info("Initialized GUI");
    }

    private void initComponents() {
        table = new FacultyTableView();
        table.init();
        toolsPanel = new ToolsPanel(owner, table);
        toolsPanel.init();
    }

    private void initActions() {
        enableActions();
    }

    private void placeComponents() {
        setTop(toolsPanel);
        setCenter(table);
    }

    private void enableActions() {
        TableView.TableViewSelectionModel<FacultyProperty> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (selectionModel.getSelectedItems().isEmpty()) {
                toolsPanel.getEditButton().disableProperty().set(true);
                toolsPanel.getDeleteButton().disableProperty().set(true);
            } else if (selectionModel.getSelectedItems().size() == 1) {
                toolsPanel.getEditButton().disableProperty().set(false);
                toolsPanel.getDeleteButton().disableProperty().set(false);
            } else if (selectionModel.getSelectedItems().size() > 1) {
                toolsPanel.getEditButton().disableProperty().set(true);
                toolsPanel.getDeleteButton().disableProperty().set(false);
            }
        });
    }
}
