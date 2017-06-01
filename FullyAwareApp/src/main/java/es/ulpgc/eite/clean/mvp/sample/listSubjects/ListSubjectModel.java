package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;
import io.realm.Realm;


public class ListSubjectModel extends GenericModel<ListSubject.ModelToPresenter>
        implements ListSubject.PresenterToModel {


    private Realm realmDatabase;
    private DatabaseFacade database;
    private boolean usingWrapper;
    private String finishLabel = "Finish";

    private static final ArrayList<Integer> listOfColors = new ArrayList<Integer>() {{
        add(R.color.color_lightblue);
        add(R.color.color_violet);
        add(R.color.color_red);
        add(R.color.color_lightgreen);
        add(R.color.color_orange);
        add(R.color.color_bluegreen);
        add(R.color.color_brown);
        add(R.color.color_black);
        add(R.color.color_grey);
        add(R.color.color_hardblue);
        add(R.color.color_yellow);

    }};
    private static final ArrayList<String> daysOfWeek = new ArrayList<String>() {{
        add("Monday");
        add("Tuesday");
        add("Wednesday");
        add("Thursday");
        add("Friday");
        add("Saturday");
        add("Sunday");

    }};

    /**
     * Method that recovers a reference to the PRESENTER
     * You must ALWAYS call {@link super#onCreate(Object)} here
     *
     * @param presenter Presenter interface
     */
    @Override
    public void onCreate(ListSubject.ModelToPresenter presenter) {
        super.onCreate(presenter);
        realmDatabase = Realm.getDefaultInstance();
        database = DatabaseFacade.getInstance();
    }

    private void saveSubject(String other, Object o, Object o1) {
        database.addSubject(other, chooseNewColor());
    }

    /**
     * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
     * Usefull for kill/stop activities that could be running on the background Threads
     *
     * @param isChangingConfiguration Informs that a change is occurring on the configuration
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Presenter To Model //////////////////////////////////////////////////////////////

    /**
     * Method to get label of the finish button.
     *
     * @return String label.
     */
    @Override
    public String getFinishLabel() {
        return this.finishLabel;
    }

    /**
     * This method choose an specific color for a subject which will be
     * unique
     *
     * @return an unique color for a subject
     */
    public int chooseNewColor() {
        List<Subject> subjectsFromDB = database.getSubjectsFromDatabase();
        Integer currentColor = 0x0;
        boolean colorFound = false;

        for (Integer color : listOfColors) {
            if (colorFound)
                break;                                              //Si se ha encontrado un color no utilizado paramos la busqueda de colores
            currentColor = color;

            for (int i = 0; i < subjectsFromDB.size(); i++) {                         //Recorremos las asignaturas para ver sus colores
                if (subjectsFromDB.get(i).getColor().equals(color))
                    break;        //Si el color de una asignatura corresponde con el posible color elegido dejamos de comparar
                // con el resto de las asignaturas y pasamos al siguiente color de la lista

                if (i == subjectsFromDB.size() - 1)
                    colorFound = true;                    //Si al terminar de recorrer todas las asignaturas no se ha encontrado ningn color igual al actual
                //entonces es que hemos encontrado un color
            }
        }
        return currentColor;
    }

    /**
     * Method to add subjects to the database with a colour.
     *
     * @param subjectList ArryaList of subjects.
     */
    @Override
    public void addSubjectsToDataBase(ArrayList<String> subjectList) {
        database.addSubject("None", chooseNewColor());
        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                database.addSubject(subjectList.get(i), chooseNewColor());
            }

        }
    }

}
