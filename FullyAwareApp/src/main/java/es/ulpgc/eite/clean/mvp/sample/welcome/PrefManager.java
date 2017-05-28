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

}

