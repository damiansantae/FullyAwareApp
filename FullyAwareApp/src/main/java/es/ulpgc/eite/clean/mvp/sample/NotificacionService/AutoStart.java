package es.ulpgc.eite.clean.mvp.sample.NotificacionService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Se ejecuta al iniciar la app
        //context.startService(new Intent(context,NotificationService.class));

    }
}
