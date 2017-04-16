package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import java.util.ArrayList;
import java.util.HashMap;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TaskToDo;


public class TaskRepository {
    private static TaskRepository repository = new TaskRepository();
    private HashMap<String, TaskToDo> tasks = new HashMap<>();

    public static TaskRepository getInstance() {
        return repository;
    }

    private TaskRepository() {
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo1","Descripcion1","Fecha1"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo2","Descripcion2","Fecha2"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo3","Descripcion3","Fecha3"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo4","Descripcion4","Fecha4"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo5","Descripcion5","Fecha5"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo6","Descripcion6","Fecha6"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo7","Descripcion7","Fecha7"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo8","Descripcion8","Fecha8"));
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Titulo9","Descripcion9","Fecha9"));

    }

    public void saveTask(TaskToDo taskToDo) {
        tasks.put(String.valueOf(taskToDo.getTaskId()), taskToDo);
    }

    public ArrayList<TaskToDo> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(TaskToDo taskToDo){
        tasks.remove(String.valueOf(taskToDo.getTaskId()));
    }

    public TaskToDo taskDone(TaskToDo taskToDo){
        return tasks.get(String.valueOf(taskToDo.getTaskId()));
    }




}