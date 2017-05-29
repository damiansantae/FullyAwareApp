package es.ulpgc.eite.clean.mvp.sample.notificationService_AlternativeFeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Executed when the app is launched
        //context.startService(new Intent(context,NotificationService.class));

    }
}
