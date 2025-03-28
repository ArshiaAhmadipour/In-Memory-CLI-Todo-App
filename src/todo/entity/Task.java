package todo.entity;


import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

import java.util.Date;

public class Task extends Entity implements Validator {
    public enum Status{
        Completed , InProgress, NotStarted
    }

    public String title;
    public String description;
    public Date dueDate;
    public Status status;

    @Override
    public Entity copy() {
        return null;
    }

    @Override
    public int getEntityCode() {
        return 0;
    }

    @Override
    public void validate(Entity entity) throws InvalidEntityException {

    }




}

