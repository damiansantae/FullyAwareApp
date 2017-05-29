package es.ulpgc.eite.clean.mvp.sample.addTask;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;


public interface AddTask {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToAddTask {

      /**
       * Method to init the screen, checking the attributes and acting in consequence
       */
    void onScreenStarted();

      /**
       * Method to set the value of the attribute toolbarVisible
       * @param visible boolean indicating if the toolbar is visible or not.
       *                The toolbar is visible = true
       *                The toolbar is not visible = false
       */
    void setToolbarVisibility(boolean visible);

      /**
       * Method to set the value of the attribute textVisible
       * @param visible boolean indicating if the text is visible or not.
       *                The text is visible = true
       *                The text is not visible = false
       */
    void setTextVisibility(boolean visible);

  }

  interface AddTaskTo {

      /**
       * Method to get the activity's context
       * @return the Activity's context
       */
    Context getManagedContext();

      /**
       * Method to destroy the actual Activity
       */
    void destroyView();

      /**
       * Method to get the value of the attribute toolbarVisible
       * @return boolean indicating the toolbar visibility.
       *            The toolbar is visible = true
       *            The toolbar is not visible = false
       */
    boolean isToolbarVisible();

      /**
       * Method to get the value of the attribute textVisible
       * @return boolean indicating the text visibility.
       *            The text is visible = true
       *            The text is not visible = false
       */
    boolean isTextVisible();
  }

  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {

    /**
    * Listener of the DatePicker
    */
    void onSelectDateBtnClicked();

    /**
    * Listener of the TimePicker
    */
    void onSelectTimeBtnClicked();

    /**
    * Listener of the Add Button
    */
    void onAddTaskBtnClicked();

    /**
    * Method to get all the subjects from the database
    * @return a List with all the Subjects
    */
    List<Subject> getSubjects();

    /**
     * Method that calls the method with the same name in  the Model. It is used to create an Intent to write a task into the Calendar
     * @param title title of the task
     * @param description description of the task
     * @param deadline date and time of the task
     * @param subjectName name of the subject related to the task
     * @return an Intent to open the Calendar app and fill it with that information
     */
    Intent writeTaskIntoCalendar(String title, String description, String deadline, String subjectName);

    /**
     * Method that calls the DatabaseFacade with the same name, to add a task to the database
     * @param subject Subject related to the task
     * @param title title of the task
     * @param description description of the task
     * @param deadline date and time when the task happens
     * @param status status of the task ("ToDo" or "Done")
     */
    void addTask(Subject subject, String title, String description, String deadline, String status);
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {

      /**
       * Method to destroy the actual Activity
        */
    void finishScreen();

      /**
       * Method to hide the toolbar
        */
    void hideToolbar();

      /**
       * Method to set the text of the TextView "Date"
       * @param txt String to be shown in the TextView
       */
    void setDateText(String txt);

      /**
       * Method to set the text of the TextView "Time"
       * @param txt String to be shown in the TextView
       */
    void setTimeText(String txt);

      /**
       * Method to get the value of the EditText "Description"
       * @return String of the value entered in the corresponding EditText
       */
    String getDescription();

      /**
       * Method to get the value of the EditText "Date"
       * @return String of the value entered in the corresponding EditText
       */
    String getDate();

      /**
       * Method to get the value of the EditText "Time"
       * @return String of the value entered in the corresponding EditText
       */
    String getTime();

      /**
       * Method to get the value of the EditText "Title"
       * @return String of the value entered in the corresponding EditText
       */
    String getTaskTitle();

      /**
       * Method to get the value of the EditText "Subject"
       * @return String of the value entered in the corresponding EditText
       */
    String getTaskSubject();

      /**
       * Method to fill the spinner's options with the names of the subjects
       */
    void setSubjectsSpinner();

    /**
     * Method to change the colour of the toolbar and status bar
     * @param colour String of the colour to be applied to the toolbar and to the status bar
     */
    void toolbarChanged(String colour);

    /**
     * Method to destroy the actual Activity
     */
    void onDestroy();

    /**
     * Method to create a Dialog, select its configuration and define the listeners of its two options
     * @param title title of the task
     * @param description description of the task
     * @param deadline date and time when the task takes place
     * @param subjectName name of the subject related to the task
     */
    void initDialog(String title, String description, String deadline, String subjectName);

    /**
     * Method to set the title of the Dialog
     * @param title String of the title
     */
    void setDialogTitle(String title);

    /**
     * Method to make the Dialog appear
     */
    void showDialog();

  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {

    /**
     * Method used to open the default user's calendar app to create an event with some parameters already written
     * @param title title of the event
     * @param description description of the event
     * @param deadline date and time the event takes place
     * @param subjectName subject related to the event
     * @return Intent with this extra information
     */
    Intent writeTaskIntoCalendar(String title, String description, String deadline, String subjectName);
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

  }

}
