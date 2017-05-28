package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.content.Context;
import android.view.View;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.TaskRecyclerViewAdapter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;

/**
 * Interface of a task to do list.
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public interface ListToDoMaster {


    ///////////////////////////////////////////////////////////////////////////////////
    // State /////////////////////////////////////////////////////////////////////////

    /**
     * Interface which provides state passing to listToDo MVP
     */
    interface ToListToDo {

        /**
         * Method which is called after the share of state variables from Mediator.
         * This method establish on the View the current state.
         *
         * @see es.ulpgc.eite.clean.mvp.sample.app.App#startingListToDoScreen(ListToDoMaster.ToListToDo)
         */
        void onScreenStarted();

        /**
         * Method that change state of toolbar visibility
         *
         * @param visible: true toolbar will be visible else it will be invisible
         */
        void setToolbarVisibility(boolean visible);

        /**
         * Method that change state of add button visibility
         *
         * @param addBtnVisibility: true add button will be visible else it will be invisible
         */
        void setAddBtnVisibility(boolean addBtnVisibility);

        /**
         * Method that change state of delete button visibility
         *
         * @param deleteBtnVisibility: true delete button will be visible else it will be invisible
         */
        void setDeleteBtnVisibility(boolean deleteBtnVisibility);

        /**
         * Method that change state of done button visibility
         *
         * @param doneBtnVisibility: true done button will be visible else it will be invisible
         */
        void setDoneBtnVisibility(boolean doneBtnVisibility);
    }


    interface ListToDoTo {
        /**
         * Method that provides current context
         *
         * @return Context: the current activity context
         */
        Context getManagedContext();

        /**
         * Method that notify the associated View to finish himself if it exists
         */
        void destroyView();

    }

    /**
     * Interface which allows to start detail screen and recover all data needed to
     * start the initial state that it will be passed to the detail screen after started
     */
    interface MasterListToDetail {
        /**
         * Method that provides current context
         *
         * @return Context: the current activity context
         */
        Context getManagedContext();

        /**
         * Method that provides the object Task which has been clicked
         *
         * @return Task: the task which is going to show its detail
         */
        Task getSelectedTask();

        /**
         * @return boolean: true if toolbar presenter state is visible else false
         */
        boolean getToolbarVisibility();

    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Screen ////////////////////////////////////////////////////////////////////////

    /**
     * Methods offered to VIEW to communicate with PRESENTER
     */
    interface ViewToPresenter extends Presenter<PresenterToView> {

        /**
         * Method that look for selected tasks and after it change them state
         * to done
         *
         * @param adapter the recyclerView adapter
         */
        void onDoneBtnClick(TaskRecyclerViewAdapter adapter);

        /**
         * Method that that interprets a click on a specific item of the recyclerView
         * according to if there is a state of task selection or not, this current item
         * was alreadyselected or not.
         *
         * @param item      the view of the row clicked on the recyclerView
         * @param position: position of the task which has been clicked
         * @param task:     Task which has been clicked
         */
        void onListClick(View item, int position, Task task);

        /**
         * Method that interpret a long click on a specific item of the recyclerView
         *
         * @param item             the view of the row clicked on the recyclerView
         * @param adapterPosition: position of the task which has been clicked
         */
        void onLongListClick(View item, int adapterPosition);

        /**
         * This method is called when the floating btn add is clicked.
         * It communicate with Navigator to go to Add Task Activity
         */
        void onAddBtnClick();

        /**
         * This method check if a task is selected searching it by his recyclerView
         * position in a table
         *
         * @param adapterPosition the item position in the recyclerView we are asking for
         * @return true if a task is selected else false
         */
        boolean isSelected(int adapterPosition);

        /**
         * This method delete one or more specific tasks
         *
         * @param adapter the recyclerView adapter
         */
        void onBinBtnClick(TaskRecyclerViewAdapter adapter);

        /**
         * This method build a bridge between View and Model in order to obtain
         * the cases of an specific String with a specific pattern
         *
         * @param task the specific object which is going to be subjected to the calculation
         * @return to the View a specific case(s) with a specific format
         */
        String getCases(Task task);

        /**
         * Method that build a communication bridge between View-Model
         * to order the items in the recycler view according to its Subject
         *
         * @see ListToDoModelMaster#orderSubjects()
         */
        void subjectFilter();

        /**
         * Method that deletes the task where swipe is occurred. It communicates with
         * database facade in order to do that.
         *
         * @param currentTask: Task where swipe is occurred
         * @see es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade
         */
        void swipeLeft(Task currentTask);

        /**
         * Method that change current task status "to-do" to "done" . It communicates with
         * database facade in order to do that
         *
         * @param currentTask: Task where swipe is occurred
         * @see es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade
         */
        void swipeRight(Task currentTask);

        /**
         * Method that build a communication bridge between View-Model
         * to know if a deadline of a tasks is elder thant current one
         *
         * @param deadline: String that show deadline of a specific task
         * @return boolean: true if current date is greater than deadline else false
         */
        boolean isTaskForgotten(String deadline);

        /**
         * This method notify Model that back button has been pressed
         */
        void onBtnBackPressed();
    }

    /**
     * Required VIEW methods available to PRESENTER
     */
    interface PresenterToView extends ContextView {
        /**
         * This method is called generally by Presenter in order to finish this
         * Activity (View)
         */
        void finishScreen();

        /**
         * Method that turns toolbar visibility to GONE
         */
        void hideToolbar();

        /**
         * Method that turns Add Button to visible
         */
        void showAddBtn();

        /**
         * Method that turns Delete Button to invisible
         */
        void hideDeleteBtn();

        /**
         * Method that turns Delete Button to visible
         */
        void showDeleteBtn();

        /**
         * Method that turns Add Button to invisible
         */
        void hideAddBtn();

        /**
         * Method that turns Done Button to invisible
         */
        void hideDoneBtn();

        /**
         * Method that turns Done Button to visible
         */
        void showDoneBtn();

        /**
         * Method that turns inform text list empty to invisible
         */
        void hideTextWhenIsEmpty();

        /**
         * Method that turns inform text list empty to visible
         */
        void showTextWhenIsEmpty();

        /**
         * This method load an specific list of Task into an adapter to inflate a
         * recycler view
         *
         * @param items: a list of task to load in custom RecyclerView Adapter
         */
        void setRecyclerAdapterContent(List<Task> items);

        /**
         * Method that change toolbar colour depending of the user share preferences
         *
         * @param colour: color to change
         */
        void toolbarChanged(String colour);

        /**
         * Method that shows a toast after deleting a task
         */
        void showToastDelete();

        /**
         * Method which notice View that button back has been pressed twice in a row
         *
         * @see ListToDoModelMaster#backPressed()
         */
        void confirmBackPressed();

        /**
         * Init all actions for swipe on a recyclerView row (it configures listeners, draw, etc)
         */
        void initSwipe();

        /**
         * This method notice View to show a Toast in order to notice him
         * to press back again to close the application
         *
         * @param toastBackConfirmation: the text showed on the toast
         */
        void showToastBackConfirmation(String toastBackConfirmation);
    }

    /**
     * Methods offered to MODEL to communicate with PRESENTER
     */
    interface PresenterToModel extends Model<ModelToPresenter> {

        /**
         * Method that gives the String label of the toast back confirmation
         *
         * @return String: message for user asking to confirm the task deleting
         */
        String getToastBackConfirmation();

        /**
         * This method start a handler delay if when back button
         * is pressed for the first time. If back button is pressed
         * again before finish the delay it will notify presenter of that
         */
        void backPressed();

        /**
         * This method calculate the case(s) of a String. And turns them to a uppercase.
         * E.g: "Application Design" will return "AD"
         *
         * @param subjectName String which is going to be subject to the calculation
         * @return String with the case(s) of the parameter
         */
        String calculateCases(String subjectName);

        /**
         * This method communicates with database to order
         * tasks to-do according to their subject.
         *
         * @return list of Task ordered
         */
        List<Task> orderSubjects();

        /**
         * Method calculate if a specific date is elder than current one
         *
         * @param date: date which is going to be compare with actual
         * @return boolean: true if specific date is elder else false
         */
        boolean compareDateWithCurrent(String date);
    }

    /**
     * Required PRESENTER methods available to MODEL
     */
    interface ModelToPresenter {


        /**
         * Method used to notify View that
         * user has confirmed the back action
         */
        void confirmBackPressed();

        /**
         * This method notify View to show a toast notifying user
         * that he must press again back button if he want to close
         * the application
         * @see es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster#showToastBackConfirmation(String)
         */
        void delayedTaskToBackStarted();
    }


}
