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

    /**
     * Method to get the name of the Subject
     * @return the String of the subject's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Method to set the value of the subject's name
     * @param name String of the name to be applied
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method to get the subject's id
     * @return String of the subject's id
     */
    public String getSubjectId(){
        return this.subjectId;
    }

    /**
     * Method to set the value of the subject's id
     * @param subjectId String of the id to be applied
     */
    public void setSubjectId(String subjectId){
        this.subjectId = subjectId;
    }

    /**
     * Method to get the subject's color
     * @return Integer corresponding with the subject's color
     */
    public Integer getColor() {
        return color;
    }

    /**
     * Method to set the value of the subject's color
     * @param color Integer of the corresponding color to be applied
     */
    public void setColor(Integer color) {
        this.color = color;
    }
}
