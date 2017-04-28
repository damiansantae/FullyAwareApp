package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.intro.PrefManager;

public class ListSubjectView
        extends GenericActivity<ListSubject.PresenterToView, ListSubject.ViewToPresenter, ListSubjectPresenter>
        implements ListSubject.PresenterToView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton bin;
    private SubjectRecyclerViewAdapter adapter;
    int counterSubject;
    private PrefManager prefManager;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";
    private String[] time;
    ArrayList<String> indexHours = new ArrayList<String>();
    ArrayList<String> indexDays = new ArrayList<String>();
    Map<String,ArrayList<String>> HoursArays = new HashMap<String, ArrayList<String>>();
    Map<String,ArrayList<String>> DaysArays = new HashMap<String, ArrayList<String>>();

    ArrayList<String> subjectList = new ArrayList<>();
    int numberOfSubjects;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsubject);

        bin = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);
        recyclerView = (RecyclerView) findViewById(R.id.item_list_recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        adapter = new SubjectRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        bin.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
        getPresenter().onBinBtnClick2(adapter);
        adapter.notifyDataSetChanged(); }

                               });

        setSupportActionBar(toolbar);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        loadSharePreferences();
        Mediator mediator = (Mediator) getApplication();
        time = new String[5];
    }

    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListSubjectPresenter.class, this);
    }


    //Este metodo sirve para inflar el menu en la action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listtodo_master_forgotten, menu);
        return true;
    }


    ///
    private void loadSharePreferences() {
        Log.d(TAG, "calling loadSharePreferences");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String colour = prefs.getString(TOOLBAR_COLOR_KEY, null);
        Log.d(TAG, "" + colour);
        if (colour != null) {
            toolbarChanged(colour);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id ==R.id.menuToDo){
            Navigator app = (Navigator) getApplication();
            app.goToListToDoScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(), "ToDo", Toast.LENGTH_SHORT).show();

        }
        else if (id ==R.id.menuDone){
            Navigator app = (Navigator) getApplication();
            app.goToListDoneScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
        }
        else if (id ==R.id.menucalendar){
            Navigator app = (Navigator) getApplication();
            app.goToScheduleScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(),"Calendar",Toast.LENGTH_SHORT).show();
        }
         else if (id ==R.id.menuPreferences) {
            Navigator app = (Navigator) getApplication();
            app.goToPreferencesScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Pasando a pantalla Preferencias");
        }

        return super.onOptionsItemSelected(item);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Presenter To View /////////////////////////////////////////////////////////////

    @Override
    public void finishScreen() {
        finish();
    }


    @Override
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }



    @Override
    public void deselect(int i, boolean b) {
        //recyclerView.setItemChecked(i,b);
    }

    @Override
    public void hideDeleteBtn() {
        bin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDeleteBtn() {
        bin.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean isItemListChecked(int pos) {
        //return recyclerView.isItemChecked(pos);
        return false;
    }

    @Override
    public void setItemChecked(int pos, boolean checked) {
        // recyclerView.setItemChecked(pos, checked);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startSelection() {
        // recyclerView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

    }

    @Override
    public void setToastDelete() {
        Toast.makeText(getApplicationContext(), "Tarea Eliminada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setChoiceMode(int i) {
    }

    @Override
    public void setRecyclerAdapterContent(List<Subject> items) {
        if (recyclerView != null) {
            SubjectRecyclerViewAdapter recyclerAdapter =
                    (SubjectRecyclerViewAdapter) recyclerView.getAdapter();
            recyclerAdapter.setItemList(items);
        }
    }

    @Override
    public void toolbarChanged(String colour) {
        List<String> colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        List<String> colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
    }

    @Override
    public void setTimeText(int i, String txt) {
            time[i] = txt;
    }




    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ListForgottenView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    ///////////////////////////////////////////////////////////////


    public class SubjectRecyclerViewAdapter
            extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.ViewHolder> {

        private List<Subject> items;

        public SubjectRecyclerViewAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        public void setItemList(List<Subject> items) {
            this.items = items;
            notifyDataSetChanged();
        }


        public List<Subject> getItems(){
            return this.items;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Subject subject = items.get(position);
            holder.bindView(subject);

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View itemView;
            private ImageView tag;
            private TextView title;
            private TextView description;
            private TextView date;

            public Subject item;

            public ViewHolder(View view) {
                super(view);

                itemView = view;

            }


            public void bindView(final Subject subject) {
                tag = (ImageView) itemView.findViewById(R.id.tag);
                title = (TextView) itemView.findViewById(R.id.title);
                description = (TextView) itemView.findViewById(R.id.description);
                date = (TextView) itemView.findViewById(R.id.date);

                //tag.setImageResource(task.getSubject());
                title.setText(subject.getName());
                description.setText(subject.getDescription());
                date.setText(subject.getTimeTable());

                //Selecciona si estaba seleccionado
                itemView.setSelected(getPresenter().isSelected(getAdapterPosition()));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().onListClick2(itemView, getAdapterPosition(),adapter,subject);

                    }
                });
                itemView.setLongClickable(true);
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        getPresenter().onLongListClick2(v,getAdapterPosition());

                        return true;
                    }
                });

            }
        }


    }




    @Override
    public void showAddUserNameDialog() {
        final AddNameDialog dialog = new AddNameDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new AddNameDialog.OnAddUserClickListener() {

            @Override
            public void onAddUserNameClickListener(String userName) {
                getPresenter().onAddUserBtnClicked(userName);
                dialog.dismiss();
                showAddSubjectsDialog();
            }
        });
    }

    public int getCounterSubject(){
        return this.counterSubject;
    }



    @Override
    public void showAddSubjectsDialog() {

        final AddFirstSubjectDialog dialog = new AddFirstSubjectDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        numberOfSubjects = 0;
        dialog.setListener(new AddFirstSubjectDialog.OnAddSubjectClickListener() {
            @Override
            public void onAddSubjectClickListener(String label) {
                EditText etSubjectName = (EditText) dialog.getView().findViewById(R.id.et_subject_name);

                if (label.equals("Finish")){

                    if(numberOfSubjects==0){
                        dialog.dismiss();
                    } else{
                        showAddHourSubjectDialog();
                        dialog.dismiss();
                    }

           } else{
                    switch (numberOfSubjects) {

                        case 0:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_1).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 1:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_2).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 2:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_3).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 3:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_4).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 4:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_5).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 5:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_6).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 6:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_7).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 7:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_8).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 8:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_9).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 9:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_10).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 10:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_11).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 11:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_12).setVisibility(View.VISIBLE);
                            etSubjectName.setText("");
                            dialog.getView().findViewById(R.id.bt_add_subject).setVisibility(View.GONE);
                            break;

                        case 13:
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }



    @Override
    public String getTimeText(int i) {
        return time[i];

    }

    @Override
    public void showAddHourSubjectDialog() {


        //Bucle que genera los indices del HashMap
        for (int i=0; i<subjectList.size(); i++){
         String hours =  "hoursOfSubject"+i;
         String days = "daysOfSubject"+i;
         indexHours.add(hours);
         indexDays.add(days);
    }

        //Bucle que genera los arrays para cada Asignatura
        for (int i=0; i<subjectList.size(); i++){
         HoursArays.put(indexHours.get(i),new ArrayList<String>());
         DaysArays.put(indexDays.get(i),new ArrayList<String>());
        }



           final AddHourSubjectDialog dialog = new AddHourSubjectDialog();
           dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
           prefManager = new PrefManager(this);
           if (!prefManager.isFirstTimeLaunch()) {
               prefManager.setFirstTimeLaunch(true); //TODO:Change that to make it work just once
               counterSubject = 0;
           }

           dialog.setListener(new AddHourSubjectDialog.OnAddHourSubjectClickListener() {
               @Override
               public void onAddHourSubjectClickListener(String label) {
                   Log.d("COUNTER TAG", ""+label);

                   //Boton Floating Add Pulsado
                   if (label.equals(getPresenter().getLabelFloatingAdd())){
                       Log.d("ADD HOUR BUTTON TAG", "BUTTON ADD HOUR CLICKED");
                       if (counterSubject == 0){
                           dialog.getView().findViewById(R.id.time_2).setVisibility(View.VISIBLE);
                           dialog.getView().findViewById(R.id.fb_delete).setVisibility(View.VISIBLE);
                           prefManager.setCounterSubject(counterSubject++);

                       } else if (counterSubject==1){
                           dialog.getView().findViewById(R.id.time_3).setVisibility(View.VISIBLE);
                           prefManager.setCounterSubject(counterSubject++);

                       } else if (counterSubject==2) {
                           dialog.getView().findViewById(R.id.time_4).setVisibility(View.VISIBLE);
                           prefManager.setCounterSubject(counterSubject++);

                       } else if(counterSubject==3) {
                           dialog.getView().findViewById(R.id.time_5).setVisibility(View.VISIBLE);
                           dialog.getView().findViewById(R.id.fb_add).setVisibility(View.INVISIBLE);
                           prefManager.setCounterSubject(counterSubject++);
                       }
                   }

                   //Boton Floating Delete Pulsado
                   if (label.equals(getPresenter().getLabelFloatingDelete())){
                       Log.d("DELETE HOUR BUTTON TAG", "BUTTON DELETE HOUR CLICKED");
                       if (counterSubject == 4){
                           dialog.getView().findViewById(R.id.time_5).setVisibility(View.GONE);
                           dialog.getView().findViewById(R.id.fb_add).setVisibility(View.VISIBLE);
                           prefManager.setCounterSubject(counterSubject--);

                           //Por si ha sido modificado por el picker
                           Button bt5 = (Button)dialog.getView().findViewById(R.id.bt_hour_5);
                           bt5.setText("HOUR");
                           setTimeText(4,"");

                       } else if (counterSubject==3){
                           dialog.getView().findViewById(R.id.time_4).setVisibility(View.GONE);
                           prefManager.setCounterSubject(counterSubject--);


                           //Por si ha sido modificado por el picker
                           Button bt4 = (Button)dialog.getView().findViewById(R.id.bt_hour_4);
                           bt4.setText("HOUR");
                           setTimeText(3,"");

                       } else if (counterSubject==2) {
                           dialog.getView().findViewById(R.id.time_3).setVisibility(View.GONE);
                           prefManager.setCounterSubject(counterSubject--);


                           //Por si ha sido modificado por el picker
                           Button bt3 = (Button)dialog.getView().findViewById(R.id.bt_hour_3);
                           bt3.setText("HOUR");
                           setTimeText(2,"");

                       } else if(counterSubject==1) {
                           dialog.getView().findViewById(R.id.time_2).setVisibility(View.GONE);
                           dialog.getView().findViewById(R.id.fb_delete).setVisibility(View.INVISIBLE);
                           prefManager.setCounterSubject(counterSubject--);

                           //Por si ha sido modificado por el picker
                           Button bt2 = (Button)dialog.getView().findViewById(R.id.bt_hour_2);
                           bt2.setText("HOUR");
                           setTimeText(1,"");


                       }
                   }
               }

               @Override
               public void onAddHourSubjectClickListener(int i) {

                   if (i==5){
                       Log.d("PRUEBA CONTENIDO",""+time[0]);
                       Log.d("PRUEBA CONTENIDO",""+time[1]);
                       Log.d("PRUEBA CONTENIDO",""+time[2]);
                       Log.d("PRUEBA CONTENIDO",""+time[3]);
                       Log.d("PRUEBA CONTENIDO",""+time[4]);
                       getPresenter().getCheckedBoxes(dialog);


                   } else{
                       getPresenter().onSelectTimeBtnClicked(i, dialog);
                       getPresenter().setTimeLabelOnButton(i,dialog);

                   }
               }


           });










    }








