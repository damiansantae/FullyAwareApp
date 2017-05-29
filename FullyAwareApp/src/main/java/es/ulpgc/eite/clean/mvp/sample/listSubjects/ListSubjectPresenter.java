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
        prefManager = new PrefManager(getView().getActivityContext());
        if (prefManager.isFirstTimeLaunch()) {
            getView().showAddUserNameDialog();
        }
        setLabelButtons(getView().getActivityContext());
        app.startingListSubjectScreen(this);

    }

    /**
     * Method that checks if the toolbar colour has been changed
     *
     * @param app Mediator: the current app.
     */
    private void checkChangesOnToolbar(Mediator app) {
        if (app.checkToolbarChanged()) {
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
    }

    /**
     * Method that sets the button labels.
     *
     * @param activityContext Contex: the current context.
     */
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

    /**
     * Auto-generated method that initializes the screen
     */
    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");

        if (isViewRunning()) {
            getView().initSwipe();
        }

        loadItems();
    }

    /**
     * Sets the toolbar visbility
     *
     * @param visible boolean true: if it is visible & false if not.
     */
    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // ListForgottenDetail To //////////////////////////////////////////////////////////////////////

    /**
     * Method that returns the actual activity context.
     *
     * @return context -> the actual activity context.
     */
    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    /**
     * Method that returns a boolean to indicate if the toolbar is visible.
     *
     * @return toolbarVisible boolean: true if visible.
     */
    @Override
    public boolean getToolbarVisibility() {
        return toolbarVisible;
    }

    /**
     * Method called when add user button is clicked.
     *
     * @param userName String: the user name.
     */
    @Override
    public void onAddUserBtnClicked(String userName) {
        prefManager.setUserName(userName);
        Log.d("TAG", "añadido con éxito el usuario");
        Log.d("TAG", prefManager.getUserName());
    }

    /**
     * Method that returns the label of button Add.
     *
     * @return String label
     */
    @Override
    public String getLabelBtnAddSubject() {
        return getModel().getLabelBtnAddSubject();
    }

    /**
     * Method that returns the label of button Hour.
     *
     * @return String label
     */
    @Override
    public String getLabelBtnHour() {
        return getModel().getLabelBtnHour();
    }

    /**
     * Method that destroys the current view.
     */
    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }
    }

    /**
     * Method that checks if the toolbar is visible.
     *
     * @return toolbarVisible boolean
     */
    @Override
    public boolean isToolbarVisible() {
        return toolbarVisible;
    }
    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Method that load the items on the subject list.
     */
    public void loadItems() {
        getView().setRecyclerAdapterContent(database.getSubjectsFromDatabase());
    }

    /**
     * Method called when an error happens deleting an item.
     */
    @Override
    public void onErrorDeletingItem(Subject item) {

    }

    /**
     * Method that sets the time text.
     */
    @Override
    public void setTimeText(int i, String txt) {
        time[i] = txt;
    }

    /**
     * Method that gets the time text.
     */
    @Override
    public String getTimeText(int i) {
        return time[i];
    }

    /**
     * Get the finish label from the model.
     */
    @Override
    public String getFinishLabel() {
        return getModel().getFinishLabel();
    }

    /**
     * Method that adds the item of an arry to the data base.
     *
     * @param subjectList ArrayList: list of subjects (string, not classes).
     */
    @Override
    public void addSubjectsToDataBase(ArrayList<String> subjectList) {
        getModel().addSubjectsToDataBase(subjectList);
    }

    /**
     * Method that save the edited subjects
     *
     * @param text,currenSubject -> String: new subject name, Subject: new subject added.
     */
    @Override
    public void saveEditSubject(String text, Subject currentSubject) {
        database.setSubjectName(currentSubject, text);
    }

    /**
     * Method called when we swipes left an item of the list subject.
     *
     * @param currentSubject Subject: item of the list.
     */
    @Override
    public void swipeLeft(Subject currentSubject) {
        database.deleteDatabaseItem(currentSubject);
    }

    /**
     * Method that launches the home screen (list to do).
     */
    @Override
    public void launchHomeScreen() {
        Navigator app = (Navigator) getApplication();
        app.goToListToDoScreen(this);
        destroyView();
    }

}
