package es.ulpgc.eite.clean.mvp.sample;

import java.util.UUID;

import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import io.realm.RealmObject;

public class TimeTable extends RealmObject{


private String timeTableId;
    private Subject subject;
    private String day;
    private String hour;

    public TimeTable (String day, String hour, Subject subject){
        this.timeTableId = UUID.randomUUID().toString();
        this.day =day;
        this.subject=subject;
        this.hour =hour;
    }
    public TimeTable(){

    }


    public String getTimeTableId() {
        return timeTableId;
    }



    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }







}
