package es.ulpgc.eite.clean.mvp.sample.listToDoDetail;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public class ListToDoModelDetail extends GenericModel<ListToDoDetail.ModelToPresenter>
    implements ListToDoDetail.PresenterToModel {

  private Task task;



  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(ListToDoDetail.ModelToPresenter presenter) {
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




  @Override
  public void setTask(Task selectedItem) {
    this.task = selectedItem;

  }

  @Override
  public Task getItem() {
    return task;
  }
}
