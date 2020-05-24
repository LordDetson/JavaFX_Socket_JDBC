package by.bsuir.event;

import by.bsuir.domain.Faculty;

public enum  ComponentType {
    FACULTY(Faculty.class);

    private final Class<?> type;

    ComponentType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
