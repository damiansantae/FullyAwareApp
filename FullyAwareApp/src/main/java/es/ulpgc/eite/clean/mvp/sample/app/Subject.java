package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;

/**
 * Created by IvanGlez on 25/04/2017.
 */

public class Subject extends RealmObject {
    private String name;
    private String subjectId;
    private String timeTable;

    public Subject (String name, String timeTable){
        this.name = name;
        this.subjectId = UUID.randomUUID().toString();
        this.timeTable = timeTable;
    }

    public Subject (){

    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSubjectId(){
        return this.subjectId;
    }

    public void setSubjectId(String subjectId){
        this.subjectId = subjectId;
    }

    public String getTimeTable(){
        return this.timeTable;
    }

    public void setTimeTable(String timeTable){
        this.timeTable = timeTable;
    }
}
