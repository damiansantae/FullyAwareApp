package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;


public interface ListSubject {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToListSubject {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);
    void setDeleteBtnVisibility(boolean deleteBtnVisibility);

  }

  interface ListSubjectTo {
    Context getManagedContext();
    void destroyView();
    boolean isToolbarVisible();
    boolean isTextVisible();
  }


  ///////////////////////////////////////////////////////////////////////////////////
  // Screen ////////////////////////////////////////////////////////////////////////

  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {


    void onListClick2(View item, int position, ListSubjectView.SubjectRecyclerViewAdapter adapter, Subject subject);

    void onLongListClick2(View item, int adapterPosition);

    boolean isSelected(int adapterPosition);

    void onBinBtnClick2(ListSubjectView.SubjectRecyclerViewAdapter adapter);

    boolean getToolbarVisibility();

    void onAddUserBtnClicked(String userName);

    String getLabelFloatingAdd();

    String getLabelFloatingDelete();

    String getLabelBtnAddSubject();

    String getLabelBtnHour();

    String getDaysChecked(int i);

    void setTimeText(int i, String txt);

    String getTimeText(int i);

    String getFinishLabel();

    void addSubjectsToDataBase(ArrayList<String> subjectList);
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {

    void finishScreen();

    void hideToolbar();

    void hideDeleteBtn();

    void showDeleteBtn();

    boolean isItemListChecked(int pos);

    void setItemChecked(int pos, boolean checked);

    void startSelection();

    void setChoiceMode(int i);

    void deselect(int i, boolean b);

    void toolbarChanged(String colour);

    void setRecyclerAdapterContent(List<Subject> items);

      void setToastDelete();

    void showAddUserNameDialog();


    void showAddSubjectsDialog();

  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void deleteItem(Subject item);

    void loadItems();

    void reloadItems();

    void setDatabaseValidity(boolean valid);

    String getErrorMessage();

    String getLabelFloatingAdd();

    String getLabelFloatingDelete();

    String getLabelBtnHour();

    String getLabelBtnAddSubject();

    void setLabelButtons();

    String getFinishLabel();

    ArrayList<String> getDaysOfWeek();

    void addSubjectsToDataBase(ArrayList<String> subjectList);
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

    void onLoadItemsSubjectStarted();

    void onLoadItemsSubjectFinished(List<Subject> itemsFromDatabase);

    void onErrorDeletingItem(Subject item);
  }
}
