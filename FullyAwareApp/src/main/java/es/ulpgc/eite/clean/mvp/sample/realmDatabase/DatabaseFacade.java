package es.ulpgc.eite.clean.mvp.sample.realmDatabase;

import android.os.Handler;
import android.util.Log;

import java.util.List;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import io.realm.Realm;

import static android.content.ContentValues.TAG;

/**
 * Created by IvanGlez on 24/04/2017.
 */

public class DatabaseFacade {

    private static final int ITEM_COUNT = 9;

    //public List<ModelItem> items = null; // An array of items
    private boolean runningTask;
    private Realm realmDatabase;
    private boolean validDatabase;
    private String errorMsg;
    private boolean usingWrapper;

    public DatabaseFacade(){
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

    public void addInitialTasks(){
        //Request realm instance

        Task Task1 = new Task("Titulo1","Descripcion1","Fecha1", "ToDo");
        Task Task2 = new Task("Titulo2","Descripcion2","Fecha2", "ToDo");
        Task Task3 = new Task("Titulo3","Descripcion3","Fecha3", "ToDo");
        Task Task4 = new Task("Titulo4","Descripcion4","Fecha4", "ToDo");
        Task Task5 = new Task("Titulo5","Descripcion5","Fecha5", "ToDo");
        Task Task6 = new Task("Titulo6","Descripcion6","Fecha6", "ToDo");
        Task Task7 = new Task("Titulo7","Descripcion7","Fecha7", "ToDo");
        Task Task8 = new Task("Titulo8","Descripcion8","Fecha8", "ToDo");
        Task Task9 = new Task("Titulo9","Descripcion9","Fecha9", "ToDo");
        Task Task10 = new Task("Titulo10","Descripcion10","Fecha10", "ToDo");



//Insert element
        realmDatabase.beginTransaction();

        realmDatabase.copyToRealm(Task1);
        realmDatabase.copyToRealm(Task2);
        realmDatabase.copyToRealm(Task3);
        realmDatabase.copyToRealm(Task4);
        realmDatabase.copyToRealm(Task5);
        realmDatabase.copyToRealm(Task6);
        realmDatabase.copyToRealm(Task7);
        realmDatabase.copyToRealm(Task8);
        realmDatabase.copyToRealm(Task9);
        realmDatabase.copyToRealm(Task10);

        realmDatabase.commitTransaction();
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


    public List<Task> getItemsFromDatabase(){
        if(usingWrapper) {
            return getItemsFromDatabaseWrapper();
        }

        return realmDatabase.where(Task.class).findAll();
    }

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

    public void addTask(Task Task) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(Task);
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

    public void addSubject(Subject subject) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(subject);
        realmDatabase.commitTransaction();
    }

    public void deleteDatabaseItem(Subject item) {
        final String id = item.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).equalTo("subjectId", id)
                        .findAll()
                        .deleteAllFromRealm();
                ;
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
                ;
            }
        });
    }

    public void setSubjectTimeTable(Subject subject, final String timeTable) {
        final String id = subject.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subject realmSubject = realm.where(Subject.class).equalTo("subjectId", id)
                        .findFirst();
                realmSubject.setTimeTable(timeTable);
                ;
            }
        });
    }
}
