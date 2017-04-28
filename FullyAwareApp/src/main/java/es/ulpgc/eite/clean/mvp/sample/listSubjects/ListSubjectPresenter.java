package es.ulpgc.eite.clean.mvp.sample.listSubjects;


import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.intro.PrefManager;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

public class ListSubjectPresenter extends GenericPresenter
        <ListSubject.PresenterToView, ListSubject.PresenterToModel, ListSubject.ModelToPresenter, ListSubjectModel>
        implements ListSubject.ViewToPresenter, ListSubject.ModelToPresenter, ListSubject.ListSubjectTo, ListSubject.ToListSubject{


    private boolean toolbarVisible;
    private boolean deleteBtnVisible;
    private boolean textVisible;
    private boolean listClicked;
    private Subject subject;
    private Subject selectedSubject;
    private boolean selectedState;
    private ArrayList<Subject> subjectsSelected = new ArrayList<>();
    private ArrayList<String> posSelected = new ArrayList<>();
    private SparseBooleanArray itemsSelected =new SparseBooleanArray();
    private DatabaseFacade database;
    private PrefManager prefManager;



    /**
     * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
     * Responsible to initialize MODEL.
     * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
     * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onCreate(ListSubject.PresenterToView view) {
        super.onCreate(ListSubjectModel.class, this);
        setView(view);
        Log.d(TAG, "calling onCreate()");

        Log.d(TAG, "calling startingLisToDoScreen()");
        Mediator app = (Mediator) getView().getApplication();
        database = new DatabaseFacade();
        app.startingListSubjectScreen(this);
        if (app.checkToolbarChanged() == true){
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }

        prefManager = new PrefManager(getView().getActivityContext());
        Log.d(TAG,""+prefManager.isFirstTimeLaunch());

        if (prefManager.isFirstTimeLaunch()) {
            getView().showAddUserNameDialog();
        }

        setLabelButtons(getView().getActivityContext());

    }

    private void setLabelButtons(Context activityContext) {
        getModel().setLabelButtons();
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call {@link GenericPresenter#setView(ContextView)}
     * to save the new instance of PresenterToView
     *
     * @param view The current VIEW instance
     */
    @Override
    public void onResume(ListSubject.PresenterToView view) {
        setView(view);
        Log.d(TAG, "calling onResume()");

        if (configurationChangeOccurred()) {
            checkDeleteBtnVisibility();

            if(listClicked) {
                getView().startSelection();
                onCheckItems();
            }
        }

        Mediator app = (Mediator) getView().getApplication();
        if (app.checkToolbarChanged() == true){
            String colour = app.getToolbarColour();
            getView().toolbarChanged(colour);
        }
        loadItems();
    }




    /**
     * Selecciona los elementos de la lista que estaban seleccionados
     */
    private void onCheckItems() {
        for(int i=0; i<posSelected.size();i++){
            setItemChecked(Integer.parseInt(posSelected.get(i)), true);
        }

    }


    /**
     * Helper method to inform Presenter that a onBackPressed event occurred
     * Called by {@link GenericActivity}
     */
    @Override
    public void onBackPressed() {
        Log.d(TAG, "calling onBackPressed()");
    }

    /**
     * Hook method called when the VIEW is being destroyed or
     * having its configuration changed.
     * Responsible to maintain MVP synchronized with Activity lifecycle.
     * Called by onDestroy methods of the VIEW layer, like: {@link GenericActivity#onDestroy()}
     *
     * @param isChangingConfiguration true: configuration changing & false: being destroyed
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        super.onDestroy(isChangingConfiguration);
        Log.d(TAG, "calling onDestroy()");
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // View To Presenter /////////////////////////////////////////////////////////////

    /*@Override
    public void onButtonClicked() {
        Log.d(TAG, "calling onButtonClicked()");
        if (isViewRunning()) {
            getModel().onChangeMsgByBtnClicked();
            getView().setText(getModel().getText());
            textVisible = true;
            buttonClicked = true;
        }
        checkTextVisibility();
    }*/




    @Override
    public void onListClick2(View v, int adapterPosition, ListSubjectView.SubjectRecyclerViewAdapter adapter, Subject subject) {
        if(selectedState){
            if(!v.isSelected()){
                v.setSelected(true);
                itemsSelected.put(adapterPosition,true);

            }else{
                v.setSelected(false);
                itemsSelected.put(adapterPosition,false);

            }
        }else{
            Navigator app = (Navigator) getView().getApplication();
            selectedSubject = subject;
            app.goToDetailScreen(this, adapter);
        }
        checkSelection();
       checkDeleteBtnVisibility();

    }

    @Override
    public void onLongListClick2(View v, int adapterPosition) {
        if(!selectedState){
            selectedState =true;
            v.setSelected(true);
            itemsSelected.put(adapterPosition,true);

        }

        checkSelection();

        checkDeleteBtnVisibility();




    }

    @Override
    public boolean isSelected(int adapterPosition) {
        boolean result = false;
        if(itemsSelected.size()!=0) {

            if (itemsSelected.get(adapterPosition)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void onBinBtnClick2(ListSubjectView.SubjectRecyclerViewAdapter adapter) {

        ArrayList<Subject> selected = getSelectedSubjects(adapter);
        for(int i=0;i<selected.size();i++){
            database.deleteDatabaseItem(selected.get(i));
        }
        itemsSelected.clear();
        checkSelection();

        checkDeleteBtnVisibility();


    }
    private ArrayList<Subject> getSelectedSubjects(ListSubjectView.SubjectRecyclerViewAdapter adapter) {
        ArrayList<Subject> selected = new ArrayList<>();
        for(int i=0;i<adapter.getItemCount();i++){
            if(itemsSelected.get(i)){
                selected.add(adapter.getItems().get(i));
            }
        }
        return selected;
    }

    private void deselectAll() {

        for (int k = 0; k < posSelected.size(); k++) {
            getView().deselect(Integer.parseInt(posSelected.get(k)), false);
        }

        posSelected.clear();
        subjectsSelected.clear();
    }

    private void checkSelection() {
        boolean somethingSelected= false;
        for(int i = 0; i < itemsSelected.size(); i++) {
            int key = itemsSelected.keyAt(i);
            // get the object by the key.
            Object obj = itemsSelected.get(key);
            if(obj.equals(true)){
                somethingSelected=true;
                break;
            }


        }

        if(somethingSelected){

            setDeleteBtnVisibility(true);
        }else{

            setDeleteBtnVisibility(false);
            selectedState=false;
        }


    }

    private void setItemChecked(int pos, boolean checked) {
        getView().setItemChecked(pos, checked);

    }

    private boolean isItemListChecked(int pos) {
        boolean result=false;
        if(posSelected.size()>0 && posSelected.contains(Integer.toString(pos))) {             //Si el array de posiciones de tareas no esta vacio y ademas contiene la posicion de la tarea a consultar
                result = true;                                                      //Entonces si estaba seleccionado
        }
        return result;
        }




    ///////////////////////////////////////////////////////////////////////////////////
    // To ListForgottenDetail //////////////////////////////////////////////////////////////////////

    @Override
    public void onScreenStarted() {
        Log.d(TAG, "calling onScreenStarted()");
   /* if(isViewRunning()) {
      getView().setLabel(getModel().getLabel());
    }
    //checkToolbarVisibility();
    //checkTextVisibility();*/

        checkDeleteBtnVisibility();
        loadItems();
    }



    @Override
    public void setToolbarVisibility(boolean visible) {
        toolbarVisible = visible;
    }

    @Override
    public void setTextVisibility(boolean visible) {
        textVisible = visible;
    }



    @Override
    public void setDeleteBtnVisibility(boolean deleteBtnVisibility) {
        deleteBtnVisible=deleteBtnVisibility;

    }




    ///////////////////////////////////////////////////////////////////////////////////
    // ListForgottenDetail To //////////////////////////////////////////////////////////////////////


    @Override
    public Context getManagedContext() {
        return getActivityContext();
    }

    public Subject getSelectedSubject() {
        return selectedSubject;
    }

    @Override
    public boolean getToolbarVisibility() {
        return toolbarVisible;
    }

    @Override
    public void onAddUserBtnClicked(String userName) {
        prefManager.setUserName(userName);
        Log.d("TAG", "añadido con éxito el usuario");
        Log.d("TAG", prefManager.getUserName());
    }

    @Override
    public String getLabelFloatingAdd() {
        return getModel().getLabelFloatingAdd();
    }

    @Override
    public String getLabelFloatingDelete() {
        return getModel().getLabelFloatingDelete();
    }

    @Override
    public String getLabelBtnAddSubject() {
        return getModel().getLabelBtnAddSubject();
    }

    @Override
    public String getLabelBtnHour() {
        return getModel().getLabelBtnHour();
    }

    @Override
    public void destroyView() {
        if (isViewRunning()) {
            getView().finishScreen();
        }
    }

    @Override
    public boolean isToolbarVisible() {
        return toolbarVisible;
    }

    @Override
    public boolean isTextVisible() {
        return textVisible;
    }


    ///////////////////////////////////////////////////////////////////////////////////

    private void checkToolbarVisibility() {
        Log.d(TAG, "calling checkToolbarVisibility()");
        if (isViewRunning()) {
            if (!toolbarVisible) {
                getView().hideToolbar();
            }
        }
    }



    private void checkDeleteBtnVisibility() {
        Log.d(TAG, "calling checkDeleteBtnVisibility()");
        if (isViewRunning()) {
            if (!deleteBtnVisible) {
                getView().hideDeleteBtn();
            } else {
                getView().showDeleteBtn();
            }
        }
    }


    public void setListClicked(boolean listClicked) {
        this.listClicked = listClicked;
    }

    @Override
    public void onLoadItemsSubjectStarted() {
        checkToolbarVisibility();
    }

    @Override
    public void onLoadItemsSubjectFinished(List<Subject> itemsFromDatabase) {
        getView().setRecyclerAdapterContent(itemsFromDatabase);
    }
    public void loadItems() {
        /*if(!(database.getValidDatabase()) && !(database.getRunningTask())) {
            startDelayedTask();
        } else {*/
        if(!(database.getRunningTask())){
            Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
          //  onLoadItemsSubjectFinished(database.getForgottenItemsFromDatabase());
        } else {
            Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
            onLoadItemsSubjectStarted();
        }
        //}

    }

    /*private void startDelayedTask() {
        Log.d(TAG, "calling startDelayedTask() method");
        database.setRunningTask(true);
        Log.d(TAG, "calling onLoadItemsSubjectStarted() method");
        onLoadItemsSubjectStarted();

        // Mock Hello: A handler to delay the answer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setItems();
                database.setRunningTask(false);
                database.setValidDatabase(true);
                Log.d(TAG, "calling onLoadItemsSubjectsFinished() method");
                //getPresenter().onLoadItemsSubjectsFinished(items);
                onLoadItemsSubjectsFinished(database.getItemsFromDatabase());
            }
        }, 0);
    }*/

    public void reloadItems() {
        //items = null;

        database.deleteAllDatabaseItems();
        database.setValidDatabase(false);
        loadItems();
    }

    /*
        @Override
        public void onLoadItemsSubjectsFinished(List<subject> items) {
            getView().setRecyclerAdapterContent(items);

        }*/
@Override
public void onErrorDeletingItem(Subject item) {

}

    @Override
    public void onSelectTimeBtnClicked(final int index, final AddHourSubjectDialog dialogA) {
        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getManagedContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                getView().setTimeText(index, hourOfDay+":"+minute);
                setTimeLabelOnButton(index, dialogA);
            }
        }, hours, minutes, false);
        timePicker.show();
    }

    @Override
    public void setTimeLabelOnButton(int i, AddHourSubjectDialog dialog) {

        if (i == 0){
            Button bt_hour1 = (Button)dialog.getView().findViewById(R.id.bt_hour_1);
            bt_hour1.setText(getView().getTimeText(i));
        } else if (i == 1){
            Button bt_hour2 = (Button)dialog.getView().findViewById(R.id.bt_hour_2);
            bt_hour2.setText(getView().getTimeText(i));
        } else if (i == 2){
            Button bt_hour3 = (Button)dialog.getView().findViewById(R.id.bt_hour_3);
            bt_hour3.setText(getView().getTimeText(i));
        } else if (i == 3){
            Button bt_hour4 = (Button)dialog.getView().findViewById(R.id.bt_hour_4);
            bt_hour4.setText(getView().getTimeText(i));
        } else if (i == 4){
            Button bt_hour5 = (Button)dialog.getView().findViewById(R.id.bt_hour_5);
            bt_hour5.setText(getView().getTimeText(i));
        }


    }

    @Override
    public void getCheckedBoxes(AddHourSubjectDialog dialog) {

        //All the layouts that contains the information, ya tu sabe.
        LinearLayout l1 = (LinearLayout) dialog.getView().findViewById(R.id.time_1);
        LinearLayout l2 = (LinearLayout) dialog.getView().findViewById(R.id.time_2);
        LinearLayout l3 = (LinearLayout) dialog.getView().findViewById(R.id.time_3);
        LinearLayout l4 = (LinearLayout) dialog.getView().findViewById(R.id.time_4);
        LinearLayout l5 = (LinearLayout) dialog.getView().findViewById(R.id.time_5);

        //Checkboxes for l1 hour space.
        CheckBox c1l1 = (CheckBox) l1.findViewById(R.id.cb_monday);
        CheckBox c2l1 = (CheckBox) l1.findViewById(R.id.cb_tuesday);
        CheckBox c3l1 = (CheckBox) l1.findViewById(R.id.cb_wednesday);
        CheckBox c4l1 = (CheckBox) l1.findViewById(R.id.cb_thursday);
        CheckBox c5l1 = (CheckBox) l1.findViewById(R.id.cb_friday);
        CheckBox c6l1 = (CheckBox) l1.findViewById(R.id.cb_saturday);
        CheckBox c7l1 = (CheckBox) l1.findViewById(R.id.cb_sunday);

        //Checkboxes for l2 hour space.
        CheckBox c1l2 = (CheckBox) l2.findViewById(R.id.cb_monday);
        CheckBox c2l2 = (CheckBox) l2.findViewById(R.id.cb_tuesday);
        CheckBox c3l2 = (CheckBox) l2.findViewById(R.id.cb_wednesday);
        CheckBox c4l2 = (CheckBox) l2.findViewById(R.id.cb_thursday);
        CheckBox c5l2 = (CheckBox) l2.findViewById(R.id.cb_friday);
        CheckBox c6l2 = (CheckBox) l2.findViewById(R.id.cb_saturday);
        CheckBox c7l2 = (CheckBox) l2.findViewById(R.id.cb_sunday);

        //Checkboxes for l3 hour space.
        CheckBox c1l3 = (CheckBox) l3.findViewById(R.id.cb_monday);
        CheckBox c2l3 = (CheckBox) l3.findViewById(R.id.cb_tuesday);
        CheckBox c3l3 = (CheckBox) l3.findViewById(R.id.cb_wednesday);
        CheckBox c4l3 = (CheckBox) l3.findViewById(R.id.cb_thursday);
        CheckBox c5l3 = (CheckBox) l3.findViewById(R.id.cb_friday);
        CheckBox c6l3 = (CheckBox) l3.findViewById(R.id.cb_saturday);
        CheckBox c7l3 = (CheckBox) l3.findViewById(R.id.cb_sunday);

        //Checkboxes for l4 hour space.
        CheckBox c1l4 = (CheckBox) l4.findViewById(R.id.cb_monday);
        CheckBox c2l4 = (CheckBox) l4.findViewById(R.id.cb_tuesday);
        CheckBox c3l4 = (CheckBox) l4.findViewById(R.id.cb_wednesday);
        CheckBox c4l4 = (CheckBox) l4.findViewById(R.id.cb_thursday);
        CheckBox c5l4 = (CheckBox) l4.findViewById(R.id.cb_friday);
        CheckBox c6l4 = (CheckBox) l4.findViewById(R.id.cb_saturday);
        CheckBox c7l4 = (CheckBox) l4.findViewById(R.id.cb_sunday);

        //Checkboxes for l5 hour space.
        CheckBox c1l5 = (CheckBox) l5.findViewById(R.id.cb_monday);
        CheckBox c2l5 = (CheckBox) l5.findViewById(R.id.cb_tuesday);
        CheckBox c3l5 = (CheckBox) l5.findViewById(R.id.cb_wednesday);
        CheckBox c4l5 = (CheckBox) l5.findViewById(R.id.cb_thursday);
        CheckBox c5l5 = (CheckBox) l5.findViewById(R.id.cb_friday);
        CheckBox c6l5 = (CheckBox) l5.findViewById(R.id.cb_saturday);
        CheckBox c7l5 = (CheckBox) l5.findViewById(R.id.cb_sunday);

        String resultL1= "";
        String resultL2= "";
        String resultL3= "";
        String resultL4= "";
        String resultL5= "";

      if (c1l1.isChecked())
            resultL1 = resultL1+"M";
      if(c2l1.isChecked())
            resultL1 = resultL1+"T";
      if (c3l1.isChecked())
            resultL1 = resultL1+"W";
      if (c4l1.isChecked())
            resultL1 = resultL1+"Th";
      if (c5l1.isChecked())
            resultL1 = resultL1+"F";
      if (c6l1.isChecked())
            resultL1 = resultL1+"S";
      if (c7l1.isChecked())
            resultL1 = resultL1 + "Sn";


      if (c1l2.isChecked())
            resultL2 = resultL2+"M";
      if (c2l2.isChecked())
            resultL2 = resultL2+"T";
      if (c3l2.isChecked())
            resultL2 = resultL2+"W";
      if (c4l2.isChecked())
            resultL2 = resultL2+"Th";
      if (c5l2.isChecked())
            resultL2 = resultL2+"F";
      if (c6l2.isChecked())
            resultL2 = resultL2+"S";
      if (c7l2.isChecked())
            resultL2 = resultL2+"Sn";


      if (c1l3.isChecked())
            resultL3 = resultL3+"M";
      if (c2l3.isChecked())
            resultL3 = resultL3+"T";
      if (c3l3.isChecked())
            resultL3 = resultL3+"W";
      if (c4l3.isChecked())
            resultL3 = resultL3+"Th";
      if (c5l3.isChecked())
            resultL3 = resultL3+"F";
      if (c6l3.isChecked())
            resultL3 = resultL3+"S";
      if (c7l3.isChecked())
            resultL3 = resultL3+"Sn";


        if (c1l4.isChecked())
            resultL4 = resultL4+"M";
     if (c2l4.isChecked())
            resultL4 = resultL4+"T";
      if (c3l4.isChecked())
            resultL4 = resultL4+"W";
        if (c4l4.isChecked())
            resultL4 = resultL4+"Th";
       if (c5l4.isChecked())
            resultL4 = resultL4+"F";
       if (c6l4.isChecked())
            resultL4 = resultL4+"S";
      if (c7l4.isChecked())
            resultL4 = resultL4+"Sn";


        if (c1l5.isChecked())
            resultL5 = resultL5+"M";
       if (c2l5.isChecked())
            resultL5 = resultL5+"T";
     if (c3l5.isChecked())
            resultL5 = resultL5+"W";
       if (c4l5.isChecked())
            resultL5 = resultL5+"Th";
       if (c5l5.isChecked())
            resultL5 = resultL5+"F";
        if (c6l5.isChecked())
            resultL5 = resultL5+"S";
        if (c7l5.isChecked())
            resultL5 = resultL5 + "Sn";


        Log.d("PRUEBA CBOXES",resultL1);
        Log.d("PRUEBA CBOXES",resultL2);
        Log.d("PRUEBA CBOXES",resultL3);
        Log.d("PRUEBA CBOXES",resultL4);
        Log.d("PRUEBA CBOXES",resultL5);



        //TODO:ANALIZAR TODOS LOS CHECKBOX Y PONERLOS DEL TIPO:
        //1) L1:MJK
        //2) L2:MJA
        //3) L3:LWK
        //4) L4: DSD
        //5) L5: SD
        //Y ESTO PARA CADA SUBJECT, ASI QUE SERÁ UN BUCLE QUE LO REPETIRÁ UNA CANTIDAD X COMO NUMERO DE SUBJECTS HAYA.

    }


}
