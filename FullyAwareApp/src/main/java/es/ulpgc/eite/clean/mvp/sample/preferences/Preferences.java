package es.ulpgc.eite.clean.mvp.sample.preferences;

import android.content.Context;
import android.widget.SimpleAdapter;
import android.widget.Toolbar;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;

/**
 * Created by Luis on 12/11/16.
 */

public interface Preferences {


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  interface ToPreferences {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setTextVisibility(boolean visible);
    void setAddBtnVisibility(boolean addBtnVisibility);
    void setDeleteBtnVisibility(boolean deleteBtnVisibility);
  }

  interface PreferencesTo {
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
    void onSelectDateBtnClicked();
    void onSelectTimeBtnClicked();
    void onAddTaskBtnClicked();
      void onListClick(int position, SimpleAdapter adapter);

    void setNewToolbarColor(int newColor);

    void setToolbarColorChanged(boolean toolbarColorChanged);
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void finishScreen();
    void hideToolbar();
    void setDateText(String txt);

    void setTimeText(String txt);


    String getDescription();

    String getDate();

    String getTime();

    String getTaskTitle();

    String getTaskSubject();

      void onChangeColourDialog(PresenterToView view);
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    void onChangeMsgByBtnClicked();
    String getText();
    String getLabel();
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

  }

}
