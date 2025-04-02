import db.Database;
import todo.entity.Step;
import todo.entity.Task;
import todo.serializer.StepSerializer;
import todo.serializer.TaskSerializer;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {
        Database.registerValidator(Task.TASK_ENTITY_ID, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());
        Database.registerSerializer(Task.TASK_ENTITY_ID, new TaskSerializer());
        Database.registerSerializer(Step.STEP_ENTITY_CODE, new StepSerializer());
        Scanner inp = new Scanner(System.in);
        while(true){
            System.out.print("What do you want to do? ");
            String command = inp.nextLine();
            command = command.toLowerCase();
            switch (command){
                case "add task": {
                    System.out.print("Title: ");
                    String title = inp.nextLine();
                    System.out.print("Description: ");
                    String description = inp.nextLine();
                    System.out.print("Date(yyyy-mm-dd format): ");
                    String dateStr = inp.nextLine();
                    TaskService.addTask(title, description, dateStr);
                    System.out.println("=====");
                    break;
                }
                case "add step": {
                    System.out.print("TaskID: ");
                    UUID taskRef = UUID.fromString(inp.nextLine());
                    System.out.print("Title: ");
                    String title = inp.nextLine();
                    StepService.addStep(taskRef, title);
                    System.out.println("=====");
                    break;
                }
                case "delete task": {
                    System.out.print("ID: ");
                    UUID id = UUID.fromString(inp.nextLine());
                    TaskService.delete(id);
                    System.out.println("=====");
                    break;
                }
                case "update task": {
                    System.out.print("Task ID: ");
                    UUID id = UUID.fromString(inp.nextLine());
                    System.out.print("Field (if date, YYYY-MM-DD format): ");
                    String field = inp.nextLine();
                    switch (field.toLowerCase()){
                        case "title": {
                            System.out.print("New Value: ");
                            String newValue = inp.nextLine();
                            TaskService.update(id, field, newValue, true, false);
                            System.out.println("=====");
                            break;
                        }
                        case "content":
                        case "description": {
                            System.out.print("New Value: ");
                            String newValue = inp.nextLine();
                            TaskService.update(id, field, newValue, false, false);
                            System.out.println("=====");
                            break;
                        }
                        case "date":
                        case "due date": {
                            System.out.print("New Value: ");
                            String newValue = inp.nextLine();
                            TaskService.update(id, field, newValue, false, true);
                            System.out.println("=====");
                            break;
                        }
                        case "status": {
                            System.out.print("\nWhat is the status?\n\n1.Completed\n2.In progress\n3.Not started\nyour option: ");
                            int option = inp.nextInt();
                            TaskService.updateStatus(id, option);
                            System.out.println("=====");
                            break;
                        }
                        default:{
                            System.out.println("invalid field.");
                            break;
                        }
                    }
                    break;
                }
                case "update step": {
                    System.out.print("Step ID: ");
                    UUID id = UUID.fromString(inp.nextLine());
                    System.out.print("Field: ");
                    String field = inp.nextLine();
                    switch (field.toLowerCase()){
                        case "title": {
                            System.out.print("New Value: ");
                            String newValue = inp.nextLine();
                            StepService.update(id, field, newValue, false);
                            System.out.println("=====");
                            break;
                        }
                        case "task ref":
                        case "taskref": {
                            System.out.println("New TaskID: ");
                            String newValue = inp.nextLine();
                            StepService.update(id, field, newValue, true);
                            System.out.println("=====");
                            break;
                        }
                        case "status": {
                            System.out.print("\nWhat is the status?\n1.Completed\n2.Not started\nyour option: ");
                            int option = inp.nextInt();
                            StepService.updateStatus(id, option);
                            System.out.println("=====");
                            break;
                        }
                        default:{
                            System.out.println("invalid field.");
                            break;
                        }
                    }
                    break;
                }
                case "delete step": {
                    System.out.print("Step ID: ");
                    UUID id = UUID.fromString(inp.nextLine());
                    StepService.delete(id);
                    System.out.println("=====");
                    break;
                }
                case "get task by id":
                case "get task": {
                    System.out.print("Task ID: ");
                    UUID id = UUID.fromString(inp.nextLine());
                    TaskService.getTaskByID(id);
                    System.out.println("=====");
                    break;
                }
                case "get incomplete tasks":
                case "get incomplete":
                case "get itasks":
                case "get tasks incomplete":{
                    TaskService.getTaskIncomplete();
                    System.out.println("=====");
                    break;
                }
                case "get tasks":
                case "get all tasks":
                case "get tasks all":{
                    TaskService.getTaskAll();
                    System.out.println("=====");
                    break;
                }

                case "save":
                case "save all":{
                    try {
                        Database.save();
                        System.out.println("all entities saved successfully.");
                    } catch (IOException e) {
                        System.out.println("cannot save.");
                        System.out.println(e.getMessage());
                    }
                }
                case "exit":{
                    System.out.println("exiting program...");
                    System.exit(0);
                }
                default:{
                    System.out.println("invalid command, try again.");
                    System.out.println("=====");
                    break;
                }
            }
        }
    }
}