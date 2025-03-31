package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;
import java.util.UUID;

public class Step extends Entity implements Trackable {
    public static final int STEP_ENTITY_CODE = 20;

    public enum Status{
        Completed, NotStarted
    }
    public String title;
    public Status status;
    public UUID taskRef;
    private Date creationDate;
    private Date lastModificationDate;

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

    @Override
    public Entity copy() {
        String titleCopy = new String(title);
        UUID taskRefCopy = taskRef;
        Step stepCopy = new Step();
        stepCopy.status = status;
        stepCopy.title = titleCopy;
        stepCopy.taskRef = taskRefCopy;
        stepCopy.id = this.id;
        stepCopy.setCreationDate(new Date(creationDate.getTime()));
        if(lastModificationDate != null){
            stepCopy.setLastModificationDate(new Date(lastModificationDate.getTime()));

        }
        return stepCopy;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }
}
