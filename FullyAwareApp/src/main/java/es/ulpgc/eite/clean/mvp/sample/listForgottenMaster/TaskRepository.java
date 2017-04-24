package es.ulpgc.eite.clean.mvp.sample.listForgottenMaster;

import java.util.ArrayList;
import java.util.HashMap;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public class TaskRepository {
    private static TaskRepository repository = new TaskRepository();
    private HashMap<String, Task> tasks = new HashMap<>();

    public static TaskRepository getInstance() {
        return repository;
    }

    private TaskRepository() {
        saveTask(new Task(R.drawable.bg_controll_plane,"Tarea olvidada","Descripcion de tarea olvidada","03/04/2017"));
    }

    public void saveTask(Task Task) {
        tasks.put(String.valueOf(Task.getTaskId()), Task);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(Task Task){
        tasks.remove(String.valueOf(Task.getTaskId()));
    }

    public Task Task(Task Task){
        return tasks.get(String.valueOf(Task.getTaskId()));
    }
}