package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Task;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TaskSerializer implements Serializer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    @Override
    public String serialize(Entity e) {
        Task task = (Task) e;
        return task.getEntityCode() + "|" +
                task.getTitle() + "|" +
                task.getDescription() + "|" +
                task.getStatus() + "|" +
                task.id + "|" +
                DATE_FORMAT.format(task.getDueDate()) + "|" +
                DATE_FORMAT.format(task.getCreationDate()) + "|" +
                DATE_FORMAT.format(task.getLastModificationDate());
    }

    @Override
    public Task deserialize(String s) {
        String[] parts = s.split("\\|");
        if (parts.length != 8) {
            throw new RuntimeException("invalid task format: " + s);
        }

        UUID id = UUID.fromString(parts[4].trim());
        String title = parts[1].trim();
        String description = parts[2].trim();
        Task.Status status = Task.Status.valueOf(parts[3].trim());

        try {
            Date dueDate = DATE_FORMAT.parse(parts[5].trim());
            Date creationDate = DATE_FORMAT.parse(parts[6].trim());
            Date lastModificationDate = DATE_FORMAT.parse(parts[7].trim());

            Task task = new Task();
            task.id = id;
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            task.setDueDate(dueDate);
            task.setCreationDate(creationDate);
            task.setLastModificationDate(lastModificationDate);
            return task;
        } catch (Exception e) {
            throw new RuntimeException("error parsing task dates: " + s, e);
        }
    }
}
