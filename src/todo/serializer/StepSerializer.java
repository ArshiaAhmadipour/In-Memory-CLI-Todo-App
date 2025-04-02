package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Step;
import todo.entity.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StepSerializer implements Serializer {
    @Override
    public String serialize(Entity e) throws IOException{
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(e);
            return baos.toString(StandardCharsets.ISO_8859_1);
        }
    }

    @Override
    public Step deserialize(String s) throws IOException, ClassNotFoundException{
        try (ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes(StandardCharsets.ISO_8859_1));
             ObjectInputStream ois = new ObjectInputStream(bais);){
            return (Step) ois.readObject();
        }
    }
}
