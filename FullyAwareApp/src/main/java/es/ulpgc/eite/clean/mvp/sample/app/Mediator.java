package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMasterTesting;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectPresenter;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesPresenter;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesView;
import es.ulpgc.eite.clean.mvp.sample.schedule_NextUpgrade.Schedule;

public interface Mediator {
    void startingDummyScreen(Dummy.ToDummy presenter);

    void startingListToDoScreen(ListToDoMaster.ToListToDo presenter);

    void startingListDoneScreen(ListDoneMaster.ToListDone presenter);
    
    void startingAddTaskScreen(AddTask.ToAddTask presenter);

    void startingPreferencesScreen(Preferences.ToPreferences presenter);
    
    void startingDetailScreen(ListToDoDetail.MasterListToDetail listToDoPresenterDetail);

    void startingScheduleScreen(Schedule.ToSchedule presenter);

    void Task(Task TaskDone);

    void startingDetailScreen(ListDoneDetail.MasterListToDetail listDonePresenterDetail);

    void toolbarColourChanged(PreferencesPresenter preferencesPresenter);

    String getToolbarColour();

    int getToolbarColourInt();

    boolean checkToolbarChanged();


    void startingListSubjectScreen(ListSubjectPresenter listSubjectsPresenter);

    void loadSharePreferences(PreferencesView view);

    void loadSharePreferences(ListToDoViewMaster view);

    void loadSharePreferences(ListDoneViewMasterTesting view);
}
