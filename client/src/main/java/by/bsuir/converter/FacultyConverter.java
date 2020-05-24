package by.bsuir.converter;

import by.bsuir.domain.Faculty;
import by.bsuir.property.FacultyProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacultyConverter {
    private FacultyConverter() { }

    public static FacultyProperty convertToProperty(Faculty faculty) {
        FacultyProperty property = new FacultyProperty();
        property.setId(faculty.getId());
        property.setName(faculty.getName());
        property.setPhone(faculty.getPhone());
        property.setEmail(faculty.getEmail());
        property.setDeansAddress(faculty.getDeansAddress());
        property.setStudentCount(faculty.getStudentCount());
        property.setDescription(faculty.getDescription());
        return property;
    }

    public static List<FacultyProperty> convertToProperties(Collection<? extends Faculty> faculties) {
        List<FacultyProperty> properties = new ArrayList<>();
        faculties.forEach(faculty -> properties.add(convertToProperty(faculty)));
        return properties;
    }

    public static Faculty convertToDomain(FacultyProperty property) {
        Faculty faculty = new Faculty();
        faculty.setId(property.getId());
        faculty.setName(property.getName());
        faculty.setPhone(property.getPhone());
        faculty.setEmail(property.getEmail());
        faculty.setDeansAddress(property.getDeansAddress());
        faculty.setStudentCount(property.getStudentCount());
        faculty.setDescription(property.getDescription());
        return faculty;
    }

    public static List<Faculty> convertToDomains(Collection<? extends FacultyProperty> properties) {
        List<Faculty> faculties = new ArrayList<>();
        properties.forEach(property -> faculties.add(convertToDomain(property)));
        return faculties;
    }
}
