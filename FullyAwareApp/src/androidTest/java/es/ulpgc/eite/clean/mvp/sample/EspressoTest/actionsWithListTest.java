package es.ulpgc.eite.clean.mvp.sample.EspressoTest;


import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.welcome.WelcomeActivity;
import es.ulpgc.eite.clean.mvp.sample.RealmDatabase.DatabaseFacade;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class actionsWithListTest {

    private final String taskTitle = "title1";
    private final String taskDescription = "description1";


    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);


    DatabaseFacade databaseFacade;

    @Before
    public void setUp() throws Exception {

        databaseFacade =DatabaseFacade.getInstance();
    }

    @Test
    public void checkFloatingBtnVisibilityAfterSelection() {

        deleteTasks();
        goToToDo();
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
 onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()))
       .perform(actionOnItemAtPosition(0, click()));

        //Comprobamos que el boton done y delete no existen y que por el contrario el add ha aparecido
        onView(withId(R.id.floatingDoneButton))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.floatingAddButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.floatingDeleteButton))
                .check(matches(not(isDisplayed())));


    }

    @Test
    public void checkBtnStateAfterRotate(){
        goToToDo();


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

    }

    @Test
    public void swipeLeftEqualToDelete(){
        goToToDo();

        onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()))
                .perform(actionOnItemAtPosition(0, swipeLeft()));

        onView(
                allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

       onView(
               allOf(withId(R.id.textWhenIsEmpty),withText("Add new tasks to do"))
       )
               .check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightEqualToDone(){
        goToToDo();


        onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()))
                .perform(actionOnItemAtPosition(0, swipeRight()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withContentDescription("spinner"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Done"), isDisplayed()));    //Select Done Option
        appCompatTextView.perform(click());

        onView(withId(R.id.item_list_recycler))
                .check(matches(atPosition(0, isDisplayed())));

goToToDoFromDone();
  addATask();
    }


    private void addATask() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatingAddButton),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()));
        floatingActionButton.perform(click());                                    //click on add floating btn

        ViewInteraction appCompatEditText = onView(
                withId(R.id.title));
        appCompatEditText.perform(scrollTo(), replaceText(taskTitle), closeSoftKeyboard());            //write a title

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.description));
        appCompatEditText2.perform(scrollTo(), replaceText(taskDescription), closeSoftKeyboard());             //write a description

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addTaskBtn), withText("Add Task")));
        appCompatButton2.perform(scrollTo(), click());                                                    //click on add task btn

    }





    private void rotateToLandscape() {        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void rotateToPortrait() {
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

private void goToToDo(){
    ViewInteraction appCompatButton = onView(
            allOf(withId(R.id.btn_skip), withText("SKIP"), isDisplayed()));
    appCompatButton.perform(click());

    pressBack();
    pressBack();

    ViewInteraction actionMenuItemView = onView(
            allOf(withContentDescription("spinner"), isDisplayed()));
    actionMenuItemView.perform(click());

    ViewInteraction appCompatTextView = onView(
            allOf(withId(R.id.title), withText("To Do"), isDisplayed()));
    appCompatTextView.perform(click());
}
private void goToToDoFromDone(){
    ViewInteraction actionMenuItemView = onView(
            allOf(withContentDescription("spinner"), isDisplayed()));
    actionMenuItemView.perform(click());

    ViewInteraction appCompatTextView = onView(
            allOf(withId(R.id.title), withText("To Do"), isDisplayed()));    //Select Done Option
    appCompatTextView.perform(click());
}
@After
public void deleteTasks(){
    databaseFacade.deleteAllDatabaseItems();
}
}

