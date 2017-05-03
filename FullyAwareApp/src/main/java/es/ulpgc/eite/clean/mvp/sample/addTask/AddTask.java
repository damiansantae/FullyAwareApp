package es.ulpgc.eite.clean.mvp.sample.addTask;

import android.content.Context;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;

/**
 * Created by Luis on 12/11/16.
 */

public interface AddTask {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToAddTask {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);
    void setAddBtnVisibility(boolean addBtnVisibility);
    void setDeleteBtnVisibility(boolean deleteBtnVisibility);
  }

  interface AddTaskTo {
    Context getManagedContext();
    void destroyView();
    boolean isToolbarVisible();
    boolean isTextVisible();
  }

  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {
    void onSelectDateBtnClicked();
    void onSelectTimeBtnClicked();
    void onAddTaskBtnClicked();

    void onLoadItemsTaskStarted();

      //ArrayList<String> getSubjectsNamesFromDatabase();
      List<Subject> getSubjects();
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();
    void setDateText(String txt);

    void setTimeText(String txt);


    String getDescription();

    String getDate();

    String getTime();

    String getTaskTitle();

    String getTaskSubject();

    void setSubjectsSpinner();

    void toolbarChanged(String colour);

    void onDestroy();
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {


    void addTask(Task Task);

  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

  }

}
