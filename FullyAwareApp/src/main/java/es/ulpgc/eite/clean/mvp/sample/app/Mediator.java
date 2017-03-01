package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDo;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;

public interface Mediator {
  void startingDummyScreen(Dummy.ToDummy presenter);
  void startingListToDoScreen(ListToDo.ToListToDo presenter);
}
