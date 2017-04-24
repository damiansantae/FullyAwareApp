package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;


public class Task extends RealmObject{


    private String taskId;
    private int subjectId;
    private String date;
    private String title;
    private String description;

    public Task(int tagId, String title, String description, String date) {
        this.taskId= UUID.randomUUID().toString();
        this.subjectId = tagId;
        this.date = date;
        this.title = title;
        this.description = description;
    }
    public Task(){

    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int tagId) {
        this.subjectId = tagId;
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
        if (obj instanceof Task){
            Task Task = (Task) obj;
            if(Task.getTaskId() == getTaskId()){
                return true;
            }
        }
        return false;
    }
}
