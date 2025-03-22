package db.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        super("Cannot find entity");
    }

    // Constructor with custom message
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Constructor with id-based message
    public EntityNotFoundException(int id) {
        super("Cannot find entity with id=" + id);
    }
}


