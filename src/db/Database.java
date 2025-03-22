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
        entities.add(e);
    }

    public static Entity get(UUID id) throws EntityNotFoundException{
        for(Entity e : entities){
            if(e.id == id){
                return e;
            }
        }
        throw new EntityNotFoundException("Entity not found.");
    }

    public static void delete(UUID id) throws EntityNotFoundException{
        for(Entity e : entities){
            if(e.id == id){
                entities.remove(e);
                System.out.println("Entity deleted.");
                return;
            }
        }
        throw new EntityNotFoundException("Entity not found to delete.");
    }

    public static void update(Entity e) throws EntityNotFoundException{
        for(Entity entity : entities){
            if(e.id == entity.id){
                entities.remove(entity);
                entities.add(e);
                System.out.println("Entity updated successfully.");
                return;
            }
        }
        throw new EntityNotFoundException("Entity with same ID not found.");
    }



}
