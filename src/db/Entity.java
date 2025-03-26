package db;

import java.util.UUID;

public abstract class Entity {
    public UUID id;
    public abstract Entity copy();
    public abstract int getEntityCode();

}
