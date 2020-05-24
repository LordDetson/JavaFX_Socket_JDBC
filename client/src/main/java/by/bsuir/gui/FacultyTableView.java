package by.bsuir.gui;

import by.bsuir.converter.FacultyConverter;
import by.bsuir.domain.Faculty;
import by.bsuir.event.ComponentType;
import by.bsuir.event.Event;
import by.bsuir.event.EventObserver;
import by.bsuir.property.FacultyProperty;
import by.bsuir.util.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacultyTableView extends TableView<FacultyProperty> implements EventObserver, Initializable {

    private static final String ALIGNMENT_CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String ALIGNMENT_CENTER_RIGHT = "-fx-alignment: CENTER-RIGHT;";

    private TableColumn<FacultyProperty, String> nameColumn;
    private TableColumn<FacultyProperty, String> phoneColumn;
    private TableColumn<FacultyProperty, String> emailColumn;
    private TableColumn<FacultyProperty, String> addressColumn;
    private TableColumn<FacultyProperty, Integer> studentCountColumn;
    private TableColumn<FacultyProperty, String> descriptionColumn;

    private int cashedSelectedItem = 0;

    public FacultyTableView() {
        setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @Override
    public void init() {
        initComponents();
        placeComponents();
    }

    private void initComponents() {
        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setStyle(ALIGNMENT_CENTER_LEFT);
        nameColumn.setPrefWidth(350);

        phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setStyle(ALIGNMENT_CENTER_LEFT);
        phoneColumn.setPrefWidth(150);

        emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setStyle(ALIGNMENT_CENTER_LEFT);
        emailColumn.setPrefWidth(150);

        addressColumn = new TableColumn<>("Dean's address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("deansAddress"));
        addressColumn.setStyle(ALIGNMENT_CENTER_LEFT);
        addressColumn.setPrefWidth(200);

        studentCountColumn = new TableColumn<>("Student count");
        studentCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        studentCountColumn.setStyle(ALIGNMENT_CENTER_RIGHT);
        studentCountColumn.setPrefWidth(50);

        descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setStyle(ALIGNMENT_CENTER_LEFT);
        descriptionColumn.setPrefWidth(600);
    }

    private void placeComponents() {
        getColumns().addAll(
                nameColumn,
                phoneColumn,
                emailColumn,
                addressColumn,
                studentCountColumn,
                descriptionColumn
        );
    }

    public int getCashedSelectedItem() {
        return cashedSelectedItem;
    }

    public void cashSelectedItem() {
        this.cashedSelectedItem = getSelectionModel().getSelectedIndex();
    }

    public void setCashedSelectedItem(int cashedSelectedItem) {
        this.cashedSelectedItem = cashedSelectedItem;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getComponentType().equals(ComponentType.FACULTY) && event.getEventType().equals(Event.EventType.REFRESH)) {
            Collection<Serializable> components = event.getComponents();
            List<FacultyProperty> data = new ArrayList<>();
            for (Serializable component : components) {
                data.add(FacultyConverter.convertToProperty((Faculty) component));
            }
            if (getItems().isEmpty()) {
                loadData(data);
            } else {
                reloadData(data);
            }
        }
    }

    public void reloadData(Collection<? extends FacultyProperty> properties) {
        getItems().clear();
        loadData(properties);
        getSelectionModel().select(getCashedSelectedItem());
    }

    public void loadData(Collection<? extends FacultyProperty> properties) {
        getItems().addAll(properties);
    }
}
