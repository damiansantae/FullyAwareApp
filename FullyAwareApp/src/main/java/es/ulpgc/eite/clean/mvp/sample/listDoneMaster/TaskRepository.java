package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

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
        saveTask(new Task(R.drawable.bg_controll_plane,"Tarea hecha","Descripcion de tarea hecha","03/04/2017"));
    }

    public void saveTask(Task task) {
        tasks.put(String.valueOf(task.getTaskId()), task);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(Task task){
        tasks.remove(String.valueOf(task.getTaskId()));
    }

    public Task taskDone(Task task){
        return tasks.get(String.valueOf(task.getTaskId()));
    }
}