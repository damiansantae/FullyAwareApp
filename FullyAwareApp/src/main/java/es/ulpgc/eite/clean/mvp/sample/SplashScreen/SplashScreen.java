package es.ulpgc.eite.clean.mvp.sample.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.welcome.WelcomeActivity;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;

    /**
     * Method that creates the splash screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
               // Intent i = new Intent(SplashScreen.this, ListToDoViewMaster.class);
                 Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
