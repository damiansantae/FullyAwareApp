package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listForgottenDetail.ListForgottenDetail;
import es.ulpgc.eite.clean.mvp.sample.listForgottenMaster.ListForgottenMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMasterTesting;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMasterTesting;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesPresenter;
import es.ulpgc.eite.clean.mvp.sample.schedule.Schedule;

public interface Navigator {

    void goToNextScreen(Dummy.DummyTo presenter);


    /*******************************************************************
     ******** Navegación direccion ListoDo al resto de pantallas y detalle*/

    void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter);

    void backToMasterScreen(ListForgottenDetail.DetailToMaster presenter);

    void goToListDoneScreen(ListToDoMaster.ListToDoTo presenter);

    void goToListForgottenScreen(ListToDoMaster.ListToDoTo presenter);

    void goToScheduleScreen(ListToDoMaster.ListToDoTo presenter);

    //Comunicación M/D
    void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster, ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter);

    void backToMasterScreen(ListToDoDetail.DetailToMaster presenter);


    /***********************************************************************
     ******** Navegación direccion ListDone al resto de pantallas y detalle*/
    void goToListForgottenScreen(ListDoneMaster.ListDoneTo presenter);
    void goToListToDoScreen(ListDoneMaster.ListDoneTo presenter);
    void goToScheduleScreen(ListDoneMaster.ListDoneTo presenter);
    void goToPreferencesScreen(ListDoneMaster.ListDoneTo presenter);


    void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster);

    void goToDetailScreen(ListForgottenMaster.MasterListToDetail listForgottenPresenterMaster);

    //Comunicación M/D
    void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster, ListDoneViewMasterTesting.TaskRecyclerViewAdapter adapter);
    void backToMasterScreen(ListDoneDetail.DetailToMaster presenter);




    /****************************************************************************
     ******** Navegación direccion ListForgotten al resto de pantallas y detalle*/
    void goToScheduleScreen(ListForgottenMaster.ListForgottenTo presenter);
    void goToListToDoScreen(ListForgottenMaster.ListForgottenTo presenter);




    void goToPreferencesScreen(Schedule.ScheduleTo presenter);


    void goToListDoneScreen(ListForgottenMaster.ListForgottenTo presenter);

    //Comunicación M/D


    /***************************************************************
     ******** Navegación direccion Shedule al resto de pantallas */

    void goToListToDoScreen(Schedule.ScheduleTo presenter);

    void goToListDoneScreen(Schedule.ScheduleTo presenter);

    void goToListForgottenScreen(Schedule.ScheduleTo presenter);


    void goToPreferencesScreen(Preferences.PreferencesTo presenter);

    void goToPreferencesScreen(ListToDoMaster.ListToDoTo presenter);

    void goToPreferencesScreen(ListForgottenMaster.ListForgottenTo presenter);

    /*******************************************************************
     ******** Navegación direccion Preferences al resto de pantallas*/
    void goToListToDoScreen(AddTaskPresenter addTaskPresenter);


    void goToChangeColourDialog(PreferencesPresenter preferencesPresenter);
}
