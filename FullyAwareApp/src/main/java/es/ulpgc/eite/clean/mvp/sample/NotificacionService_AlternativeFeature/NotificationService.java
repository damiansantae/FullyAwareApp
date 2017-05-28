package es.ulpgc.eite.clean.mvp.sample.NotificacionService_AlternativeFeature;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;

public class NotificationService extends Service {
    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    //iniciamos el servicio y lo destruimos para que no se quede guardado en RAM
        stopSelf();
        return START_NOT_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
       /* //Se lanza la notificación 5 segundos despues de iniciar la app
        scheduleNotification(getNotification("Notificación automática","Se genera notificación cada 10 segundos"));
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.set(
                alarm.RTC_WAKEUP,
                System.currentTimeMillis() + (1000 * 10),
                PendingIntent.getService(this, 10000, new Intent(this, NotificationService.class), 0)
        );*/
    }

    //Notificacion
    /*private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);
    }

    private Notification getNotification(String title, String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);

        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.logofully);

        return builder.build();
    }*/

    public static void notification(String title, String content, Context context){
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.logofully);

        Intent resultIntent = new Intent(context, ListToDoViewMaster.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ListToDoViewMaster.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, builder.build());
    }



    public static void setNotificationAlarm(String title, String date, String time, Context context){

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.putExtra("Title", title);
        intent.putExtra("Deadline", date + " - " + time);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        long prueba = System.currentTimeMillis();
        /*c.set(Calendar.YEAR, Integer.parseInt(date.substring(6)));
        c.set(Calendar.MONTH, Integer.parseInt(date.substring(3,5)));
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(0,2))-1);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
        c.set(Calendar.MINUTE, Integer.parseInt(time.substring(3)));*/
        c.set(Integer.parseInt(date.substring(6)), Integer.parseInt(date.substring(3,5))-1, Integer.parseInt(date.substring(0,2)), Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(3)));
        long result = c.getTimeInMillis();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Log.d("Tag", String.valueOf(c.getTimeInMillis()));
        alarmMgr.set(AlarmManager.RTC_WAKEUP, result, alarmIntent);
    }
}
