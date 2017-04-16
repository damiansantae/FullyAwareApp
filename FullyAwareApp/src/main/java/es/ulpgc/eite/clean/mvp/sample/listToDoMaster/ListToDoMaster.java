package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.content.Context;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.TaskToDo;


public interface ListToDoMaster {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToListToDo {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);
    void setAddBtnVisibility(boolean addBtnVisibility);
    void setDeleteBtnVisibility(boolean deleteBtnVisibility);
    void setDoneBtnVisibility(boolean deleteBtnVisibility);
  }

  interface ListToDoTo {
    Context getManagedContext();
    void destroyView();
    boolean isToolbarVisible();
    boolean isTextVisible();
  }
  /**
   * Interfaz que permite iniciar la pantalla del detalle y recopilar los valores necesarios
   * para rellenar el estado inicial que se pasará a la pantalla del detalle al iniciarse
   */
   interface MasterListToDetail{
    Context getManagedContext();
    TaskToDo getSelectedTaskToDo();
    boolean getToolbarVisibility();

      String getTaskDate();
  }
  /**
   * Interfaz que permite fijar los valores incluidos en el estado pasado desde la pantalla
   * del detalle cuando está finaliza
   */
  interface DetailToMaster {
  }


  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {
//    void onButtonClicked();

    void onListClick(int position, Task_Adapter adapter);

    void onLongListClick(int pos, Task_Adapter adapter);

    void onBinBtnClick(Task_Adapter adapter);

    void onAddBtnClick(Task_Adapter adapter);

    void onDoneBtnClick(Task_Adapter adapter);

      void onSwipeMade(int pos, Task_Adapter adapter);

      void onListClick2(TaskToDo item, ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter);

    void onLongListClick2(TaskToDo item);

      void onAddBtnClick();
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();
    void hideText();
    void showText();

      void showAddBtn();

      void hideDeleteBtn();

      void showDeleteBtn();

      void setText(String txt);
    void setLabel(String txt);

    boolean isItemListChecked(int pos);

    void setItemChecked(int pos, boolean checked);

    void startSelection();

    void setChoiceMode(int i);

      void hideAddBtn();

    void hideDoneBtn();

    void showDoneBtn();

    void deselect(int i, boolean b);

      void setRecyclerAdapterContent(List<TaskToDo> items);
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void deleteItem(TaskToDo item);
    void loadItems();
    void reloadItems();
    void setDatabaseValidity(boolean valid);
    String getErrorMessage();
    void addInitialTasks();
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {
    Context getManagedContext();
    void onErrorDeletingItem(TaskToDo item);
    void onLoadItemsTaskFinished(List<TaskToDo> items);
    void onLoadItemsTaskStarted();
  }


}
