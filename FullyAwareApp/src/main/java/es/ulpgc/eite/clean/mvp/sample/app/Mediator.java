package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.schedule.Schedule;

public interface Mediator {
  void startingDummyScreen(Dummy.ToDummy presenter);

  void startingListToDoScreen(ListToDoMaster.ToListToDo presenter);

  void startingListDoneScreen(ListDoneMaster.ToListDone presenter);

  void startingListForgottenScreen(ListForgotten.ToListForgotten presenter);

  void startingAddTaskScreen(AddTask.ToAddTask presenter);

    void startingPreferencesScreen(Preferences.ToPreferences presenter);



    void startingDetailScreen(ListToDoDetail.MasterListToDetail listToDoPresenterDetail);

    void startingScheduleScreen(Schedule.ToSchedule presenter);
    

   void taskDone(Task taskDone);

    void startingDetailScreen(ListDoneDetail.MasterListToDetail listDonePresenterDetail);


}
