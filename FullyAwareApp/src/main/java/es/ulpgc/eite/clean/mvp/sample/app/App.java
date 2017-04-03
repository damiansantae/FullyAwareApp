package es.ulpgc.eite.clean.mvp.sample.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskView;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.dummy.DummyView;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDonePresenterDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;


public class App extends Application implements Mediator, Navigator {

  private DummyState toDummyState, dummyToState;
  private ListToDoState toListToDoState, listToDoToState;
  private ListDoneState toListDoneState, listDoneToState;
  private ListForgottenState toListForgottenState, listForgottenToState;
  private AddTaskState toAddTaskState, addTaskToState;

    private DetailState masterListToDetailState;
  private ListState listToDoDetailToMasterState;
  private ListState listDoneDetailToMasterState;

  @Override
  public void onCreate() {
    super.onCreate();
    toDummyState = new DummyState();
    toDummyState.toolbarVisibility = false;
    toDummyState.textVisibility = false;

    toListToDoState = new ListToDoState();
    toListToDoState.toolbarVisibility = true;
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
    toAddTaskState.toolbarVisibility = true;
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
  public void startingListToDoScreen(ListToDoMaster.ToListToDo presenter){
    if(toListToDoState != null) {
      presenter.setToolbarVisibility(toListToDoState.toolbarVisibility);
      presenter.setTextVisibility(toListToDoState.textVisibility);
      presenter.setAddBtnVisibility(toListToDoState.addBtnVisibility);
      presenter.setDeleteBtnVisibility(toListToDoState.deleteBtnVisibility);
        presenter.setDoneBtnVisibility(toListToDoState.doneBtnVisibility);


    }
    presenter.onScreenStarted();
  }

  @Override
  public void startingListDoneScreen(ListDoneMaster.ToListDone presenter) {
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
    /**
     * Llamado cuando arranca el detalle para fijar su estado inicial
     *
     * @param presenter implementando la interfaz necesaria para fijar su estado inicial
     *  en funcion de los valores pasado desde el maestro
     */
    @Override
    public void startingDetailScreen(ListToDoDetail.MasterListToDetail presenter){
        if(masterListToDetailState != null) {
            presenter.setToolbarVisibility(!masterListToDetailState.toolbarVisible);
            presenter.setItem(masterListToDetailState.selectedItem);
        }

        // Una vez fijado el estado inicial, el detalle puede iniciarse normalmente
        masterListToDetailState = null;
        presenter.onScreenStarted();
    }


    @Override
  public void taskDone(Task taskDone) {
   // ListDonePresenter.setNewTask(null); // PENDIENTE: Preguntar como llamar directamente al presentador de ListDoneMaster o crear clase Task Común
  }

  @Override
  public void startingDetailScreen(ListDonePresenterDetail listDonePresenterDetail) {

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
  public void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter) {

     // listToDoToState.toolbarVisibility = presenter.isToolbarVisible();
      //listDoneToState.textVisibility = presenter.isTextVisible();
    addTaskToState = new AddTaskState();
      addTaskToState.toolbarVisibility=true;

      Context view = presenter.getManagedContext();
      if (view != null) {
        view.startActivity(new Intent(view, AddTaskView.class));
      }
  }

  @Override
  public void goToListToDoScreen(AddTaskPresenter addTaskPresenter) {
    if (listToDoToState==null){
      listToDoToState = new ListToDoState();

    }
    listToDoToState.toolbarVisibility=true;

    Context view = addTaskPresenter.getManagedContext();
    if (view != null) {
      view.startActivity(new Intent(view, ListToDoViewMaster.class));
    }

  }

    @Override
    public void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster) {
        masterListToDetailState =new DetailState();
        masterListToDetailState.toolbarVisible = listToDoPresenterMaster.getToolbarVisibility();
        masterListToDetailState.selectedItem = listToDoPresenterMaster.getSelectedTask();

        // Arrancamos la pantalla del detalle sin finalizar la del maestro
        Context view = listToDoPresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListToDoViewDetail.class));
        }
    }



  @Override
  public void backToMasterScreen(ListToDoDetail.DetailToMaster presenter) {
    listToDoDetailToMasterState = new ListState();
    listToDoDetailToMasterState.taskToDelete = presenter.getTaskToDelete();

    // Al volver al maestro, el detalle debe finalizar
    presenter.destroyView();
  }

  @Override
  public void goToDetailScreen(ListDonePresenterMaster listDonePresenterMaster) {

  }

  @Override
  public void backToMasterScreen(ListDoneDetail.DetailToMaster presenter) {
    listDoneDetailToMasterState = new ListState();
    listDoneDetailToMasterState.taskToDelete = presenter.getTaskToDelete();

    // Al volver al maestro, el detalle debe finalizar
    presenter.destroyView();
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
      boolean doneBtnVisibility;



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

    private class DetailState {
        boolean toolbarVisible;
        Task selectedItem;
    }


  /**
   * Estado a actualizar en el maestro en función de la ejecución del detalle
   */
  private class ListState {
    Task taskToDelete;
  }

}
