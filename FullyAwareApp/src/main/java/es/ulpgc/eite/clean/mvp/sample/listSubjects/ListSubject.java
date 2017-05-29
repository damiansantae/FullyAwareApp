package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;


public interface ListSubject {


    ///////////////////////////////////////////////////////////////////////////////////
    // State /////////////////////////////////////////////////////////////////////////

    interface ToListSubject {

        void onScreenStarted();

        void setToolbarVisibility(boolean visible);


    }

    interface ListSubjectTo {

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

        boolean getToolbarVisibility();

        void onAddUserBtnClicked(String userName);

        String getFinishLabel();

        void addSubjectsToDataBase(ArrayList<String> subjectList);

        void saveEditSubject(String text, Subject currentSubject);

        void swipeLeft(Subject currentSubject);

        void launchHomeScreen();
    }

    /**
     * Required VIEW methods available to PRESENTER
     */
    interface PresenterToView extends ContextView {

        void finishScreen();

        void hideToolbar();

        void toolbarChanged(String colour);

        void setRecyclerAdapterContent(List<Subject> items);

        void showAddUserNameDialog();

        void showAddSubjectsDialog();

        void initSwipe();
    }

    /**
     * Methods offered to MODEL to communicate with PRESENTER
     */
    interface PresenterToModel extends Model<ModelToPresenter> {

        String getFinishLabel();

        void addSubjectsToDataBase(ArrayList<String> subjectList);
    }

    /**
     * Required PRESENTER methods available to MODEL
     */
    interface ModelToPresenter {

    }
}
