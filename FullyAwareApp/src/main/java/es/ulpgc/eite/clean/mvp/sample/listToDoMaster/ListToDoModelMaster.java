package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public class ListToDoModelMaster extends GenericModel<ListToDoMaster.ModelToPresenter>
    implements ListToDoMaster.PresenterToModel {

  private String listToDoText;
  private String listToDoLabel;
  private int numOfTimes;
  private String msgText;
  private static final int ITEM_COUNT = 9;

  public List<Task> items = null;
  private boolean runningTask;
  private String errorMsg;

  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(ListToDoMaster.ModelToPresenter presenter) {
    super.onCreate(presenter);

    listToDoLabel = "Click Me!";
    listToDoText = "Hello World!";
    errorMsg = "Error deleting item";
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

  ////////////////////////////////////////////////////////////

  private void addItem(Task task) {
    items.add(task);
  }
  private Task createItem(int position) {
    return new Task(R.drawable.bg_controll_plane,"Titulo6","Descripcion6","Fecha6");
  }

  private String makeDetails(int position) {
    StringBuilder builder = new StringBuilder();
    builder.append("Details about Item: ").append(position).append("\n");
    for (int count = 0; count < position; count++) {
      builder.append("\nMore details information here.");
    }
    return builder.toString();
  }
  @Override
  public void loadItems() {
    if(items == null && !runningTask) {
      startDelayedTask();
    } else {
      if(!runningTask){
        Log.d(TAG, "calling onLoadItemsTaskFinished() method");
        getPresenter().onLoadItemsTaskFinished(items);
      } else {
        Log.d(TAG, "calling onLoadItemsTaskStarted() method");
        getPresenter().onLoadItemsTaskStarted();
      }
    }
  }
  private void setItems(){
    items = new ArrayList();

    // Add some sample items.
    for (int count = 1; count <= ITEM_COUNT; count++) {
      addItem(createItem(count));
    }
  }

  private void startDelayedTask() {
    Log.d(TAG, "calling startDelayedTask() method");
    runningTask = true;
    Log.d(TAG, "calling onLoadItemsTaskStarted() method");
    getPresenter().onLoadItemsTaskStarted();

    // Mock Hello: A handler to delay the answer
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        setItems();
        runningTask = false;
        Log.d(TAG, "calling onLoadItemsTaskFinished() method");
        getPresenter().onLoadItemsTaskFinished(items);
      }
    }, 5000);
  }
}
