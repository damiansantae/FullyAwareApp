package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Task extends RealmObject{

    @PrimaryKey
    private String taskId;
    private Subject subject;
    private String date;
    private String title;
    private String description;
    private String status;

    public Task(Subject subject, String title, String description, String date, String status) {
        this.taskId= UUID.randomUUID().toString();
        this.subject = subject;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description, String date, String status){
        this.taskId= UUID.randomUUID().toString();
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(){

    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status){
        this.status = status;
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
