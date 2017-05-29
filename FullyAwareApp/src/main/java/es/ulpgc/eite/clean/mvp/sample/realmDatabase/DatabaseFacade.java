package es.ulpgc.eite.clean.mvp.sample.realmDatabase;

import java.util.List;
import java.util.UUID;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TimeTable;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import io.realm.Realm;


public class DatabaseFacade {

    private Realm realmDatabase;

    private static DatabaseFacade databaseFacade = new DatabaseFacade();

    public static DatabaseFacade getInstance() {
        return databaseFacade;
    }

    private DatabaseFacade(){
        realmDatabase = Realm.getDefaultInstance();

    }
    

    /////////////////////////////////////////////////////////////////////////////////////

    /*********************************************************************
     ********Methods to prove the database funcionality */


    public void createTestingScenario() {

        //  The transaction begins
        realmDatabase.beginTransaction();

        //  The objects are created
        Subject diseño = realmDatabase.createObject(Subject.class, UUID.randomUUID().toString());
        diseño.setName("Diseño de Aplicaciones");
        diseño.setColor(R.color.color_lightblue);

        Subject aplicaciones = realmDatabase.createObject(Subject.class, UUID.randomUUID().toString());
        aplicaciones.setName("Aplicaciones de Red");
        aplicaciones.setColor(R.color.color_violet);

        TimeTable firstTimeMon = realmDatabase.createObject(TimeTable.class, UUID.randomUUID().toString());
        firstTimeMon.setDay("Monday");
        firstTimeMon.setHour("8:00-10:00");
        firstTimeMon.setSubject(diseño);

        TimeTable secondTimeMon = realmDatabase.createObject(TimeTable.class, UUID.randomUUID().toString());
        secondTimeMon.setDay("Monday");
        secondTimeMon.setHour("10:00-12:00");
        secondTimeMon.setSubject(aplicaciones);

        Task task1 = realmDatabase.createObject(Task.class, UUID.randomUUID().toString());
        task1.setTitle("Practica 1");
        task1.setDescription("Terminar apartado 1");
        task1.setDate("25/03/2017");
        task1.setStatus("ToDo");
        task1.setSubject(diseño);

        Task task2 = realmDatabase.createObject(Task.class, UUID.randomUUID().toString());
        task2.setTitle("Practica 2");
        task2.setDescription("Terminar apartado 2");
        task2.setDate("24/03/2017");
        task2.setStatus("ToDo");
        task2.setSubject(aplicaciones);

        //  The objects are inserted in the database when the transaction is closed
        realmDatabase.commitTransaction();


    }

