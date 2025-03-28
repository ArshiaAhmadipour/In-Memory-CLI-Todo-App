package todo.entity;

import db.Entity;

public class Step extends Entity {
    public enum Status{
        Completed, NotStarted
    }

    public String title;
    public Status status;
    public int taskRef;

    @Override
    public Entity copy() {
        String titleCopy = new String(title);
        int taskRefCopy = taskRef;
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
