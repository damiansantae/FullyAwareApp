package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDonePresenterDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;

public interface Navigator {
  void goToNextScreen(Dummy.DummyTo presenter);

  void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter);
  void goToListToDoScreen(AddTaskPresenter addTaskPresenter);


  void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster);
  void backToMasterScreen(ListToDoDetail.DetailToMaster presenter);



  void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster);

  void backToMasterScreen(ListDoneDetail.DetailToMaster presenter);
}
