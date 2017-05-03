package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Subject extends RealmObject {
    private String name;
    @PrimaryKey
    private String subjectId;
    private Integer color;




    public Subject (String name,  Integer color){
        this.name = name;
        this.subjectId = UUID.randomUUID().toString();
        this.color = color;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
