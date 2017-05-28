package es.ulpgc.eite.clean.mvp.sample.preferences;


import android.content.Context;
import android.widget.SimpleAdapter;
import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;

public interface Preferences {


    ///////////////////////////////////////////////////////////////////////////////////
    // State /////////////////////////////////////////////////////////////////////////
    /**
     * Methods related to the different states of the app.
     */

    interface ToPreferences {

        void onScreenStarted();

        void setToolbarVisibility(boolean visible);
    }

    interface PreferencesTo {

        Context getManagedContext();

        void destroyView();

        boolean isToolbarVisible();
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Screen ////////////////////////////////////////////////////////////////////////

    /**
     * Methods offered to VIEW to communicate with PRESENTER
     */
    interface ViewToPresenter extends Presenter<PresenterToView> {

        void onListClick(int position, SimpleAdapter adapter);

        void setNewToolbarColor(int newColor);

        void setToolbarColorChanged(boolean toolbarColorChanged);

        void toolbarChanged();

        String[] getPrefItemLabels();

        String[] getPrefDescriptionItemsLabels();

        CharSequence getNotSupportedSystem();
    }

    /**
     * Required VIEW methods available to PRESENTER
     */
    interface PresenterToView extends ContextView {

        void finishScreen();

        void onChangeColourDialog(PresenterToView view);

        String getColorHex(int color);

        void setNewToolbarColor(int newColor);

        void setToolbarColorChanged(boolean toolbarColorChanged);

        void toolbarChanged(String colour);
    }

    /**
     * Methods offered to MODEL to communicate with PRESENTER
     */
    interface PresenterToModel extends Model<ModelToPresenter> {

        String[] getPrefItemLabels();

        String[] getPrefDescriptionItemsLabels();

        String getDialogInfoTitle();

        CharSequence getDialogInfoDescription();

        String getVisitButtonLabel();

        String getOkButtonLabel();

        String getPaypalUrl();

        String getGitHubUrl();

        String getNotSupportedSystem();
    }

    /**
     * Required PRESENTER methods available to MODEL
     */
    interface ModelToPresenter {

    }

}
