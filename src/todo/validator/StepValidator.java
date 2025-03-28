package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(entity instanceof Step){
            try{
                Database.get(((Step) entity).taskRef);
            } catch (EntityNotFoundException e) {
                throw new InvalidEntityException("Task Ref is not set right.");
            }

        }else{
            throw new IllegalArgumentException("Entity must be a Step.");
        }
    }
}
