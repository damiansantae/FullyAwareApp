package es.ulpgc.eite.clean.mvp.sample.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.addTask.AddTaskView;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMaster;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubject;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectPresenter;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectView;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoPresenterMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;
import es.ulpgc.eite.clean.mvp.sample.notificationService_AlternativeFeature.NotificationService;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesPresenter;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesView;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.ModuleSubjectTask;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.ModuleSubjectTimeTable;
import es.ulpgc.eite.clean.mvp.sample.schedule_NextUpgrade.Schedule;
import es.ulpgc.eite.clean.mvp.sample.schedule_NextUpgrade.ScheduleView;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application implements Mediator, Navigator {
    private ListToDoState toListToDoState, listToDoToState;
    private ListDoneState toListDoneState, listDoneToState;
    private ListSubjectState toListSubjectState, listSubjectToState;
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
                .modules(new ModuleSubjectTask(), new ModuleSubjectTimeTable())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        startService(new Intent(this, NotificationService.class));

        toListToDoState = new ListToDoState();
        toListToDoState.toolbarVisibility = true;
        toListToDoState.addBtnVisibility = true;
        toListToDoState.deleteBtnVisibility = false;

        toListDoneState = new ListDoneState();
        toListDoneState.toolbarVisibility = true;
        toListDoneState.deleteBtnVisibility=false;
        toListDoneState.TaskDone = null;

        toAddTaskState = new AddTaskState();
        toAddTaskState.toolbarVisibility = true;

        toPreferencesState = new PreferencesState();
        toPreferencesState.toolbarVisibility = true;

        toScheduleState = new ScheduleState();
        toScheduleState.toolbarVisibility = true;

        toListSubjectState = new ListSubjectState();
        toListSubjectState.toolbarVisibility = true;
        toListSubjectState.addBtnVisibility = true;
        toListSubjectState.deleteBtnVisibility = false;


    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Mediator //////////////////////////////////////////////////////////////////////


    @Override
    public void startingListToDoScreen(ListToDoMaster.ToListToDo presenter) {
        if (toListToDoState != null) {
            presenter.setToolbarVisibility(toListToDoState.toolbarVisibility);
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
        if (toListDoneState != null) {
            presenter.setToolbarVisibility(toListDoneState.toolbarVisibility);
            presenter.setDeleteBtnVisibility(toListDoneState.deleteBtnVisibility);
        }
        presenter.onScreenStarted();
    }


    @Override
    public void startingAddTaskScreen(AddTask.ToAddTask presenter) {
        if (toAddTaskState != null) {
            presenter.setToolbarVisibility(toAddTaskState.toolbarVisibility);
        }
        presenter.onScreenStarted();
    }


    @Override
    public void startingPreferencesScreen(Preferences.ToPreferences presenter) {
        if (toPreferencesState != null) {
            presenter.setToolbarVisibility(toPreferencesState.toolbarVisibility);
        }
        presenter.onScreenStarted();
    }




    @Override
    public void startingScheduleScreen(Schedule.ToSchedule presenter) {
        if (toScheduleState != null) {
            presenter.setToolbarVisibility(toScheduleState.toolbarVisibility);
        }
        presenter.onScreenStarted();

    }



    /**
     * It is called when detail to do is started
     *
     * @param presenter implemented the necesary interface to fix initial state according
     *                  to the values passed from master
     */
    @Override
    public void startingDetailScreen(ListToDoDetail.MasterListToDetail presenter) {
        if (masterListToDetailToDoState != null) {
            presenter.setToolbarVisibility(masterListToDetailToDoState.toolbarVisible);
            presenter.setTask(masterListToDetailToDoState.selectedItem);
            presenter.setMaster((ListToDoPresenterMaster) masterListToDetailToDoState.master);
        }
        masterListToDetailToDoState = null;
        presenter.onScreenStarted();
    }

    /**
     * It is called when detail done is started
     *
     * @param presenter implemented the necesary interface to fix initial state according
     *                  to the values passed from master
     */
    @Override
    public void startingDetailScreen(ListDoneDetail.MasterListToDetail presenter) {

        if (masterListToDetailDoneState != null) {
            presenter.setToolbarVisibility(masterListToDetailDoneState.toolbarVisible);
            presenter.setItem(masterListToDetailDoneState.selectedItem);
            presenter.setMaster((ListDonePresenterMaster) masterListToDetailDoneState.master);
        }

        masterListToDetailDoneState = null;
        presenter.onScreenStarted();
    }


    @Override
    public void startingListSubjectScreen(ListSubjectPresenter listSubjectsPresenter) {
listSubjectsPresenter.onScreenStarted();
    }

    @Override
    public void setToolbarColorChanged(PreferencesView view, boolean toolbarColorChanged) {
        PrefManager prefManager = new PrefManager(view.getActivityContext());
        prefManager.setToolbarColourChanged(toolbarColorChanged);
        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
        }
        preferencesToState.toolbarVisibility = true;
        preferencesToState.toolbarColourChanged = toolbarColorChanged;
    }

    @Override
    public void setToolbarColour(PreferencesView view, int newColor) {
        PrefManager prefManager = new PrefManager(view.getActivityContext());
        prefManager.setToolbarColour(newColor);
        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
        }
        preferencesToState.toolbarVisibility = true;
        preferencesToState.toolbarColour = newColor;
    }

    /////////////////TOOLBAR CHANGES METHODS
    @Override
    public String getToolbarColour() {
        String newColourString = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

         newColourString = getColorHex(preferencesToState.toolbarColour);
        }
        return newColourString;
    }

    @Override
    public boolean checkToolbarChanged() {
        String APP_PREF = "androidhive-welcome";
        String TOOLBAR_COLOUR_CHANGED = "toolbar-coulour-changed";
        String TOOLBAR_COLOUR = "toolbar-coulour";
        SharedPreferences prefs = this.getSharedPreferences(
                APP_PREF, Context.MODE_PRIVATE);

        if (preferencesToState == null) {
            preferencesToState = new PreferencesState();
            preferencesToState.toolbarVisibility = true;
            preferencesToState.toolbarColourChanged = prefs.getBoolean(TOOLBAR_COLOUR_CHANGED,false);
            preferencesToState.toolbarColour = prefs.getInt(TOOLBAR_COLOUR, 0);
        }
         return preferencesToState.toolbarColourChanged;
    }


    private String getColorHex(int color) {
        return String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Navigator /////////////////////////////////////////////////////////////////////




    @Override
    public void goToAddTaskScreen(ListToDoMaster.ListToDoTo presenter) {
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
    public void goToListToDoScreen(ListSubject.ListSubjectTo presenter) {
        if (listToDoToState == null) {
            listToDoToState = new ListToDoState();

        }
        listToDoToState.toolbarVisibility = true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListToDoViewMaster.class));
        }

            presenter.destroyView();
        }


    @Override
    public void goToListDoneScreen(ListSubject.ListSubjectTo presenter) {
        if (listDoneToState == null) {
            listDoneToState = new ListDoneState();
        }
        listDoneToState.toolbarVisibility = true;
        listDoneToState.deleteBtnVisibility=true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewMaster.class));


            presenter.destroyView();
        }

    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void goToEditSubjects(PreferencesPresenter preferencesPresenter) {
        if (listSubjectToState == null) {
            listSubjectToState = new ListSubjectState();
        }

        Context view = preferencesPresenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListSubjectView.class));
        }
    }


    @Override
    public void goToDetailScreen(ListToDoMaster.MasterListToDetail listToDoPresenterMaster) {
        if (masterListToDetailToDoState == null) {
            masterListToDetailToDoState = new DetailToDoState();
        }

        masterListToDetailToDoState.toolbarVisible = listToDoPresenterMaster.getToolbarVisibility();
        masterListToDetailToDoState.selectedItem = listToDoPresenterMaster.getSelectedTask();
        masterListToDetailToDoState.master = listToDoPresenterMaster;

        Context view = listToDoPresenterMaster.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListToDoViewDetail.class));
        }
    }

    @Override
    public void backToMasterScreen(ListToDoDetail.DetailToMaster presenter) {
        listToDoDetailToMasterState = new ListToDoStateTask();
        listToDoDetailToMasterState.TaskToDelete = presenter.getTaskToDelete();
        presenter.destroyView();
    }


    @Override
    public void goToDetailScreen(ListDoneMaster.MasterListToDetail listDonePresenterMaster) {
        if(masterListToDetailDoneState==null){
            masterListToDetailDoneState = new DetailDoneState();
        }

        masterListToDetailDoneState.toolbarVisible = listDonePresenterMaster.getToolbarVisibility();
        masterListToDetailDoneState.selectedItem = listDonePresenterMaster.getSelectedTask();
        masterListToDetailDoneState.master = listDonePresenterMaster;

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
        listDoneToState.deleteBtnVisibility=true;

        Context view = presenter.getManagedContext();
        if (view != null) {
            view.startActivity(new Intent(view, ListDoneViewMaster.class));


            presenter.destroyView();
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
            view.startActivity(new Intent(view, ListDoneViewMaster.class));
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

            view.startActivity(new Intent(view, ListToDoViewMaster.class));


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

    private class ListToDoState {
        boolean toolbarVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
        boolean doneBtnVisibility;


    }

    private class ListDoneState {
        boolean toolbarVisibility;
        boolean deleteBtnVisibility;
        Task TaskDone;
    }

    private class ListSubjectState {
        boolean toolbarVisibility;
        boolean textVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
    }


    private class AddTaskState {
        boolean toolbarVisibility;
        boolean addBtnVisibility;
        boolean deleteBtnVisibility;
    }

    private class PreferencesState {
        boolean toolbarVisibility;
        int toolbarColour;
        boolean toolbarColourChanged = false;
    }

    private class ScheduleState {
        boolean toolbarVisibility;
    }

    private class DetailToDoState {
        boolean toolbarVisible;
        Task selectedItem;
        public ListToDoMaster.MasterListToDetail master;
    }

    private class DetailDoneState {
        boolean toolbarVisible;
        Task selectedItem;
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

}
