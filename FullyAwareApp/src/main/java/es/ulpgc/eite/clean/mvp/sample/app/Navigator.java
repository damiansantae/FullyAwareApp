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
     ******** Navegación direccion ListoDo al resto de pantallas y detalle*/

    void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter);

    void goToListDoneScreen(ListToDoMaster.ListToDoTo presenter);

    void goToScheduleScreen(ListToDoMaster.ListToDoTo presenter);

    void goToScheduleScreen(ListSubject.ListSubjectTo presenter);

    //Comunicación M/D
    void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster);
    void backToMasterScreen(ListToDoDetail.DetailToMaster presenter);


    /***********************************************************************
     ******** Navegación direccion ListDone al resto de pantallas y detalle*/
    void goToListToDoScreen(ListDoneMaster.ListDoneTo presenter);
    void goToScheduleScreen(ListDoneMaster.ListDoneTo presenter);
    void goToPreferencesScreen(ListDoneMaster.ListDoneTo presenter);

    //Comunicación M/D
    void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster);
    void backToMasterScreen(ListDoneDetail.DetailToMaster presenter);



    /***************************************************************
     ******** Navegación direccion Shedule al resto de pantallas */

    void goToListToDoScreen(Schedule.ScheduleTo presenter);

    void goToListDoneScreen(Schedule.ScheduleTo presenter);


    void goToPreferencesScreen(ListToDoMaster.ListToDoTo presenter);


    void goToPreferencesScreen(Schedule.ScheduleTo presenter);

    /*******************************************************************
     ******** Navegación direccion Preferences al resto de pantallas*/


    /*******************************************************************
     ******** Navegación direccion Subjects al resto de pantallas*/

    void goToListToDoScreen(ListSubject.ListSubjectTo presenter);
    void goToListSubjectScreen(ListSubjectPresenter presenter);
    void goToListDoneScreen(ListSubject.ListSubjectTo presenter);
    void goToPreferencesScreen(ListSubject.ListSubjectTo presenter);
    void goToDetailScreen(ListSubjectPresenter listSubjectPresenter, ListSubjectView.SubjectRecyclerViewAdapter adapter);
    void startActivity(Intent intent);
    void goToEditSubjects(PreferencesPresenter preferencesPresenter);
}
