package es.ulpgc.eite.clean.mvp.sample.listForgottenMaster;

import android.content.Context;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.TaskForgotten;


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
    TaskForgotten getSelectedTaskForgotten();
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



    /*
          @Override
          public void onLoadItemsTaskFinished(List<TaskForgotten> items) {
              getView().setRecyclerAdapterContent(items);
      
          }*/
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();
    void hideText();
    void showText();


      void hideDeleteBtn();

      void showDeleteBtn();

      void setText(String txt);
    void setLabel(String txt);

    boolean isItemListChecked(int pos);

    void setItemChecked(int pos, boolean checked);

    void startSelection();

    void setChoiceMode(int i);
    

    void deselect(int i, boolean b);
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void deleteItem(TaskForgotten item);
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

    void onLoadItemsTaskFinished(List<TaskForgotten> itemsFromDatabase);

    void onErrorDeletingItem(TaskForgotten item);
  }
}
