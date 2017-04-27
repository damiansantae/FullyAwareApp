package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;


public class Subject extends RealmObject {
    private String name;
    private String subjectId;



    public Subject (String name, String timeTable){
        this.name = name;
        this.subjectId = UUID.randomUUID().toString();
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


}
