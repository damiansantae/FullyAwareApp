package es.ulpgc.eite.clean.mvp.sample.realmDatabase;

import android.util.Log;

import java.util.List;
import java.util.UUID;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.TimeTable;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import io.realm.Realm;

import static android.content.ContentValues.TAG;


public class DatabaseFacade {

    private static final int ITEM_COUNT = 9;

    //public List<ModelItem> items = null; // An array of items
    private boolean runningTask;
    private Realm realmDatabase;
    private boolean validDatabase;
    private String errorMsg;
    private boolean usingWrapper;

    private static DatabaseFacade databaseFacade = new DatabaseFacade();


    public static DatabaseFacade getInstance() {
        return databaseFacade;
    }

    private DatabaseFacade(){
        realmDatabase = Realm.getDefaultInstance();
        runningTask = false;
        validDatabase = true;

    }




    /**
     * Llamado para recuperar los elementos a mostrar en la lista.
     * Si el contenido ya ha sido fijado antes, se notificará inmediatamente al presentador y,
     * sino es el caso, la notificación se realizará al finalizar la tarea que fija este contenido
     */
    /*public void loadItems() {
        if (!validDatabase && !runningTask) {
            startDelayedTask();
        } else {
            if (!runningTask) {
                Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
                getPresenter().onLoadItemsSubjectsFinished(getItemsFromDatabase());
            } else {
                Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
                getPresenter().onLoadItemsSubjectStarted();
            }
        }

    }*/




    /*public void deleteItem(Task item) {
        if (getItemsFromDatabase().contains(item)){
            //items.remove(item);
            deleteDatabaseItem(item);
        } else {
            getPresenter().onErrorDeletingItem(item);
        }
    }*/

    /**
     * Llamado para recuperar los elementos iniciales de la lista.
     * En este caso siempre se llamará a la tarea asíncrona
     */

    /*public void reloadItems() {
        //items = null;
        deleteAllDatabaseItems();
        validDatabase = false;
        loadItems();
    }*/

    public void setDatabaseValidity(boolean valid) {
        validDatabase = valid;
    }

