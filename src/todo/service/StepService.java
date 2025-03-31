package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;

import java.util.Scanner;
import java.util.UUID;

public class StepService {
    public static void saveStep(UUID taskRef, String title) {
        Step step = new Step();
        step.taskRef = taskRef;
        step.setStatus(Step.Status.NotStarted);
        step.setTitle(title);
        try {
            Database.add(step);
            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);
            System.out.println("Creation Date: " + step.getCreationDate());
        } catch (InvalidEntityException e) {
            throw new RuntimeException("Step is Invalid.");
        }
    }

    public static void addStep(UUID taskRef, String title){
        StepService.saveStep(taskRef, title);
    }

    public static void delete(UUID id){
        Scanner inp = new Scanner(System.in);
        try{
            Database.delete(id);
            System.out.println("Step deleted. ID = " + id);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public static void update(UUID id, String field, String newValue, Boolean isTaskRef){
        try{
            Step step = (Step) Database.get(id);
            if(isTaskRef){
                UUID newTaskRef = UUID.fromString(newValue);
                UUID temp = step.taskRef;
                step.taskRef = newTaskRef;
                try {
                    Database.update(step);
                    System.out.println("Step's task successfully changed.");
                    System.out.println("Old TaskRef: " + temp);
                    System.out.println("New TaskRef: " + newValue);
                    System.out.println("Modification Date: " + step.getLastModificationDate());
                } catch (InvalidEntityException e) {
                    System.out.println("cannot change Task of Step.");
                    System.out.println(e.getMessage());
                }
            }else {
                String temp = step.getTitle();
                step.setTitle(newValue);
                try{
                    Database.update(step);
                    System.out.println("Step successfully updated.");
                    System.out.println("Old title: " + temp);
                    System.out.println("New Value: " + newValue);
                    System.out.println("Modification Date: " + step.getLastModificationDate());
                } catch (InvalidEntityException e) {
                    System.out.println("cannot update step.");
                    System.out.println(e.getMessage());
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("cannot update step.");
            System.out.println(e.getMessage());
        }
    }

    public static void updateStatus(UUID id, int option){
        try{
            Step step = (Step) Database.get(id);
            switch (option){
                case 1: {
                    String temp = String.valueOf(step.getStatus());
                    step.setStatus(Step.Status.Completed);
                    try {
                        Database.update(step);
                        System.out.println("Step's status changed.");
                        System.out.println("Old status: " + temp);
                        System.out.println("New status: " + step.getStatus());
                        System.out.println("Modification Date: " + step.getLastModificationDate());
                        TaskService.checkTaskStatus(step.taskRef);
                    } catch (InvalidEntityException e) {
                        System.out.println("cannot update step.");
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    String temp = String.valueOf(step.getStatus());
                    step.setStatus(Step.Status.NotStarted);
                    try{
                        Database.update(step);
                        System.out.println("Step's status changed.");
                        System.out.println("Old status: " + temp);
                        System.out.println("New status: " + step.getStatus());
                        System.out.println("Modification Date: " + step.getLastModificationDate());
                        TaskService.checkTaskStatus(step.taskRef);
                    }catch (InvalidEntityException e){
                        System.out.println("cannot update step.");
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default: {
                    System.out.println("Invalid Option.");
                    break;
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("cannot update step");
            System.out.println(e.getMessage());
        }
    }
}
