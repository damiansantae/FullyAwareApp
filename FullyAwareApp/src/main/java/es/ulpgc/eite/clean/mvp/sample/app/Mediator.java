package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDone.ListDone;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;

public interface Mediator {
  void startingDummyScreen(Dummy.ToDummy presenter);

  void startingListToDoScreen(ListToDoMaster.ToListToDo presenter);

  void startingListDoneScreen(ListDone.ToListDone presenter);

  void startingListForgottenScreen(ListForgotten.ToListForgotten presenter);

  void startingAddTaskScreen(AddTask.ToAddTask presenter);
  void startingDetailScreen(ListToDoDetail.MasterListToDetail listToDoPresenterDetail);


  void taskDone(Task taskDone);

}
