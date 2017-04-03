package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDone.ListDone;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;

public interface Navigator {
  void goToNextScreen(Dummy.DummyTo presenter);
  void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter);

  void goToListToDoScreen(AddTaskPresenter addTaskPresenter);

  void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster);
  void backToMasterScreen(ListToDoDetail.DetailToMaster presenter);

    void goToListDoneScreen(ListToDoMaster.ListToDoTo presenter);
  void goToListForgottenScreen(ListToDoMaster.ListToDoTo presenter);

  void goToListToDoScreen(ListForgotten.ListForgottenTo presenter);

  void goToListDoneScreen(ListForgotten.ListForgottenTo presenter);

    void goToListForgottenScreen(ListDone.ListDoneTo presenter);

    void goToListToDoScreen(ListDone.ListDoneTo presenter);
}
