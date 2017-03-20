package es.ulpgc.eite.clean.mvp.sample.addTask;

import es.ulpgc.eite.clean.mvp.GenericModel;


public class AddTaskModel extends GenericModel<AddTask.ModelToPresenter>
    implements AddTask.PresenterToModel {

  private String dummyText;
  private String dummyLabel;
  private int numOfTimes;
  private String msgText;

  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(AddTask.ModelToPresenter presenter) {
    super.onCreate(presenter);

    dummyLabel = "Click Me!";
    dummyText = "Hello World!";
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
  public void onChangeMsgByBtnClicked() {
    msgText = dummyText;
    if(numOfTimes > 0) {
      msgText += ", " + numOfTimes + " times";
    }
    numOfTimes++;
  }

  @Override
  public String getText() {
    return msgText;
  }

  @Override
  public String getLabel() {
    return dummyLabel;
  }
}
