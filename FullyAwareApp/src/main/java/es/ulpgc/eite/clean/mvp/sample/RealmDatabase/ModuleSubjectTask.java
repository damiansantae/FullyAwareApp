package es.ulpgc.eite.clean.mvp.sample.RealmDatabase;

import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import io.realm.annotations.RealmModule;


@RealmModule(classes = {Subject.class, Task.class})
public class ModuleSubjectTask {
}
