package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDonePresenterDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;

public interface Mediator {
  void startingDummyScreen(Dummy.ToDummy presenter);

  void startingListToDoScreen(ListToDoMaster.ToListToDo presenter);

  void startingListDoneScreen(ListDoneMaster.ToListDone presenter);

  void startingListForgottenScreen(ListForgotten.ToListForgotten presenter);

  void startingAddTaskScreen(AddTask.ToAddTask presenter);

  void startingDetailScreen(ListToDoDetail.MasterListToDetail listToDoPresenterDetail);

   void taskDone(Task taskDone);

    void startingDetailScreen(ListDoneDetail.MasterListToDetail listDonePresenterDetail);


}
