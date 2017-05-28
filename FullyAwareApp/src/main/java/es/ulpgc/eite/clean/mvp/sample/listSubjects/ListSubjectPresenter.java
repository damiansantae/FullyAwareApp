package es.ulpgc.eite.clean.mvp.sample.listSubjects;


import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;
import es.ulpgc.eite.clean.mvp.sample.RealmDatabase.DatabaseFacade;

public class ListSubjectPresenter extends GenericPresenter
        <ListSubject.PresenterToView, ListSubject.PresenterToModel, ListSubject.ModelToPresenter, ListSubjectModel>
        implements ListSubject.ViewToPresenter, ListSubject.ModelToPresenter, ListSubject.ListSubjectTo, ListSubject.ToListSubject {


    private boolean toolbarVisible;
    private boolean deleteBtnVisible;
    private boolean textVisible;
    private boolean listClicked;
    private Subject subject;
    private Subject selectedSubject;
    private boolean selectedState;
    private ArrayList<Subject> subjectsSelected = new ArrayList<>();
    private ArrayList<String> posSelected = new ArrayList<>();
    private SparseBooleanArray itemsSelected = new SparseBooleanArray();
    private DatabaseFacade database;
    private PrefManager prefManager;
    private String[] time = new String[5];
    private String[] daysChecked = new String[5];
    ArrayList<CheckBox> checkboxesL1 = new ArrayList<CheckBox>();
    ArrayList<CheckBox> checkboxesL2 = new ArrayList<CheckBox>();
    ArrayList<CheckBox> checkboxesL3 = new ArrayList<CheckBox>();
    ArrayList<CheckBox> checkboxesL4 = new ArrayList<CheckBox>();
    ArrayList<CheckBox> checkboxesL5 = new ArrayList<CheckBox>();

    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(ListSubject.PresenterToView view) {
        super.onCreate(ListSubjectModel.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");

        Log.d(TAG, "calling startingLisToDoScreen()");
        Mediator app = (Mediator) getView().getApplication();
        database =DatabaseFacade.getInstance();
        app.startingListSubjectScreen(this);
        if (app.checkToolbarChanged() == true) {
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }

        prefManager = new PrefManager(getView().getActivityContext());
        Log.d(TAG, "" + prefManager.isFirstTimeLaunch());

        if (prefManager.isFirstTimeLaunch()) {
            getView().showAddUserNameDialog();
        }

        setLabelButtons(getView().getActivityContext());

    }

    private void setLabelButtons(Context activityContext) {
        getModel().setLabelButtons();
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(ListSubject.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");

        if (configurationChangeOccurred()) {
            checkDeleteBtnVisibility();

            if (listClicked) {
                getView().startSelection();
                onCheckItems();
            }
        }

        Mediator app = (Mediator) getView().getApplication();
        if (app.checkToolbarChanged() == true) {
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
        loadItems();
    }


    /**
     * Selecciona los elementos de la lista que estaban seleccionados
     */
    private void onCheckItems() {
        for (int i = 0; i < posSelected.size(); i++) {
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
    public void onListClick2(View v, int adapterPosition, ListSubjectView.SubjectRecyclerViewAdapter adapter, Subject subject) {
        if (selectedState) {
            if (!v.isSelected()) {
                v.setSelected(true);
                itemsSelected.put(adapterPosition, true);

            } else {
                v.setSelected(false);
                itemsSelected.put(adapterPosition, false);

            }
        } else {
            Navigator app = (Navigator) getView().getApplication();
            selectedSubject = subject;
            app.goToDetailScreen(this, adapter);
        }
        checkSelection();
        checkDeleteBtnVisibility();

    }

    @Override
    public void onLongListClick2(View v, int adapterPosition) {
        if (!selectedState) {
            selectedState = true;
            v.setSelected(true);
            itemsSelected.put(adapterPosition, true);

        }

        checkSelection();

        checkDeleteBtnVisibility();


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
    public void onBinBtnClick2(ListSubjectView.SubjectRecyclerViewAdapter adapter) {

        ArrayList<Subject> selected = getSelectedSubjects(adapter);
        for (int i = 0; i < selected.size(); i++) {
            database.deleteDatabaseItem(selected.get(i));
        }
        itemsSelected.clear();
        checkSelection();

        checkDeleteBtnVisibility();


    }

    private ArrayList<Subject> getSelectedSubjects(ListSubjectView.SubjectRecyclerViewAdapter adapter) {
        ArrayList<Subject> selected = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (itemsSelected.get(i)) {
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
        subjectsSelected.clear();
    }

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

    private void setItemChecked(int pos, boolean checked) {
        getView().setItemChecked(pos, checked);

    }

    private boolean isItemListChecked(int pos) {
        boolean result = false;
        if (posSelected.size() > 0 && posSelected.contains(Integer.toString(pos))) {             //Si el array de posiciones de tareas no esta vacio y ademas contiene la posicion de la tarea a consultar
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
        deleteBtnVisible = deleteBtnVisibility;

    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListForgottenDetail To //////////////////////////////////////////////////////////////////////


    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    public Subject getSelectedSubject() {
        return selectedSubject;
    }

    @Override
    public boolean getToolbarVisibility() {
        return toolbarVisible;
    }

    @Override
    public void onAddUserBtnClicked(String userName) {
        prefManager.setUserName(userName);
        Log.d("TAG", "añadido con éxito el usuario");
        Log.d("TAG", prefManager.getUserName());
    }

    @Override
    public String getLabelFloatingAdd() {
        return getModel().getLabelFloatingAdd();
    }

    @Override
    public String getLabelFloatingDelete() {
        return getModel().getLabelFloatingDelete();
    }

    @Override
    public String getLabelBtnAddSubject() {
        return getModel().getLabelBtnAddSubject();
    }

    @Override
    public String getLabelBtnHour() {
        return getModel().getLabelBtnHour();
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
    public void onLoadItemsSubjectStarted() {
        checkToolbarVisibility();
    }

    @Override
    public void onLoadItemsSubjectFinished(List<Subject> itemsFromDatabase) {
        getView().setRecyclerAdapterContent(itemsFromDatabase);
    }

    public void loadItems() {
        /*if(!(database.getValidDatabase()) && !(database.getRunningTask())) {
            startDelayedTask();
        } else {*/
            Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
            onLoadItemsSubjectStarted();

        //}

    }

    /*private void startDelayedTask() {
        Log.d(TAG, "calling startDelayedTask() method");
        database.setRunningTask(true);
        Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
        onLoadItemsSubjectStarted();

        // Mock Hello: A handler to delay the answer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setItems();
                database.setRunningTask(false);
                database.setValidDatabase(true);
                Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
                //getPresenter().onLoadItemsSubjectsFinished(items);
                onLoadItemsSubjectsFinished(database.getTasksFromDatabase());
            }
        }, 0);
    }*/

    /*
        @Override
        public void onLoadItemsSubjectsFinished(List<subject> items) {
            getView().setRecyclerAdapterContent(items);

        }*/
    @Override
    public void onErrorDeletingItem(Subject item) {

    }

    @Override
    public void setTimeText(int i, String txt) {
        time[i] = txt;
    }

    @Override
    public String getTimeText(int i) {
        return time[i];
    }


    @Override
    public String getDaysChecked(int i) {
        return daysChecked[i]; //TODO: Metodo no utilizado por el momento
    }

    @Override
    public String getFinishLabel() {
        return getModel().getFinishLabel();
    }

    @Override
    public void addSubjectsToDataBase(ArrayList<String> subjectList) {
        getModel().addSubjectsToDataBase(subjectList);
    }

}
