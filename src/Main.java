import db.Database;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;


public class Main {
    public static void main(String[] args) {
        Database.registerValidator(Task.TASK_ENTITY_ID, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());
        TaskService.addTask();
        StepService.addStep();
        TaskService.delete();
    }
}