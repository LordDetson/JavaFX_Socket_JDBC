package by.bsuir.config;

import by.bsuir.exception.ExceptionHandler;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final Logger log = LogManager.getLogger(DatabaseManager.class);
    private static final String URL_FORMAT = "jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=%s&serverTimezone=%s";
    private final Properties connectionProperties = new Properties();
    private final Connection connection;

    public DatabaseManager() {
        loadProperties();
        connection = setUpConnection();
        executeScript();
    }

    private void loadProperties() {
        InputStream connectionPropertiesIS = getClass().getResourceAsStream("/connection.properties");
        try {
            log.info("Loading connection property...");
            connectionProperties.load(connectionPropertiesIS);
        } catch (IOException e) {
            ExceptionHandler.handle(Thread.currentThread(), e);
        }
    }

    private Connection setUpConnection() {
        Connection result = null;
        try {
            log.info("Setup connection...");
            String url = String.format(URL_FORMAT,
                    connectionProperties.getProperty("server.host"),
                    connectionProperties.getProperty("server.port"),
                    connectionProperties.getProperty("db.name"),
                    connectionProperties.getProperty("db.createIfNotExist"),
                    connectionProperties.getProperty("server.timezone")
            );
            log.info("Url connection to database server: " + url);
            result = DriverManager.getConnection(url,
                    connectionProperties.getProperty("user.name"),
                    connectionProperties.getProperty("user.password")
            );
        } catch (SQLException e) {
            ExceptionHandler.handle(Thread.currentThread(), e);
        }
        return result;
    }

    private void executeScript() {
        if (Boolean.parseBoolean(connectionProperties.getProperty("db.createTableIfNotExist")) &&
                !checkExistingTable("faculty")) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            log.info("Execute createTable.sql script");
            scriptRunner.runScript(new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("/createTable.sql"))));
            log.info("Execute initTable.sql script");
            scriptRunner.runScript(new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("/initTable.sql"))));
        }
    }

    private boolean checkExistingTable(String tableName) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("show tables like '" + tableName + "'")) {
            if (resultSet.next()) {
                return resultSet.getString(1).equals(tableName);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return false;
    }

    public Connection getConnection() {
        return connection;
    }
}
