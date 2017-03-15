package es.ulpgc.eite.clean.mvp.sample.listToDo;

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
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo1","Descripcion1","Fecha1"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo2","Descripcion2","Fecha2"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo3","Descripcion3","Fecha3"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo4","Descripcion4","Fecha4"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo5","Descripcion5","Fecha5"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo6","Descripcion6","Fecha6"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo7","Descripcion7","Fecha7"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo8","Descripcion8","Fecha8"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Tilutlo9","Descripcion9","Fecha9"));

    }

    private void saveTask(Task task) {
        tasks.put(String.valueOf(task.getTaskId()), task);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(Task task){
        tasks.remove(String.valueOf(task.getTaskId()));

    }
}