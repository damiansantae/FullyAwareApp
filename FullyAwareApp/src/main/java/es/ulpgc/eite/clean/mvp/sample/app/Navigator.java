package es.ulpgc.eite.clean.mvp.sample.app;

import android.content.Intent;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubject;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectPresenter;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectView;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesPresenter;
import es.ulpgc.eite.clean.mvp.sample.schedule_NextUpgrade.Schedule;

public interface Navigator {


    /*******************************************************************
     ******** Navigation to all of the screens and its detail from LisToDo*/

    /**
     * Method to save the state of ListToDo and go to AddTask
     * @param presenter Presenter of ListToDo
     */
    void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter);

    /**
     * Method to save the state of ListToDo and go to ListDone
     * @param presenter Presenter of ListToDo
     */
    void goToListDoneScreen(ListToDoMaster.ListToDoTo presenter);

    /**
     * Method to save the state of ListToDo and go to Preferences
     * @param presenter Presenter of ListToDo
     */
    void goToPreferencesScreen(ListToDoMaster.ListToDoTo presenter);


    //Comunicación M/D

    /**
     * Method to save the state of ListToDo and go to its detail
     * @param listToDoPresenterMaster Presenter of ListToDoMaster
     */
    void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster);

    /**
     * Method to save the LisToDoDetail state, and go to Master screen
     * @param presenter LisToDoDetail presenter
     */
    void backToMasterScreen(ListToDoDetail.DetailToMaster presenter);


    /***********************************************************************
     ******** Navigation to all of the screens and its detail from ListDone*/

    /**
     * Method to save the state of ListDone and go to ListToDo
     * @param presenter Presenter of ListDone
     */
    void goToListToDoScreen(ListDoneMaster.ListDoneTo presenter);

    /**
     * Method to save the state of ListDone and go to Preferences
     * @param presenter Presenter of ListDone
     */
    void goToPreferencesScreen(ListDoneMaster.ListDoneTo presenter);

    //Comunicación M/D

    /**
     * Method to save the state of ListDone and go to its detail
     * @param listDonePresenterMaster Presenter of listDoneMaster
     */
    void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster);

    /**
     * Method to save the LisDoneDetail state, and go to Master screen
     * @param presenter LisDoneDetail presenter
     */
    void backToMasterScreen(ListDoneDetail.DetailToMaster presenter);



    /***************************************************************
     ******** Navigation to all of the screens from Schedule */

    ////////////////////////Next Upgrade/////////////////////////////////////////////

    void goToListToDoScreen(Schedule.ScheduleTo presenter);

    void goToListDoneScreen(Schedule.ScheduleTo presenter);

    void goToPreferencesScreen(Schedule.ScheduleTo presenter);


    /*******************************************************************
     ******** Navigation to all of the screens from ListSubjects */

    /**
     * Method to save the state of ListSubjects and go to ListToDo
     * @param presenter Presenter of ListSubjects
     */
    void goToListToDoScreen(ListSubject.ListSubjectTo presenter);

    /**
     * Method to save the state of ListSubjects and go to ListDone
     * @param presenter Presenter of ListSubjects
     */
    void goToListDoneScreen(ListSubject.ListSubjectTo presenter);



/*******************************************************************
 ******** Navigation to all of the screens from ListSubjects */


    /**
     * Method to save the state of Preferences and go to ListSubjects
     * @param preferencesPresenter Presenter of Preferences
     */
    void goToEditSubjects(PreferencesPresenter preferencesPresenter);


    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    void goToPreferencesScreen(ListSubject.ListSubjectTo presenter);

    /**
     * Method who calls its super class equivalent method to start an activity
     * @param intent Intent to launch an activity and carry extra information if necessary
     */
    void startActivity(Intent intent);
}
