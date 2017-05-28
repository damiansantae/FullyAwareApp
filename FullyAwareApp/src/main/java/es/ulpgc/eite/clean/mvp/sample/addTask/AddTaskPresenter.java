package es.ulpgc.eite.clean.mvp.sample.addTask;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;
import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;


public class AddTaskPresenter extends GenericPresenter
    <AddTask.PresenterToView, AddTask.PresenterToModel, AddTask.ModelToPresenter, AddTaskModel>
    implements AddTask.ViewToPresenter, AddTask.ModelToPresenter, AddTask.AddTaskTo, AddTask.ToAddTask {


  private boolean toolbarVisible;
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

    database = DatabaseFacade.getInstance();

    Mediator app = (Mediator) getView().getApplication();
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
  // ///////////////////////View To Presenter ///////////////////////////////////////

                //////////////////Listeners///////////////////////


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
    //  Values gotten from the view
    String title = getTitle();
    String description = getDescription();
    String subjectName = getSubject();
    Subject subject = database.getSubject(subjectName);
    String time = getTime();
    String date = getDate();
    String deadline = getDeadLine(time,date);

    //  Task added to the database
    database.addTask(subject, title, description, deadline, "ToDo");

    //getModel().addEvent(title, subjectName, deadline, getApplicationContext());

    //  The Intent to start the app Calendar is obtained, and then is used by startActivity()
    Intent intent = getModel().writeTaskIntoCalendar(title, description, deadline, subjectName);
    Navigator app = (Navigator) getView().getApplication();
    app.startActivy(intent);

    Context context = getApplicationContext();
    CharSequence text = "Task added";
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();

    //NotificationService.notification(title, deadline, getManagedContext());
    //NotificationService.setNotificationAlarm(getTitle(), getDate(), getTime(), getManagedContext());

    //  The activity is destroyed
    destroyView();

  }

                //////////////////Methods to get View parameters///////////////////////

    /**
     * Method that joins the time and date in a String
     * @param time time selected in the timePicker
     * @param date date selected in the datePicker
     * @return a String formed by the date and time, with a "-" between them
     */
    private String getDeadLine(String time, String date) {
        return date +" - "+ time;
    }


    /**
     * Method to get the time from the TimePicker and transform it into a time String with an accurate format
     * @return a String with the time selected
     */
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


    /**
     * Method to get the date from the DatePicker and transform it into a date String with an accurate format
     * @return a String with the date selected
     */
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


    /**
     * Method to get the subjectName from the Spinner and return it
     * @return a String with the Subject name
     */
    private String getSubject() {
        return getView().getTaskSubject();
    }


    /**
     * Method to get the description from the corresponding text view and return it
     * @return a String with the description of the task
     */
    private String getDescription() {
        return getView().getDescription();
    }


    /**
     * Method to get the title from the corresponding text view and return it
     * @return a String with the task's title
     */
    private String getTitle() {
      return getView().getTaskTitle();
    }



    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////


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


    /**
     * Method to show or hide the toolbar depending on the value of the attribute toolbarVisible
     */
    private void checkToolbarVisibility(){
    Log.d(TAG, "calling checkToolbarVisibility()");
    if(isViewRunning()) {
      if (!toolbarVisible) {
        getView().hideToolbar();
      }
    }
  }


@Override
  public List<Subject> getSubjects(){
    List<Subject>list = database.getSubjectsFromDatabase();
    return list;
}



}
