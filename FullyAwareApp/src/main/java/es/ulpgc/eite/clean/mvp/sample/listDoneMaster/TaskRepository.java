package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import java.util.ArrayList;
import java.util.HashMap;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TaskDone;


public class TaskRepository {
    private static TaskRepository repository = new TaskRepository();
    private HashMap<String, TaskDone> tasks = new HashMap<>();

    public static TaskRepository getInstance() {
        return repository;
    }

    private TaskRepository() {
        saveTask(new TaskDone(R.drawable.bg_controll_plane,"Tarea hecha","Descripcion de tarea hecha","03/04/2017"));
    }

    public void saveTask(TaskDone TaskDone) {
        tasks.put(String.valueOf(TaskDone.getTaskId()), TaskDone);
    }

    public ArrayList<TaskDone> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(TaskDone TaskDone){
        tasks.remove(String.valueOf(TaskDone.getTaskId()));
    }

    public TaskDone taskDone(TaskDone TaskDone){
        return tasks.get(String.valueOf(TaskDone.getTaskId()));
    }
}