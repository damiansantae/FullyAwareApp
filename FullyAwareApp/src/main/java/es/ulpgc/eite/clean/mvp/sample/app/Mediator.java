package es.ulpgc.eite.clean.mvp.sample.app;

import android.view.View;

import es.ulpgc.eite.clean.mvp.sample.addTask.AddTask;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneDetail.ListDoneViewDetail;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneMaster;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMaster;
import es.ulpgc.eite.clean.mvp.sample.listSubjects.ListSubjectPresenter;
import es.ulpgc.eite.clean.mvp.sample.listToDoDetail.ListToDoDetail;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;
import es.ulpgc.eite.clean.mvp.sample.preferences.Preferences;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesPresenter;
import es.ulpgc.eite.clean.mvp.sample.preferences.PreferencesView;
import es.ulpgc.eite.clean.mvp.sample.schedule_NextUpgrade.Schedule;

public interface Mediator {

    /**
     * This method gives variables states saved in the Mediator to the ListToDo
     * Presenter it is called when Presenter is need to be created
     * @param presenter the Presenter which needs to be created
     */
    void startingListToDoScreen(ListToDoMaster.ToListToDo presenter);

    void startingListDoneScreen(ListDoneMaster.ToListDone presenter);
    
    void startingAddTaskScreen(AddTask.ToAddTask presenter);

    void startingPreferencesScreen(Preferences.ToPreferences presenter);
    
    void startingDetailScreen(ListToDoDetail.MasterListToDetail listToDoPresenterDetail);

    void startingScheduleScreen(Schedule.ToSchedule presenter);

    void Task(Task TaskDone);

    void startingDetailScreen(ListDoneDetail.MasterListToDetail listDonePresenterDetail);

    String getToolbarColour();

    boolean checkToolbarChanged();

    void startingListSubjectScreen(ListSubjectPresenter listSubjectsPresenter);

    void setToolbarColorChanged(PreferencesView view, boolean toolbarColorChanged);

    void setToolbarColour(PreferencesView view, int newColor);
}
