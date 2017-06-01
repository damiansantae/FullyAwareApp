package es.ulpgc.eite.clean.mvp.sample.EspressoTest;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.welcome.WelcomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationFromToDoTests {

    private final String taskTitle = "title1";
    private final String taskDescription = "description1";


    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

boolean isAlreadyInstalled;
    @Test
    public void goToDetail() {
        //TODO: Aqui tambien
        if(!isAlreadyInstalled){
            goToToDo();
        }


        addATask();             //add a new task

        //Click on the new added task
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.item_list_recycler),
                        withParent(withId(R.id.content_listToDo)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.subject_from_detail))
                .check(matches(withText(taskTitle)));           //See if it is the correct title

        onView(withId(R.id.task_description))
                .check(matches(withText(taskDescription))); //See if it is the correct description

    }

    @Test
    public void goToDone() {


        ViewInteraction actionMenuItemView = onView(
                allOf(withContentDescription("spinner"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Done"), isDisplayed()));    //Select Done Option
        appCompatTextView.perform(click());


        //Check we are in done activity
        onView(withId(R.id.done_activity))
                .check(matches(isDisplayed()));

        onView(
                allOf(withContentDescription("spinner"), isDisplayed()))
                .perform(click());                                               //Click in navigation menu

        //See that To Do option is visible in navigation menu
        onView(
                allOf(withId(R.id.title),withText("To Do")))
                .check(matches(isDisplayed()));

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

         onView(
                allOf(withId(android.R.id.button2), withText("NO")))                              //clck NO (add into calendar)
        .perform(scrollTo(), click());

    }

    private void goToToDo(){
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_skip), withText("SKIP"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_subject_name), isDisplayed()));
        appCompatEditText.perform(replaceText("Damian"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.bt_add_user), withText("Add"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.bt_finish), withText("Finish")));
        appCompatButton3.perform(scrollTo(), click());


    }


}

