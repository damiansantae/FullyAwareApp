package es.ulpgc.eite.clean.mvp.sample;


import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmUnitTests {


    Realm realmDatabase;
    DatabaseFacade databaseFacade = new DatabaseFacade();

    @Before
    public void setUp() throws Exception {

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();

        realmDatabase = Realm.getInstance(config);

    }

    @Test
    public void databaseIsEmptyInitially() throws IOException {
        Assert.assertTrue(realmDatabase.isEmpty());
    }

    @Test
    public void checkIsEmptyAfterAddSubject() throws IOException {
        databaseFacade.addSubject("Diseño de Aplicaciones", Color.BLACK);
        Assert.assertFalse(realmDatabase.isEmpty());
        Assert.assertFalse(databaseFacade.getSubjectsFromDatabase().isEmpty());
    }

    @Test
    public void checkIsEmptyAfterAddTask() throws IOException {
        databaseFacade.addTask(null, "title", "escription", "24/03/2017", "ToDo");
        Assert.assertFalse(databaseFacade.getItemsFromDatabase().isEmpty());
        Assert.assertFalse(databaseFacade.getToDoItemsFromDatabase().isEmpty());
    }

    @Test
    public void deleteAllSubjects() throws IOException {
        databaseFacade.deleteAllDatabaseSubjects();
        Assert.assertTrue(databaseFacade.getSubjectsFromDatabase().isEmpty());
realmDatabase.close();
    }

}