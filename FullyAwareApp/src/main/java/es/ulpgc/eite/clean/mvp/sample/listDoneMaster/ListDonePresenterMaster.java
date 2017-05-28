package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;


import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDonePresenterDetail;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

/**
 * Presenter of a task done  list.
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListDonePresenterMaster extends GenericPresenter
        <ListDoneMaster.PresenterToView, ListDoneMaster.PresenterToModel, ListDoneMaster.ModelToPresenter, ListDoneModelMaster>
        implements ListDoneMaster.ViewToPresenter, ListDoneMaster.ModelToPresenter, ListDoneMaster.ListDoneTo, ListDoneMaster.ToListDone, ListDoneMaster.MasterListToDetail, ListDoneMaster.DetailToMaster, Observer {


    private boolean toolbarVisible;
    private boolean deleteBtnVisible;
    private boolean textVisible;
    private boolean listClicked;
    private boolean selectedState;
    private Task selectedTask;
    private SparseBooleanArray itemsSelected =new SparseBooleanArray();
    private DatabaseFacade database;




    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(ListDoneMaster.PresenterToView view) {
        super.onCreate(ListDoneModelMaster.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");


        Log.d(TAG, "calling startingLisToDoScreen()");
        Mediator app = (Mediator) getView().getApplication();
        database =DatabaseFacade.getInstance();

        app.startingListDoneScreen(this);
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
    public void onResume(ListDoneMaster.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");

        if (configurationChangeOccurred()) {    //if screen rotation
            checkToolbarVisibility();
            checkDeleteBtnVisibility();
        }

        Mediator app = (Mediator) getView().getApplication();
        checkToolbarColourChanges(app);
        loadItems();
    }



//TODO: JORDI COMENTA ESTO
    private void checkToolbarColourChanges(Mediator app){
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


    @Override
    public void onListClick(View v, int adapterPosition, Task task) {
        if(selectedState){
            if(!v.isSelected()){
                v.setSelected(true);
                itemsSelected.put(adapterPosition,true);

            }else{
                v.setSelected(false);
                itemsSelected.put(adapterPosition,false);

            }
        }else{
            Navigator app = (Navigator) getView().getApplication();
            selectedTask=task;
            app.goToDetailScreen(this);
        }
        checkSelection();
      checkDeleteBtnVisibility();

    }
    /**
     * Method that order view to show or hide view items as buttons
     * according of the selected state
     */
    private void checkSelection() {
        boolean somethingSelected = false;
        for (int i = 0; i < itemsSelected.size(); i++) {
            int key = itemsSelected.keyAt(i);
            // get the object by the key.
            Object obj = itemsSelected.get(key);
            if (obj.equals(true)) {
                somethingSelected = true;
                break;
            }
        }

        if (somethingSelected) {

            setDeleteBtnVisibility(true);
        } else {

            setDeleteBtnVisibility(false);
            selectedState = false;
        }


    }

    @Override
    public void onLongListClick(View v, int adapterPosition) {
        if(!selectedState){
            selectedState =true;
            v.setSelected(true);
            itemsSelected.put(adapterPosition,true);
        }
        checkDeleteBtnVisibility();




    }
    @Override
    public boolean isSelected(int adapterPosition) {
        boolean result = false;
        if(itemsSelected.size()!=0) {

            if (itemsSelected.get(adapterPosition)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void onBinBtnClick(ListDoneViewMaster.TaskRecyclerViewAdapter adapter) {

        ArrayList<Task> selected = getSelectedTasks(adapter);
        for(int i=0;i<selected.size();i++){
            database.deleteDatabaseItem(selected.get(i));

        }
        itemsSelected.clear();
        checkSelection();
        checkDeleteBtnVisibility();

    }

    @Override
    public String getCases(Task task) {
        String subjectName= task.getSubject().getName();
        return  getModel().calculateCases(subjectName);


    }

    /**
     * This method looks for the tasks in the selected table which has
     * his field on true (selected)
     *
     * @param adapter the recyclerView adapter
     * @return a list of Tasks which are selected
     */
    private ArrayList<Task> getSelectedTasks(ListDoneViewMaster.TaskRecyclerViewAdapter adapter) {
        ArrayList<Task> selected = new ArrayList<>();
        for(int i=0;i<adapter.getItemCount();i++){
            if(itemsSelected.get(i)){
                selected.add(adapter.getItems().get(i));
            }
        }
        return selected;
    }





    ///////////////////////////////////////////////////////////////////////////////////
    // To ListDoneTo //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
   if(isViewRunning()) {

    }
        checkDeleteBtnVisibility();
        loadItems();
    }


    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }


    @Override
    public void setDeleteBtnVisibility(boolean deleteBtnVisibility) {
        deleteBtnVisible=deleteBtnVisibility;

    }



    ///////////////////////////////////////////////////////////////////////////////////
    // ListDone To //////////////////////////////////////////////////////////////////////


    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    @Override
    public boolean getToolbarVisibility() {
        return toolbarVisible;
    }

    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }
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
    


    private void checkDeleteBtnVisibility() {
        Log.d(TAG, "calling checkDeleteBtnVisibility()");
        if (isViewRunning()) {
            if (!deleteBtnVisible) {
                getView().hideDeleteBtn();
            } else {
                getView().showDeleteBtn();
            }
        }
    }


    public void loadItems() {
       getView().setRecyclerAdapterContent(database.getDoneItemsFromDatabase());
        }


    /**
     * When detail make a notifyObserver() this method is accessed.
     * It delete the task which showed its detail before
     * @param o: Observable which did notifyObserver()
     * @param arg: boolean if true then delete task
     * @see ListDonePresenterDetail.ObservableDone#notifyObservers()
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg.equals(true)){
            database.deleteDatabaseItem(selectedTask);

            getView().showToastDelete();
        }



    }
}
