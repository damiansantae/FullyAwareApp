package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;

public class ListToDoPresenterMaster extends GenericPresenter
        <ListToDoMaster.PresenterToView, ListToDoMaster.PresenterToModel, ListToDoMaster.ModelToPresenter, ListToDoModelMaster>
        implements ListToDoMaster.ViewToPresenter, ListToDoMaster.ModelToPresenter, ListToDoMaster.ListToDoTo, ListToDoMaster.ToListToDo, ListToDoMaster.MasterListToDetail, ListToDoMaster.DetailToMaster, ListToDoMaster.Observer {


    private boolean toolbarVisible;
    private boolean buttonClicked;
    private boolean deleteBtnVisible;
    private boolean addBtnVisible;
    private boolean doneBtnVisible;
    private boolean textVisible;
    private boolean listClicked;
    private Task taskDone;
    private Task selectedTask;
    private ArrayList<Task> tasksSelected = new ArrayList<>();
    private ArrayList<String> posSelected = new ArrayList<>();
    private ListToDoDetail.Observable detail;




    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(ListToDoMaster.PresenterToView view) {
        super.onCreate(ListToDoModelMaster.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");

        Log.d(TAG, "calling startingLisToDoScreen()");
        Mediator app = (Mediator) getView().getApplication();
        app.startingListToDoScreen(this);
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(ListToDoMaster.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");

        if (configurationChangeOccurred()) {
            //getView().setLabel(getModel().getLabel());


           checkToolbarVisibility();
            //checkTextVisibility();
            checkAddBtnVisibility();
            
            checkDeleteBtnVisibility();
            checkDoneBtnVisibility();
            CheckDoneBtnVisibility();
            if(listClicked) {
                getView().startSelection();

                onCheckItems();
            }

//            if (buttonClicked) {
//                getView().setText(getModel().getText());
//            }
        }
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

    @Override
    public void onButtonClicked() {
        Log.d(TAG, "calling onButtonClicked()");
        if (isViewRunning()) {
            getModel().onChangeMsgByBtnClicked();
            getView().setText(getModel().getText());
            textVisible = true;
            buttonClicked = true;
        }
        checkTextVisibility();
    }

    @Override
    public void onListClick(int position, Task_Adapter adapter) {
        Task currentTask = adapter.getItem(position);
        if (listClicked) {                                //Esta seleccionado algo?

            if (isItemListChecked(position)) {            //Si el elemento ya estaba seleccionado
                setItemChecked(position, false);         //Se deselecciona
                Log.v("se deselecciona", "pos: " + position);
                tasksSelected.remove(currentTask);       //Se elimina del Array de seleccionados
                posSelected.remove(Integer.toString(position));                  //Se elimina del array de posiciones seleccionadas

                checkSelection();                       //Comprobamos si sigue alguno seleccionado
            } else {                                      //Si no estaba seleccionado

                setItemChecked(position, true);          //Lo seleccionamos
                Log.v("se selecciona", "pos: " + position);

                tasksSelected.add(currentTask);           //Se añade al array de seleccionados
                posSelected.add(Integer.toString(position));                     //Se añade al array de posiciones seleccionadas (Para poder eliminarlas tras el borrado)
            }

        } else {                                          //Si no estaba ningun elemento seleccionado
            //Codigo DETALLE
            selectedTask = adapter.getItem(position);
            Navigator app = (Navigator) getView().getApplication();
           // app.goToDetailScreen(this, adapter);
        }
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
checkAddBtnVisibility();
    }
    @Override
    public void onListClick2(Task item, ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter) {
        Task currentTask = item;
        if (listClicked) {                                //Esta seleccionado algo?

            if (isTaskSelected(currentTask)) {            //Si el elemento ya estaba seleccionado
                deselectTask(currentTask);                    //Se deselecciona

                //checkSelection();                       //Comprobamos si sigue alguno seleccionado
            } else {                                      //Si no estaba seleccionado
                tasksSelected.add(currentTask);
            }

        } else {                                          //Si no estaba ningun elemento seleccionado
            //Codigo DETALLE
            selectedTask = currentTask;
            Navigator app = (Navigator) getView().getApplication();
            app.goToDetailScreen(this, adapter);
        }
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
        checkAddBtnVisibility();

    }



    private void deselectTask(Task currentTask) {
       tasksSelected.remove(currentTask);
    }

    private boolean isTaskSelected(Task currentTask) {
        boolean result = false;
        for(int i=0;i<tasksSelected.size();i++){
            if(currentTask.equals(tasksSelected.get(i)))
                result= true;
    }
        return result;

}

    @Override
    public void onLongListClick(int pos, Task_Adapter adapter) {
        getView().startSelection();           //iniciamos modo seleccion multiple

        Log.v("long click", "pos: " + pos);
        Task currentTask = adapter.getItem(pos);



        if (isItemListChecked(pos)) {                //Si el elemento ya estaba seleccionado
            setItemChecked(pos, false);          //Se deselecciona
            Log.v("Se deselecciona", "pos: " + pos);

            tasksSelected.remove(currentTask);       //Se elimina del Array de seleccionados
            posSelected.remove(Integer.toString(pos));                  //Se elimina del array de posiciones seleccionadas
            checkSelection();                        //miramos si hay algun seleccionado
        } else {                                      //Si no estaba seleccionado
            setListClicked(true);                   //actualizamos estado a algo seleccionado
            setItemChecked(pos, true);           //Se selecciona
            Log.v("Se selecciona", "pos: " + pos);
            tasksSelected.add(currentTask);           //Se añade al array de seleccionados
            posSelected.add(Integer.toString(pos));                     //Se añade al array de posiciones seleccionadas (Para poder eliminarlas tras el borrado)+
           checkSelection();

        }
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();

    }

    @Override
    public void onLongListClick2(Task task) {
        getView().startSelection();           //iniciamos modo seleccion multiple


        Task currentTask = task;



        if (isTaskSelected(currentTask)) {                //Si el elemento ya estaba seleccionado
               //Se deselecciona
            tasksSelected.remove(currentTask);


           // checkSelection();                        //miramos si hay algun seleccionado
        } else {                                      //Si no estaba seleccionado
            setListClicked(true);                   //actualizamos estado a algo seleccionado
                   //Se selecciona
            tasksSelected.add(currentTask);
         //checkSelection();

        }
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();

    }

    @Override
    public void onAddBtnClick() {
        Navigator app = (Navigator)getView().getApplication();

        app.goToAddTaskScreen(this);

    }

    public void onSwipeMade(int position, Task_Adapter adapter){
 /*       int sizes = posSelected.size();
        if (sizes != 0) {                                //Si el buffer de tareas seleccionadas no es nulo
            for (int i = 0; i < sizes; i++) {            //Lo recorremos para elminarlas
                TaskRepository.getInstance().deleteTask(tasksSelected.get(i));  //Se elimina la tarea
                adapter.remove(tasksSelected.get(i));
            }
            deselectAll();                              //Deseleccionamos los index de las posiciones eliminadas
            checkSelection();
    }
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
    }*/

    Task currentTask = adapter.getItem(position);
        if (listClicked) {                                //Esta seleccionado algo?

            int sizes = posSelected.size();
            if (sizes != 0) {                                //Si el buffer de tareas seleccionadas no es nulo
                for (int i = 0; i < sizes; i++) {            //Lo recorremos para elminarlas
                    TaskRepository.getInstance().deleteTask(tasksSelected.get(i));  //Se elimina la tarea
                    adapter.remove(tasksSelected.get(i));
                }
                deselectAll();                              //Deseleccionamos los index de las posiciones eliminadas
                checkSelection();
            }
            checkAddBtnVisibility();
            checkDeleteBtnVisibility();
            checkDoneBtnVisibility();

    } else {                                          //Si no estaba ningun elemento seleccionado
        //Codigo DETALLE

            TaskRepository.getInstance().deleteTask(currentTask);  //Se elimina la tarea
            adapter.remove(currentTask);
            deselectAll();                              //Deseleccionamos los index de las posiciones eliminadas
            checkSelection();
    }
    checkDeleteBtnVisibility();
    checkDoneBtnVisibility();
    checkAddBtnVisibility();
}




    @Override
    public void onBinBtnClick(Task_Adapter adapter) {
        int size = posSelected.size();
        if (size != 0) {                                //Si el buffer de tareas seleccionadas no es nulo
            for (int i = 0; i < size; i++) {            //Lo recorremos para elminarlas
                TaskRepository.getInstance().deleteTask(tasksSelected.get(i));  //Se elimina la tarea
                adapter.remove(tasksSelected.get(i));

            }
            Context context = getApplicationContext();
            if(size == 1) {
                CharSequence text = "Task removed";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else if(size > 1){
                CharSequence text = "Tasks removed";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            deselectAll();                              //Deseleccionamos los index de las posiciones eliminadas
            checkSelection();

        }
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();

    }

    //TODO: Quitar parametro
    @Override
    public void onAddBtnClick(Task_Adapter adapter) {
        Navigator app = (Navigator)getView().getApplication();

        app.goToAddTaskScreen(this);
    }

    @Override
    public void onDoneBtnClick(Task_Adapter adapter) {
        int size = posSelected.size();
        if (size !=0){
            for (int i = 0; i < size; i++){

                Mediator app = (Mediator) getApplication();
                app.taskDone(TaskRepository.getInstance().taskDone(tasksSelected.get(i)));
                TaskRepository.getInstance().deleteTask(tasksSelected.get(i));
                adapter.remove(tasksSelected.get(i));
            }
            deselectAll();                              //Deseleccionamos los index de las posiciones eliminadas
            checkSelection();
        }
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
    }




    private void deselectAll() {

        for (int k = 0; k < posSelected.size(); k++) {
            getView().deselect(Integer.parseInt(posSelected.get(k)), false);
        }

        posSelected.clear();
        tasksSelected.clear();
    }

    private void checkSelection() {
        if (posSelected.size() == 0) {                   //Si no hay nada seleccionado
            setListClicked(false);                      //Cambiamos estado a nada seleccionado
          // getView().setChoiceMode(0);                 //Cambiamos modo de seleccionamiento a nulo

            deleteBtnVisible=false;
            doneBtnVisible=false;
            addBtnVisible=true;
        } else {                                          //Si hay algo seleccionado
            getView().setChoiceMode(2);                 //Cambiamos modo a seleccion multiple
            deleteBtnVisible=true;
            doneBtnVisible=true;
            addBtnVisible=false;

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
    // To ListDoneDetail //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
   /* if(isViewRunning()) {
      getView().setLabel(getModel().getLabel());
    }
    //checkToolbarVisibility();
    //checkTextVisibility();*/
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();

        getModel().loadItems();
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
    public void setAddBtnVisibility(boolean addBtnVisibility) {
        addBtnVisible=addBtnVisibility;

    }

    @Override
    public void setDeleteBtnVisibility(boolean deleteBtnVisibility) {
        deleteBtnVisible=deleteBtnVisibility;

    }

    @Override
    public void setDoneBtnVisibility(boolean doneBtnVisibility) {
        doneBtnVisible=doneBtnVisibility;

    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListDoneDetail To //////////////////////////////////////////////////////////////////////


    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    @Override
    public Task getSelectedTask() {
        return selectedTask;
    }

    @Override
    public boolean getToolbarVisibility() {
        return toolbarVisible;
    }

    @Override
    public String getTaskDate() {
        return null;
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

    private void checkTextVisibility() {
        Log.d(TAG, "calling checkTextVisibility()");
        if (isViewRunning()) {
            if (!textVisible) {
                getView().hideText();
            } else {
                getView().showText();
            }
        }
    }

    private void checkAddBtnVisibility() {
        Log.d(TAG, "calling checkAddBtnVisibility()");
        if (isViewRunning()) {
            if (!addBtnVisible) {
                getView().hideAddBtn();
            } else {
                getView().showAddBtn();
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
    private void checkDoneBtnVisibility() {
        Log.d(TAG, "calling checkDoneBtnVisibility()");
        if (isViewRunning()) {
            if (!doneBtnVisible) {
                getView().hideDoneBtn();
            } else {
                getView().showDoneBtn();
            }
        }
    }
    private void CheckDoneBtnVisibility() {
        Log.d(TAG, "calling checkDoneBtnVisibility()");
        if (isViewRunning()) {
            if (!doneBtnVisible) {
                getView().hideDoneBtn();
            } else {
                getView().showDoneBtn();
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
    public void onLoadItemsTaskFinished(List<Task> items) {
        getView().setRecyclerAdapterContent(items);

    }



    @Override
    public void update() {
       Task taskToDelete= (Task) detail.getUpdate(this);
        TaskRepository.getInstance().deleteTask(taskToDelete);


    }

    @Override
    public void setObservable(ListToDoDetail.Observable detail) {
        this.detail=detail;


    }
}