/*

    private void findCheckBoxesChecked(AddHourSubjectDialog dialog) {
        prefManager = new PrefManager(dialog.getContext());
        int counterSubject = prefManager.getCounterSubject();
        Log.d("CHECK COUNTER", "" + counterSubject);
        Log.d("CHECK SELECTION TAG", "ENTRA AL METODO");

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


        HashMap<String,String> daysChecked = new HashMap<String,String>();

            if (c1l1.isChecked()) {
                daysChecked.put("M1","Monday");
            } else if (c2l1.isChecked()) {
                daysChecked.put("T1","Tuesday");
            } else if (c3l1.isChecked()) {
                daysChecked.put("W1","Wednesday");
            } else if (c4l1.isChecked()) {
                daysChecked.put("X1","Thursday");
            } else if (c5l1.isChecked()) {
                daysChecked.put("F1","Friday");
            } else if (c6l1.isChecked()) {
                daysChecked.put("S1","Saturday");
            } else if (c7l1.isChecked()) {
                daysChecked.put("Sn1","Sunday");
            }

Log.d("PRUEBAAA", String.valueOf(daysChecked.values()));


;
      /*
        if (counterSubject == 1) {
            if (c1l2.isChecked()) {
                daysChecked.add("Monday");
            } else if (c2l2.isChecked()) {
                daysChecked.add("Tuesdar");
            } else if (c3l2.isChecked()) {
                daysChecked.add("Wednesday");
            } else if (c4l2.isChecked()) {
                daysChecked.add("Thursday");
            } else if (c5l3.isChecked()) {
                daysChecked.add("Friday");
            } else if (c6l4.isChecked()) {
                daysChecked.add("Saturday");
            } else if (c7l5.isChecked()) {
                daysChecked.add("Sunday");
            }
        }
        if (counterSubject == 2) {
            if (c1l3.isChecked()) {
                daysChecked.add("Monday");
            } else if (c2l3.isChecked()) {
                daysChecked.add("Tuesday");
            } else if (c3l3.isChecked()) {
                daysChecked.add("Wednesday");
            } else if (c4l3.isChecked()) {
                daysChecked.add("Thursday");
            } else if (c5l3.isChecked()) {
                daysChecked.add("Friday");
            } else if (c6l3.isChecked()) {
                daysChecked.add("Saturday");
            } else if (c7l3.isChecked()) {
                daysChecked.add("Sunday");
            }
        }
        if (counterSubject == 3) {
            if (c1l4.isChecked()) {
                daysChecked.add("Monday");
            } else if (c2l4.isChecked()) {
                daysChecked.add("Tuesdar");
            } else if (c3l4.isChecked()) {
                daysChecked.add("Wednesday");
            } else if (c4l4.isChecked()) {
                daysChecked.add("Thursday");
            } else if (c5l4.isChecked()) {
                daysChecked.add("Friday");
            } else if (c6l4.isChecked()) {
                daysChecked.add("Saturday");
            } else if (c7l4.isChecked()) {
                daysChecked.add("Sunday");
            }
        }
        if (counterSubject == 4) {
            if (c1l5.isChecked()) {
                daysChecked.add("Monday");
            } else if (c2l5.isChecked()) {
                daysChecked.add("Tuesdar");
            } else if (c3l5.isChecked()) {
                daysChecked.add("Wednesday");
            } else if (c4l5.isChecked()) {
                daysChecked.add("Thursday");
            } else if (c5l5.isChecked()) {
                daysChecked.add("Friday");
            } else if (c6l5.isChecked()) {
                daysChecked.add("Saturday");
            } else if (c7l5.isChecked()) {
                daysChecked.add("Sunday");
            }
        }*/

        //TODO:RECOGER HORAS DEL PICKER Y AÑADIRLAS A LOS ARRAY

    }



   /* @Override
    public void showAddSubjectsDialog() {
        final AddHourSubjectDialog dialog = new AddHourSubjectDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        counterSubject =0;
        prefManager = new PrefManager(this);
        prefManager.setCounterSubject(counterSubject);
        if (!prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(true); //TODO:Change that to make it work just once
        }


        dialog.setListener(new AddHourSubjectDialog.OnAddHourSubjectClickListener() {
            @Override
            public void onAddHourSubjectClickListener(String label) {
                Log.d("COUNTER TAG", ""+label);

        //Boton Floating Add Pulsado
              if (label.equals(getPresenter().getLabelFloatingAdd())){
                  Log.d("ADD HOUR BUTTON TAG", "BUTTON ADD HOUR CLICKED");
                    if (counterSubject == 0){
                        dialog.getView().findViewById(R.id.time_2).setVisibility(View.VISIBLE);
                        dialog.getView().findViewById(R.id.fb_delete).setVisibility(View.VISIBLE);
                        prefManager.setCounterSubject(counterSubject++);

                    } else if (counterSubject==1){
                        dialog.getView().findViewById(R.id.time_3).setVisibility(View.VISIBLE);
                        prefManager.setCounterSubject(counterSubject++);

                    } else if (counterSubject==2) {
                        dialog.getView().findViewById(R.id.time_4).setVisibility(View.VISIBLE);
                        prefManager.setCounterSubject(counterSubject++);

                    } else if(counterSubject==3) {
                        dialog.getView().findViewById(R.id.time_5).setVisibility(View.VISIBLE);
                        dialog.getView().findViewById(R.id.fb_add).setVisibility(View.INVISIBLE);
                        prefManager.setCounterSubject(counterSubject++);
                    }
                }

        //Boton Floating Delete Pulsado
                if (label.equals(getPresenter().getLabelFloatingDelete())){
                    Log.d("DELETE HOUR BUTTON TAG", "BUTTON DELETE HOUR CLICKED");
                    if (counterSubject == 4){
                        dialog.getView().findViewById(R.id.time_5).setVisibility(View.GONE);
                        dialog.getView().findViewById(R.id.fb_add).setVisibility(View.VISIBLE);
                        prefManager.setCounterSubject(counterSubject--);

                    } else if (counterSubject==3){
                        dialog.getView().findViewById(R.id.time_4).setVisibility(View.GONE);
                        prefManager.setCounterSubject(counterSubject--);

                    } else if (counterSubject==2) {
                        dialog.getView().findViewById(R.id.time_3).setVisibility(View.GONE);
                        prefManager.setCounterSubject(counterSubject--);

                    } else if(counterSubject==1) {
                        dialog.getView().findViewById(R.id.time_2).setVisibility(View.GONE);
                        dialog.getView().findViewById(R.id.fb_delete).setVisibility(View.INVISIBLE);
                        prefManager.setCounterSubject(counterSubject--);
                    }
                }

        //Boton AddSubject Pulsado
                if (label.equals((getPresenter().getLabelBtnAddSubject()))){
                    Log.d("ADD SUBJECT BUTTON TAG", "BUTTON ADD SUBJECT CLICKED");
                    findCheckBoxesChecked(dialog);
                    //findHours();
                }





                }

            @Override
            public void onAddHourSubjectClickListener(int i) {
                    getPresenter().onSelectTimeBtnClicked(i);
            }


        });*/


