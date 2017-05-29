package es.ulpgc.eite.clean.mvp.sample.addTask;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import es.ulpgc.eite.clean.mvp.GenericModel;
import me.everything.providers.android.calendar.CalendarProvider;


public class AddTaskModel extends GenericModel<AddTask.ModelToPresenter>
    implements AddTask.PresenterToModel {


  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(AddTask.ModelToPresenter presenter) {
    super.onCreate(presenter);
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


  @Override
  public Intent writeTaskIntoCalendar(String title,String description, String deadline, String subject){

    //  The string "deadline" is subdivided in parts following the format used, and each part parsed into Integers
    String day = deadline.substring(0, 2);
    int intDay = Integer.parseInt(day);

    String month = deadline.substring(3, 5);
    int intMonth = Integer.parseInt(month)-1;

    String year = deadline.substring(6, 10);
    int intYear = Integer.parseInt(year);

    String hour = deadline.substring(13, 15);
    int intHour = Integer.parseInt(hour);

    int intEndHour = Integer.parseInt(hour)+1;

    String minutes = deadline.substring(16);
    int intMinutes = Integer.parseInt(minutes);

    //  The time is defined using Calendar
    Calendar beginTime = Calendar.getInstance();
    beginTime.set(intYear, intMonth, intDay, intHour, intMinutes);
    Calendar endTime = Calendar.getInstance();
    endTime.set(intYear, intMonth, intDay, intEndHour, intMinutes);

    TimeZone timeZone = TimeZone.getDefault();
    String stringTimeZone = timeZone.getID();

    // An intent is created and Extra Information is added to it using the parameters of the method
    Intent intent = new Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.DESCRIPTION, subject + " - " + description);

    return intent;

  }

    /**
     * (NextUpgrade) Method to get user's calendars
      * @param context application context
     * @return a cursor with the user's calendars and some atributtes of them
     */
  public Cursor getUserCalendar(Context context) {
    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.

      return null;

    }
    final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

// The indices for the projection array above.
    final int PROJECTION_ID_INDEX = 0;
    final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    Cursor cursor = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
            EVENT_PROJECTION,
            null, null, null);
    return cursor;
  }

    /**
     * Method to add an event with some to the user's default calendar directly
     * @param title title of the event
     * @param subject subject related to the event
     * @param deadline date and time the event takes place
     * @param context application context
     */
  public void addEvent(String title, String subject, String deadline, Context context) {
    String day = deadline.substring(0, 2);
    int intDay = Integer.parseInt(day);

    String month = deadline.substring(3, 5);
    int intMonth = Integer.parseInt(month)-1;

    String year = deadline.substring(6, 10);
    int intYear = Integer.parseInt(year)-1900;

    String hour = deadline.substring(13, 15);
    int intHour = Integer.parseInt(hour);

    int intEndHour = Integer.parseInt(hour)+1;

    String minutes = deadline.substring(16);
    int intMinutes = Integer.parseInt(minutes);

    TimeZone timeZone = TimeZone.getDefault();
    String stringTimeZone = timeZone.getID();

    Date dtStart = new Date(intYear, intMonth, intDay, intHour, intMinutes);

    Date dtEnd = new Date(intYear, intMonth, intDay, intEndHour, intMinutes);

    Cursor cursor = getUserCalendar(context);
    cursor.moveToFirst();

    long id = getCalendars(context).get(0).id;


    ContentResolver cr = context.getContentResolver();

    //int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars._ID)));

    ContentValues values = new ContentValues();
    values.put(CalendarContract.Events.DTSTART, dtStart.getTime());
    values.put(CalendarContract.Events.DTEND, dtEnd.getTime());
    values.put(CalendarContract.Events.TITLE, title);
    values.put(CalendarContract.Events.DESCRIPTION, subject + " - " + deadline);
    values.put(CalendarContract.Events.CALENDAR_ID, id);
    values.put(CalendarContract.Events.EVENT_TIMEZONE, stringTimeZone.toString());
    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.

      return;
    }
    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

    String stringEventID = uri.getLastPathSegment();
    long eventID = Long.parseLong(stringEventID);


    /*ContentResolver cr2 = getApplicationContext().getContentResolver();
    ContentValues values2 = new ContentValues();
    values2.put(CalendarContract.Reminders.MINUTES, 1);
    values2.put(CalendarContract.Reminders.EVENT_ID, eventID);
    values2.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_DEFAULT);
    cr2.insert(CalendarContract.Reminders.CONTENT_URI, values);*/

  }

    /**
     * Method to get user's calendars
     * @param context application context
     * @return a List with all the user's calendars
     */
  private List<me.everything.providers.android.calendar.Calendar> getCalendars(Context context){
    CalendarProvider provider = new CalendarProvider(context);
        /*String name = provider.getCalendar(1).name.toString();
        String yoquese = provider.getCalendar(1).accountName.toString();
        String shjk = provider.getCalendar(1).ownerAccount.toString();
        int snjs = provider.getCalendar(1).calendarAccessLevel;
        int bhjb = provider.getCalendar(1).calendarColor;
      String dhj = provider.getCalendar(1).displayName.toString();
      String hdbhjb = provider.getCalendar(1).allowedReminders.toString();
      String sghj = provider.getCalendar(1).calendarTimeZone.toString();
      long jhjk = provider.getCalendar(1).id;*/


    List<me.everything.providers.android.calendar.Calendar> calendars = provider.getCalendars().getList();

    return  calendars;
  }


}
