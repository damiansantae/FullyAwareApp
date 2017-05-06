package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import java.util.ArrayList;
import java.util.HashMap;

import es.ulpgc.eite.clean.mvp.sample.app.Task;


public class TaskRepository {

    private static TaskRepository repository = new TaskRepository();
    private HashMap<String, Task> tasks = new HashMap<>();

    public static TaskRepository getInstance() {
        return repository;
    }

    private TaskRepository() {
        /*saveTask(new Task(R.drawable.bg_controll_plane,"Titulo1","Descripcion1","Fecha1"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo2","Descripcion2","Fecha2"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo3","Descripcion3","Fecha3"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo4","Descripcion4","Fecha4"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo5","Descripcion5","Fecha5"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo6","Descripcion6","Fecha6"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo7","Descripcion7","Fecha7"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo8","Descripcion8","Fecha8"));
        saveTask(new Task(R.drawable.bg_controll_plane,"Titulo9","Descripcion9","Fecha9"));*/

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