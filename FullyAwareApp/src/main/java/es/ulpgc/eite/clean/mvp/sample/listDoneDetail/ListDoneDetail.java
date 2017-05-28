package es.ulpgc.eite.clean.mvp.sample.listDoneDetail;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;

public interface ListDoneDetail {


    ///////////////////////////////////////////////////////////////////////////////////
    // State /////////////////////////////////////////////////////////////////////////

    /**
     * Interface that allows to start detail screen and get all the necessary values
     * to complete initial state which is going to share to detail screen when it starts
     */
    interface MasterListToDetail {

        /**
         * This method set toolbar visibility state saved in Mediator into
         * Presenter toolbar visibility state
         *
         * @param b: boolean visibility of toolbar
         */
        void setToolbarVisibility(boolean b);

        /**
         * Method that set into Model the Task storaged in Mediator which has been shared by Master
         *
         * @param selectedItem: the task which is going to show detail
         */
        void setItem(Task selectedItem);

        /**
         * This method is called when shared stated to Master-Mediator-Detail is completed
         */
        void onScreenStarted();

        /**
         * This method registers the master as a Observer. Is called by mediator
         *
         * @param master: the object master
         */
        void setMaster(ListDonePresenterMaster master);
    }

    /**
     * Interface that allows to fix all tha values included in the passing states form detail
     * screen when it finish
     */
    interface DetailToMaster {
        void destroyView();

        Task getTaskToDelete();
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Screen ////////////////////////////////////////////////////////////////////////

    /**
     * Methods offered to VIEW to communicate with PRESENTER
     */
    interface ViewToPresenter extends Presenter<PresenterToView> {
        /**
         * Build a communication bridge between View-Model
         * to get the current task which is shared from Master
         *
         * @return Task: the current task showed on detail
         */
        Task getTask();

        /**
         * This method is called when delete option in toolbar menu
         * has been clicked. It notify the observer of that.
         *
         * @see ListDonePresenterDetail.ObservableDone#notifyMaster()
         */
        void onDeleteActionClicked();
    }

    /**
     * Required VIEW methods available to PRESENTER
     */
    interface PresenterToView extends ContextView {

        /**
         * This method finish the View
         */
        void finishScreen();

        /**
         * This method turn visibility toolbar to invisible
         */
        void hideToolbar();


        void toolbarChanged(String colour);
    }

    /**
     * Methods offered to MODEL to communicate with PRESENTER
     */
    interface PresenterToModel extends Model<ModelToPresenter> {

        /**
         * Storage in Model current task
         *
         * @param selectedItem: current Task which is showed on detail
         */
        void setTask(Task selectedItem);

        /**
         * This method provides to the Presenter the storaged task
         *
         * @return Task: task which is storage in Model
         */
        Task getTask();
    }

    /**
     * Required PRESENTER methods available to MODEL
     */
    interface ModelToPresenter {

    }


}