    /**
     * Method to delete the items created with the test method
     */
    public void deleteTestItems() {
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).beginsWith("title", "Titulo")
                        .findAll()
                        .deleteAllFromRealm();
                ;
            }
        });
    }

    public void deleteAllDatabaseItems(){
        for(Task item: getTasksFromDatabase()){
            deleteDatabaseItem(item);
        }
    }



    /***********************************************************
     ******** Methods used to work with Task table in database*/

    /**
     * Method that deletes an specific item from
     * the database
     *
     * @param item Task object
     */
    public void deleteDatabaseItem(Task item) {
        final String id = item.getTaskId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).equalTo("taskId", id)
                        .findAll()
                        .deleteAllFromRealm();
                ;
            }
        });

    }

    /**
     * Method that look over database items which
     * belong to Task table
     * @return a list with that elements
     */
    public List<Task> getTasksFromDatabase(){
        return realmDatabase.where(Task.class).findAll();
    }

    /**
     * Method that look over database items which
     * belong to Task table and its field "status" with the value "ToDo"
     * @return a list with that elements
     */
    public List<Task> getToDoTasksFromDatabase(){
        return realmDatabase.where(Task.class).equalTo("status", "ToDo").findAll();
    }

    /**
     * Method that look over database items which
     * belong to Task table and its field "status" with the value "Done"
     * @return a list with that elements
     */
    public List<Task> getDoneTasksFromDatabase(){
        return realmDatabase.where(Task.class).equalTo("status", "Done").findAll();
    }

    /**
     * Method that look over database items which
     * belong to Task table and its field "status" with the value "Forgotten"
     * @return a list with that elements
     */


    /**
     * Method to add a Task object to the database
     * @param subject Subject of the task
     * @param title title of the task
     * @param description description of the task
     * @param date date of the task
     * @param status status of the task
     *               It can be:
     *                  -ToDo
     *                  -Done
     */
    public void addTask(Subject subject, String title, String description, String date, String status) {
        realmDatabase.beginTransaction();
        Task task = realmDatabase.createObject(Task.class, UUID.randomUUID().toString());
        task.setSubject(subject);
        task.setTitle(title);
        task.setDescription(description);
        task.setDate(date);
        task.setStatus(status);
        realmDatabase.commitTransaction();
    }

    public void setTaskStatus(Task task, final String done) {
        final String id = task.getTaskId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Task realmTask = realm.where(Task.class).equalTo("taskId", id)
                        .findFirst();
                        realmTask.setStatus(done);
                ;
            }
        });
    }


    /*************************************************************
     ******** Methods used to work with Subject table in database*/


    /**
     * Method to add a Subject object to the database
     * @param name Subject's name
     * @param color Color related to the subject, to be used in the element list of the view
     */
    public void addSubject(String name, Integer color) {
        realmDatabase.beginTransaction();
        Subject subject = realmDatabase.createObject(Subject.class, UUID.randomUUID().toString());
        subject.setName(name);
        subject.setColor(color);
        realmDatabase.commitTransaction();
    }


    /**
     * Method to get an specific Subject from database
     * @param subjectName name of the Subject to be deleted
     * @return the corresponding Subject
     */
    public Subject getSubject(String subjectName) {
        Subject subject = realmDatabase.where(Subject.class).equalTo("name", subjectName).findFirst();
        return subject;
    }

    /**
     * Method to delete a Subject object from the database
     * @param item Subject to be deleted
     */
    public void deleteDatabaseItem(Subject item) {
        final String id = item.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Subject.class).equalTo("subjectId", id)
                        .findAll()
                        .deleteAllFromRealm();

            }
        });
    }

    /**
     * Method to get all the Subject objects from the database
     * @return
     */
    public List<Subject> getSubjectsFromDatabase(){
        return realmDatabase.where(Subject.class).findAll();
    }

    /**
     * Method to delete all Subject objects from the database
     */
    public void deleteAllDatabaseSubjects(){
        for(Subject item: getSubjectsFromDatabase()){
            deleteDatabaseItem(item);
        }
    }

    /**
     * Method to change the name of a concrete Subject in database
     * @param subject Subject to be edited
     * @param name New name to be given
     */
    public void setSubjectName(Subject subject, final String name){
        final String id = subject.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subject realmSubject = realm.where(Subject.class).equalTo("subjectId", id)
                        .findFirst();
                realmSubject.setName(name);

            }
        });
    }

    /**
     * Method to change the color of a concrete Subject in database
     * @param subject Subject to be edited
     * @param color New color to be given
     */
    public void setSubjectColor(Subject subject, final int color) {
        final String id = subject.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subject realmSubject = realm.where(Subject.class).equalTo("subjectId", id)
                        .findFirst();
                realmSubject.setColor(color);

            }
        });
    }



    /*****************************************************************
     ******** Methods used to work with TimeTable table in database (Next Upgrade)*/


   /* *//**
     * Method that adds an specific Schedule into
     * the database
     *
     * @param
     *//*
    public void addTimeTable(String day, String hour, Subject subject) {
        realmDatabase.beginTransaction();
        TimeTable timeTable = realmDatabase.createObject(TimeTable.class, UUID.randomUUID().toString());
        timeTable.setDay(day);
        timeTable.setHour(hour);
        timeTable.setSubject(subject);
        realmDatabase.commitTransaction();
    }


    *//**
     * Method that deletes an specific schedule from
     * the database
     *
     * @param timeTable TimeTable class
     *//*
    public void deleteDatabaseTimeTable(TimeTable timeTable) {
        final String id = timeTable.getTimeTableId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).equalTo("timeTableId", id)
                        .findAll()
                        .deleteAllFromRealm();

            }
        });
    }

    public List<TimeTable> getTimeTablesFromDatabase(){
        return realmDatabase.where(TimeTable.class).findAll();
    }






    *//**
     * Method that deletes all items which
     * belong to TimeTable table
     *//*
    public void deleteAllDatabaseTimeTables(){
        for(TimeTable item: getTimeTablesFromDatabase()){
            deleteDatabaseTimeTable(item);
        }
    }


    *//**
     * Inserts a day into the column day of an specific
     * TimeTable item
     *//*
    public void setDay(TimeTable timeTable, final String day){
        final String id = timeTable.getTimeTableId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TimeTable realmTimeTable = realm.where(TimeTable.class).equalTo("timeTableId", id)
                        .findFirst();
                realmTimeTable.setDay(day);

            }
        });
    }


    *//**
     * Inserts an iterval hours into the column hour of an specific
     * TimeTable item
     *//*
    public void setHour(TimeTable timeTable, final String hour){
        final String id = timeTable.getTimeTableId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TimeTable realmTimeTable = realm.where(TimeTable.class).equalTo("timeTableId", id)
                        .findFirst();
                realmTimeTable.setHour(hour);

            }
        });
    }

    *//**
     * Inserts an specific Subject of the table Subject creating
     * a foreign key into the column subject of a specific TimeTable item
     *//*
    public void setSubject(TimeTable timeTable, final Subject subject){
        final String id = timeTable.getTimeTableId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TimeTable realmTimeTable = realm.where(TimeTable.class).equalTo("timeTableId", id)
                        .findFirst();
                realmTimeTable.setSubject(subject);

            }
        });
    }
*/


}
