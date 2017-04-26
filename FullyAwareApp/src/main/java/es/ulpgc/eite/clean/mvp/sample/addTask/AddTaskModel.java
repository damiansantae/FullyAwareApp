package es.ulpgc.eite.clean.mvp.sample.addTask;

import android.util.Log;

import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import io.realm.Realm;


public class AddTaskModel extends GenericModel<AddTask.ModelToPresenter>
    implements AddTask.PresenterToModel {

  private static final int ITEM_COUNT = 9;

  //public List<ModelItem> items = null; // An array of items
  private boolean runningTask;
  private Realm realmDatabase;
  private boolean validDatabase;
  private String errorMsg;
  private boolean usingWrapper;


  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(AddTask.ModelToPresenter presenter) {
    super.onCreate(presenter);
    realmDatabase = Realm.getDefaultInstance();
    //validDatabase = true;
    errorMsg = "Error deleting item!";
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

  /////////////////////////////////////////////////////////////////////////////////////
  // Presenter To Model //////////////////////////////////////////////////////////////


  /**
   * Llamado para recuperar los elementos a mostrar en la lista.
   * Si el contenido ya ha sido fijado antes, se notificará inmediatamente al presentador y,
   * sino es el caso, la notificación se realizará al finalizar la tarea que fija este contenido
   */
  /*@Override
  public void loadItems() {
    if(!validDatabase && !runningTask) {
      startDelayedTask();
    } else {
      if(!runningTask){
        Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
        getPresenter().onLoadItemsSubjectsFinished(getItemsFromDatabase());
      } else {
        Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
        getPresenter().onLoadItemsSubjectStarted();
      }
    }*/

    /*
    if(items == null && !runningTask) {
      startDelayedTask();
    } else {
      if(!runningTask){
        Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
        getPresenter().onLoadItemsSubjectsFinished(items);
      } else {
        Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
        getPresenter().onLoadItemsSubjectStarted();
      }
    }
    */


  /*
  @Override
  public void deleteItem(ModelItem item) {
    if (items.contains(item)){
      items.remove(item);
    } else {
      getPresenter().onErrorDeletingItem(item);
    }
  }
  */

  /*@Override
  public void deleteItem(Task item) {
    if (getItemsFromDatabase().contains(item)){
      //items.remove(item);
      deleteDatabaseItem(item);
    } else {
      getPresenter().onErrorDeletingItem(item);
    }
  }*/

  /**
   * Llamado para recuperar los elementos iniciales de la lista.
   * En este caso siempre se llamará a la tarea asíncrona
   */
  /*@Override
  public void reloadItems() {
    //items = null;
    deleteAllDatabaseItems();
    validDatabase = false;
    loadItems();
  }*/


  public void setDatabaseValidity(boolean valid) {
    validDatabase = valid;
  }


  public String getErrorMessage() {
    return errorMsg;
  }

  /////////////////////////////////////////////////////////////////////////////////////

  /*
  private void addItem(ModelItem item) {
    items.add(item);
  }

  private ModelItem createItem(int position) {
    return new ModelItem(String.valueOf(position), "Item " + position, makeDetails(position));
  }
  */

  private String makeDetails(int position) {
    StringBuilder builder = new StringBuilder();
    builder.append("Details about Item: ").append(position).append("\n");
    for (int count = 0; count < position; count++) {
      builder.append("\nMore details information here.");
    }
    return builder.toString();
  }

  /*
  private void setItems(){
    items = new ArrayList();

    // Add some sample items
    for (int count = 1; count <= ITEM_COUNT; count++) {
      addItem(createItem(count));
    }
  }
  */

  /**
   * Llamado para recuperar los elementos a mostrar en la lista.
   * Consiste en una tarea asíncrona que retrasa un tiempo la obtención del contenido.
   * El modelo notificará al presentador cuando se inicia y cuando finaliza esta tarea.
   */
  /*private void startDelayedTask() {
    Log.d(TAG, "calling startDelayedTask() method");
    runningTask = true;
    Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
    getPresenter().onLoadItemsSubjectStarted();

    // Mock Hello: A handler to delay the answer
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        //setItems();
        runningTask = false;
        validDatabase = true;
        Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
        //getPresenter().onLoadItemsSubjectsFinished(items);
        getPresenter().onLoadItemsSubjectsFinished(getItemsFromDatabase());
      }
    }, 5000);
  }*/




  /////////////////////////////////////////////////////////////////////////////////////

  public void addInitialTasks(){
    //Request realm instance

    Task Task1 = new Task("Titulo1","Descripcion1","Fecha1", "ToDo");
    Task Task2 = new Task("Titulo2","Descripcion2","Fecha2", "ToDo");
    Task Task3 = new Task("Titulo3","Descripcion3","Fecha3", "ToDo");
    Task Task4 = new Task("Titulo4","Descripcion4","Fecha4", "ToDo");
    Task Task5 = new Task("Titulo5","Descripcion5","Fecha5", "ToDo");
    Task Task6 = new Task("Titulo6","Descripcion6","Fecha6", "ToDo");
    Task Task7 = new Task("Titulo7","Descripcion7","Fecha7", "ToDo");
    Task Task8 = new Task("Titulo8","Descripcion8","Fecha8", "ToDo");
    Task Task9 = new Task("Titulo9","Descripcion9","Fecha9", "ToDo");
    Task Task10 = new Task("Titulo10","Descripcion10","Fecha10", "ToDo");



//Insert element
    realmDatabase.beginTransaction();

    realmDatabase.copyToRealm(Task1);
    realmDatabase.copyToRealm(Task2);
    realmDatabase.copyToRealm(Task3);
    realmDatabase.copyToRealm(Task4);
    realmDatabase.copyToRealm(Task5);
    realmDatabase.copyToRealm(Task6);
    realmDatabase.copyToRealm(Task7);
    realmDatabase.copyToRealm(Task8);
    realmDatabase.copyToRealm(Task9);
    realmDatabase.copyToRealm(Task10);

    realmDatabase.commitTransaction();
  }

  private void deleteAllDatabaseItems(){
    for(Task item: getItemsFromDatabase()){
      deleteDatabaseItem(item);
    }
  }

  private void deleteDatabaseItem(Task item) {
    final String id = item.getTaskId();
    realmDatabase.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.where(Task.class).equalTo("id", id)
                .findAll()
                .deleteAllFromRealm();
        ;
      }
    });
    /*
    realmDatabase.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.where(ModelItem.class).equalTo("id", id)
            .findAll()
            .deleteAllFromRealm();
      }
    });
    */
  }

  /*private void setDatabaseItemsFromJson(){
    setItemsFromJsonStream("database.json");
    //setItemsFromJsonObjectArray();
  }

  private void setItemsFromJsonStream(String filename)  {
    Log.d(TAG, "calling setItemsFromJsonStream() method");

    usingWrapper = true;

    try {

      // Use streams if you are worried about the size of the JSON whether it was persisted on disk
      // or received from the network.
      Context context = getPresenter().getManagedContext();
      InputStream stream = context.getAssets().open(filename);

      // Open a transaction to store items into the realmDatabase
      realmDatabase.beginTransaction();
      try {
        realmDatabase.createAllFromJson(Task.class, stream);
        realmDatabase.commitTransaction();
      } catch (IOException error) {
        Log.d(TAG, "error=" +  error);
        // Remember to cancel the transaction if anything goes wrong.
        realmDatabase.cancelTransaction();
      } finally {
        if (stream != null) {
          stream.close();
        }
      }

    } catch (IOException ex) {
      Log.d(TAG, "error=" +  ex);
    }
  }*/



  private List<Task> getItemsFromDatabase(){
    if(usingWrapper) {
      return getItemsFromDatabaseWrapper();
    }

    return realmDatabase.where(Task.class).findAll();
  }

  private List<Task> getItemsFromDatabaseWrapper(){
    Log.d(TAG, "calling getItemsFromDatabaseWrapper() method");
    List<Task> dbItems = realmDatabase.where(Task.class).findAll();

    Log.d(TAG, "items=" +  dbItems);
    return dbItems;
  }

  @Override
  public void addTask(Task Task) {
    realmDatabase.beginTransaction();
    realmDatabase.copyToRealm(Task);
    realmDatabase.commitTransaction();
  }

  /*
  private void setItemsFromDatabase(){
    items = realmDatabase.where(ModelItem.class).findAll();
  }
  */

  /*private void setItemsFromJsonObjectArray(){
    usingWrapper = false;

    // Add some sample items
    for (int count = 1; count <= ITEM_COUNT; count++) {
      saveFromJsonObject(createJsonObject(count));
    }
  }

  private JSONObject createJsonObject(int id){
    Log.d(TAG, "calling createJsonObject() method");

    Map<String, String> item = new HashMap();
    item.put("id", String.valueOf(id));
    item.put("content", "Item " + id);
    item.put("details", makeDetails(id));
    final JSONObject json = new JSONObject(item);
    Log.d(TAG, "json=" +  json);
    return json;
  }

  private void saveFromJsonObject(final JSONObject json) {
    Log.d(TAG, "calling saveFromJsonObject() method");

    realmDatabase.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.createObjectFromJson(Task.class, json);
      }
    });
  }*/
}
