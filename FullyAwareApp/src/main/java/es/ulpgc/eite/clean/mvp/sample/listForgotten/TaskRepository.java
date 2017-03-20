package es.ulpgc.eite.clean.mvp.sample.listForgotten;

import java.util.ArrayList;
import java.util.HashMap;

import es.ulpgc.eite.clean.mvp.sample.R;

/**
 * Created by Damian on 26/02/2017.
 */

public class TaskRepository {
    private static TaskRepository repository = new TaskRepository();
    private HashMap<String, Task> tasks = new HashMap<>();

    public static TaskRepository getInstance() {
        return repository;
    }

    private TaskRepository() {
        saveTask(new Task(R.drawable.bg_controll_plane,"Tareahecha1","Descripcion1","Fecha1"));


    }

    private void saveTask(Task task) {
        tasks.put(String.valueOf(task.getTaskId()), task);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(Task task) {
        tasks.remove(String.valueOf(task.getTaskId()));
    }
}