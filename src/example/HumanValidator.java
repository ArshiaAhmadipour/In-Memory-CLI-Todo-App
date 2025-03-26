package example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Human)){
            throw new IllegalArgumentException("Entity not human.");
        }else if(((Human) entity).age < 0){
            throw new InvalidEntityException("Age must be positive.");
        }else if(((Human) entity).name == null || ((Human) entity).name.isEmpty()){
            throw new InvalidEntityException("Name can't be empty or null.");
        }else{
            System.out.println("Human is valid.");
        }

    }
}
