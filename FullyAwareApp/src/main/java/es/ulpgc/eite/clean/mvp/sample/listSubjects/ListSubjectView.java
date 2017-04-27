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
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Mediator;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;

public class ListSubjectView
        extends GenericActivity<ListSubject.PresenterToView, ListSubject.ViewToPresenter, ListSubjectPresenter>
        implements ListSubject.PresenterToView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton bin;
    int counterSubject;
    private SparseBooleanArray subjectsSelected;
    private SubjectRecyclerViewAdapter adapter;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";

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

    private int getCounterSubject(){
        return this.counterSubject;
    }


    @Override
    public void showAddSubjectsDialog() {
        final AddSubjectDialog dialog = new AddSubjectDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        counterSubject =0;
        Log.d("COUNTER TAG PRUEBA", "" + counterSubject);
        dialog.setListener(new AddSubjectDialog.OnAddSubjectClickListener() {
            @Override
            public void onAddSubjectClickListener(String label) {
                Log.d("COUNTER TAG", ""+label);


              if (label.equals(getPresenter().getLabelFloatingAdd())){ //Boton Floating Add Pulsado
                    if (counterSubject == 0){
                        dialog.getView().findViewById(R.id.time_2).setVisibility(View.VISIBLE);
                        dialog.getView().findViewById(R.id.fb_delete).setVisibility(View.VISIBLE);
                        counterSubject++;

                    } else if (counterSubject==1){
                        dialog.getView().findViewById(R.id.time_3).setVisibility(View.VISIBLE);
                        counterSubject++;

                    } else if (counterSubject==2) {
                        dialog.getView().findViewById(R.id.time_4).setVisibility(View.VISIBLE);
                        counterSubject++;

                    } else if(counterSubject==3) {
                        dialog.getView().findViewById(R.id.time_5).setVisibility(View.VISIBLE);
                        counterSubject++;
                        dialog.getView().findViewById(R.id.fb_add).setVisibility(View.INVISIBLE);
                    } else {
                    //TODO:Toast de aviso que no se puede añadir mas horarios.
                    }

                }

                if (label.equals(getPresenter().getLabelFloatingDelete())){
                    Log.d("DELETE BUTTON TAG", "ENTRA AL BOTON");
                    if (counterSubject == 4){
                        dialog.getView().findViewById(R.id.time_5).setVisibility(View.GONE);
                        counterSubject--;
                        dialog.getView().findViewById(R.id.fb_add).setVisibility(View.VISIBLE);
                    } else if (counterSubject==3){
                        dialog.getView().findViewById(R.id.time_4).setVisibility(View.GONE);
                        counterSubject--;

                    } else if (counterSubject==2) {
                        dialog.getView().findViewById(R.id.time_3).setVisibility(View.GONE);
                        counterSubject--;

                    } else if(counterSubject==1) {
                        dialog.getView().findViewById(R.id.time_2).setVisibility(View.GONE);
                        counterSubject--;
                        dialog.getView().findViewById(R.id.fb_delete).setVisibility(View.INVISIBLE);
                    } else {
                        //TODO:Toast de aviso que no se puede añadir mas horarios.
                    }


                }

                }




    });
}
}
