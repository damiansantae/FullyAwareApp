package es.ulpgc.eite.clean.mvp.sample.listForgottenMaster;

import java.util.ArrayList;
import java.util.HashMap;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TaskForgotten;


public class TaskRepository {
    private static TaskRepository repository = new TaskRepository();
    private HashMap<String, TaskForgotten> tasks = new HashMap<>();

    public static TaskRepository getInstance() {
        return repository;
    }

    private TaskRepository() {
        saveTask(new TaskForgotten(R.drawable.bg_controll_plane,"Tarea olvidada","Descripcion de tarea olvidada","03/04/2017"));
    }

    public void saveTask(TaskForgotten TaskForgotten) {
        tasks.put(String.valueOf(TaskForgotten.getTaskId()), TaskForgotten);
    }

    public ArrayList<TaskForgotten> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(TaskForgotten TaskForgotten){
        tasks.remove(String.valueOf(TaskForgotten.getTaskId()));
    }

    public TaskForgotten TaskForgotten(TaskForgotten TaskForgotten){
        return tasks.get(String.valueOf(TaskForgotten.getTaskId()));
    }
}