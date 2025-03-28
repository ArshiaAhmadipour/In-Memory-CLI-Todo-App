package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;

import java.util.UUID;

public class StepService {
    public static void saveStep(UUID taskRef, String title) {
        Step step = new Step();
        step.taskRef = taskRef;
        step.status = Step.Status.NotStarted;
        step.title = title;
        try {
            Database.add(step);
        } catch (InvalidEntityException e) {
            throw new RuntimeException("Step is Invalid.");
        }
    }
}
