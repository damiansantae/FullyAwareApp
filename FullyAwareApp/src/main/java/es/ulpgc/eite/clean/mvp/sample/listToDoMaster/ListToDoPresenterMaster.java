package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;
//Prueba

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectModel;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

import static android.content.Context.MODE_PRIVATE;

public class ListToDoPresenterMaster extends GenericPresenter
        <ListToDoMaster.PresenterToView, ListToDoMaster.PresenterToModel,
                ListToDoMaster.ModelToPresenter, ListToDoModelMaster>
        implements ListToDoMaster.ViewToPresenter, ListToDoMaster.ModelToPresenter,
        ListToDoMaster.ListToDoTo, ListToDoMaster.ToListToDo,
        ListToDoMaster.MasterListToDetail, ListToDoMaster.DetailToMaster, Observer{


    private boolean toolbarVisible;
    private boolean deleteBtnVisible;
    private boolean addBtnVisible;
    private boolean doneBtnVisible;
    private boolean textVisible;
    private boolean textWhenIsEmptyVisible;
    private boolean selectedState;
    private Task selectedTask;
    private ArrayList<Task> tasksSelected = new ArrayList<>();
    private ArrayList<String> posSelected = new ArrayList<>();

    private SparseBooleanArray itemsSelected =new SparseBooleanArray();
    private DatabaseFacade database;
    int counter=0;

    SharedPreferences myprefs;
    public static final String MY_PREFS = "MyPrefs";
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";

    private static final int READ_CALENDAR_PERMISSIONS_REQUEST = 1;
    private static final int WRITE_CALENDAR_PERMISSIONS_REQUEST = 2;




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
        database =DatabaseFacade.getInstance();



        app.startingListToDoScreen(this);
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
            checkTextWhenIsEmptyVisibility();
            CheckDoneBtnVisibility();
            if(selectedState) {
                getView().startSelection();

                onCheckItems();
            }

//            if (buttonClicked) {
//                getView().setText(getModel().getText());
//            }
        }

        Mediator app = (Mediator) getView().getApplication();
        checkToolbarColourChanges(app);
        loadItems();
    }

    private void checkTextWhenIsEmptyVisibility() {
        Log.d(TAG, "calling checkTextWhenIsEmptyVisibility()");
        if (isViewRunning()) {
            if (database.getToDoItemsFromDatabase().size() == 0) {
                getView().showTextWhenIsEmpty();
            } else {
                getView().hideTextWhenIsEmpty();
            }
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


    private void checkToolbarColourChanges(Mediator app){

        Context context = getApplicationContext();
        SharedPreferences myprefs = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        if (app.checkToolbarChanged() == true){

            Log.d("999AQUIIIIIIIII", "ENTRA AL IF");
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);


            SharedPreferences.Editor editor = myprefs.edit();
            editor.putString(TOOLBAR_COLOR_KEY, colour);
            Log.d("999AQUIIIIIIIII", ""+ app.getToolbarColour());
            editor.commit();
            Log.d("999AQUIIIIIIIII", ""+ myprefs.getString(TOOLBAR_COLOR_KEY, null));
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
    public void onListClick2(View v, int adapterPosition, ListToDoViewMaster.TaskRecyclerViewAdapter adapter, Task task) {
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
checkSelection2();
        checkAddBtnVisibility();checkDoneBtnVisibility();checkDeleteBtnVisibility();

    }

    private void checkSelection2() {
        boolean somethingSelected= false;
        for(int i = 0; i < itemsSelected.size(); i++) {
            int key = itemsSelected.keyAt(i);
            // get the object by the key.
            Object obj = itemsSelected.get(key);
            if(obj.equals(true)){
                somethingSelected=true;
                break;
            }


        }

       if(somethingSelected){
           setAddBtnVisibility(false);
           setDoneBtnVisibility(true);
           setDeleteBtnVisibility(true);
       }else{
           setAddBtnVisibility(true);
           setDoneBtnVisibility(false);
           setDeleteBtnVisibility(false);
           selectedState=false;
       }



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
    public void onLongListClick2(View v, int adapterPosition) {
        if(!selectedState){
            selectedState =true;
            v.setSelected(true);
            itemsSelected.put(adapterPosition,true);

        }

checkSelection2();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();




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
    public void onBinBtnClick2(ListToDoViewMaster.TaskRecyclerViewAdapter adapter) {

   ArrayList<Task> selected = getSelectedTasks(adapter);
        for(int i=0;i<selected.size();i++){
            database.deleteDatabaseItem(selected.get(i));
          //  Log.d(TAG+ "ONBInItem a eliminar", selected.get(i).getTaskId());
        }
itemsSelected.clear();
checkSelection2();
        checkTextWhenIsEmptyVisibility();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();

    }


    @Override
    public String getCases(Task task) {
        String subjectName= task.getSubject().getName();

        return  getModel().calculateCases(subjectName);


    }


    private ArrayList<Task> getSelectedTasks(ListToDoViewMaster.TaskRecyclerViewAdapter adapter) {
        ArrayList<Task> selected = new ArrayList<>();
        for(int i=0;i<adapter.getItemCount();i++){
            if(itemsSelected.get(i)){
                selected.add(adapter.getItems().get(i));
            }
        }
        return selected;
    }




    @Override
    public void onAddBtnClick() {
        Navigator app = (Navigator)getView().getApplication();
        app.goToAddTaskScreen(this);
    }

    @Override
    public void onDoneBtnClick(ListToDoViewMaster.TaskRecyclerViewAdapter adapter) {
        ArrayList<Task> selected = getSelectedTasks(adapter);
        for(int i=0;i<selected.size();i++){
            database.setItemStatus(selected.get(i), "Done");

        }

        for(int j=0;j<adapter.getItemCount();j++){
            if(itemsSelected.get(j)){
                adapter.notifyItemRemoved(j);
            }

        }

        itemsSelected.clear();
        checkSelection2();
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
    // To ListDoTo //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
    if(isViewRunning()) {
  getView().initSwipe();
        getView().initDialog();
    }

    //isTaskForgotten(database.getItemsFromDatabase().get(0).getDate());


//TODO:Descomentar cuando se instala la app por primera vez y luego comentar
      //database.createTestingScenario();
        ListSubjectModel subjectAssistant = new ListSubjectModel();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
        checkTextWhenIsEmptyVisibility();
        loadItems();
        requestUserPermissions();
    }

    public void requestUserPermissions(){
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED)) {


            ActivityCompat.requestPermissions((Activity)getView(),
                    new String[]{android.Manifest.permission.READ_CALENDAR},
                    READ_CALENDAR_PERMISSIONS_REQUEST);

            ActivityCompat.requestPermissions((Activity)getView(),
                    new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    WRITE_CALENDAR_PERMISSIONS_REQUEST);

        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_CALENDAR_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case WRITE_CALENDAR_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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

    @Override
    public void setTextWhenIsEmptyVisibility(boolean textWhenIsEmptyVisibility) {
        this.textWhenIsEmptyVisible = textWhenIsEmptyVisibility;

    }

    @Override
    public void subjectFilter() {
        List<Task> orderedList = getModel().orderSubjects();
        getView().setRecyclerAdapterContent(orderedList);
    }

    @Override
    public void swipeLeft(Task currentTask) {
        database.deleteDatabaseItem(currentTask);
        checkTextWhenIsEmptyVisibility();

    }

    @Override
    public void swipeRight(Task currentTask) {
        database.setItemStatus(currentTask, "Done");
        checkTextWhenIsEmptyVisibility();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListToDo To //////////////////////////////////////////////////////////////////////


    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    @Override
    public void onErrorDeletingItem(Task item) {

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

    public void setSelectedState(boolean selectedState) {
        this.selectedState = selectedState;
    }


    @Override
    public void onLoadItemsTaskStarted() {
        checkToolbarVisibility();

    }

    @Override
    public void confirmBackPressed() {
        getView().confirmBackPressed();
    }

    @Override
    public void delayedTaskToBackStarted() {

        getView().showToastBackConfirmation(getModel().getToastBackConfirmation());
    }

    @Override
    public void onLoadItemsTaskFinished(List<Task> items) {
        getView().setRecyclerAdapterContent(items);
    }

    @Override
    public void update(Observable o, Object arg) {

        if(arg.equals("delete")){
            database.deleteDatabaseItem(selectedTask);
            getView().setToastDelete();
        }else if(arg.equals("done")){
            database.setItemStatus(selectedTask, "Done");
        }

    }
    public void loadItems() {
                onLoadItemsTaskFinished(database.getToDoItemsFromDatabase());
            }


    public void reloadItems() {
        database.deleteAllDatabaseItems();
        database.setValidDatabase(false);
        loadItems();
    }

    @Override
    public boolean isTaskForgotten(String deadline){
        boolean isTaskForgotten = false;

        String day = deadline.substring(0, 2);
        int intDay = Integer.parseInt(day);

        String month = deadline.substring(3, 5);
        int intMonth = Integer.parseInt(month)-1;

        String year = deadline.substring(6, 10);
        int intYear = Integer.parseInt(year)-1900;

        String hour = deadline.substring(13, 15);
        int intHour = Integer.parseInt(hour);

        String minutes = deadline.substring(16);
        int intMinutes = Integer.parseInt(minutes);

        Date deadlineDate = new Date(intYear, intMonth, intDay, intHour, intMinutes);

        Date currentDate = new Date();

        if(currentDate.after(deadlineDate)){
            isTaskForgotten = true;
        }
        return isTaskForgotten;
    }

    @Override
    public void onBtnBackPressed() {
        getModel().startBackPressed();
    }

    public void checkForgottenTasks(){
        List<Task> tasks = database.getToDoItemsFromDatabase();
        for(int i = 0; i < tasks.size(); i++){
            String deadline = tasks.get(i).getDate();

            String day = deadline.substring(0, 2);
            int intDay = Integer.parseInt(day);

            String month = deadline.substring(3, 5);
            int intMonth = Integer.parseInt(month)-1;

            String year = deadline.substring(6, 10);
            int intYear = Integer.parseInt(year)-1900;

            String hour = deadline.substring(13, 15);
            int intHour = Integer.parseInt(hour);

            String minutes = deadline.substring(16);
            int intMinutes = Integer.parseInt(minutes);

            Date deadlineDate = new Date(intYear, intMonth, intDay, intHour, intMinutes);

            Date currentDate = new Date();

            if(currentDate.after(deadlineDate)){
                tasks.get(i);
            }
        }
    }
}
