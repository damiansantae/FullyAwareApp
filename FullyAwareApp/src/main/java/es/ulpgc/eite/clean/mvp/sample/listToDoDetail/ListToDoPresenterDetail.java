package es.ulpgc.eite.clean.mvp.sample.listToDoDetail;


import android.util.Log;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMasterTesting;

public class ListToDoPresenterDetail extends GenericPresenter
        <ListToDoDetail.PresenterToView, ListToDoDetail.PresenterToModel, ListToDoDetail.ModelToPresenter, ListToDoModelDetail>
        implements ListToDoDetail.ViewToPresenter, ListToDoDetail.ModelToPresenter, ListToDoDetail.MasterListToDetail, ListToDoDetail.DetailToMaster, ListToDoDetail.Observable {




private boolean toolbarVisible;
    private ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter;
    private ListToDoMaster.Observer observer;
    private boolean isChanged;
    private final Object MUTEX= new Object();
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
    public Task getTask() {
        return getModel().getTask();
    }

    @Override
    public void onDeleteActionClicked() {
       /* Navigator app = (Navigator) getView().getApplication();
        app.backToMasterScreen(this);*/
       this.isChanged=true;
       notifyObservers();
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
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }

    @Override
    public void setItem(Task selectedItem) {
        getModel().setTask(selectedItem);

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




    @Override
    public void register(ListToDoMaster.Observer obj) {
        if(obj == null) throw new NullPointerException("Null Observer");
        synchronized (MUTEX) {
            if(observer==null) observer = obj;
        }


    }

    @Override
    public void unregister() {
        synchronized (MUTEX) {
           observer=null;
        }


    }

    @Override
    public void notifyObservers() {
   ListToDoMaster.Observer localObserver;

        synchronized (MUTEX) {
            if (!isChanged)
                return;
            localObserver = observer;
            this.isChanged=false;
        }
    localObserver.update();
        }

    @Override
    public Object getUpdate(ListToDoMaster.Observer obj) {
        return getTaskToDelete();
    }




}
