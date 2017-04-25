package es.ulpgc.eite.clean.mvp.sample.preferences;




import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;


import android.util.Log;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.TaskRepository;

public class PreferencesPresenter extends GenericPresenter
    <Preferences.PresenterToView, Preferences.PresenterToModel, Preferences.ModelToPresenter, PreferencesModel>
    implements Preferences.ViewToPresenter, Preferences.ModelToPresenter, Preferences.PreferencesTo, Preferences.ToPreferences {


  private boolean toolbarVisible;
  private boolean buttonClicked;
  private boolean textVisible;

 private boolean toolbarColorChanged;
  private int toolbarColour;
    List<String> colorPrimaryList;
    List<String> colorPrimaryDarkList;
    SharedPreferences preferences;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";


  /**
   * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
   * Responsible to initialize MODEL.
   * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
   * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onCreate(Preferences.PresenterToView view) {
    super.onCreate(PreferencesModel.class, this);
    setView(view);
    Log.d(TAG, "calling onCreate()");

    Log.d(TAG, "calling startingDummyScreen()");
    Mediator app = (Mediator) getView().getApplication();
   app.startingPreferencesScreen(this);
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
  public void onResume(Preferences.PresenterToView view) {
    setView(view);
    Log.d(TAG, "calling onResume()");

    if(configurationChangeOccurred()) {

      checkToolbarVisibility();
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
    String subject = getSubject();
    String time = getTime();
    String date = getDate();
    String deadline = getDeadLine(time,date);
    TaskRepository.getInstance().saveTask(new Task(title,description,deadline, "ToDo"));
    Navigator app = (Navigator)getView().getApplication();
    //app.goToListToDoScreen(this);
    Context context = getApplicationContext();
    CharSequence text = "Task added";
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();

  }

  @Override
  public void onListClick(int position, SimpleAdapter adapter) {

    Object item = adapter.getItem(position);
    Object selectedItem = adapter.getItem(position);
      Navigator app = (Navigator) getView().getApplication();
      if (position==0){
        getView().onChangeColourDialog(getView());

      } else if (position ==1){
         //app.goToEditSubjects();
      } else if (position==2){
          openBrowserToDonate();
      } else if (position == 3){

          final AlertDialog alertDialog = new AlertDialog.Builder(getView().getActivityContext()).create();
          alertDialog.setTitle("FullyAware App and xDroidInc");
          alertDialog.setMessage("We provide services and products through our service models but mainly " +
                  "Applications for Mobile. " + "\nThis application was created with much love for Application Design (Software Engineering)." +
                  "\nFor more information please visit us at github.com/xDroidInc");
          alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "VISIT",
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          openBrowserToVisit();
                          dialog.dismiss();
                      }

                  });

          alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                      }
                  });

          alertDialog.show();


      }

        Mediator mediator =(Mediator) getView().getApplication();
        //app.goToDetailScreen(this);




    }

    private void openBrowserToDonate() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/co/home"));
        getView().getActivityContext().startActivity(intent);
    }

    private void openBrowserToVisit() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/xDroidInc"));
        getView().getActivityContext().startActivity(intent);
    }


    @Override
  public void setNewToolbarColor(int newColor) {
    this.toolbarColour = newColor;
  }

  @Override
  public void setToolbarColorChanged(boolean toolbarColorChanged) {
    this.toolbarColorChanged = toolbarColorChanged;

  }

  @Override
  public void toolbarChanged() {
    Mediator mediator =(Mediator) getView().getApplication();
    mediator.toolbarColourChanged(this);
  }


  public int getToolbarColour(){
    return this.toolbarColour;
  }


  private String getDeadLine(String time, String date) {
        return time +" - "+ date;
    }

    private String getTime() {
    return getView().getTime();
  }

  private String getDate() {
        return getView().getDate();
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
  // To ListDoneMaster //////////////////////////////////////////////////////////////////////

  @Override
  public void onScreenStarted() {
    Log.d(TAG, "calling onScreenStarted()");
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
  // ListDoneMaster To //////////////////////////////////////////////////////////////////////


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


  public boolean getToolbarColourChanged() {
    return this.toolbarColorChanged;
  }
}
