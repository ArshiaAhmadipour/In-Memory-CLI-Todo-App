package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static final HashMap<Integer, Validator> validators = new HashMap<>();
    private static final HashMap<Integer, Serializer> serializers = new HashMap<>();

    private static final String FILE_PATH = "src/db/data/db.txt";

    private Database() {
    }

    public static void add(Entity entityInput) throws InvalidEntityException {
        if (entityInput instanceof Trackable) {
            ((Trackable) entityInput).setCreationDate(new Date());
            ((Trackable) entityInput).setLastModificationDate(new Date());
        }
        Database.isValid(entityInput);
        entityInput.id = UUID.randomUUID();
        entities.add(entityInput.copy());
    }

    public static Entity get(UUID id) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id.equals(id)) {
                return entity.copy();
            }
        }
        throw new EntityNotFoundException("Entity not found.");
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> returnList = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == entityCode) {
                returnList.add(entity.copy());
            }
        }
        return returnList;
    }

    public static void delete(UUID id) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id.equals(id)) {
                entities.remove(entity);
                return;
            }
        }
        throw new EntityNotFoundException("Entity not found to delete.");
    }

    public static void update(Entity entityInput) throws EntityNotFoundException, InvalidEntityException {
        for (Entity entity : entities) {
            if (entityInput.id.equals(entity.id)) {
                entities.remove(entity);
                Database.isValid(entityInput);
                if (entityInput instanceof Trackable) {
                    ((Trackable) entityInput).setLastModificationDate(new Date());
                }
                entities.add(entityInput.copy());
                return;
            }
        }
        throw new EntityNotFoundException("Entity with same ID not found.");
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator already exists.");
        } else {
            validators.put(entityCode, validator);
        }
    }

    private static void isValid(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        validator.validate(entity);
    } //for checking if entity is valid.

    public static void registerSerializer(int entityCode, Serializer serializer) {
        if (serializers.containsKey(entityCode)) {
            throw new IllegalArgumentException("Serializer already exists.");
        } else {
            serializers.put(entityCode, serializer);
        }
    }

    private static String serialize(Entity entity) throws InvalidEntityException, IOException {
        Serializer serializer = serializers.get(entity.getEntityCode());
        return serializer.serialize(entity);
    }

    public static void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, StandardCharsets.UTF_8))) {
            for (Entity entity : entities) {
                Serializer serializer = serializers.get(entity.getEntityCode());
                if (serializer == null) {
                    throw new RuntimeException("missing serializer for entity code: " + entity.getEntityCode());
                }
                writer.write(serializer.serialize(entity));
                writer.newLine();
            }
        }
    }


    public static void load() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 0) {
                    throw new RuntimeException("invalid entity format: " + line);
                }

                int entityCode;
                try {
                    entityCode = Integer.parseInt(parts[0].trim());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid entity code in line: " + line);
                }

                Serializer serializer = serializers.get(entityCode);
                if (serializer == null) {
                    throw new RuntimeException("no serializer found for entity code: " + entityCode);
                }

                try {
                    Entity entity = serializer.deserialize(line);
                    entities.add(entity);
                } catch (Exception e) {
                    throw new RuntimeException("error deserializing entity: " + line, e);
                }
            }
        }
    }

}
