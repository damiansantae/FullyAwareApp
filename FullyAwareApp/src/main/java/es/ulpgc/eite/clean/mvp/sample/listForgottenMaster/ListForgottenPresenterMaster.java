package es.ulpgc.eite.clean.mvp.sample.listForgottenMaster;


import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

public class ListForgottenPresenterMaster extends GenericPresenter
        <ListForgottenMaster.PresenterToView, ListForgottenMaster.PresenterToModel, ListForgottenMaster.ModelToPresenter, ListForgottenModelMaster>
        implements ListForgottenMaster.ViewToPresenter, ListForgottenMaster.ModelToPresenter, ListForgottenMaster.ListForgottenTo, ListForgottenMaster.ToListForgotten, ListForgottenMaster.MasterListToDetail, ListForgottenMaster.DetailToMaster,Observer{


    private boolean toolbarVisible;

    private boolean deleteBtnVisible;

    private boolean textVisible;
    private boolean listClicked;
    private Task task;

    private Task selectedTask;
    private boolean selectedState;
    private ArrayList<Task> tasksSelected = new ArrayList<>();
    private ArrayList<String> posSelected = new ArrayList<>();
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
    public void onCreate(ListForgottenMaster.PresenterToView view) {
        super.onCreate(ListForgottenModelMaster.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");

        Log.d(TAG, "calling startingLisToDoScreen()");
        Mediator app = (Mediator) getView().getApplication();
        database = new DatabaseFacade();
        app.startingListForgottenScreen(this);
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
    public void onResume(ListForgottenMaster.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");

        if (configurationChangeOccurred()) {
            //getView().setLabel(getModel().getLabel());


           // checkToolbarVisibility();
            //checkTextVisibility();


            checkDeleteBtnVisibility();


            if(listClicked) {
                getView().startSelection();

                onCheckItems();
            }

//            if (buttonClicked) {
//                getView().setText(getModel().getText());
//            }
        }

        Mediator app = (Mediator) getView().getApplication();
        if (app.checkToolbarChanged() == true){
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
        loadItems();
    }



    /**
     * Selecciona los elementos de la lista que estaban seleccionados
     */
    private void onCheckItems() {
        for(int i=0; i<posSelected.size();i++){
            setItemChecked(Integer.parseInt(posSelected.get(i)), true);
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

    /*@Override
    public void onButtonClicked() {
        Log.d(TAG, "calling onButtonClicked()");
        if (isViewRunning()) {
            getModel().onChangeMsgByBtnClicked();
            getView().setText(getModel().getText());
            textVisible = true;
            buttonClicked = true;
        }
        checkTextVisibility();
    }*/





    @Override
    public void onBinBtnClick(Task_Adapter adapter) {
        int size = posSelected.size();
        if (size != 0) {                                //Si el buffer de tareas seleccionadas no es nulo
            for (int i = 0; i < size; i++) {            //Lo recorremos para elminarlas
                TaskRepository.getInstance().deleteTask(tasksSelected.get(i));  //Se elimina la tarea
                adapter.remove(tasksSelected.get(i));

            }

            deselectAll();                              //Deseleccionamos los index de las posiciones eliminadas
            checkSelection();

        }

        checkDeleteBtnVisibility();


    }

    @Override
    public void onSwipeMade(int pos, Task_Adapter adapter) {
        //ToDo: implementar eliminado con Swipe
    }

    @Override
    public void onListClick2(View v, int adapterPosition, ListForgottenViewMaster.TaskRecyclerViewAdapter adapter, Task task) {
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
            app.goToDetailScreen(this, adapter);
        }
        checkSelection();
       checkDeleteBtnVisibility();

    }

    @Override
    public void onLongListClick2(View v, int adapterPosition) {
        if(!selectedState){
            selectedState =true;
            setDeleteBtnVisibility(true);
            v.setSelected(true);
            itemsSelected.put(adapterPosition,true);

        }
        checkSelection();
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
    public void onBinBtnClick2(ListForgottenViewMaster.TaskRecyclerViewAdapter adapter) {

        ArrayList<Task> selected = getSelectedTasks(adapter);
        for(int i=0;i<selected.size();i++){
            database.deleteDatabaseItem(selected.get(i));

        }

        for(int j=0;j<adapter.getItemCount();j++){
            if(itemsSelected.get(j)){
                adapter.notifyItemRemoved(j);
            }

        }

        itemsSelected.clear();
        checkSelection();

    }
    private ArrayList<Task> getSelectedTasks(ListForgottenViewMaster.TaskRecyclerViewAdapter adapter) {
        ArrayList<Task> selected = new ArrayList<>();
        for(int i=0;i<adapter.getItemCount();i++){
            if(itemsSelected.get(i)){
                selected.add(adapter.getItems().get(i));
            }
        }
        return selected;
    }

    private void deselectAll() {

        for (int k = 0; k < posSelected.size(); k++) {
            getView().deselect(Integer.parseInt(posSelected.get(k)), false);
        }

        posSelected.clear();
        tasksSelected.clear();
    }

    private void checkSelection() {
        boolean somethingSelected = false;
        for (int i = 0; i <= itemsSelected.size(); i++) {
            if (itemsSelected.get(i)){
                somethingSelected=true;
                break;

            }

        }
        if(!somethingSelected){
            selectedState = false;
            setDeleteBtnVisibility(false);

        }else{
            setDeleteBtnVisibility(true);

        }


    }

    private void setItemChecked(int pos, boolean checked) {
        getView().setItemChecked(pos, checked);

    }

    private boolean isItemListChecked(int pos) {
        boolean result=false;
        if(posSelected.size()>0 && posSelected.contains(Integer.toString(pos))) {             //Si el array de posiciones de tareas no esta vacio y ademas contiene la posicion de la tarea a consultar
                result = true;                                                      //Entonces si estaba seleccionado
        }
        return result;
        }




    ///////////////////////////////////////////////////////////////////////////////////
    // To ListForgottenDetail //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
   /* if(isViewRunning()) {
      getView().setLabel(getModel().getLabel());
    }
    //checkToolbarVisibility();
    //checkTextVisibility();*/

        checkDeleteBtnVisibility();
        loadItems();
    }



    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }

    @Override
    public void setTextVisibility(boolean visible) {
        textVisible = visible;
    }



    @Override
    public void setDeleteBtnVisibility(boolean deleteBtnVisibility) {
        deleteBtnVisible=deleteBtnVisibility;

    }




    ///////////////////////////////////////////////////////////////////////////////////
    // ListForgottenDetail To //////////////////////////////////////////////////////////////////////


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

    @Override
    public boolean isToolbarVisible() {
        return toolbarVisible;
    }

    @Override
    public boolean isTextVisible() {
        return textVisible;
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


    public void setListClicked(boolean listClicked) {
        this.listClicked = listClicked;
    }

    @Override
    public void onLoadItemsTaskStarted() {
        checkToolbarVisibility();

    }

    @Override
    public void onLoadItemsTaskFinished(List<Task> itemsFromDatabase) {
        getView().setRecyclerAdapterContent(itemsFromDatabase);
    }
    public void loadItems() {
        /*if(!(database.getValidDatabase()) && !(database.getRunningTask())) {
            startDelayedTask();
        } else {*/
        if(!(database.getRunningTask())){
            Log.d(TAG, "calling onLoadItemsTaskFinished() method");
            onLoadItemsTaskFinished(database.getForgottenItemsFromDatabase());
        } else {
            Log.d(TAG, "calling onLoadItemsTaskStarted() method");
            onLoadItemsTaskStarted();
        }
        //}

    }
    /*private void startDelayedTask() {
        Log.d(TAG, "calling startDelayedTask() method");
        database.setRunningTask(true);
        Log.d(TAG, "calling onLoadItemsTaskStarted() method");
        onLoadItemsTaskStarted();

        // Mock Hello: A handler to delay the answer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setItems();
                database.setRunningTask(false);
                database.setValidDatabase(true);
                Log.d(TAG, "calling onLoadItemsTaskFinished() method");
                //getPresenter().onLoadItemsTaskFinished(items);
                onLoadItemsTaskFinished(database.getItemsFromDatabase());
            }
        }, 0);
    }*/

    public void reloadItems() {
        //items = null;

        database.deleteAllDatabaseItems();
        database.setValidDatabase(false);
        loadItems();
    }

    /*
        @Override
        public void onLoadItemsTaskFinished(List<task> items) {
            getView().setRecyclerAdapterContent(items);

        }*/
@Override
public void onErrorDeletingItem(Task item) {

}

    @Override
    public void update(Observable o, Object arg) {

        if(arg.equals(true)){
            getView().setToastDelete();
            getModel().deleteItem(selectedTask);

        }


    }
}
