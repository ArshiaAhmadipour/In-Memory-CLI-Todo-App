package todo.service;

import db.Database;
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
    public static void addStep(){
        Scanner inp = new Scanner(System.in);
        System.out.print("TaskID: ");
        UUID taskRef = UUID.fromString(inp.nextLine());
        System.out.print("Title: ");
        String title = inp.nextLine();
        StepService.saveStep(taskRef, title);
    }
}
