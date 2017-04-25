package es.ulpgc.eite.clean.mvp.sample.realmDatabase;

import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import io.realm.annotations.RealmModule;

/**
 * Created by IvanGlez on 25/04/2017.
 */
@RealmModule(classes = {Subject.class, Task.class})
public class Module {
}
