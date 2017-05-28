package es.ulpgc.eite.clean.mvp.sample.schedule_NextUpgrade;


import android.content.Context;
import android.util.Log;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;

public class SchedulePresenter extends GenericPresenter
        <Schedule.PresenterToView, Schedule.PresenterToModel, Schedule.ModelToPresenter, ScheduleModel>
        implements Schedule.ViewToPresenter, Schedule.ModelToPresenter, Schedule.ScheduleTo, Schedule.ToSchedule{


    private boolean toolbarVisible;





    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(Schedule.PresenterToView view) {
        super.onCreate(ScheduleModel.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");

        Log.d(TAG, "calling startingSchedule()");
        Mediator app = (Mediator) getView().getApplication();
        app.startingScheduleScreen(this);

        app.startingScheduleScreen(this);
        if (app.checkToolbarChanged() == true){
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(Schedule.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");
        if (configurationChangeOccurred()) {
           checkToolbarVisibility();

        }
        Mediator app = (Mediator) getView().getApplication();
        if (app.checkToolbarChanged() == true){
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
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





    ///////////////////////////////////////////////////////////////////////////////////

    private void checkToolbarVisibility() {
        Log.d(TAG, "calling checkToolbarVisibility()");
        if (isViewRunning()) {
            if (!toolbarVisible) {
                getView().hideToolbar();
            }
        }
    }


    @Override
    public void onScreenStarted() {
        if(isViewRunning()) {
          //Colocar labels en la tabla y botones
        }
        checkToolbarVisibility();

    }

    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;

    }

    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }

    }

    @Override
    public boolean isToolbarVisible() {
        return toolbarVisible;
    }


}
