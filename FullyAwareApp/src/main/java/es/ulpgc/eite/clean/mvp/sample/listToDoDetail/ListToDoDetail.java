package es.ulpgc.eite.clean.mvp.sample.listToDoDetail;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoPresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMasterTesting;

public interface ListToDoDetail {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  /**
   * Interfaz que permite iniciar la pantalla del detalle y recopilar los valores necesarios
   * para rellenar el estado inicial que se pasará a la pantalla del detalle al iniciarse
   */
  interface MasterListToDetail{
    void setToolbarVisibility(boolean b);
    void setItem(Task selectedItem);

    void onScreenStarted();

      void setAdapter(ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter);


    void setMaster(ListToDoPresenterMaster master);
  }
  /**
   * Interfaz que permite fijar los valores incluidos en el estado pasado desde la pantalla
   * del detalle cuando está finaliza
   */
  interface DetailToMaster {
    void destroyView();
    Task getTaskToDelete();
  }

  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {
    void onButtonClicked();

    Task getTask();

    void onDeleteActionClicked();
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();


    void toolbarChanged(String colour);
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void setTask(Task selectedItem);
    Task getTask();
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

  }

  ///////////////////////////////////////////


}
