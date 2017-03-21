package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.listDone.ListDone;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDo;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDoPresenter;
import es.ulpgc.eite.clean.mvp.sample.listToDo.Task;

public interface Mediator {
  void startingDummyScreen(Dummy.ToDummy presenter);

  void startingListToDoScreen(ListToDo.ToListToDo presenter);

  void startingListDoneScreen(ListDone.ToListDone presenter);

  void startingListForgottenScreen(ListForgotten.ToListForgotten presenter);

  void taskDone(Task taskDone);

  void startingAddTaskScreen(AddTaskPresenter addTaskPresenter);
}