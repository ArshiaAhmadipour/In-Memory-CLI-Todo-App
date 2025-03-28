package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Task;

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
}
