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

    private static final int ITEM_COUNT = 9;

    private boolean runningTask;
    private Realm realmDatabase;
    private DatabaseFacade database;
    private boolean validDatabase;
    private String errorMsg;
    private boolean usingWrapper;
    private String floatingAddLabel;
    private String floatingDeleteLabel;
    private String btAddSubjectLabel;
    private String btHourLabel;
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
        database =DatabaseFacade.getInstance();
        saveSubject("None",null,null);
        errorMsg = "Error deleting item!";
    }

    private void saveSubject(String other, Object o, Object o1) {
        database.addSubject(other,chooseNewColor());
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


    @Override
    public void deleteItem(Subject item) {
        if (getItemsFromDatabase().contains(item)){
            //items.remove(item);
            deleteDatabaseItem(item);
        } else {
            getPresenter().onErrorDeletingItem(item);
        }
    }

    /**
     * Llamado para recuperar los elementos iniciales de la lista.
     * En este caso siempre se llamará a la tarea asíncrona
     */

    @Override
    public void setDatabaseValidity(boolean valid) {
        validDatabase = valid;
    }

    @Override
    public String getErrorMessage() {
        return errorMsg;
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position).append("\n");
        for (int count = 0; count < position; count++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * Llamado para recuperar los elementos a mostrar en la lista.
     * Consiste en una tarea asíncrona que retrasa un tiempo la obtención del contenido.
     * El modelo notificará al presentador cuando se inicia y cuando finaliza esta tarea.
     */



    /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String getLabelFloatingAdd() {
        return this.floatingAddLabel;
    }

    @Override
    public void setLabelButtons() {
        this.floatingAddLabel = "floatingAdd";
        this.floatingDeleteLabel = "floatingDelete";
        this.btAddSubjectLabel = "buttonAddSubject";
        this.btHourLabel = "buttonHour";
    }

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
    public int chooseNewColor(){
        List<Subject> subjectsFromDB = database.getSubjectsFromDatabase();
        Integer currentColor = 0x0;
        boolean colorFound=false;

        for (Integer color: listOfColors) {
            if (colorFound)break;                                              //Si se ha encontrado un color no utilizado paramos la busqueda de colores
            currentColor =color;

            for(int i =0; i<subjectsFromDB.size();i++){                         //Recorremos las asignaturas para ver sus colores
                if(subjectsFromDB.get(i).getColor().equals(color))break;        //Si el color de una asignatura corresponde con el posible color elegido dejamos de comparar
                                                                                // con el resto de las asignaturas y pasamos al siguiente color de la lista

                if(i==subjectsFromDB.size()-1)colorFound =true;                    //Si al terminar de recorrer todas las asignaturas no se ha encontrado ningn color igual al actual
                                                                                //entonces es que hemos encontrado un color
            }
        }
        return currentColor;
    }

    @Override
    public ArrayList<String> getDaysOfWeek() {
        return this.daysOfWeek;
    }

    @Override
    public void addSubjectsToDataBase(ArrayList<String> subjectList) {
        for (int i=0; i < subjectList.size(); i++){
            database.addSubject(subjectList.get(i),chooseNewColor());
        }

    }

    @Override
    public String getLabelFloatingDelete() {
        return floatingDeleteLabel;
    }

    @Override
    public String getLabelBtnHour() {
        return btHourLabel;
    }

    @Override
    public String getLabelBtnAddSubject() {
        return btAddSubjectLabel;
    }


    private void deleteAllDatabaseItems(){
        for(Subject item: getItemsFromDatabase()){
            deleteDatabaseItem(item);
        }
    }

    private void deleteDatabaseItem(Subject item) {
        final String id = item.getSubjectId();
        realmDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm();

            }
        });
    }

    private List<Subject> getItemsFromDatabase(){
        if(usingWrapper) {
            return getItemsFromDatabaseWrapper();
        }

        return realmDatabase.where(Subject.class).findAll();
    }

    private List<Subject> getItemsFromDatabaseWrapper(){
        Log.d(TAG, "calling getItemsFromDatabaseWrapper() method");
        List<Subject> dbItems = realmDatabase.where(Subject.class).findAll();

        Log.d(TAG, "items=" +  dbItems);
        return dbItems;
    }




}
