package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.content.Context;
import android.view.View;
import java.util.List;
import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.TaskRecyclerViewAdapter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public interface ListToDoMaster {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

    /**
     * Interface which provides state passing to listToDo MVP
     */
  interface ToListToDo {
        /**
         *This method is called when
         */
    void onScreenStarted();

        /**
         *
         * @param visible
         */
    void setToolbarVisibility(boolean visible);

        /**
         *
         * @param visible
         */
    void setTextVisibility(boolean visible);

        /**
         *
         * @param addBtnVisibility
         */
    void setAddBtnVisibility(boolean addBtnVisibility);

        /**
         *
         * @param deleteBtnVisibility
         */
    void setDeleteBtnVisibility(boolean deleteBtnVisibility);

        /**
         *
         * @param deleteBtnVisibility
         */
    void setDoneBtnVisibility(boolean deleteBtnVisibility);
  }

  interface ListToDoTo {
    Context getManagedContext();
    void destroyView();
    boolean isToolbarVisible();
    boolean isTextVisible();
  }
  /**
   * Interface which allows to start detail screen and recover all data needed to
   * start the initial state that it will be passed to the detail screen after started
   */
   interface MasterListToDetail{
    Context getManagedContext();
    Task getSelectedTask();
    boolean getToolbarVisibility();

  }

  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {

    void onDoneBtnClick(TaskRecyclerViewAdapter adapter);


      void onListClick(View item, int position, Task task);

    void onLongListClick(View item, int adapterPosition);

      void onAddBtnClick();

      boolean isSelected(int adapterPosition);

    void onBinBtnClick(TaskRecyclerViewAdapter adapter);

      String getCases(Task task);

    void subjectFilter();

    void swipeLeft(Task currentTask);

    void swipeRight(Task currentTask);

    boolean isTaskForgotten(String deadline);

    void onBtnBackPressed();
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();

      void showAddBtn();

      void hideDeleteBtn();

      void showDeleteBtn();

      void hideAddBtn();

    void hideDoneBtn();

    void showDoneBtn();

      void hideTextWhenIsEmpty();

    void showTextWhenIsEmpty();


  void setRecyclerAdapterContent(List<Task> items);


    void toolbarChanged(String colour);

    void setToastDelete();

    void confirmBackPressed();

    void initSwipe();

    void initDialog();

    void showToastBackConfirmation(String toastBackConfirmation);
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    String getToastBackConfirmation();

    void deleteItem(Task item);
    void loadItems();

    void startBackPressed();

    void reloadItems();
    void setDatabaseValidity(boolean valid);
    String getErrorMessage();
    void addInitialTasks();

    void deleteTestItems();

    void deleteDatabaseItem(Task item);

    String calculateCases(String subjectName);

    List<Task> orderSubjects();
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {
    Context getManagedContext();
    void onErrorDeletingItem(Task item);
    void onLoadItemsTaskFinished(List<Task> items);
    void onLoadItemsTaskStarted();

    void confirmBackPressed();

    void delayedTaskToBackStarted();
  }


}
