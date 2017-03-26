package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDo;

public interface Navigator {
  void goToNextScreen(Dummy.DummyTo presenter);
  void goToAddTaskScreen(ListToDo.ListToDoTo presenter);

  void goToListToDoScreen(AddTaskPresenter addTaskPresenter);
}
