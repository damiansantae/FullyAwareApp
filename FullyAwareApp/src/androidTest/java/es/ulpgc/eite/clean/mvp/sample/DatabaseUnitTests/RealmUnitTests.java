package es.ulpgc.eite.clean.mvp.sample.DatabaseUnitTests;


import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

/**
 * Unit Tests to test DatabaseFacade methods
 */
@RunWith(AndroidJUnit4.class)
public class RealmUnitTests extends TestCase {


    DatabaseFacade databaseFacade;

    @Before
    public void setUp() throws Exception {

        databaseFacade =DatabaseFacade.getInstance();
        databaseFacade.deleteAllDatabaseItems();

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
        databaseFacade.addTask(null,"title","description","24/03/2017","ToDo");
        assertFalse(databaseFacade.getTasksFromDatabase().size()==0);
    }


    /*******************************************************************
     ******** Action Delete Tests *************************************/

    /**
     * Test full subject deletion
     * @throws IOException
     */
    @Test
    public void deleteAllSubjects () throws IOException {
        databaseFacade.deleteAllDatabaseSubjects();
        assertTrue(databaseFacade.getSubjectsFromDatabase().isEmpty());
    }

    /**
     * Test full task deletion
     * @throws IOException
     */
    @Test
    public void deleteAllTask () throws IOException {
        databaseFacade.deleteAllDatabaseItems();
        List<Task> tasks = databaseFacade.getTasksFromDatabase();
       assertTrue(tasks.isEmpty());
    }

    /**
     * Test task deletion
     * @throws IOException
     */
    @Test
    public void deleteATask () throws IOException{

        databaseFacade.addTask(null, "titulo", "descripcion", "29/08/2017", "ToDo");
        Task task = databaseFacade.getToDoTasksFromDatabase().get(0);               //Extraemos el objeto Task que acabamos de añadir
                                                                                    //para eliminarlo
        databaseFacade.deleteDatabaseItem(task);
        List<Task> tasks = databaseFacade.getTasksFromDatabase();
        assertEquals(0,tasks.size());                                                //Como solo había una Task la lista de Task debe estar vacía
    }


    /*******************************************************************
     ******** Action Check Tests *************************************/

    /**
     * Test if field status is assigned in the right way
     * @throws IOException
     */
    @Test
    public void checkTaskStatus () throws IOException{

        databaseFacade.addTask(null, "titulo", "descripcion", "29/08/2017", "ToDo");            //Añadimos una tarea a tabla Task con status To-Do
        databaseFacade.addTask(null, "titulo2", "descripcion2", "29/08/2017", "Done");          //Añadimos una tarea a tabla Task con status Done

        List<Task> toDoTasks = databaseFacade.getToDoTasksFromDatabase();
        List<Task> DoneTasks = databaseFacade.getDoneTasksFromDatabase();
        List<Task> allTasks = databaseFacade.getTasksFromDatabase();

        assertEquals(1,toDoTasks.size());
        assertEquals(1,DoneTasks.size());
        assertEquals(2,allTasks.size());

    }

    @After                                  //Esta rutina se ejecuta depués de cada test unitario
    public void deleteTestBD() {
    databaseFacade.deleteAllDatabaseItems();

    }



}