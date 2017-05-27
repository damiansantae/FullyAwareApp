package es.ulpgc.eite.clean.mvp.sample.intro;


import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class addNewTask {
    private DatabaseFacade databaseFacade = new DatabaseFacade();


    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);


    @Before
    public void cleanDB(){
        databaseFacade.deleteAllDatabaseItems();
    }

    @Test
    public void checkFloatingBtnVisibilityAfterSelection() {

        goToToDoActivity();
        addATask();


        //Seleccionamos el elemento de la lista
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, longClick()));

//Comprobamos que el boton done y delete existen y que por el contrario el add ha desaparecido
        onView(withId(R.id.floatingDoneButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.floatingAddButton))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.floatingDeleteButton))
                .check(matches(isDisplayed()));

        //Quitamos estado de selección
        ViewInteraction deselectRecyclerView = onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        //Comprobamos que el boton done y delete no existen y que por el contrario el add ha aparecido
        onView(withId(R.id.floatingDoneButton))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.floatingAddButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.floatingDeleteButton))
                .check(matches(not(isDisplayed())));

    }


    private void addATask() {
        //Vamos a añadir una nueva tarea
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatingAddButton),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()));
        floatingActionButton.perform(click());                                    //click en el boton de añadir

        ViewInteraction appCompatEditText = onView(
                withId(R.id.title));
        appCompatEditText.perform(scrollTo(), replaceText("titulo1"), closeSoftKeyboard());            //escribimos un titulo

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.description));
        appCompatEditText2.perform(scrollTo(), replaceText("titulo2"), closeSoftKeyboard());             //escribimos una descripcion

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addTaskBtn), withText("Add Task")));
        appCompatButton2.perform(scrollTo(), click());                                                    //Click en el boton add Task

    }

    @Test
    public void checkBtnStateAfterRotate(){
        goToToDoActivity();

        //Seleccionamos el elemento de la lista
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, longClick()));


       rotateToLandscape();

        //Comprobamos que tras girar los botones siguen en el estado que deberían
        onView(withId(R.id.floatingDoneButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.floatingAddButton))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.floatingDeleteButton))
                .check(matches(isDisplayed()));
        rotateToPortrait();
    }



    private void goToToDoActivity(){
        //Pantalla de Welcome
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_skip), withText("SKIP"), isDisplayed()));
        appCompatButton.perform(click());                                       //click al boton skip

        pressBack();                                                            //salimos de añadir nombre
        pressBack();

        ViewInteraction actionMenuItemView = onView(
                allOf(withContentDescription("spinner"), isDisplayed()));
        actionMenuItemView.perform(click());                                     //Click en el navegador de la toolbar

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("To Do"), isDisplayed()));
        appCompatTextView.perform(click());                                      //Click en To-Do

    }

    private void rotateToLandscape() {        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void rotateToPortrait() {
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}

