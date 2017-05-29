package es.ulpgc.eite.clean.mvp.sample.preferences;

import es.ulpgc.eite.clean.mvp.GenericModel;


public class PreferencesModel extends GenericModel<Preferences.ModelToPresenter>
        implements Preferences.PresenterToModel {

    private String dialogInfoTitle;
    private CharSequence dialogInfoDescription;
    private String visitButtonLabel, okButtonLabel, paypalURL, gitHubURL, noCompatibleSystemLabel;

    /**
     * Method that recovers a reference to the PRESENTER
     * You must ALWAYS call {@link super#onCreate(Object)} here
     *
     * @param presenter Presenter interface
     */
    @Override
    public void onCreate(Preferences.ModelToPresenter presenter) {
        super.onCreate(presenter);
        dialogInfoTitle = "FullyAware App and xDroidInc";
        dialogInfoDescription = "We provide services and products through our service models but mainly " +
                "Applications for Mobile. " + "\nThis application was created with much love for Application Design (Software Engineering)." +
                "\nFor more information please visit us at github.com/xDroidInc";
        visitButtonLabel = "VISIT";
        okButtonLabel = "OK";
        paypalURL = "https://www.paypal.com/co/home";
        gitHubURL = "http://www.github.com/xDroidInc";
        noCompatibleSystemLabel = "Sistema Operativo no compatible";
    }

    /**
     * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
     * Usefull for kill/stop activities that could be running on the background Threads
     *
     * @param isChangingConfiguration Informs that a change is occurring on the configuration
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Presenter To Model ////////////////////////////////////////////////////////////

    /**
     * Returns dialogInfo title label.
     *
     * @return String title label.
     */
    @Override
    public String getDialogInfoTitle() {
        return dialogInfoTitle;
    }

    /**
     * Returns dialogInfoDescription char sequence.
     *
     * @return CharSequence description of info dialog.
     */
    @Override
    public CharSequence getDialogInfoDescription() {
        return dialogInfoDescription;
    }

    /**
     * Returns visit button label.
     *
     * @return String visit button label.
     */
    @Override
    public String getVisitButtonLabel() {
        return visitButtonLabel;
    }

    /**
     * Returns ok button label
     *
     * @return String ok button label.
     */
    @Override
    public String getOkButtonLabel() {
        return okButtonLabel;
    }

    /**
     * Returns a String that contains an URL (paypal).
     *
     * @return String paypal URL.
     */
    @Override
    public String getPaypalUrl() {
        return paypalURL;
    }

    /**
     * Returns a String that contains an URL (github).
     *
     * @return String github URL.
     */
    @Override
    public String getGitHubUrl() {
        return gitHubURL;
    }

    /**
     * Returns a label that indicates a not supported system error.
     *
     * @return String not supported system label.
     */
    @Override
    public String getNotSupportedSystem() {
        return noCompatibleSystemLabel;
    }


}