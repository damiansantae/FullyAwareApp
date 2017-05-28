package es.ulpgc.eite.clean.mvp.sample.app;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TimeTable_NextUpgrade extends RealmObject{

@PrimaryKey
private String timeTableId;
    private Subject subject;
    private String day;
    private String hour;

    public TimeTable_NextUpgrade(String day, String hour, Subject subject){
        this.timeTableId = UUID.randomUUID().toString();
        this.day =day;
        this.subject=subject;
        this.hour =hour;
    }
    public TimeTable_NextUpgrade(){

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

    public String getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(String timeTableId) {
        this.timeTableId = timeTableId;
    }







}
