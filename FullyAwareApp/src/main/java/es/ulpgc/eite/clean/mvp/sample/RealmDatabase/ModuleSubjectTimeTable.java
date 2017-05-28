package es.ulpgc.eite.clean.mvp.sample.RealmDatabase;

import es.ulpgc.eite.clean.mvp.sample.app.TimeTable_NextUpgrade;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import io.realm.annotations.RealmModule;



@RealmModule(classes = {Subject.class, TimeTable_NextUpgrade.class})
public class ModuleSubjectTimeTable {
}
