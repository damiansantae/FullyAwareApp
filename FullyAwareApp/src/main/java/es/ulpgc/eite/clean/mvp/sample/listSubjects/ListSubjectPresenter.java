package es.ulpgc.eite.clean.mvp.sample.listSubjects;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.CheckBox;

import java.util.ArrayList;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;

public class ListSubjectPresenter extends GenericPresenter
        <ListSubject.PresenterToView, ListSubject.PresenterToModel, ListSubject.ModelToPresenter, ListSubjectModel>
        implements ListSubject.ViewToPresenter, ListSubject.ModelToPresenter, ListSubject.ListSubjectTo, ListSubject.ToListSubject {


    private boolean toolbarVisible;
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
        database = DatabaseFacade.getInstance();
        checkChangesOnToolbar(app);

        Log.d(TAG, "" + prefManager.isFirstTimeLaunch());

        prefManager = new PrefManager(getView().getActivityContext());
        if (prefManager.isFirstTimeLaunch()) {
            getView().showAddUserNameDialog();
        }
        setLabelButtons(getView().getActivityContext());
        app.startingListSubjectScreen(this);

    }

    private void checkChangesOnToolbar(Mediator app) {
        if (app.checkToolbarChanged()) {
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
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
        Mediator app = (Mediator) getView().getApplication();
        if (configurationChangeOccurred()) {
            checkChangesOnToolbar(app);
        }
        checkChangesOnToolbar(app);
        loadItems();
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
    // To ListForgottenDetail //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");

        if (isViewRunning()) {
            getView().initSwipe();
        }

        loadItems();
    }


    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListForgottenDetail To //////////////////////////////////////////////////////////////////////


    @Override
    public Context getManagedContext() {
        return getActivityContext();
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
    ///////////////////////////////////////////////////////////////////////////////////
    private void checkToolbarVisibility() {
        Log.d(TAG, "calling checkToolbarVisibility()");
        if (isViewRunning()) {
            if (!toolbarVisible) {
                getView().hideToolbar();
            }
        }
    }

    public void loadItems() {
        getView().setRecyclerAdapterContent(database.getSubjectsFromDatabase());
    }


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
    public String getFinishLabel() {
        return getModel().getFinishLabel();
    }

    @Override
    public void addSubjectsToDataBase(ArrayList<String> subjectList) {
        getModel().addSubjectsToDataBase(subjectList);
    }

    @Override
    public void saveEditSubject(String text, Subject currentSubject) {
        database.setSubjectName(currentSubject,text);
    }

    @Override
    public void swipeLeft(Subject currentSubject) {
        database.deleteDatabaseItem(currentSubject);
    }

    @Override
    public void launchHomeScreen() {
        Navigator app = (Navigator) getApplication();
        app.goToListToDoScreen(this);
        destroyView();
    }

}
