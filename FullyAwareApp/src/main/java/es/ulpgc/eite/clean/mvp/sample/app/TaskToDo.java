package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;


public class TaskToDo extends RealmObject{


    private String taskId;
    private int tagId;
    private String date;
    private String title;
    private String description;

    public TaskToDo(int tagId, String title, String description, String date) {
        this.taskId= UUID.randomUUID().toString();
        this.tagId = tagId;
        this.date = date;
        this.title = title;
        this.description = description;
    }
    public TaskToDo(){

    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskToDo){
            TaskToDo taskToDo = (TaskToDo) obj;
            if(taskToDo.getTaskId() == getTaskId()){
                return true;
            }
        }
        return false;
    }
}
