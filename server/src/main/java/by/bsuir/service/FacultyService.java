package by.bsuir.service;

import by.bsuir.dao.Repository;
import by.bsuir.domain.Faculty;
import by.bsuir.event.ComponentType;
import by.bsuir.event.Event;
import by.bsuir.event.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacultyService implements Service<Faculty> {

    private final Repository<Faculty> facultyRepository;

    public FacultyService(Repository<Faculty> facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public void handleEvent(Event event) {
        if (checkEvent(event)) {
            switch (event.getEventType()) {
                case CREATED:
                    add((Faculty) event.getComponent());
                    break;
                case UPDATED:
                    update((Faculty) event.getComponent());
                    break;
                case DELETED:
                    Collection<Serializable> components = event.getComponents();
                    List<Faculty> faculties = new ArrayList<>();
                    for (Serializable component : components) {
                        faculties.add((Faculty) component);
                    }
                    deleteAll(faculties);
                    break;
                default:
                    assert false : "No implementation operation";
            }
        }
        Server.getSender().create()
                .components(getAll())
                .crudType(Event.EventType.REFRESH)
                .componentType(ComponentType.FACULTY)
                .send();
    }

    private boolean checkEvent(Event event) {
        return event.getComponentType().equals(ComponentType.FACULTY);
    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepository.getAll();
    }

    @Override
    public void add(Faculty domain) {
            facultyRepository.create(domain);
    }

    @Override
    public void update(Faculty domain) {
        facultyRepository.update(domain);
    }

    @Override
    public void deleteAll(Collection<? extends Faculty> domains) {
        facultyRepository.deleteAll(domains);
    }
}
