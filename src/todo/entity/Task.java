package todo.entity;


import db.Entity;
import db.Trackable;
import db.Validator;
import db.exception.InvalidEntityException;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int TASK_ENTITY_ID = 10;
    public enum Status{
        Completed , InProgress, NotStarted
    }

    public String title;
    public String description;
    public Date dueDate;
    public Status status;

    @Override
    public Entity copy() {
        String titleCopy = new String(title);
        String descriptionCopy = new String(description);
        Date dueDateCopy = new Date(dueDate.getTime());
        Task taskCopy = new Task();
        taskCopy.title = titleCopy;
        taskCopy.description = descriptionCopy;
        taskCopy.dueDate = dueDateCopy;
        taskCopy.status = status;

        return taskCopy;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_ID;
    }

    @Override
    public void setCreationDate(Date date) {

    }

    @Override
    public Date getCreationDate() {
        return null;
    }

    @Override
    public void setLastModificationDate(Date date) {

    }

    @Override
    public Date getLastModificationDate() {
        return null;
    }
}

