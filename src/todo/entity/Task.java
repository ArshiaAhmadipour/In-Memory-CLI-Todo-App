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

    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    @Override
    public Entity copy() {
        String titleCopy = new String(this.getTitle());
        String descriptionCopy = new String(this.getDescription());
        Date dueDateCopy = new Date(this.getDueDate().getTime());
        Task taskCopy = new Task();
        taskCopy.setTitle(titleCopy);
        taskCopy.setDescription(descriptionCopy);
        taskCopy.setDueDate(dueDateCopy);
        taskCopy.setStatus(this.getStatus());
        taskCopy.id = this.id;

        return taskCopy;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_ID;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}

