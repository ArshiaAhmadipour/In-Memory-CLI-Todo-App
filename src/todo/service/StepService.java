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
        step.status = Step.Status.NotStarted;
        step.title = title;
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

    public static void delete(){
        Scanner inp = new Scanner(System.in);
        System.out.print("Step ID: ");
        UUID id = UUID.fromString(inp.nextLine());
        try{
            Database.delete(id);
            System.out.println("Step deleted. ID = " + id);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
