package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.UUID;

public class Database {

    private static ArrayList<Entity> entities = new ArrayList<>();

    private Database() {}

    public static void add(Entity e){
        e.id = UUID.randomUUID();
        System.out.println("Entity added successfully. id: " + e.id);
        entities.add(e.copy());
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

    public static void update(Entity entityInput) throws EntityNotFoundException{
        for(Entity entity : entities){
            if(entityInput.id == entity.id){
                entities.remove(entity);
                entities.add(entityInput.copy());
                System.out.println("Entity updated successfully.");
                return;
            }
        }
        throw new EntityNotFoundException("Entity with same ID not found.");
    }



}