    public String getErrorMessage() {
        return errorMsg;
    }


    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position).append("\n");
        for (int count = 0; count < position; count++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    /**
     * Llamado para recuperar los elementos a mostrar en la lista.
     * Consiste en una tarea asíncrona que retrasa un tiempo la obtención del contenido.
     * El modelo notificará al presentador cuando se inicia y cuando finaliza esta tarea.
     */
    /*private void startDelayedTask() {
        Log.d(TAG, "calling startDelayedTask() method");
        runningTask = true;
        Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
        getPresenter().onLoadItemsSubjectStarted();

        // Mock Hello: A handler to delay the answer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setItems();
                runningTask = false;
                validDatabase = true;
                Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
                //getPresenter().onLoadItemsSubjectsFinished(items);
                getPresenter().onLoadItemsSubjectsFinished(getItemsFromDatabase());
            }
        }, 0);
    }*/




    /////////////////////////////////////////////////////////////////////////////////////

    /*********************************************************************
     ********Métodos de prueba para comprobar funcionamiento de database */


    public void createTestingScenario() {
        //Request realm instance

        /*Subject diseño = new Subject("Diseño de Aplicaciones", R.color.bg_screen1);
        Subject aplicaciones = new Subject("Aplicaciones de Red", R.color.bg_screen2);
        Subject organizacion = new Subject("Organización de Computadores", R.color.bg_screen3);
        Subject ingles = new Subject("Inglés", R.color.bg_screen4);
        Subject sistemas = new Subject("Administración de Sistemas", R.color.bg_screen5);

        TimeTable firstTimeMon = new TimeTable("Monday","08:00-10:00",aplicaciones);
        TimeTable secondTimeMon = new TimeTable("Monday","10:00-12:00",sistemas);
        TimeTable thirdTimeMon = new TimeTable("Monday","12:00-14:00",sistemas);

        TimeTable firstTimeT = new TimeTable("Tuesday","08:00-10:00",ingles);
        TimeTable secondTimeT = new TimeTable("Tuesday","10:00-12:00",diseño);
        TimeTable thirdTimeT = new TimeTable("Tuesday","12:00-14:00",diseño);

        TimeTable firstTimeW = new TimeTable("Wednesday","08:00-10:00",sistemas);
        TimeTable secondTimeW = new TimeTable("Wednesday","10:00-12:00",organizacion);
        TimeTable thirdTimeW = new TimeTable("Wednesday","12:00-14:00",organizacion);

        TimeTable firstTimeTh = new TimeTable("Thursday","08:00-10:00",diseño);
        TimeTable secondTimeTh = new TimeTable("Thursday","10:00-12:00",aplicaciones);
        TimeTable thirdTimeTh = new TimeTable("Thursday","12:00-14:00",aplicaciones);

        TimeTable firstTimeF = new TimeTable("Friday","08:00-10:00",organizacion);
        TimeTable secondTimeF = new TimeTable("Friday","10:00-12:00",ingles);
        TimeTable thirdTimeF = new TimeTable("Friday","12:00-14:00",ingles);



        Task Task1 = new Task(aplicaciones, "Titulo1","Descripcion1","Fecha1", "ToDo");
        Task Task2 = new Task(aplicaciones, "Titulo2","Descripcion2","Fecha2", "ToDo");
        Task Task3 = new Task(sistemas, "Titulo4","Descripcion4","Fecha4", "ToDo");
        Task Task4 = new Task(ingles,"Titulo5","Descripcion5","Fecha5", "ToDo");
        Task Task5 = new Task(diseño, "Titulo6","Descripcion6","Fecha6", "ToDo");
        Task Task6 = new Task(organizacion, "Titulo7","Descripcion7","Fecha7", "ToDo");



        Task Task11 = new Task(diseño, "Titulo11","Descripcion11","Fecha11", "Done");
        Task Task12 = new Task(organizacion,"Titulo12","Descripcion12","Fecha12", "Done");
        Task Task13 = new Task(aplicaciones,"Titulo13","Descripcion13","Fecha13", "Done");

        Task Task14 = new Task(ingles,"Titulo14","Descripcion14","Fecha14", "Forgotten");
        Task Task15 = new Task(sistemas,"Titulo15","Descripcion15","Fecha15", "Forgotten");
        Task Task16 = new Task(organizacion,"Titulo16","Descripcion16","Fecha16", "Forgotten");*/



//Insert element

        realmDatabase.beginTransaction();

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

        realmDatabase.commitTransaction();

        /*realmDatabase.beginTransaction();

        realmDatabase.copyToRealm(firstTimeMon);
        realmDatabase.copyToRealm(firstTimeT);
        realmDatabase.copyToRealm(firstTimeW);
        realmDatabase.copyToRealm(firstTimeTh);
        realmDatabase.copyToRealm(firstTimeF);

        realmDatabase.copyToRealm(secondTimeMon);
        realmDatabase.copyToRealm(secondTimeT);
        realmDatabase.copyToRealm(secondTimeW);
        realmDatabase.copyToRealm(secondTimeTh);
        realmDatabase.copyToRealm(secondTimeF);

        realmDatabase.copyToRealm(thirdTimeMon);
        realmDatabase.copyToRealm(thirdTimeT);
        realmDatabase.copyToRealm(thirdTimeW);
        realmDatabase.copyToRealm(thirdTimeTh);
        realmDatabase.copyToRealm(thirdTimeF);


        realmDatabase.copyToRealm(Task1);
        realmDatabase.copyToRealm(Task2);
        realmDatabase.copyToRealm(Task3);
        realmDatabase.copyToRealm(Task4);
        realmDatabase.copyToRealm(Task5);
        realmDatabase.copyToRealm(Task6);

        realmDatabase.copyToRealm(Task11);
        realmDatabase.copyToRealm(Task12);
        realmDatabase.copyToRealm(Task13);
        realmDatabase.copyToRealm(Task14);
        realmDatabase.copyToRealm(Task15);
        realmDatabase.copyToRealm(Task16);


        realmDatabase.commitTransaction();*/

    }






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
        for(Task item: getItemsFromDatabase()){
            deleteDatabaseItem(item);
        }
    }



    /***********************************************************
     ******** Methods used to work with Task table in database*/

    /**
     * Method that deletes an specific item from
     * the database
     *
     * @param item Task class
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
    public List<Task> getItemsFromDatabase(){
        if(usingWrapper) {
            return getItemsFromDatabaseWrapper();
        }

        return realmDatabase.where(Task.class).findAll();
    }

    /**
     * Method that look over database items which
     * belong to Task table
     * @return a list with that elements
     */
    public List<Task> getToDoItemsFromDatabase(){
        if(usingWrapper) {
            return getItemsFromDatabaseWrapper();
        }

        return realmDatabase.where(Task.class).equalTo("status", "ToDo").findAll();
    }

    public List<Task> getDoneItemsFromDatabase(){
        if(usingWrapper) {
            return getItemsFromDatabaseWrapper();
        }

        return realmDatabase.where(Task.class).equalTo("status", "Done").findAll();
    }

    public List<Task> getForgottenItemsFromDatabase(){
        if(usingWrapper) {
            return getItemsFromDatabaseWrapper();
        }


        return realmDatabase.where(Task.class).equalTo("status", "Forgotten").findAll();
    }



    private List<Task> getItemsFromDatabaseWrapper(){
        Log.d(TAG, "calling getItemsFromDatabaseWrapper() method");
        List<Task> dbItems = realmDatabase.where(Task.class).findAll();

        Log.d(TAG, "items=" +  dbItems);
        return dbItems;
    }

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


    public boolean getRunningTask() {
        return runningTask;
    }

    public boolean getValidDatabase() {
        return validDatabase;
    }

    public void setRunningTask(boolean runningTask) {
        this.runningTask = runningTask;
    }

    public void setValidDatabase(boolean validDatabase) {
        this.validDatabase = validDatabase;
    }

    public void setItemStatus(Task task, final String done) {
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


    public void addSubject(String name, Integer color) {
        realmDatabase.beginTransaction();
        Subject subject = realmDatabase.createObject(Subject.class, UUID.randomUUID().toString());
        subject.setName(name);
        subject.setColor(color);
        realmDatabase.commitTransaction();
    }

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

    private List<Subject> getSubjectsFromDatabaseWrapper(){
        Log.d(TAG, "calling getItemsFromDatabaseWrapper() method");
        List<Subject> dbItems = realmDatabase.where(Subject.class).findAll();

        Log.d(TAG, "items=" +  dbItems);
        return dbItems;
    }

    public List<Subject> getSubjectsFromDatabase(){
        if(usingWrapper) {
            return getSubjectsFromDatabaseWrapper();
        }

        return realmDatabase.where(Subject.class).findAll();
    }

    public void deleteAllDatabaseSubjects(){
        for(Subject item: getSubjectsFromDatabase()){
            deleteDatabaseItem(item);
        }
    }

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

    public void setSubjectColor(Subject subject, final String color) {
        final String id = subject.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subject realmSubject = realm.where(Subject.class).equalTo("subjectId", id)
                        .findFirst();
                realmSubject.setName(color);

            }
        });
    }



    /*****************************************************************
     ******** Methods used to work with TimeTable table in database*/


    /**
     * Method that adds an specific Schedule into
     * the database
     *
     * @param
     */
    public void addTimeTable(String day, String hour, Subject subject) {
        realmDatabase.beginTransaction();
        TimeTable timeTable = realmDatabase.createObject(TimeTable.class, UUID.randomUUID().toString());
        timeTable.setDay(day);
        timeTable.setHour(hour);
        timeTable.setSubject(subject);
        realmDatabase.commitTransaction();
    }


    /**
     * Method that deletes an specific schedule from
     * the database
     *
     * @param timeTable TimeTable class
     */
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

    /**
     * This method search in database for TimeTable objects
     * and return a list with all of them
     *
     * @return dbItems, a List with TimeTable objects
     */
    private List<TimeTable> getTimeTablesFromDatabaseWrapper(){
        Log.d(TAG, "calling getTimeTablesFromDatabaseWrapper() method");
        List<TimeTable> dbItems = realmDatabase.where(TimeTable.class).findAll();

        Log.d(TAG, "items=" +  dbItems);
        return dbItems;
    }


    public List<TimeTable> getTimeTablesFromDatabase(){
        if(usingWrapper) {
            return getTimeTablesFromDatabaseWrapper();
        }

        return realmDatabase.where(TimeTable.class).findAll();
    }






    /**
     * Method that deletes all items which
     * belong to TimeTable table
     */
    public void deleteAllDatabaseTimeTables(){
        for(TimeTable item: getTimeTablesFromDatabase()){
            deleteDatabaseTimeTable(item);
        }
    }


    /**
     * Inserts a day into the column day of an specific
     * TimeTable item
     */
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


    /**
     * Inserts an iterval hours into the column hour of an specific
     * TimeTable item
     */
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

    /**
     * Inserts an specific Subject of the table Subject creating
     * a foreign key into the column subject of a specific TimeTable item
     */
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


    public Subject getSubject(String subjectName) {
       Subject subject = realmDatabase.where(Subject.class).equalTo("name", subjectName).findFirst();
        return subject;
    }
}
