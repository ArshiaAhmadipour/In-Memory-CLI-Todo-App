package db;

import java.io.Serializable;

public interface Serializer extends Serializable {
    String serialize(Entity e);
    Entity deserialize(String s);
}
