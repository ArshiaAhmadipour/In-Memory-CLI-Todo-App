package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Step;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StepSerializer implements Serializer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        return step.getEntityCode() + "|" +
                step.getTitle() + "|" +
                step.taskRef + "|" +
                step.getStatus() + "|" +
                step.id + "|" +
                DATE_FORMAT.format(step.getCreationDate()) + "|" +
                DATE_FORMAT.format(step.getLastModificationDate());
    }

    @Override
    public Step deserialize(String s) {
        String[] parts = s.split("\\|");
        if (parts.length != 7) {
            throw new RuntimeException("invalid step format: " + s);
        }

        UUID id = UUID.fromString(parts[4].trim());
        String title = parts[1].trim();
        UUID taskRef = UUID.fromString(parts[2].trim());
        Step.Status status = Step.Status.valueOf(parts[3].trim());

        try {
            Date creationDate = DATE_FORMAT.parse(parts[5].trim());
            Date lastModificationDate = DATE_FORMAT.parse(parts[6].trim());

            Step step = new Step();
            step.id = id;
            step.setTitle(title);
            step.taskRef = taskRef;
            step.setStatus(status);
            step.setCreationDate(creationDate);
            step.setLastModificationDate(lastModificationDate);
            return step;
        } catch (Exception e) {
            throw new RuntimeException("error parsing step dates: " + s, e);
        }
    }
}
