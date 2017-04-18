package es.ulpgc.eite.clean.mvp.sample.listForgottenDetail;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.app.TaskForgotten;


public class ListForgottenModelDetail extends GenericModel<ListForgottenDetail.ModelToPresenter>
    implements ListForgottenDetail.PresenterToModel {

  private TaskForgotten taskForgotten;



  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(ListForgottenDetail.ModelToPresenter presenter) {
    super.onCreate(presenter);


  }

  /**
   * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
   * Usefull for kill/stop activities that could be running on the background Threads
   *
   * @param isChangingConfiguration Informs that a change is occurring on the configuration
   */
  @Override
  public void onDestroy(boolean isChangingConfiguration) {

  }

  ///////////////////////////////////////////////////////////////////////////////////
  // Presenter To Model ////////////////////////////////////////////////////////////




  public void setTaskForgotten(TaskForgotten selectedItem) {
    this.taskForgotten = selectedItem;

  }

  public TaskForgotten getTaskForgotten() {
    return taskForgotten;
  }
}
