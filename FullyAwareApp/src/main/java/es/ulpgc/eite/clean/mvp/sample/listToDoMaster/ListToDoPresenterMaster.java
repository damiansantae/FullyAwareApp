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
import es.ulpgc.eite.clean.mvp.sample.TaskRecyclerViewAdapter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;

/**
 * Presenter of a task to do  list.
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListToDoPresenterMaster extends GenericPresenter
        <ListToDoMaster.PresenterToView, ListToDoMaster.PresenterToModel, ListToDoMaster.ModelToPresenter, ListToDoModelMaster>
        implements ListToDoMaster.ViewToPresenter, ListToDoMaster.ModelToPresenter,
        ListToDoMaster.ListToDoTo, ListToDoMaster.ToListToDo,
        ListToDoMaster.MasterListToDetail, Observer {


    private boolean toolbarVisible;
    private boolean deleteBtnVisible;
    private boolean addBtnVisible;
    private boolean doneBtnVisible;
    private boolean textWhenIsEmptyVisible;
    private boolean selectedState;
    private Task selectedTask;
    private SparseBooleanArray itemsSelected = new SparseBooleanArray();
    private DatabaseFacade database;
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
        database = DatabaseFacade.getInstance();

        app.startingListToDoScreen(this);
        app.loadSharePreferences((ListToDoViewMaster) getView());


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

        if (configurationChangeOccurred()) {        //if screen rotation
        }

        checkSelection();
        checkToolbarVisibility();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
        checkTextWhenIsEmptyVisibility();
        checkDoneBtnVisibility();
        Mediator app = (Mediator) getView().getApplication();
        app.loadSharePreferences((ListToDoViewMaster) getView());
        loadItems();
    }

    /**
     * Method that look if there is a task to do in database
     * and configure inform text visibility on View agree of that
     */
    private void checkTextWhenIsEmptyVisibility() {
        Log.d(TAG, "calling checkTextWhenIsEmptyVisibility()");
        if (isViewRunning()) {
            if (database.getToDoTasksFromDatabase().size() == 0) {
                getView().showTextWhenIsEmpty();
            } else {
                getView().hideTextWhenIsEmpty();
            }
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
        if (selectedState) {                                            //If there is a selected state (some task selected)
            if (!v.isSelected()) {                                      //If the task is not selected, then select it
                v.setSelected(true);
                itemsSelected.put(adapterPosition, true);               //insert position of this task in a table

            } else {                                                    //If not, then deselect it
                v.setSelected(false);
                itemsSelected.put(adapterPosition, false);              //delete position of this task in a table

            }
        } else {                                                        //if there is not selected state, then go to task detail
            Navigator app = (Navigator) getView().getApplication();
            selectedTask = task;
            app.goToDetailScreen(this);
        }

        checkSelection();
        checkAddBtnVisibility();
        checkDoneBtnVisibility();
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
            setAddBtnVisibility(false);
            setDoneBtnVisibility(true);
            setDeleteBtnVisibility(true);
        } else {
            setAddBtnVisibility(true);
            setDoneBtnVisibility(false);
            setDeleteBtnVisibility(false);
            selectedState = false;
        }

    }


    @Override
    public void onLongListClick(View v, int adapterPosition) {
        if (!selectedState) {                           //If there is no selected state (no task selected), then
            //start selected stated and selected the task
            selectedState = true;
            v.setSelected(true);
            itemsSelected.put(adapterPosition, true);
        }

        checkSelection();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();


    }


    @Override
    public boolean isSelected(int adapterPosition) {
        boolean result = false;
        if (itemsSelected.size() != 0) {

            if (itemsSelected.get(adapterPosition)) {
                result = true;
            }
        }
        return result;
    }


    @Override
    public void onBinBtnClick(TaskRecyclerViewAdapter adapter) {

        ArrayList<Task> selected = getSelectedTasks(adapter);

        for (int i = 0; i < selected.size(); i++) {
            database.deleteDatabaseItem(selected.get(i));
        }
        itemsSelected.clear();
        checkSelection();
        checkTextWhenIsEmptyVisibility();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();

    }


    @Override
    public String getCases(Task task) {
        String subjectName = task.getSubject().getName();
        return getModel().calculateCases(subjectName);


    }

    /**
     * This method looks for the tasks in the selected table which has
     * his field on true (selected)
     *
     * @param adapter the recyclerView adapter
     * @return a list of Tasks which are selected
     */
    private ArrayList<Task> getSelectedTasks(TaskRecyclerViewAdapter adapter) {
        ArrayList<Task> selected = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (itemsSelected.get(i)) {
                selected.add(adapter.getItems().get(i));
            }
        }
        return selected;
    }


    @Override
    public void onAddBtnClick() {
        Navigator app = (Navigator) getView().getApplication();
        app.goToAddTaskScreen(this);
    }


    @Override
    public void onDoneBtnClick(TaskRecyclerViewAdapter adapter) {
        ArrayList<Task> selected = getSelectedTasks(adapter);
        for(int i=0;i<selected.size();i++){
            database.setTaskStatus(selected.get(i), "Done");

        }

        for (int j = 0; j < adapter.getItemCount(); j++) {
            if (itemsSelected.get(j)) {
                adapter.notifyItemRemoved(j);
            }

        }

        itemsSelected.clear();
        checkSelection();
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // To ListDoTo //////////////////////////////////////////////////////////////////////


    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
        if (isViewRunning()) {
            getView().initSwipe();
        }
        checkAddBtnVisibility();
        checkDeleteBtnVisibility();
        checkDoneBtnVisibility();
        checkTextWhenIsEmptyVisibility();
        loadItems();
        requestUserPermissions();
    }

    //TODO: IVAN COMENTA ESTE METODO
    public void requestUserPermissions() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED)) {


            ActivityCompat.requestPermissions((Activity) getView(),
                    new String[]{android.Manifest.permission.READ_CALENDAR},
                    READ_CALENDAR_PERMISSIONS_REQUEST);

            ActivityCompat.requestPermissions((Activity) getView(),
                    new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    WRITE_CALENDAR_PERMISSIONS_REQUEST);

        }

    }

    //TODO: IVAN COMENTA ESTE METODO
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
    public void setAddBtnVisibility(boolean addBtnVisibility) {
        addBtnVisible = addBtnVisibility;

    }

    @Override
    public void setDeleteBtnVisibility(boolean deleteBtnVisibility) {
        deleteBtnVisible = deleteBtnVisibility;

    }

    @Override
    public void setDoneBtnVisibility(boolean doneBtnVisibility) {
        doneBtnVisible = doneBtnVisibility;

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
        database.setTaskStatus(currentTask, "Done");
        checkTextWhenIsEmptyVisibility();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListToDo To //////////////////////////////////////////////////////////////////////


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


    @Override
    public void confirmBackPressed() {
        getView().confirmBackPressed();
    }

    @Override
    public void delayedTaskToBackStarted() {

        getView().showToastBackConfirmation(getModel().getToastBackConfirmation());
    }

    private void loadItems() {
        getView().setRecyclerAdapterContent(database.getToDoTasksFromDatabase());
    }


    @Override
    public boolean isTaskForgotten(String deadline) {

        return getModel().compareDateWithCurrent(deadline);

    }

    @Override
    public void onBtnBackPressed() {
        getModel().backPressed();
    }

    public void checkForgottenTasks() {
        List<Task> tasks = database.getToDoTasksFromDatabase();
        for (int i = 0; i < tasks.size(); i++) {
            String deadline = tasks.get(i).getDate();

            String day = deadline.substring(0, 2);
            int intDay = Integer.parseInt(day);

            String month = deadline.substring(3, 5);
            int intMonth = Integer.parseInt(month) - 1;

            String year = deadline.substring(6, 10);
            int intYear = Integer.parseInt(year) - 1900;

            String hour = deadline.substring(13, 15);
            int intHour = Integer.parseInt(hour);

            String minutes = deadline.substring(16);
            int intMinutes = Integer.parseInt(minutes);

            Date deadlineDate = new Date(intYear, intMonth, intDay, intHour, intMinutes);

            Date currentDate = new Date();

            if (currentDate.after(deadlineDate)) {
                tasks.get(i);
            }
        }
    }

    public int getToolbarColour() {
        PrefManager prefManager = new PrefManager(getActivityContext());
        return prefManager.getToolbarColour();
    }

    /**
     *When detail make a notifyObserver() this method is accessed.
     * It delete the task which showed its detail before or change it
     * status field to done
     * @param o: Observable which did notifyObserver()
     * @param arg: String "delete" or "done"
     *
     */
    @Override
    public void update(Observable o, Object arg) {

        if (arg.equals("delete")) {
            database.deleteDatabaseItem(selectedTask);
            getView().showToastDelete();
        } else if (arg.equals("done")) {
            database.setTaskStatus(selectedTask, "Done");
        }

    }
}
