package es.ulpgc.eite.clean.mvp.sample.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import es.ulpgc.eite.clean.mvp.sample.NotificationService;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskView;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.dummy.DummyView;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMaster;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgotten;
import es.ulpgc.eite.clean.mvp.sample.listForgotten.ListForgottenView;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMasterTesting;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesView;
import es.ulpgc.eite.clean.mvp.sample.schedule.Schedule;
import es.ulpgc.eite.clean.mvp.sample.schedule.ScheduleView;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application implements Mediator, Navigator {

    private DummyState toDummyState, dummyToState;
    private ListToDoState toListToDoState, listToDoToState;
    private ListDoneState toListDoneState, listDoneToState;
    private ListForgottenState toListForgottenState, listForgottenToState;
    private AddTaskState toAddTaskState, addTaskToState;
    private ScheduleState toScheduleState, scheduleToState;

    private DetailToDoState masterListToDetailToDoState;
    private DetailDoneState masterListToDetailDoneState;
    private ListToDoStateTask listToDoDetailToMasterState;
    private ListDoneStateTask listDoneDetailToMasterState;
    private PreferencesState toPreferencesState, preferencesToState;

    @Override
    public void onCreate() {
        super.onCreate();
        //Inicializamos servicio de notificaciones
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("tasks.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        startService(new Intent(this, NotificationService.class));
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
        toListDoneState.taskToDoDone = null;

        toListForgottenState = new ListForgottenState();
        toListForgottenState.toolbarVisibility = false;
        toListForgottenState.textVisibility = false;
        toListForgottenState.deleteBtnVisibility = false;

        toAddTaskState = new AddTaskState();
        toAddTaskState.toolbarVisibility = true;
        toAddTaskState.textVisibility = false;
        toAddTaskState.addBtnVisibility = true;
        toAddTaskState.deleteBtnVisibility = false;

        toPreferencesState = new PreferencesState();
        toPreferencesState.toolbarVisibility = true;
        toPreferencesState.textVisibility = false;
        toPreferencesState.addBtnVisibility = true;
        toPreferencesState.deleteBtnVisibility = false;

        toScheduleState = new ScheduleState();
        toScheduleState.toolbarVisibility = true;

    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Mediator //////////////////////////////////////////////////////////////////////

    @Override
    public void startingDummyScreen(Dummy.ToDummy presenter) {
        if (toDummyState != null) {
            presenter.setToolbarVisibility(toDummyState.toolbarVisibility);
            presenter.setTextVisibility(toDummyState.textVisibility);
        }
        presenter.onScreenStarted();
    }

    @Override
    public void startingListToDoScreen(ListToDoMaster.ToListToDo presenter) {
        if (toListToDoState != null) {
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
        if (toDummyState != null) {
            presenter.setToolbarVisibility(toListDoneState.toolbarVisibility);
            presenter.setTextVisibility(toListDoneState.textVisibility);
            presenter.setDeleteBtnVisibility(toListDoneState.deleteBtnVisibility);
        }
        presenter.onScreenStarted();
    }


    @Override
    public void startingAddTaskScreen(AddTask.ToAddTask presenter) {
        if (toAddTaskState != null) {
            presenter.setToolbarVisibility(toAddTaskState.toolbarVisibility);
            presenter.setTextVisibility(toAddTaskState.textVisibility);
            presenter.setAddBtnVisibility(toAddTaskState.addBtnVisibility);
            presenter.setDeleteBtnVisibility(toAddTaskState.deleteBtnVisibility);
        }
        presenter.onScreenStarted();
    }


    @Override
    public void startingListForgottenScreen(ListForgotten.ToListForgotten presenter) {
        if (toListForgottenState != null) {
            presenter.setToolbarVisibility(toListForgottenState.toolbarVisibility);
            presenter.setTextVisibility(toListForgottenState.textVisibility);
            presenter.setDeleteBtnVisibility(toListForgottenState.deleteBtnVisibility);

        }
        presenter.onScreenStarted();
    }

    @Override
    public void startingPreferencesScreen(Preferences.ToPreferences presenter) {
        if (toAddTaskState != null) {
            presenter.setToolbarVisibility(toAddTaskState.toolbarVisibility);
            presenter.setTextVisibility(toAddTaskState.textVisibility);
            presenter.setAddBtnVisibility(toAddTaskState.addBtnVisibility);
            presenter.setDeleteBtnVisibility(toAddTaskState.deleteBtnVisibility);
        }
        presenter.onScreenStarted();
    }


    /**
     * Llamado cuando arranca el detalle para fijar su estado inicial
     *
     * @param presenter implementando la interfaz necesaria para fijar su estado inicial
     *                  en funcion de los valores pasado desde el maestro
     */
    @Override
    public void startingDetailScreen(ListToDoDetail.MasterListToDetail presenter) {
        if (masterListToDetailToDoState != null) {
            presenter.setToolbarVisibility(masterListToDetailToDoState.toolbarVisible);
            presenter.setItem(masterListToDetailToDoState.selectedItem);
            presenter.setAdapter(masterListToDetailToDoState.adapter);
        }

        // Una vez fijado el estado inicial, el detalle puede iniciarse normalmente
        masterListToDetailToDoState = null;
        presenter.onScreenStarted();
    }

    @Override
    public void startingScheduleScreen(Schedule.ToSchedule presenter) {
        if (toScheduleState != null) {
            presenter.setToolbarVisibility(toScheduleState.toolbarVisibility);
        }
        presenter.onScreenStarted();

    }


    @Override
    public void taskDone(TaskToDo taskToDoDone) {
        // ListDonePresenter.setNewTask(null); // PENDIENTE: Preguntar como llamar directamente al presentador de ListDoneMaster o crear clase TaskToDo Común
    }

    @Override
    public void startingDetailScreen(ListDoneDetail.MasterListToDetail presenter) {

        if (masterListToDetailDoneState != null) {
            presenter.setToolbarVisibility(!masterListToDetailDoneState.toolbarVisible);
            presenter.setItem(masterListToDetailDoneState.selectedItem);
        }

        // Una vez fijado el estado inicial, el detalle puede iniciarse normalmente
        masterListToDetailDoneState = null;
        presenter.onScreenStarted();
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
        addTaskToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, AddTaskView.class));
        }
    }


    @Override
    public void goToPreferencesScreen(ListToDoMaster.ListToDoTo presenter) {

        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
        }
        preferencesToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, PreferencesView.class));

        }

    }

    @Override
    public void goToListToDoScreen(AddTaskPresenter addTaskPresenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = addTaskPresenter.getManagedContext();
        if (view != null) {
            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));
            view.startActivity(new Intent(view, ListToDoViewMasterTesting.class));
        }

    }

    @Override
    public void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster, ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter) {
        masterListToDetailToDoState = new DetailToDoState();
        masterListToDetailToDoState.toolbarVisible = listToDoPresenterMaster.getToolbarVisibility();
        masterListToDetailToDoState.selectedItem = listToDoPresenterMaster.getSelectedTaskToDo();
        masterListToDetailToDoState.adapter = adapter;

        // masterListToDetailToDoState.subject = listToDoPresenterMaster.getSelectedTaskToDo().getTagId();

        // Arrancamos la pantalla del detalle sin finalizar la del maestro
        Context view = listToDoPresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListToDoViewDetail.class));
        }
    }

    @Override
    public void backToMasterScreen(ListToDoDetail.DetailToMaster presenter) {
        listToDoDetailToMasterState = new ListToDoStateTask();
        listToDoDetailToMasterState.taskToDoToDelete = presenter.getTaskToDelete();

        // Al volver al maestro, el detalle debe finalizar
        presenter.destroyView();
    }

    @Override
    public void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster) {
        masterListToDetailDoneState = new DetailDoneState();
        masterListToDetailDoneState.toolbarVisible = listDonePresenterMaster.getToolbarVisibility();
        masterListToDetailDoneState.selectedItem = listDonePresenterMaster.getSelectedTaskDone();

        // Al igual que en el to do arrancamos la pantalla del detalle sin finalizar la del maestro.
        Context view = listDonePresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewDetail.class));
        }
    }


    @Override
    public void backToMasterScreen(ListDoneDetail.DetailToMaster presenter) {
        listDoneDetailToMasterState = new ListDoneStateTask();
        listDoneDetailToMasterState.taskDoneToDelete = presenter.getTaskToDelete();

        // Al volver al maestro, el detalle debe finalizar
        presenter.destroyView();
    }

    @Override
    public void goToListDoneScreen(ListToDoMaster.ListToDoTo presenter) {
        if (listDoneToState == null) {
            listDoneToState = new ListDoneState();
        }
        listDoneToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewMaster.class));
            presenter.destroyView();
        }

    }

    @Override
    public void goToListForgottenScreen(ListToDoMaster.ListToDoTo presenter) {
        if (listForgottenToState == null) {
            listForgottenToState = new ListForgottenState();
        }
        listForgottenToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, ListForgottenView.class));

        }

    }

    @Override
    public void goToScheduleScreen(ListToDoMaster.ListToDoTo presenter) {
        if (scheduleToState == null) {
            scheduleToState = new ScheduleState();
        }
        scheduleToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, ScheduleView.class));

        }
    }

    @Override
    public void goToListToDoScreen(ListForgotten.ListForgottenTo presenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));
            view.startActivity(new Intent(view, ListToDoViewMasterTesting.class));
        }


    }

    @Override
    public void goToListDoneScreen(ListForgotten.ListForgottenTo presenter) {
        if (listDoneToState == null) {
            listDoneToState = new ListDoneState();
        }
        listDoneToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewMaster.class));
        }


    }

    @Override
    public void goToScheduleScreen(ListForgotten.ListForgottenTo presenter) {
        if (scheduleToState == null) {
            scheduleToState = new ScheduleState();
        }
        scheduleToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, ScheduleView.class));

        }

    }

    @Override
    public void goToListToDoScreen(Schedule.ScheduleTo presenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListToDoViewMasterTesting.class));
        }

    }

    @Override
    public void goToListDoneScreen(Schedule.ScheduleTo presenter) {
        if (listDoneToState == null) {
            listDoneToState = new ListDoneState();
        }
        listDoneToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewMaster.class));
        }


    }

    @Override
    public void goToListForgottenScreen(Schedule.ScheduleTo presenter) {
        if (listForgottenToState == null) {
            listForgottenToState = new ListForgottenState();
        }
        listForgottenToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, ListForgottenView.class));

        }

    }

    @Override
    public void goToPreferencesScreen(Preferences.PreferencesTo presenter) {
        //TODO: borrar metodo de la interfaz.
    }

    @Override
    public void goToListForgottenScreen(ListDoneMaster.ListDoneTo presenter) {
        if (listForgottenToState == null) {
            listForgottenToState = new ListForgottenState();
        }
        listForgottenToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, ListForgottenView.class));

        }


    }

    @Override
    public void goToListToDoScreen(ListDoneMaster.ListDoneTo presenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {

            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));
            view.startActivity(new Intent(view, ListToDoViewMasterTesting.class));


        }


    }

    @Override
    public void goToScheduleScreen(ListDoneMaster.ListDoneTo presenter) {
        if (scheduleToState == null) {
            scheduleToState = new ScheduleState();
        }
        scheduleToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, ScheduleView.class));

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
        boolean doneBtnVisibility;


    }

    private class ListDoneState {
        boolean toolbarVisibility;
        boolean textVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
        TaskToDo taskToDoDone;
    }

    private class ListForgottenState {
        boolean toolbarVisibility;
        boolean textVisibility;
        boolean deleteBtnVisibility;
    }

    private class AddTaskState {
        boolean toolbarVisibility;
        boolean textVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
    }

    private class PreferencesState {
        boolean toolbarVisibility;
        boolean textVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
    }

    private class ScheduleState {
        boolean toolbarVisibility;
    }

    private class DetailToDoState {
        boolean toolbarVisible;
        TaskToDo selectedItem;
        String subject;
        String date;
        ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter;
    }

    private class DetailDoneState {
        boolean toolbarVisible;
        TaskDone selectedItem;
        String subject;
        String date;
        ListToDoViewMasterTesting.TaskRecyclerViewAdapter adapter;
    }


    /**
     * Estado a actualizar en el maestro en función de la ejecución del detalle
     */
    private class ListToDoStateTask {
        TaskToDo taskToDoToDelete;
    }

    private class ListDoneStateTask {
        TaskDone taskDoneToDelete;
    }

}
