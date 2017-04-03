package es.ulpgc.eite.clean.mvp.sample.listDoneDetail;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;

public interface ListDoneDetail {


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
