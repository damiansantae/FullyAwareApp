package es.ulpgc.eite.clean.mvp.sample.DatabaseUnitTests;


import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@RunWith(AndroidJUnit4.class)
public class RealmUnitTests extends TestCase {



    Realm realmDatabase;
    DatabaseFacade databaseFacade = new DatabaseFacade();

    @Before
    public void setUp() throws Exception {

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
       realmDatabase = Realm.getInstance(config);

    }

    @Test
    public void databaseIsEmptyInitially() throws IOException {
        assertTrue(realmDatabase.isEmpty());
    }

    /*******************************************************************
     ******** Action Add Tests *************************************/

    @Test
    public void checkIsEmptyAfterAddSubject() throws IOException {
        databaseFacade.addSubject("Diseño de Aplicaciones", Color.BLACK);
        List<Subject> subjects = databaseFacade.getSubjectsFromDatabase();
        assertFalse(subjects.size()==0);
    }

    @Test
    public void checkIsEmptyAfterAddTask() throws IOException {
        databaseFacade.addTask(null,"title","escription","24/03/2017","ToDo");
        assertFalse(databaseFacade.getItemsFromDatabase().size()==0);
    }


    /*******************************************************************
     ******** Action Delete Tests *************************************/
    @Test
    public void deleteAllSubjects () throws IOException {
        databaseFacade.deleteAllDatabaseSubjects();
        assertTrue(databaseFacade.getSubjectsFromDatabase().isEmpty());
    }

    @Test
    public void deleteAllTask () throws IOException {
        databaseFacade.deleteAllDatabaseItems();
        List<Task> tasks = databaseFacade.getItemsFromDatabase();
       assertTrue(tasks.isEmpty());
    }

    @Test
    public void deleteATask () throws IOException{
        databaseFacade.addTask(null, "titulo", "descripcion", "29/08/2017", "ToDo");
        Task task = databaseFacade.getToDoItemsFromDatabase().get(0);               //Extraemos el objeto Task que acabamos de añadir
                                                                                    //para eliminarlo
        databaseFacade.deleteDatabaseItem(task);
        List<Task> tasks = databaseFacade.getItemsFromDatabase();
        assertFalse(tasks.size()==0);                                                //Como solo había una Task la lista de Task debe estar vacía
    }



}