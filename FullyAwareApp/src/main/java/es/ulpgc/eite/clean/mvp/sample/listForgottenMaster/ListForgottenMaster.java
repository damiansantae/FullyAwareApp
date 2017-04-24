package es.ulpgc.eite.clean.mvp.sample.listForgottenMaster;

import android.content.Context;
import android.view.View;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public interface ListForgottenMaster {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToListForgotten {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);
    void setDeleteBtnVisibility(boolean deleteBtnVisibility);

  }

  interface ListForgottenTo {
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
    Task getSelectedTask();
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


    void onBinBtnClick(Task_Adapter adapter);



    void onSwipeMade(int pos, Task_Adapter adapter);

    void onListClick2(View item, int position, ListForgottenViewMaster.TaskRecyclerViewAdapter adapter, Task task);

    void onLongListClick2(View item, int adapterPosition);


    boolean isSelected(int adapterPosition);

    void onBinBtnClick2(ListForgottenViewMaster.TaskRecyclerViewAdapter adapter);
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

    void toolbarChanged(String colour);
    void setRecyclerAdapterContent(List<Task> items);


  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void deleteItem(Task item);
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

    void onLoadItemsTaskFinished(List<Task> itemsFromDatabase);

    void onErrorDeletingItem(Task item);
  }
}
