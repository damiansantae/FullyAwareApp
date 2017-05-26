package es.ulpgc.eite.clean.mvp.sample.addTask;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.NotificationService;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;
import me.everything.providers.android.calendar.CalendarProvider;

//import me.everything.providers.android.calendar.Calendar;

public class AddTaskPresenter extends GenericPresenter
    <AddTask.PresenterToView, AddTask.PresenterToModel, AddTask.ModelToPresenter, AddTaskModel>
    implements AddTask.ViewToPresenter, AddTask.ModelToPresenter, AddTask.AddTaskTo, AddTask.ToAddTask {


  private boolean toolbarVisible;
  private boolean buttonClicked;
  private boolean textVisible;
  private DatabaseFacade database;

  /**
   * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
   * Responsible to initialize MODEL.
   * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
   * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onCreate(AddTask.PresenterToView view) {
    super.onCreate(AddTaskModel.class, this);
    setView(view);
    Log.d(TAG, "calling onCreate()");

    Log.d(TAG, "calling startingDummyScreen()");
    Mediator app = (Mediator) getView().getApplication();
    database = new DatabaseFacade();
    app.startingAddTaskScreen(this);
    if (app.checkToolbarChanged() == true){
      String colour = app.getToolbarColour();
      getView().toolbarChanged(colour);
    }
  }

  /**
   * Operation called by VIEW after its reconstruction.
   * Always call {@link GenericPresenter#setView(ContextView)}
   * to save the new instance of PresenterToView
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onResume(AddTask.PresenterToView view) {
    setView(view);
    Log.d(TAG, "calling onResume()");

    if(configurationChangeOccurred()) {

      checkToolbarVisibility();
      getView().setSubjectsSpinner();
    }

    Mediator app = (Mediator) getView().getApplication();
    if (app.checkToolbarChanged() == true){
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
  @Override
  public void onSelectDateBtnClicked() {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePicker = new DatePickerDialog(getManagedContext(), new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        getView().setDateText(dayOfMonth+"/"+monthOfYear+"/"+year);
      }
    }, year, month, day);
    datePicker.show();
  }

  @Override
  public void onSelectTimeBtnClicked() {
    final Calendar c = Calendar.getInstance();
    int hours = c.get(Calendar.HOUR_OF_DAY);
    int minutes = c.get(Calendar.MINUTE);

    TimePickerDialog timePicker = new TimePickerDialog(getManagedContext(), new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        getView().setTimeText(hourOfDay+":"+minute);
      }
    }, hours, minutes, false);
    timePicker.show();
  }

  @Override
  public void onAddTaskBtnClicked() {
    String title = getTitle();
    String description = getDescription();
    String subjectName = getSubject();
    Subject subject = database.getSubject(subjectName);
    String time = getTime();
    String date = getDate();
    String deadline = getDeadLine(time,date);
    database.addTask(subject, title, description, deadline, "ToDo");
    addEvent(title, subjectName, deadline);
      Navigator app = (Navigator)getView().getApplication();
      app.goToListToDoScreen(this);
      Context context = getApplicationContext();
      CharSequence text = "Task added";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();

    //NotificationService.notification(title, deadline, getManagedContext());
      NotificationService.setNotificationAlarm(getTitle(), getDate(), getTime(), getManagedContext());
      destroyView();

  }
    public void writeTaskIntoCalendar(String title,String description){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        Navigator app = (Navigator)getView().getApplication();
        app.startActivy(intent);

    }


  public Cursor getUserCalendar() {
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
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

    Cursor cursor = getApplicationContext().getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
            EVENT_PROJECTION,
            null, null, null);
    return cursor;
  }

  public void addEvent(String title, String subject, String deadline) {
    String day = deadline.substring(0, 2);
    int intDay = Integer.parseInt(day);

    String month = deadline.substring(3, 5);
    int intMonth = Integer.parseInt(month)-1;

    String year = deadline.substring(6, 10);
    int intYear = Integer.parseInt(year)-1;

    String hour = deadline.substring(13, 15);
    int intHour = Integer.parseInt(hour);

    int intEndHour = Integer.parseInt(hour)+1;

    String minutes = deadline.substring(16);
    int intMinutes = Integer.parseInt(minutes);

    TimeZone timeZone = TimeZone.getDefault();
    String stringTimeZone = timeZone.getID();

    Date dtStart = new Date(intYear, intMonth, intDay, intHour, intMinutes);

    Date dtEnd = new Date(intYear, intMonth, intDay, intEndHour, intMinutes);

    Cursor cursor = getUserCalendar();
    cursor.moveToFirst();

    long id = prueba().get(0).id;


    ContentResolver cr = getApplicationContext().getContentResolver();

    //int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars._ID)));

    ContentValues values = new ContentValues();
    values.put(CalendarContract.Events.DTSTART, dtStart.getTime());
    values.put(CalendarContract.Events.DTEND, dtEnd.getTime());
    values.put(CalendarContract.Events.TITLE, title);
    values.put(CalendarContract.Events.DESCRIPTION, subject + " - " + deadline);
    values.put(CalendarContract.Events.CALENDAR_ID, id);
    values.put(CalendarContract.Events.EVENT_TIMEZONE, stringTimeZone.toString());
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
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

    private List<me.everything.providers.android.calendar.Calendar> prueba(){
        CalendarProvider provider = new CalendarProvider(getApplicationContext());
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




    private String getDeadLine(String time, String date) {
        return date +" - "+ time;
    }

    private String getTime() {
      String result = getView().getTime();
      if(result.indexOf(":") < 2){
        result = "0" + result;
      }
      if(result.length() < 5){
        result = result.substring(0,2) + ":0" + result.substring(3);
      }
      return result;
  }

  private String getDate() {
    String monthFinal = "";
    String result = getView().getDate();;
    if(result.indexOf("/") < 2){
      result = "0" + result;
    }
    if(result.indexOf("/", 3) < 5){
      result = result.substring(0,2) + "/0" + result.substring(3);
    }
    String month = result.substring(3,5);
    int monthInt = Integer.parseInt(month);
    int monthIntFinal = monthInt + 1;
    if(monthIntFinal < 10){
      monthFinal = "0" + String.valueOf(monthIntFinal);
    }else{
      monthFinal = String.valueOf(monthIntFinal);
    }

    result = result.substring(0,3) + monthFinal + result.substring(5);
        return result;
    }

    private String getSubject() {
        return getView().getTaskSubject();
    }

    private String getDescription() {
        return getView().getDescription();
    }

    private String getTitle() {
      return getView().getTaskTitle();
    }

    ///////////////////////////////////////////////////////////////////////////////////
  // To ListSubject //////////////////////////////////////////////////////////////////////

  @Override
  public void onScreenStarted() {
    Log.d(TAG, "calling onScreenStarted()");

          if(isViewRunning()) {
      getView().setSubjectsSpinner();
    }

    checkToolbarVisibility();

  }



  @Override
  public void setToolbarVisibility(boolean visible) {
    toolbarVisible = visible;
  }

  @Override
  public void setTextVisibility(boolean visible) {
    textVisible = visible;
  }

  @Override
  public void setAddBtnVisibility(boolean addBtnVisibility) {

  }

  @Override
  public void setDeleteBtnVisibility(boolean deleteBtnVisibility) {

  }


  ///////////////////////////////////////////////////////////////////////////////////
  // ListSubject To //////////////////////////////////////////////////////////////////////


  @Override
  public Context getManagedContext(){
    return getActivityContext();
  }

  @Override
  public void destroyView(){
    if(isViewRunning()) {
      getView().finishScreen();
    }
  }
  @Override
  public boolean isToolbarVisible() {
    return toolbarVisible;
  }

  @Override
  public boolean isTextVisible() {
    return textVisible;
  }


  ///////////////////////////////////////////////////////////////////////////////////

  private void checkToolbarVisibility(){
    Log.d(TAG, "calling checkToolbarVisibility()");
    if(isViewRunning()) {
      if (!toolbarVisible) {
        getView().hideToolbar();
      }
    }
  }

  @Override
  public void onLoadItemsTaskStarted() {
    checkToolbarVisibility();

  }

  /*@Override
  public ArrayList<String> getSubjectsNamesFromDatabase() {
    ArrayList<String> subjectsNames = new ArrayList<>();
    List<Subject> subjects = database.getSubjectsFromDatabase();
    for(int i=0; i<subjects.size(); i++){
      subjectsNames.add(subjects.get(i).getName());
    }
    return subjectsNames;
  }*/

@Override
  public List<Subject> getSubjects(){
List<Subject>list =database.getSubjectsFromDatabase();
  return list;
}



}
