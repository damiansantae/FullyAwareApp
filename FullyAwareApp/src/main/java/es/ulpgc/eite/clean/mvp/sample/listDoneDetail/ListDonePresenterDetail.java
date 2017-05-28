package es.ulpgc.eite.clean.mvp.sample.listDoneDetail;


import android.util.Log;

import java.util.Observable;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;

public class ListDonePresenterDetail extends GenericPresenter
        <ListDoneDetail.PresenterToView, ListDoneDetail.PresenterToModel, ListDoneDetail.ModelToPresenter, ListDoneModelDetail>
        implements ListDoneDetail.ViewToPresenter, ListDoneDetail.ModelToPresenter, ListDoneDetail.MasterListToDetail, ListDoneDetail.DetailToMaster {




private boolean toolbarVisible;
    private ObservableDone observableDone;

    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(ListDoneDetail.PresenterToView view) {
        super.onCreate(ListDoneModelDetail.class, this);
        observableDone = new ObservableDone();
        setView(view);

        //it must call Mediator to establish state which was shared by the master
        Mediator app = (Mediator) getView().getApplication();
        app.startingDetailScreen(this);
        checkToolbarColourChanges(app);
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(ListDoneDetail.PresenterToView view) {
        setView(view);

        if(configurationChangeOccurred()) {
            checkToolbarVisibility();
        }
        Mediator app = (Mediator) getView().getApplication();
        checkToolbarColourChanges(app);

    }



    /**
     * Helper method to inform Presenter that a onBackPressed event occurred
     * Called by {@link GenericActivity}
     */
    @Override
    public void onBackPressed() {
        Log.d(TAG, "calling onBackPressed()");
    }

    /**
     * Hook method called when the VIEW is being destroyed or
     * having its configuration changed.
     * Responsible to maintain MVP synchronized with Activity lifecycle.
     * Called by onDestroy methods of the VIEW layer, like: {@link GenericActivity#onDestroy()}
     *
     * @param isChangingConfiguration true: configuration changing & false: being destroyed
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        super.onDestroy(isChangingConfiguration);
        Log.d(TAG, "calling onDestroy()");
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // View To Presenter /////////////////////////////////////////////////////////////



    @Override
    public Task getTask() {
        return getModel().getTask();
    }

    @Override
    public void onDeleteActionClicked() {
        Navigator app = (Navigator) getView().getApplication();
        observableDone.notifyMaster();
        app.backToMasterScreen(this);

    }



    ///////////////////////////////////////////////////////////////////////////////////
    // To ListDoneDetail //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");

    if(isViewRunning()) {
        checkToolbarVisibility();
    }

    }

    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }

    @Override
    public void setItem(Task selectedItem) {
        getModel().setTask(selectedItem);

    }

    @Override
    public void setMaster(ListDonePresenterMaster master) {
        observableDone.addObserver(master);

    }

    ///////////////////////////////////////////////////////////////////////////////////
    // LisDoneDetail To //////////////////////////////////////////////////////////////////////


    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }
    }

    @Override
    public Task getTaskToDelete() {
        return getModel().getTask();
    }





    ///////////////////////////////////////////////////////////////////////////////////

    private void checkToolbarVisibility() {
        Log.d(TAG, "calling checkToolbarVisibility()");
        if (isViewRunning()) {
            if (!toolbarVisible) {
                getView().hideToolbar();
            }
        }
    }


    private void checkToolbarColourChanges(Mediator app){
        if (app.checkToolbarChanged() == true){
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
    }

    private class ObservableDone extends Observable {

        private void notifyMaster(){
            setChanged();
            notifyObservers(true);
        }

    }



}
