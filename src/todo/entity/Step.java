package todo.entity;

import db.Entity;

public class Step extends Entity {
    public enum Status{
        Completed, NotStarted
    }

    public String title;
    public Status status;
    public int taskRef =

    @Override
    public Entity copy() {
        return null;
    }

    @Override
    public int getEntityCode() {
        return 0;
    }
}
