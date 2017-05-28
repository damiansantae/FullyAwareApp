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
     * It returns the value stored on SharedPreferences -> IS_FIRST_TIME_LAUNCH
     */
        public boolean isFirstTimeLaunch() {
            return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        }



        public void setUserName(String userName) {
            editor.putString(USER_NAME,userName);
            editor.commit();
        }

        public String getUserName(){
            return pref.getString(USER_NAME, "User Name");
        }

        public void setHourFrames(int counterSubject) {
            editor.putInt(COUNTER_SUBJECT, counterSubject);
            editor.commit();
    }

    public int getCounterSubject() {
            return pref.getInt(COUNTER_SUBJECT, 0);
    }
}

