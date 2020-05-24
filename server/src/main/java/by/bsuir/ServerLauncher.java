package by.bsuir;

import by.bsuir.config.DatabaseManager;
import by.bsuir.dao.FacultyRepository;
import by.bsuir.dao.Repository;
import by.bsuir.domain.Faculty;
import by.bsuir.event.ComponentType;
import by.bsuir.event.Event;
import by.bsuir.event.Server;
import by.bsuir.service.FacultyService;
import by.bsuir.service.Service;

public class ServerLauncher {

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        Repository<Faculty> facultyRepository = new FacultyRepository(databaseManager.getConnection());
        Service<Faculty> facultyService = new FacultyService(facultyRepository);
        Server.launch();
        Server.getReceiver().addObserver(event -> {
            if (event.getComponentType().equals(ComponentType.FACULTY) &&
                    event.getEventType().equals(Event.EventType.LOAD_DATA)) {
                Server.getSender().create()
                        .componentType(ComponentType.FACULTY)
                        .crudType(Event.EventType.REFRESH)
                        .components(facultyService.getAll())
                        .send();
            }
        });
        Server.getReceiver().addObserver(facultyService);
    }
}
