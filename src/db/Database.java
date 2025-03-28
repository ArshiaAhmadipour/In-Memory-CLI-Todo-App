package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void add(Entity entityInput) throws InvalidEntityException{
        if(entityInput instanceof Trackable){
            ((Trackable) entityInput).setCreationDate(new Date());
        }
        Database.isValid(entityInput);
        entityInput.id = UUID.randomUUID();
        entities.add(entityInput);
        System.out.println("Entity added successfully.");
    }

    public static Entity get(UUID id) throws EntityNotFoundException{
        for(Entity entity : entities){
            if(entity.id == id){
                return entity.copy();
            }
        }
        throw new EntityNotFoundException("Entity not found.");
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> returnList = new ArrayList<>();
        for(Entity entity : entities){
            if(entity.getEntityCode() == entityCode){
                returnList.add(entity);
            }
        }
        return returnList;
    }

    public static void delete(UUID id) throws EntityNotFoundException{
        for(Entity entity : entities){
            if(entity.id == id){
                entities.remove(entity);
                System.out.println("Entity deleted.");
                return;
            }
        }
        throw new EntityNotFoundException("Entity not found to delete.");
    }

    public static void update(Entity entityInput) throws EntityNotFoundException, InvalidEntityException{
        for(Entity entity : entities){
            if(entityInput.id == entity.id){
                entities.remove(entity);
                Database.isValid(entityInput);
                if(entityInput instanceof Trackable){
                    ((Trackable) entityInput).setLastModificationDate(new Date());
                }
                entities.add(entityInput.copy());
                System.out.println("Entity updated successfully.");
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
        if(entity instanceof Validator) {
            Validator validator = validators.get(entity.getEntityCode());
            validator.validate(entity);
        }
        return;
    } //for checking if entity is valid.
}
