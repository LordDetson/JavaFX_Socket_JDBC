package by.bsuir.dao;

import by.bsuir.domain.Faculty;
import by.bsuir.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacultyRepository implements Repository<Faculty> {
    private static final String SELECT_ALL = "select * from faculty";
    private static final String INSERT_FACULTY = "insert into faculty (name, phone, email, deansAddress, studentCount, description) values (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_FACULTY = "update faculty set name = ?, phone = ?, email = ?, deansAddress = ?, studentCount = ?, description = ? where id = ?";
    private static final String DELETE_FACULTY = "delete from faculty where id = ?";

    private final Connection connection;

    public FacultyRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Faculty> getAll() {
        List<Faculty> faculties = new ArrayList<>();
        try (PreparedStatement selectAll = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = selectAll.executeQuery()) {
            while (resultSet.next()) {
                faculties.add(createFaculty(resultSet));
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return faculties;
    }

    @Override
    public void create(Faculty domain) {
        try (PreparedStatement insertFaculty = connection.prepareStatement(INSERT_FACULTY)){
            getPreparedStatementWithData(insertFaculty, domain).executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    @Override
    public void update(Faculty domain) {
        try (PreparedStatement updateFaculty = connection.prepareStatement(UPDATE_FACULTY)){
            PreparedStatement updateFacultyWithData = getPreparedStatementWithData(updateFaculty, domain);
            updateFacultyWithData.setInt(7, domain.getId());
            updateFacultyWithData.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    @Override
    public void deleteAll(Collection<? extends Faculty> domains) {
        try (PreparedStatement deleteFaculty = connection.prepareStatement(DELETE_FACULTY)){
            for (Faculty domain : domains) {
                deleteFaculty.setInt(1, domain.getId());
                deleteFaculty.execute();
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    private Faculty createFaculty(ResultSet resultSet) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setId(resultSet.getInt("id"));
        faculty.setName(resultSet.getString("name"));
        faculty.setPhone(resultSet.getString("phone"));
        faculty.setEmail(resultSet.getString("email"));
        faculty.setDeansAddress(resultSet.getString("deansAddress"));
        faculty.setStudentCount(resultSet.getInt("studentCount"));
        faculty.setDescription(resultSet.getString("description"));
        return faculty;
    }

    private PreparedStatement getPreparedStatementWithData(PreparedStatement preparedStatement, Faculty domain) throws SQLException {
        preparedStatement.setString(1, domain.getName());
        preparedStatement.setString(2, domain.getPhone());
        preparedStatement.setString(3, domain.getEmail());
        preparedStatement.setString(4, domain.getDeansAddress());
        preparedStatement.setInt(5, domain.getStudentCount());
        preparedStatement.setString(6, domain.getDescription());
        return preparedStatement;
    }
}
