package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TaskService {
    public static void setAsCompleted(UUID taskId) {
        Task task = (Task) Database.get(taskId);
        task.setStatus(Task.Status.Completed);
        try {
            Database.update(task);
        } catch (InvalidEntityException e) {
            throw new RuntimeException("task not found in Database to update.");
        }
    }

    public static void addTask(String title, String description, String dateStr){
        Task task = new Task();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            task.setDueDate(formatter.parse(dateStr));
            task.setTitle(title);
            task.setStatus(Task.Status.NotStarted);
            task.setDescription(description);
            try {
                Database.add(task);
                System.out.println("Task Saved Successfully.");
                System.out.println("id: " + task.id);
            } catch (InvalidEntityException e) {
                System.out.println("Cannot save task.");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Cannot save task.");
            System.out.println("Invalid Date Format");

        }
    }

    public static void delete(UUID id){
        try{
            ArrayList<Entity> steps = new ArrayList<>();
            steps = Database.getAll(Step.STEP_ENTITY_CODE);
            if(!(steps.isEmpty())){
                for(Entity entity : steps){
                    if(((Step) entity).taskRef.equals(id)){
                        Database.delete(entity.id);
                    }
                }
            }
            Database.delete(id);
            System.out.println("Task and all it's steps deleted.");
            System.out.println("Task ID: " + id);
        } catch (EntityNotFoundException e) {
            System.out.println("cannot delete Task with id = " + id);
            System.out.println(e.getMessage());
        }
    }

    public static void update(UUID id, String field, String newValue, Boolean isTitle, Boolean isDate){
        try {
            Task task = (Task) Database.get(id);
            if(isTitle){
                try{
                    String temp = task.getTitle();
                    task.setTitle(newValue);
                    Database.update(task);
                    System.out.println("Successfully updated task.");
                    System.out.println("Field: Title");
                    System.out.println("Old Value: " + temp);
                    System.out.println("New Value: " + newValue);
                    System.out.println("Modification Date: " + task.getLastModificationDate());
                } catch (InvalidEntityException e) {
                    System.out.println("cannot update task.");
                    System.out.println(e.getMessage());
                }

            } else if (isDate) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date temp = task.getDueDate();
                    task.setDueDate(formatter.parse(newValue));
                    try{
                        Database.update(task);
                        System.out.println("Successfully updated task.");
                        System.out.println("Field: Due Date");
                        System.out.println("Old Value: " + temp);
                        System.out.println("New Value: " + newValue);
                        System.out.println("Modification Date: " + task.getLastModificationDate());
                    } catch (InvalidEntityException e) {
                        System.out.println("cannot update task.");
                        System.out.println(e.getMessage());
                    }
                } catch (ParseException e) {
                    System.out.println("cannot update task.");
                    System.out.println("invalid date format (YYYY-MM-DD)");
                }

            }else{
                String temp = task.getDescription();
                task.setDescription(newValue);
                try {
                    Database.update(task);
                    System.out.println("Successfully updated task.");
                    System.out.println("Field: Description");
                    System.out.println("Old Value: " + temp);
                    System.out.println("New Value: " + newValue);
                    System.out.println("Modification Date: " + task.getLastModificationDate());
                } catch (InvalidEntityException e) {
                    System.out.println("cannot update entity.");
                    System.out.println(e.getMessage());
                }
            }

        } catch (EntityNotFoundException e) {
            System.out.println("cannot update task");
            System.out.println(e.getMessage());
        }
    }

    public static void updateStatus(UUID id, int option){
        try{
            Task task = (Task) Database.get(id);
            switch (option){
                case 1: {
                    String temp = String.valueOf(task.getStatus());
                    task.setStatus(Task.Status.Completed);
                    ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
                    for(Entity entity : steps){
                        if(((Step) entity).taskRef.equals(id)){
                            ((Step) entity).setStatus(Step.Status.Completed);
                            try {
                                Database.update(entity);
                            } catch (InvalidEntityException e) {
                                System.out.println("cannot update task.");
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    try {
                        Database.update(task);
                        System.out.println("Successfully updated task.");
                        System.out.println("Field: Status");
                        System.out.println("Old Value: " + temp);
                        System.out.println("New Value: Completed");
                        System.out.println("***all steps has been set to complete***");
                        System.out.println("Modification Date: " + task.getLastModificationDate());

                    } catch (InvalidEntityException e) {
                        System.out.println("cannot update task.");
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    String temp = String.valueOf(task.getStatus());
                    task.setStatus(Task.Status.InProgress);
                    try {
                        Database.update(task);
                        System.out.println("Successfully updated task.");
                        System.out.println("Field: Description");
                        System.out.println("Old Value: " + temp);
                        System.out.println("New Value: In progress");
                        System.out.println("Modification Date: " + task.getLastModificationDate());

                    } catch (InvalidEntityException e) {
                        System.out.println("cannot update task.");
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 3: {
                    String temp = String.valueOf(task.getStatus());
                    task.setStatus(Task.Status.NotStarted);
                    try {
                        Database.update(task);
                        System.out.println("Successfully updated task.");
                        System.out.println("Field: Description");
                        System.out.println("Old Value: " + temp);
                        System.out.println("New Value: not started");
                        System.out.println("Modification Date: " + task.getLastModificationDate());

                    } catch (InvalidEntityException e) {
                        System.out.println("cannot update task.");
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default: {
                    System.out.println("invalid option.");
                    break;
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("cannot update task.");
            System.out.println(e.getMessage());
        }
    }

    public static void checkTaskStatus(UUID id){
        try{
            ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
            ArrayList<Step> taskSteps = new ArrayList<>();
            int counter = 0;
            for(Entity entity : steps){
                if(((Step) entity).taskRef.equals(id)){
                    taskSteps.add((Step) entity);
                }
            }
            for (Step step : taskSteps){
                if(step.getStatus().equals(Step.Status.Completed)){
                    counter++;
                }
            }
            if(counter != 0){
                if(steps.size() == counter){
                    Task task = (Task) Database.get(id);
                    task.setStatus(Task.Status.Completed);
                    try{
                        Database.update(task);
                        System.out.println("###All steps of task complete.");
                        System.out.println("###task status changed to complete.");
                    } catch (InvalidEntityException e) {
                        System.out.println("cannot update Task (all steps complete)");
                        System.out.println(e.getMessage());
                    }
                }else{
                    Task task = (Task) Database.get(id);
                    if(task.getStatus().equals(Task.Status.Completed)){
                        task.setStatus(Task.Status.InProgress);
                        try{
                            Database.update(task);
                            System.out.println("###Not all Task steps complete.");
                            System.out.println("###Task status changed to In progress.");
                        } catch (InvalidEntityException e) {
                            System.out.println("cannot update task (missing complete step error)");
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
    public static void getTaskByID(UUID id){
        try{
            Task task = (Task) Database.get(id);
            ArrayList<Entity> stepList = Database.getAll(Step.STEP_ENTITY_CODE);
            ArrayList<Step> steps = new ArrayList<>();
            for(Entity entity: stepList){
                if(((Step) entity).taskRef.equals(id)){
                    steps.add((Step) entity);
                }
            }
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Due Date: " + task.getDueDate());
            System.out.println("ID: " + task.id);
            System.out.println("Status: " + task.getStatus());
            if(steps.isEmpty()){
                System.out.println("Steps: no steps for this task.");
            }else{
                for(Step step: steps){
                    System.out.print("\t+ " + step.getTitle() + "\n");
                    System.out.print("\t\tID: " + step.id + "\n");
                    System.out.print("\t\tStatus: " + step.getStatus() + "\n");
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("cannot get Task.");
            System.out.println(e.getMessage());
        }
    }
    public static void getTaskIncomplete(){
        ArrayList<Entity> Tasks = Database.getAll(Task.TASK_ENTITY_ID);
        int counter = 1;
        for(Entity entity: Tasks){
            if(((Task) entity).getStatus().equals(Task.Status.NotStarted) || ((Task) entity).getStatus().equals(Task.Status.InProgress)){
                System.out.println(counter + ".");
                TaskService.getTaskByID(((Task) entity).id);
                counter++;
            }
        }
    }

    public static void getTaskAll(){
        ArrayList<Entity> Tasks = Database.getAll(Task.TASK_ENTITY_ID);
        int counter = 1;
        for(Entity entity: Tasks){
            System.out.println(counter + ":");
            TaskService.getTaskByID(((Task) entity).id);
            counter++;
        }
    }
}
