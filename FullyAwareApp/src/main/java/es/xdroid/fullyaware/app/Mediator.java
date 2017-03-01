package es.xdroid.fullyaware.app;

import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDo;

public interface Mediator {
  void startingDummyScreen(Dummy.ToDummy presenter);
  void startingListToDoScreen(ListToDo.ToListToDo presenter);
}
