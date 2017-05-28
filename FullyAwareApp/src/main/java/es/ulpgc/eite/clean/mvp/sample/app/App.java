package es.ulpgc.eite.clean.mvp.sample.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;

import es.ulpgc.eite.clean.mvp.sample.NotificationService;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskPresenter;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskView;
import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
import es.ulpgc.eite.clean.mvp.sample.dummy.DummyView;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMasterTesting;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubject;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectPresenter;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectView;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoPresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesPresenter;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesView;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.ModuleSubjectTask;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.ModuleSubjectTimeTable;
import es.ulpgc.eite.clean.mvp.sample.schedule.Schedule;
import es.ulpgc.eite.clean.mvp.sample.schedule.ScheduleView;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application implements Mediator, Navigator {

    private DummyState toDummyState, dummyToState;
    private ListToDoState toListToDoState, listToDoToState;
    private ListDoneState toListDoneState, listDoneToState;
    private ListSubjectState toListSubjectState, listSubjectToState;
    private ListForgottenState toListForgottenState, listForgottenToState;
    private AddTaskState toAddTaskState, addTaskToState;
    private ScheduleState toScheduleState, scheduleToState;

    private DetailToDoState masterListToDetailToDoState;
    private DetailDoneState masterListToDetailDoneState;
    private ListToDoStateTask listToDoDetailToMasterState;
    private ListDoneStateTask listDoneDetailToMasterState;
    private ListForgottenStateTask listForgottenDetailToMasterState;
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
                .modules(new ModuleSubjectTask(), new ModuleSubjectTimeTable())
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
        toListDoneState.TaskDone = null;

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

        toListSubjectState = new ListSubjectState();
        toListSubjectState.toolbarVisibility = true;
        toListSubjectState.textVisibility = false;
        toListSubjectState.addBtnVisibility = true;
        toListSubjectState.deleteBtnVisibility = false;


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

    /*public void getUserCalendar() {
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "(" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?)";
        String[] selectionArgs = new String[]{"com.google"};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
    }*/


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
    public void startingPreferencesScreen(Preferences.ToPreferences presenter) {
        if (toPreferencesState != null) {
            presenter.setToolbarVisibility(toPreferencesState.toolbarVisibility);
            presenter.setTextVisibility(toPreferencesState.textVisibility);
            presenter.setAddBtnVisibility(toPreferencesState.addBtnVisibility);
            presenter.setDeleteBtnVisibility(toPreferencesState.deleteBtnVisibility);
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
            presenter.setMaster((ListToDoPresenterMaster) masterListToDetailToDoState.master);

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
    public void Task(Task TaskDone) {
        // ListDonePresenter.setNewTask(null); // PENDIENTE: Preguntar como llamar directamente al presentador de ListDoneMaster o crear clase Task Común
    }

    @Override
    public void startingDetailScreen(ListDoneDetail.MasterListToDetail presenter) {

        if (masterListToDetailDoneState != null) {
            presenter.setToolbarVisibility(!masterListToDetailDoneState.toolbarVisible);
            presenter.setItem(masterListToDetailDoneState.selectedItem);
            presenter.setAdapter(masterListToDetailDoneState.adapter);
            presenter.setMaster((ListDonePresenterMaster) masterListToDetailDoneState.master);
        }

        // Una vez fijado el estado inicial, el detalle puede iniciarse normalmente
        masterListToDetailDoneState = null;
        presenter.onScreenStarted();
    }


    @Override
    public void startingListSubjectScreen(ListSubjectPresenter listSubjectsPresenter) {

    }


    /////////////////TOOLBAR CHANGES METHODS
    @Override
    public void toolbarColourChanged(PreferencesPresenter presenter) {
        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
        }
        preferencesToState.toolbarVisibility = true;
        preferencesToState.toolbarColour = presenter.getToolbarColour();
        preferencesToState.toolbarColourChanged = presenter.getToolbarColourChanged();

    /*    if (preferencesToState.toolbarColourChanged == true){
            changeToolbarColour(presenter, preferencesToState.toolbarColour);
        }
        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, PreferencesView.class));
*/
        }

    @Override
    public String getToolbarColour() {
        String newColourString = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         newColourString = getColorHex(preferencesToState.toolbarColour);
        }

        return newColourString;
    }

    @Override
    public int getToolbarColourInt() {
        return preferencesToState.toolbarColour;
    }


    @Override
    public boolean checkToolbarChanged() {
        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
            preferencesToState.toolbarVisibility = true;
            preferencesToState.toolbarColour = 111;
        }


        return this.preferencesToState.toolbarColourChanged;
    }




    private String getColorHex(int color) {
        return String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
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
    public void goToPreferencesScreen(Schedule.ScheduleTo presenter) {

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
            view.startActivity(new Intent(view, ListToDoViewMaster.class));
        }

    }

    @Override
    public void goToChangeColourDialog(PreferencesPresenter preferencesPresenter) {
        Context view = preferencesPresenter.getManagedContext();
        if (view != null) {
            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));
           // view.startActivity(new Intent(view, ExtrasActivity.class));
        }
    }

    @Override
    public void goToAddSubjectScreen(ListSubjectPresenter listSubjectsPresenter) {

    }

    @Override
    public void goToListToDoScreen(ListSubject.ListSubjectTo presenter) {

        if (listSubjectToState == null) {
            listSubjectToState = new ListSubjectState();
        }

        listSubjectToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));
            view.startActivity(new Intent(view, ListToDoViewMaster.class));
        }



    }

    @Override
    public void goToListSubjectScreen(ListSubjectPresenter presenter) {

    }

    @Override
    public void goToListDoneScreen(ListSubject.ListSubjectTo presenter) {

    }


    @Override
    public void goToPreferencesScreen(ListSubject.ListSubjectTo presenter) {

    }

    @Override
    public void goToDetailScreen(ListSubjectPresenter listSubjectPresenter, ListSubjectView.SubjectRecyclerViewAdapter adapter) {

    }

    @Override
    public void startActivy(Intent intent) {
        startActivity(intent);
    }


    @Override
    public void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster, ListToDoViewMaster.TaskRecyclerViewAdapter adapter) {
        masterListToDetailToDoState = new DetailToDoState();
        masterListToDetailToDoState.toolbarVisible = listToDoPresenterMaster.getToolbarVisibility();
        masterListToDetailToDoState.selectedItem = listToDoPresenterMaster.getSelectedTask();

        masterListToDetailToDoState.adapter = adapter;
        masterListToDetailToDoState.master = listToDoPresenterMaster;

        // masterListToDetailToDoState.subject = listToDoPresenterMaster.getSelectedSubject().getTagId();

        // Arrancamos la pantalla del detalle sin finalizar la del maestro
        Context view = listToDoPresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListToDoViewDetail.class));
        }
    }

    @Override
    public void backToMasterScreen(ListToDoDetail.DetailToMaster presenter) {
        listToDoDetailToMasterState = new ListToDoStateTask();
        listToDoDetailToMasterState.TaskToDelete = presenter.getTaskToDelete();

        // Al volver al maestro, el detalle debe finalizar
        presenter.destroyView();
    }

    @Override
    public void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster) {
        masterListToDetailDoneState = new DetailDoneState();
        masterListToDetailDoneState.toolbarVisible = listDonePresenterMaster.getToolbarVisibility();
        masterListToDetailDoneState.selectedItem = listDonePresenterMaster.getSelectedTask();

        // Al igual que en el to do arrancamos la pantalla del detalle sin finalizar la del maestro.
        Context view = listDonePresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewDetail.class));
        }
    }

    @Override
    public void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster, ListDoneViewMasterTesting.TaskRecyclerViewAdapter adapter) {
        masterListToDetailDoneState = new DetailDoneState();
        masterListToDetailDoneState.toolbarVisible = listDonePresenterMaster.getToolbarVisibility();
        masterListToDetailDoneState.selectedItem = listDonePresenterMaster.getSelectedTask();
        masterListToDetailDoneState.adapter = adapter;
masterListToDetailDoneState.master=listDonePresenterMaster;
        // masterListToDetailToDoState.subject = listToDoPresenterMaster.getSelectedSubject().getTagId();

        // Arrancamos la pantalla del detalle sin finalizar la del maestro
        Context view = listDonePresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewDetail.class));
        }
    }


    @Override
    public void backToMasterScreen(ListDoneDetail.DetailToMaster presenter) {
        listDoneDetailToMasterState = new ListDoneStateTask();
        listDoneDetailToMasterState.TaskToDelete = presenter.getTaskToDelete();

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
            view.startActivity(new Intent(view, ListDoneViewMasterTesting.class));

            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));

            presenter.destroyView();
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
    public void goToScheduleScreen(ListSubject.ListSubjectTo presenter) {

    }


    @Override
    public void goToListToDoScreen(Schedule.ScheduleTo presenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            presenter.destroyView();
            view.startActivity(new Intent(view, ListToDoViewMaster.class));
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
            presenter.destroyView();
            view.startActivity(new Intent(view, ListDoneViewMasterTesting.class));

            //TODO: activar esta linea para funcionamiento con listView view.startActivity(new Intent(view, ListToDoViewMaster.class));

        }


    }


    @Override
    public void goToPreferencesScreen(Preferences.PreferencesTo presenter) {
        //TODO: borrar metodo de la interfaz.
    }


    @Override
    public void goToListToDoScreen(ListDoneMaster.ListDoneTo presenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {

            //TODO: activar esta linea para funcionamiento con listView: view.startActivity(new Intent(view, ListToDoViewMaster.class));
            view.startActivity(new Intent(view, ListToDoViewMaster.class));


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

    @Override
    public void goToPreferencesScreen(ListDoneMaster.ListDoneTo presenter) {

        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
        }
        preferencesToState.toolbarVisibility = true;
        Context view = presenter.getManagedContext();

        if (view != null) {
            view.startActivity(new Intent(view, PreferencesView.class));

        }


    }

    public void writeTaskIntoCalendar(){
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);

        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=DAILY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);
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
        Task TaskDone;
    }

    private class ListSubjectState {
        boolean toolbarVisibility;
        boolean textVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
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
        int toolbarColour;
        boolean toolbarColourChanged = false;
    }

    private class ScheduleState {
        boolean toolbarVisibility;
    }

    private class DetailToDoState {
        boolean toolbarVisible;
        Task selectedItem;
        String subject;
        String date;
        ListToDoViewMaster.TaskRecyclerViewAdapter adapter;
        public ListToDoMaster.MasterListToDetail master;
    }

    private class DetailDoneState {
        boolean toolbarVisible;
        Task selectedItem;
        String subject;
        String date;
        ListDoneViewMasterTesting.TaskRecyclerViewAdapter adapter;
        public ListDoneMaster.MasterListToDetail master;
    }


    /**
     * Estado a actualizar en el maestro en función de la ejecución del detalle
     */
    private class ListToDoStateTask {
        Task TaskToDelete;
    }

    private class ListDoneStateTask {
        Task TaskToDelete;
    }

    private class ListForgottenStateTask {
        Task taskToDelete;
    }


}
