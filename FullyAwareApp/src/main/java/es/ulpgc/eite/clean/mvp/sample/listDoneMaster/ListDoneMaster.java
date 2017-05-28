package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import android.content.Context;
import android.view.View;

import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public interface ListDoneMaster {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToListDone {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);

    void setDeleteBtnVisibility(boolean deleteBtnVisibility);

  }

  interface ListDoneTo {
    Context getManagedContext();
    void destroyView();
    boolean isToolbarVisible();
    boolean isTextVisible();
  }
  /**
   * Interfaz que permite iniciar la pantalla del detalle y recopilar los valores necesarios
   * para rellenar el estado inicial que se pasará a la pantalla del detalle al iniciarse
   */
   interface MasterListToDetail{
    Context getManagedContext();
    Task getSelectedTask();
    boolean getToolbarVisibility();

  }
  /**
   * Interfaz que permite fijar los valores incluidos en el estado pasado desde la pantalla
   * del detalle cuando está finaliza
   */
  interface DetailToMaster {
  }


  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {


    void onListClick2(View item, int position, Task task);

    void onLongListClick2(View item, int adapterPosition);

    boolean isSelected(int adapterPosition);

    void onBinBtnClick2(ListDoneViewMasterTesting.TaskRecyclerViewAdapter adapter);


      String getCases(Task task);
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
      void toolbarChanged(String colour);

      void finishScreen();
    void hideToolbar();

      void hideDeleteBtn();

      void showDeleteBtn();

    boolean isItemListChecked(int pos);

    void setItemChecked(int pos, boolean checked);

    void startSelection();

    void setChoiceMode(int i);
    

    void deselect(int i, boolean b);
    void setRecyclerAdapterContent(List<Task> items);

      void setToastDelete();
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void deleteItem(Task item);
    void loadItems();
    void reloadItems();
    void setDatabaseValidity(boolean valid);
    String getErrorMessage();
    void addInitialTasks();

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
      String calculateCases(String subjectName);
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

    void onLoadItemsTaskStarted();

    void onLoadItemsTaskFinished(List<Task> itemsFromDatabase);

    void onErrorDeletingItem(Task item);
  }
}
