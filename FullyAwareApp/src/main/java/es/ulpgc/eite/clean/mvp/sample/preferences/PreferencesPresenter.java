package es.ulpgc.eite.clean.mvp.sample.preferences;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.SimpleAdapter;
import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;

public class PreferencesPresenter extends GenericPresenter
        <Preferences.PresenterToView, Preferences.PresenterToModel, Preferences.ModelToPresenter, PreferencesModel>
        implements Preferences.ViewToPresenter, Preferences.ModelToPresenter, Preferences.PreferencesTo, Preferences.ToPreferences {


    private boolean toolbarVisible;
    private PrefManager prefManager;
    private boolean toolbarColorChanged;
    private int toolbarColour;

    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(Preferences.PresenterToView view) {
        super.onCreate(PreferencesModel.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");

        Log.d(TAG, "calling startingDummyScreen()");
        Mediator app = (Mediator) getView().getApplication();
        app.startingPreferencesScreen(this);
        prefManager = new PrefManager(getManagedContext());
        if (app.checkToolbarChanged()) {
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
        app.loadSharePreferences((PreferencesView) getView());
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(Preferences.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");

        if (configurationChangeOccurred()) {

            checkToolbarVisibility();
            Mediator app = (Mediator) getView().getApplication();
            app.loadSharePreferences((PreferencesView) getView());
        }

        Mediator app = (Mediator) getView().getApplication();
        if (app.checkToolbarChanged()) {
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

    /**
     * Called method to inform Presenter that an item of the list has been pressed.
     *
     * @param position,adapter int: element of the list, SimpleAdapter: list adapter.
     */
    @Override
    public void onListClick(int position, SimpleAdapter adapter) {

        if (position == 0) {
            getView().onChangeColourDialog(getView());
        } else if (position == 1) {
            //app.goToEditSubjects();
        } else if (position == 2) {
            openBrowserToDonate();
        } else if (position == 3) {

            final AlertDialog alertDialog = new AlertDialog.Builder(getManagedContext()).create();
            alertDialog.setTitle("FullyAware App and xDroidInc");
            alertDialog.setMessage("We provide services and products through our service models but mainly " +
                    "Applications for Mobile. " + "\nThis application was created with much love for Application Design (Software Engineering)." +
                    "\nFor more information please visit us at github.com/xDroidInc");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "VISIT",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            openBrowserToVisit();
                            dialog.dismiss();
                        }

                    });

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.show();
        }
    }


    /**
     * Method that opens the phone browser and redirect to paypal.com.
     */
    private void openBrowserToDonate() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/co/home"));
        getManagedContext().startActivity(intent);
    }

    /**
     * Method that opens the phone browser and goes to github.com/xDroidInc.
     */
    private void openBrowserToVisit() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/xDroidInc"));
        getManagedContext().startActivity(intent);
    }

    /**
     * Method that sets the toolbarColour.
     *
     * @param newColor int: color of the app.
     */
    @Override
    public void setNewToolbarColor(int newColor) {
        this.toolbarColour = newColor;
        prefManager.setToolbarColour(newColor);
    }


    /**
     * Method that sets a boolean if toolbar colour has been changed.
     *
     * @param toolbarColorChanged boolean: indicates if toolbar colour has been changed.
     */
    @Override
    public void setToolbarColorChanged(boolean toolbarColorChanged) {
        this.toolbarColorChanged = toolbarColorChanged;

    }

    /**
     * Method that notifies the app that the toolbar colour has been changed.
     */
    @Override
    public void toolbarChanged() {
        Mediator mediator = (Mediator) getView().getApplication();
        mediator.toolbarColourChanged(this);
    }

    /**
     * Method that returns the toolbar colour.
     *
     * @return int: toolbar colour.
     */
    public int getToolbarColour() {
        return this.toolbarColour;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // To ListDoneMaster //////////////////////////////////////////////////////////////////////

    /**
     * Method that checks the toolbar visibility.
     */
    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
        checkToolbarVisibility();
    }

    /**
     * Method that sets a the toolbar visibility
     *
     * @param visible boolean: true if toolbar visible & false if not.
     */
    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // ListDoneMaster To //////////////////////////////////////////////////////////////////////

    /**
     * Returns the activity context (of the view related(.
     *
     * @return Context
     */
    @Override
    public Context getManagedContext() {
        return getView().getActivityContext();
    }

    /**
     * Destroys the actual view.
     */
    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }
    }

    /**
     * Method that returns if the toolbar is visible
     *
     * @return boolean true: if toolbar is visible & false : if not.
     */
    @Override
    public boolean isToolbarVisible() {
        return toolbarVisible;
    }


    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Method that checks if the toolbar is visible
     * If it is not visible it hides the toolbar.
     */
    private void checkToolbarVisibility() {
        Log.d(TAG, "calling checkToolbarVisibility()");
        if (isViewRunning()) {
            if (isToolbarVisible()) {
                getView().hideToolbar();
            }
        }
    }

    /**
     * Method that returns if the toolbar colour has been changed
     *
     * @return toolbarColorChanged boolean.
     */
    public boolean getToolbarColourChanged() {
        return this.toolbarColorChanged;
    }
}
