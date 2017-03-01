package es.ulpgc.eite.clean.mvp.sample.listToDo;

import es.ulpgc.eite.clean.mvp.GenericModel;


public class ListToDoModel extends GenericModel<ListToDo.ModelToPresenter>
    implements ListToDo.PresenterToModel {

  private String listToDoText;
  private String listToDoLabel;
  private int numOfTimes;
  private String msgText;

  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(ListToDo.ModelToPresenter presenter) {
    super.onCreate(presenter);

    listToDoLabel = "Click Me!";
    listToDoText = "Hello World!";
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
    msgText = listToDoText;
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
    return listToDoLabel;
  }
}
