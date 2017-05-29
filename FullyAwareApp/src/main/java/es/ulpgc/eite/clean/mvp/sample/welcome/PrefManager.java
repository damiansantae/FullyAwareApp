package es.ulpgc.eite.clean.mvp.sample.welcome;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager {

        SharedPreferences pref;
        SharedPreferences.Editor editor;
        Context _context;
        int PRIVATE_MODE = 0;
        private static final String APP_PREF = "androidhive-welcome";
        private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
        private static final String USER_NAME = "userName";
        private static final String COUNTER_SUBJECT = "counterSubject";
        private static final String TOOLBAR_COLOUR = "toolbar-coulour";
        private static final String TOOLBAR_COLOUR_CHANGED = "toolbar-coulour-changed";


    /**
     * Public constructor of PrefManager Class
     * It receives the actual context.
     *
     * @param context Context context
     */
        public PrefManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(APP_PREF, PRIVATE_MODE);
            editor = pref.edit();
        }

    /**
     * Method that sets value of isFirstTime boolean
     * isFirstTime boolean specifies if the app is being launched
     * for the first time. It is stored on a SharedPreferences object
     * @param isFirstTime boolean
     */
        public void setFirstTimeLaunch(boolean isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
            editor.commit();
        }

    /**
     * Method that returns isFirstTime boolean
     * isFirstTime boolean specifies if the app is being launched for the first time
     * @return IS_FIRST_TIME_LAUNCH -> value stored on SharedPreferences.
     */
        public boolean isFirstTimeLaunch() {
            return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        }

    /**
     * Method that sets userName value. String userName is stored on SharedPreferences.
     * userName is the name of the person who is using the app.
     *  @param userName String
     */
        public void setUserName(String userName) {
            editor.putString(USER_NAME,userName);
            editor.commit();
        }

    /**
     * Method that returns the userName value. String userName is stored on SharedPreferences.
     * userName is the name of the person who is using the app.
     *  @return USER_NAME -> value stored on SharedPreferences.
     */
        public String getUserName(){
            return pref.getString(USER_NAME, "User Name");
        }

    /**
     * Method that sets toolbar colour of the app. String colour is stored on SharedPreferences.
     *  @param colour String
     */
        public void setToolbarColour(int colour){
            editor.putInt(TOOLBAR_COLOUR, colour);
            editor.commit();
        }

    /**
     * Method that returns the toolbar colour value.
     * It returns the toolbar colour to apply changes in other activities.
     *  @return TOOLBAR_COLOUR -> value stored on SharedPreferences.
     */
    public int getToolbarColour(){
        return pref.getInt(TOOLBAR_COLOUR, 0);
    }

    public void setToolbarColourChanged(boolean toolbarColourChanged) {
        editor.putBoolean(TOOLBAR_COLOUR_CHANGED, toolbarColourChanged);
        editor.commit();
    }

    public boolean getToolbarColourChanged(){
        return pref.getBoolean(TOOLBAR_COLOUR_CHANGED, false);
    }
}

