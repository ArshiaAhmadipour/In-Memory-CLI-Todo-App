package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class TaskService {
    public static void setAsCompleted(UUID taskId) {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.Completed;
        try {
            Database.update(task);
        } catch (InvalidEntityException e) {
            throw new RuntimeException("task not found in Database to update.");
        }
    }

    public static void addTask(){
        Scanner inp = new Scanner(System.in);
        System.out.print("Title: ");
        String title = inp.nextLine();
        System.out.print("Description: ");
        String description = inp.nextLine();
        System.out.print("Date(yyyy-mm-dd format): ");
        String dateStr = inp.nextLine();
        Task task = new Task();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateStr);
            task.dueDate = date;
            task.title = title;
            task.status = Task.Status.NotStarted;
            task.description = description;
            try {
                Database.add(task);
                System.out.println("Task Saved Successfully.");
                System.out.println("id: " + task.id);
            } catch (InvalidEntityException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Invalid Date Format");

        }
    }
}
