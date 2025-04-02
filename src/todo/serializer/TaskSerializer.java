package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TaskSerializer implements Serializer {
    @Override
    public String serialize(Entity e) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeInt(e.getEntityCode()); // Include entity code so we read it back in loading.
            oos.writeObject(e);
            return baos.toString(StandardCharsets.ISO_8859_1);
        }
    }

    @Override
    public Task deserialize(String s) throws IOException, ClassNotFoundException{
        try (ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes(StandardCharsets.ISO_8859_1));
             ObjectInputStream ois = new ObjectInputStream(bais);){
            return (Task) ois.readObject();
        }
    }
}
