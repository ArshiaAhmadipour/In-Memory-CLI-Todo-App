package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static HashMap<Integer, Serializer> serializers = new HashMap<>();

    private Database() {}

    public static void add(Entity entityInput) throws InvalidEntityException{
        if(entityInput instanceof Trackable){
            ((Trackable) entityInput).setCreationDate(new Date());
        }
        Database.isValid(entityInput);
        entityInput.id = UUID.randomUUID();
        entities.add(entityInput.copy());
    }

    public static Entity get(UUID id) throws EntityNotFoundException{
        for(Entity entity : entities){
            if(entity.id.equals(id)){
                return entity.copy();
            }
        }
        throw new EntityNotFoundException("Entity not found.");
    }

    public static ArrayList<Entity> getAll(int entityCode){
        ArrayList<Entity> returnList = new ArrayList<>();
        for(Entity entity : entities){
            if(entity.getEntityCode() == entityCode){
                returnList.add(entity.copy());
            }
        }
        return returnList;
    }

    public static void delete(UUID id) throws EntityNotFoundException{
        for(Entity entity : entities){
            if(entity.id.equals(id)){
                entities.remove(entity);
                return;
            }
        }
        throw new EntityNotFoundException("Entity not found to delete.");
    }

    public static void update(Entity entityInput) throws EntityNotFoundException, InvalidEntityException{
        for(Entity entity : entities){
            if(entityInput.id.equals(entity.id)){
                entities.remove(entity);
                Database.isValid(entityInput);
                if(entityInput instanceof Trackable){
                    ((Trackable) entityInput).setLastModificationDate(new Date());
                }
                entities.add(entityInput.copy());
                return;
            }
        }
        throw new EntityNotFoundException("Entity with same ID not found.");
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if(validators.containsKey(entityCode)){
            throw new IllegalArgumentException("Validator already exists.");
        }else{
            validators.put(entityCode, validator);
        }
    }

    private static void isValid(Entity entity) throws InvalidEntityException{
            Validator validator = validators.get(entity.getEntityCode());
            validator.validate(entity);
    } //for checking if entity is valid.

    public static void registerSerializer(int entityCode, Serializer serializer){
        if(serializers.containsKey(entityCode)){
            throw new IllegalArgumentException("Serializer already exists.");
        }else {
            serializers.put(entityCode, serializer);
        }
    }

    private static String serialize(Entity entity) throws InvalidEntityException, IOException {
        Serializer serializer = serializers.get(entity.getEntityCode());
        return serializer.serialize(entity);
    }

    public static void save(){
        for (Entity entity : entities){

        }
    }
}
