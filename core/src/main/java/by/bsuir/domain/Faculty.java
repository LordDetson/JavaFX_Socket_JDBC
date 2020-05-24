package by.bsuir.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Faculty implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String deansAddress;
    private Integer studentCount;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeansAddress() {
        return deansAddress;
    }

    public void setDeansAddress(String deansAddress) {
        this.deansAddress = deansAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id &&
                Objects.equals(name, faculty.name) &&
                Objects.equals(studentCount, faculty.studentCount) &&
                Objects.equals(phone, faculty.phone) &&
                Objects.equals(email, faculty.email) &&
                Objects.equals(deansAddress, faculty.deansAddress) &&
                Objects.equals(description, faculty.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, studentCount, phone, email, deansAddress, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Faculty.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
