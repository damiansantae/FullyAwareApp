package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

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
        saveTask(new TaskToDo(R.drawable.bg_controll_plane,"Tarea hecha","Descripcion de tarea hecha","03/04/2017"));
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