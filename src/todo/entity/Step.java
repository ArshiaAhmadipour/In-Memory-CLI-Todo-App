package todo.entity;

import db.Entity;

import java.util.UUID;

public class Step extends Entity {
    public enum Status{
        Completed, NotStarted
    }

    public String title;
    public Status status;
    public UUID taskRef;

    @Override
    public Entity copy() {
        String titleCopy = new String(title);
        UUID taskRefCopy = taskRef;
        Step stepCopy = new Step();
        stepCopy.status = status;
        stepCopy.title = titleCopy;
        stepCopy.taskRef = taskRefCopy;
        return stepCopy;
    }

    @Override
    public int getEntityCode() {
        return 0;
    }
}
