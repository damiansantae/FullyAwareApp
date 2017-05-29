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

    /**
     * Method to get the task's related subject
     * @return a Subject object
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Method to set the value of the subject related to a task
     * @param subject an Subject object
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Method to get the value of the date attribute
     * @return an String with the corresponding value
     */
    public String getDate() {
        return date;
    }

    /**
     * Method no set the value of the date attribute
     * @param date an String with the date to be applied
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Method to get the value of the title attribute
     * @return an String with the corresponding value
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set the value of the title attribute
     * @param title an String with the title to be applied
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to get the value of the description attribute
     * @return an String with the corresponding value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the value of the description attribute
     * @param description an String with the description to be applied
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get the value of the taskId attribute
     * @return an String with the corresponding value
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Method to set the value of the taskId attribute
     * @param taskId an String with the taskId to be applied
     */
    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    /**
     * Method to get the value of the status attribute
     * @return an String with the corresponding value
     */
    public String getStatus() {
        return status;
    }

    /**
     * Method to set the value of the date attribute
     * @param status an String with the status to be applied
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Method to compare to strings
     * @param obj an Object
     * @return a boolean indicating if they are equal or not
     */
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
