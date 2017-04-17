package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import android.content.Context;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.TaskDone;


public interface ListDoneMaster {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToListDone {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);

    void setDeleteBtnVisibility(boolean deleteBtnVisibility);

  }

  interface ListDoneTo {
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
    TaskDone getSelectedTaskDone();
    boolean getToolbarVisibility();

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

    void onListClick(int position, Task_Adapter adapter);

    void onLongListClick(int pos, Task_Adapter adapter);

    void onBinBtnClick(Task_Adapter adapter);
    void onListClick2(TaskDone item, ListDoneViewMasterTesting.TaskRecyclerViewAdapter adapter);

    void onLongListClick2(TaskDone item);



    /*
          @Override
          public void onLoadItemsTaskFinished(List<TaskDone> items) {
              getView().setRecyclerAdapterContent(items);
      
          }*/
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();

      void hideDeleteBtn();

      void showDeleteBtn();

    boolean isItemListChecked(int pos);

    void setItemChecked(int pos, boolean checked);

    void startSelection();

    void setChoiceMode(int i);
    

    void deselect(int i, boolean b);
    void setRecyclerAdapterContent(List<TaskDone> items);
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void deleteItem(TaskDone item);
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

    void onLoadItemsTaskStarted();

    void onLoadItemsTaskFinished(List<TaskDone> itemsFromDatabase);

    void onErrorDeletingItem(TaskDone item);
  }
}
