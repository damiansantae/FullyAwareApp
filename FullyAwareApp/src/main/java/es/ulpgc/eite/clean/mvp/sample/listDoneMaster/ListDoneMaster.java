package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import android.content.Context;
import android.view.View;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


/**
 * Interface of a task done list.
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public interface ListDoneMaster {


    ///////////////////////////////////////////////////////////////////////////////////
    // State /////////////////////////////////////////////////////////////////////////

    interface ToListDone {
        /**
         * Method which is called after the share of state variables from Mediator.
         * This method establish on the View the current state.
         *
         * @see es.ulpgc.eite.clean.mvp.sample.app.App#startingListDoneScreen(ToListDone)
         */
        void onScreenStarted();

        /**
         * Method that change state of toolbar visibility
         *
         * @param visible: true toolbar will be visible else it will be invisible
         */
        void setToolbarVisibility(boolean visible);


        /**
         * Method that change state of delete button visibility
         *
         * @param deleteBtnVisibility: true delete button will be visible else it will be invisible
         */
        void setDeleteBtnVisibility(boolean deleteBtnVisibility);

    }

    interface ListDoneTo {
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

    /**
     * Interfaz que permite fijar los valores incluidos en el estado pasado desde la pantalla
     * del detalle cuando está finaliza
     */
    interface DetailToMaster {
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Screen ////////////////////////////////////////////////////////////////////////

    /**
     * Methods offered to VIEW to communicate with PRESENTER
     */
    interface ViewToPresenter extends Presenter<PresenterToView> {

        /**
         * Method that that interprets a click on a specific item of the recyclerView
         * according to if there is a state of task selection or not, this current item
         * was already selected or not.
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
        void onBinBtnClick(ListDoneViewMaster.TaskRecyclerViewAdapter adapter);

        /**
         * This method build a bridge between View and Model in order to obtain
         * the cases of an specific String with a specific pattern
         *
         * @param task the specific object which is going to be subjected to the calculation
         * @return to the View a specific case(s) with a specific format
         */
        String getCases(Task task);
    }

    /**
     * Required VIEW methods available to PRESENTER
     */
    interface PresenterToView extends ContextView {
        void toolbarChanged(String colour);

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
         * Method that turns Delete Button to invisible
         */
        void hideDeleteBtn();

        /**
         * Method that turns Delete Button to visible
         */
        void showDeleteBtn();

        /**
         * This method load an specific list of Task into an adapter to inflate a
         * recycler view
         *
         * @param items: a list of task to load in custom RecyclerView Adapter
         */
        void setRecyclerAdapterContent(List<Task> items);

        /**
         * Method that shows a toast after deleting a task
         */
        void showToastDelete();
    }

    /**
     * Methods offered to MODEL to communicate with PRESENTER
     */
    interface PresenterToModel extends Model<ModelToPresenter> {

        /**
         * This method calculate the case(s) of a String. And turns them to a uppercase.
         * E.g: "Application Design" will return "AD"
         *
         * @param subjectName String which is going to be subject to the calculation
         * @return String with the case(s) of the parameter
         */
        String calculateCases(String subjectName);
    }

    /**
     * Required PRESENTER methods available to MODEL
     */
    interface ModelToPresenter {

    }
}
