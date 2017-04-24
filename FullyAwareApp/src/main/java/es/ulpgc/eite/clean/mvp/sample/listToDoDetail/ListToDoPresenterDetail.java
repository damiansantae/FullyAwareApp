package es.ulpgc.eite.clean.mvp.sample.listToDoDetail;


import android.util.Log;

import java.util.Observable;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.TaskToDo;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoPresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMasterTesting;

public class ListToDoPresenterDetail extends GenericPresenter
        <ListToDoDetail.PresenterToView, ListToDoDetail.PresenterToModel, ListToDoDetail.ModelToPresenter, ListToDoModelDetail>
        implements ListToDoDetail.ViewToPresenter, ListToDoDetail.ModelToPresenter, ListToDoDetail.MasterListToDetail, ListToDoDetail.DetailToMaster{




private boolean toolbarVisible;
    private ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter;
    private ListToDoPresenterMaster master;

    private boolean isChanged;
    private final Object MUTEX= new Object();
    private Observado observado;

    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(ListToDoDetail.PresenterToView view) {
        super.onCreate(ListToDoModelDetail.class, this);
        observado = new Observado();
        setView(view);

        // Debe llamarse al arrancar el detalle para fijar su estado inicial.
        // En este caso, este estado es fijado por el mediador en función de
        // los valores pasados desde el maestro
        Mediator app = (Mediator) getView().getApplication();
        app.startingDetailScreen(this);

    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(ListToDoDetail.PresenterToView view) {
        setView(view);

        // Verificamos si mostramos o no la barra de tareas cuando se produce un giro de pantalla
        // en función de la orientación actual de la pantalla
        if(configurationChangeOccurred()) {
            checkToolbarVisibility();
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

    @Override
    public void onButtonClicked() {

    }

    @Override
    public TaskToDo getTask() {
        return getModel().getTaskToDo();
    }

    @Override
    public void onDeleteActionClicked() {
       Navigator app = (Navigator) getView().getApplication();
        observado.notifyMaster();
        app.backToMasterScreen(this);

    }


    ///////////////////////////////////////////////////////////////////////////////////
    // To ListDoneDetail //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
        checkToolbarVisibility();
   /* if(isViewRunning()) {
      getView().setLabel(getModel().getLabel());
    }
    //checkToolbarVisibility();
    //checkTextVisibility();*/

    }

    @Override
    public void setAdapter(ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter) {
        this.adapter=adapter;
    }

    @Override
    public void setMaster(ListToDoPresenterMaster master) {
observado.addObserver(master);
    }


    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }

    @Override
    public void setItem(TaskToDo selectedItem) {
        getModel().setTaskToDo(selectedItem);

    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListDoneDetail To //////////////////////////////////////////////////////////////////////




    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }
    }

    @Override
    public TaskToDo getTaskToDelete() {
        return getModel().getTaskToDo();
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


public class Observado extends Observable{

    public void notifyMaster(){
        setChanged();
        notifyObservers(true);
    }

}

}
