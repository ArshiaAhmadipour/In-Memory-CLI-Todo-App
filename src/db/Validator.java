package db;
import db.exception.*;

public interface Validator {
    void validate(Entity entity) throws InvalidEntityException;


}
