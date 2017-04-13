package es.ulpgc.eite.clean.mvp.sample.schedule;

import es.ulpgc.eite.clean.mvp.GenericModel;


public class ScheduleModel extends GenericModel<Schedule.ModelToPresenter>
    implements Schedule.PresenterToModel {

  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(Schedule.ModelToPresenter presenter) {
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




  ////////////////////////////////////////////////////////////


}
