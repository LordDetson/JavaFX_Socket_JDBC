package by.bsuir.property;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class FacultyProperty {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty deansAddress;
    private SimpleIntegerProperty studentCount;
    private SimpleStringProperty description;

    public FacultyProperty() {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        deansAddress = new SimpleStringProperty();
        studentCount = new SimpleIntegerProperty();
        description = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDeansAddress() {
        return deansAddress.get();
    }

    public SimpleStringProperty deansAddressProperty() {
        return deansAddress;
    }

    public void setDeansAddress(String deansAddress) {
        this.deansAddress.set(deansAddress);
    }

    public int getStudentCount() {
        return studentCount.get();
    }

    public SimpleIntegerProperty studentCountProperty() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount.set(studentCount);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacultyProperty property = (FacultyProperty) o;
        return Objects.equals(name, property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
