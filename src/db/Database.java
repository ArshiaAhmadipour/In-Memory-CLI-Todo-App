package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Database {

    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void add(Entity e) throws InvalidEntityException{
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);
        e.id = UUID.randomUUID();
        entities.add(e);
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
        Validator validator = validators.get(entityInput.getEntityCode());
        for(Entity entity : entities){
            if(entityInput.id == entity.id){
                entities.remove(entity);
                validator.validate(entityInput);
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




}
