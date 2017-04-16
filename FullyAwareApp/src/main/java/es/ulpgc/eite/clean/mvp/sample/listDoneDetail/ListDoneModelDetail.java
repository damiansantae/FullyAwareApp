package es.ulpgc.eite.clean.mvp.sample.listDoneDetail;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.app.TaskToDo;


public class ListDoneModelDetail extends GenericModel<ListDoneDetail.ModelToPresenter>
    implements ListDoneDetail.PresenterToModel {

  private TaskToDo taskToDo;



  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(ListDoneDetail.ModelToPresenter presenter) {
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




  public void setTaskToDo(TaskToDo selectedItem) {
    this.taskToDo = selectedItem;

  }

  public TaskToDo getTaskToDo() {
    return taskToDo;
  }
}
