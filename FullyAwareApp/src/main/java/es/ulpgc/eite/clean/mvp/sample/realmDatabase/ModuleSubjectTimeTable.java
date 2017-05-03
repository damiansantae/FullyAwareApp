package es.ulpgc.eite.clean.mvp.sample.realmDatabase;

import es.ulpgc.eite.clean.mvp.sample.TimeTable;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import io.realm.annotations.RealmModule;



@RealmModule(classes = {Subject.class, TimeTable.class})
public class ModuleSubjectTimeTable {
}
