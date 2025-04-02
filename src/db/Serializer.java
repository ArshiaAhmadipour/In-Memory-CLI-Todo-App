package db;

import java.io.IOException;
import java.io.Serializable;

public interface Serializer extends Serializable {
    String serialize(Entity e) throws IOException;
    Entity deserialize(String s) throws IOException, ClassNotFoundException;
}
