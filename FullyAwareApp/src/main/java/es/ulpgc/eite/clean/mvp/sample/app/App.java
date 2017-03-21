package es.ulpgc.eite.clean.mvp.sample.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskView;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.listDone.ListDonePresenter;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDo;
import es.ulpgc.eite.clean.mvp.sample.listDone.ListDone;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.dummy.DummyView;
import es.ulpgc.eite.clean.mvp.sample.listToDo.ListToDoPresenter;
import es.ulpgc.eite.clean.mvp.sample.listToDo.Task;


public class App extends Application implements Mediator, Navigator {

  private DummyState toDummyState, dummyToState;
  private ListToDoState toListToDoState, listToDoToState;
  private ListDoneState toListDoneState, listDoneToState;
  private ListForgottenState toListForgottenState, listForgottenToState;
  private AddTaskState toAddTaskState, addTaskToState;

  @Override
  public void onCreate() {
    super.onCreate();
    toDummyState = new DummyState();
    toDummyState.toolbarVisibility = false;
    toDummyState.textVisibility = false;

    toListToDoState = new ListToDoState();
    toListToDoState.toolbarVisibility = false;
    toListToDoState.textVisibility = false;
    toListToDoState.addBtnVisibility = true;
    toListToDoState.deleteBtnVisibility = false;

    toListDoneState = new ListDoneState();
    toListDoneState.toolbarVisibility = false;
    toListDoneState.textVisibility = false;
    toListDoneState.taskDone = null;

    toListForgottenState = new ListForgottenState();
    toListToDoState.toolbarVisibility = false;
    toListToDoState.textVisibility = false;
    toListToDoState.addBtnVisibility = true;
    toListToDoState.deleteBtnVisibility = false;

    toAddTaskState = new AddTaskState();
    toAddTaskState.toolbarVisibility = false;
    toAddTaskState.textVisibility = false;
    toAddTaskState.addBtnVisibility = true;
    toAddTaskState.deleteBtnVisibility = false;

  }

  ///////////////////////////////////////////////////////////////////////////////////
  // Mediator //////////////////////////////////////////////////////////////////////

  @Override
  public void startingDummyScreen(Dummy.ToDummy presenter){
    if(toDummyState != null) {
      presenter.setToolbarVisibility(toDummyState.toolbarVisibility);
      presenter.setTextVisibility(toDummyState.textVisibility);
    }
    presenter.onScreenStarted();
  }

  @Override
  public void startingListToDoScreen(ListToDo.ToListToDo presenter){
    if(toListToDoState != null) {
      presenter.setToolbarVisibility(toListToDoState.toolbarVisibility);
      presenter.setTextVisibility(toListToDoState.textVisibility);
      presenter.setAddBtnVisibility(toListToDoState.addBtnVisibility);
      presenter.setDeleteBtnVisibility(toListToDoState.deleteBtnVisibility);

    }
    presenter.onScreenStarted();
  }

  @Override
  public void startingListDoneScreen(ListDone.ToListDone presenter) {
    if(toDummyState != null) {
      presenter.setToolbarVisibility(toListDoneState.toolbarVisibility);
      presenter.setTextVisibility(toListDoneState.textVisibility);
      presenter.setAddBtnVisibility(toListDoneState.addBtnVisibility);
      presenter.setDeleteBtnVisibility(toListDoneState.deleteBtnVisibility);
    }
    presenter.onScreenStarted();
  }


  @Override
  public void startingAddTaskScreen(AddTask.ToAddTask presenter) {
    if(toAddTaskState != null) {
      presenter.setToolbarVisibility(toAddTaskState.toolbarVisibility);
      presenter.setTextVisibility(toAddTaskState.textVisibility);
      presenter.setAddBtnVisibility(toAddTaskState.addBtnVisibility);
      presenter.setDeleteBtnVisibility(toAddTaskState.deleteBtnVisibility);
    }
    presenter.onScreenStarted();
  }


  @Override
  public void startingListForgottenScreen(ListForgotten.ToListForgotten presenter) {
    if(toListForgottenState != null) {
      presenter.setToolbarVisibility(toListForgottenState.toolbarVisibility);
      presenter.setTextVisibility(toListForgottenState.textVisibility);
      presenter.setAddBtnVisibility(toListForgottenState.addBtnVisibility);
      presenter.setDeleteBtnVisibility(toListForgottenState.deleteBtnVisibility);

    }
    presenter.onScreenStarted();
  }

  @Override
  public void taskDone(Task taskDone) {

    ListDonePresenter.setNewTask(null); // PENDIENTE: Preguntar como llamar directamente al presentador de ListDone o crear clase Task Común
  }


  ///////////////////////////////////////////////////////////////////////////////////
  // Navigator /////////////////////////////////////////////////////////////////////


  @Override
  public void goToNextScreen(Dummy.DummyTo presenter) {
    dummyToState = new DummyState();
    dummyToState.toolbarVisibility = presenter.isToolbarVisible();
    dummyToState.textVisibility = presenter.isTextVisible();

    Context view = presenter.getManagedContext();
    if (view != null) {
      view.startActivity(new Intent(view, DummyView.class));
      presenter.destroyView();
    }

  }

  @Override
  public void goToAddTaskScreen(ListToDo.ListToDoTo presenter) {
      listToDoToState = new ListToDoState();
      listToDoToState.toolbarVisibility = presenter.isToolbarVisible();
      listDoneToState.textVisibility = presenter.isTextVisible();

      Context view = presenter.getManagedContext();
      if (view != null) {
        view.startActivity(new Intent(view, AddTaskView.class));
        presenter.destroyView();
      }
  }


  ///////////////////////////////////////////////////////////////////////////////////
  // State /////////////////////////////////////////////////////////////////////////

  private class DummyState {
    boolean toolbarVisibility;
    boolean textVisibility;
  }

  private class ListToDoState {
    boolean toolbarVisibility;
    boolean textVisibility;
    boolean addBtnVisibility;
    boolean deleteBtnVisibility;


  }

  private class ListDoneState {
    boolean toolbarVisibility;
    boolean textVisibility;
    boolean addBtnVisibility;
    boolean deleteBtnVisibility;
    Task taskDone;
  }

  private class ListForgottenState {
    boolean toolbarVisibility;
    boolean textVisibility;
    boolean addBtnVisibility;
    boolean deleteBtnVisibility;
  }

  private class AddTaskState {
    boolean toolbarVisibility;
    boolean textVisibility;
    boolean addBtnVisibility;
    boolean deleteBtnVisibility;
  }


}
