package es.ulpgc.eite.clean.mvp.sample.addTask;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.listToDo.Task;
import es.ulpgc.eite.clean.mvp.sample.listToDo.TaskRepository;

public class AddTaskPresenter extends GenericPresenter
    <AddTask.PresenterToView, AddTask.PresenterToModel, AddTask.ModelToPresenter, AddTaskModel>
    implements AddTask.ViewToPresenter, AddTask.ModelToPresenter, AddTask.AddTaskTo, AddTask.ToAddTask {


  private boolean toolbarVisible;
  private boolean buttonClicked;
  private boolean textVisible;

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
    app.startingAddTaskScreen(this);
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
      String dateTime = getDeadLine();

    TaskRepository.getInstance().saveTask(new Task(R.drawable.bg_controll_plane,title,description,dateTime));

  }

    private String getDeadLine() {
        return null;
    }

    private String getSubject() {
        return null;
    }

    private String getDescription() {
        return null;
    }

    private String getTitle() {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////
  // To ListDone //////////////////////////////////////////////////////////////////////

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
  // ListDone To //////////////////////////////////////////////////////////////////////


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




}
