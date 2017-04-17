package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.TaskToDo;

public class ListToDoViewMasterTesting
        extends GenericActivity<ListToDoMaster.PresenterToView, ListToDoMaster.ViewToPresenter, ListToDoPresenterMaster>
        implements ListToDoMaster.PresenterToView {

    private Toolbar toolbar;
    private Button button;
    private TextView text;
    private RecyclerView recyclerView;
    private FloatingActionButton bin;
    private FloatingActionButton add;
    private FloatingActionButton done;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;

    enum Direction {LEFT, RIGHT}

    private SparseBooleanArray tasksSelected;

    private TaskRecyclerViewAdapter adapter;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtodo);

        ////////////////////////////////////////////////////////////
        bin = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);
        add = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        done = (FloatingActionButton) findViewById(R.id.floatingDoneButton);
        ///////////////////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.item_list_recycler);
        adapter = new TaskRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        /*recyclerView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        historicX = event.getX();
                        historicY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        if (event.getX() - historicX < -DELTA) {
                            int posicion = 0;
                            getPresenter().onSwipeMade(posicion, adapter);
                            adapter.notifyDataSetChanged();
                            Log.d("error msg", "SWIPE MODE");
                            return true;
                        }
                        else if (event.getX() - historicX > DELTA) {
                //Swipe hacia el otro lado;
                            return true;
                        }
                        break;

                    default:
                        return false;
                }
                return false;
            }
        });*/


        bin.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       // getPresenter().onBinBtnClick(adapter);

                                       adapter.notifyDataSetChanged();
                                   }

                               }
        );

        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       getPresenter().onAddBtnClick();

                                       //adapter.notifyDataSetChanged();
                                   }

                               }
        );
        done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // getPresenter().onDoneBtnClick(adapter);
                                        // recyclerView.clearChoices();
                                        adapter.notifyDataSetChanged();
                                    }

                                }
        );

        ////////////////////////////////////////////////////////

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListToDoPresenterMaster.class, this);
    }


    //Este metodo sirve para inflar el menu en la action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listtodo_master_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

        } else if (id == R.id.menuToDo) {

            Toast.makeText(getApplicationContext(), "ToDo", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuDone) {
            Navigator app = (Navigator) getApplication();
            app.goToListDoneScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menucalendar) {
            Navigator app = (Navigator) getApplication();
            app.goToScheduleScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Calendar", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuForgotten) {
            Navigator app = (Navigator) getApplication();
            app.goToListForgottenScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Forgotten", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.menuPreferences) {
            Navigator app = (Navigator) getApplication();
            app.goToPreferencesScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "PULSADO");
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
    public void hideAddBtn() {
        add.setVisibility(View.INVISIBLE);


    }

    @Override
    public void hideDoneBtn() {
        done.setVisibility(View.INVISIBLE);


    }

    @Override
    public void showDoneBtn() {
        done.setVisibility(View.VISIBLE);


    }

    @Override
    public void deselect(int i, boolean b) {
        //recyclerView.setItemChecked(i,b);

    }

    @Override
    public void showAddBtn() {
        add.setVisibility(View.VISIBLE);


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
    public void setChoiceMode(int i) {
       /* if (i == 0) {               //Modo de seleccion nulo
            recyclerView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
            recyclerView.invalidateViews();

        } else if (i == 1) {             //Modo de seleccion unico
            recyclerView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        } else if (i == 2) {             ///Modo de seleccion multiple
            recyclerView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);


        } else {
            Log.d("error msg", "error desconocido de al seleccionar modo de seleccionamiento");
        }*/
    }

    @Override
    public void setRecyclerAdapterContent(List<TaskToDo> items) {
        if (recyclerView != null) {
            TaskRecyclerViewAdapter recyclerAdapter =
                    (TaskRecyclerViewAdapter) recyclerView.getAdapter();
            recyclerAdapter.setItemList(items);
        }
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


    public class TaskRecyclerViewAdapter
            extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

        private List<TaskToDo> items;

        public TaskRecyclerViewAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        public void setItemList(List<TaskToDo> items) {
            this.items = items;
            notifyDataSetChanged();
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            TaskToDo task = items.get(position);
            holder.bindView(task);

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

            public TaskToDo item;

            public ViewHolder(View view) {
                super(view);

                itemView = view;

            }

            /* @Override
             public String toString() {
                 return super.toString() + " '" + contentView.getText() + "'";
             }*/
            public void bindView(TaskToDo task) {
                tag = (ImageView) itemView.findViewById(R.id.tag);
                title = (TextView) itemView.findViewById(R.id.title);
                description = (TextView) itemView.findViewById(R.id.description);
                date = (TextView) itemView.findViewById(R.id.date);

                tag.setImageResource(task.getTagId());
                title.setText(task.getTitle());
                description.setText(task.getDescription());
                date.setText(task.getDate());

                //Selecciona si estaba seleccionado
              itemView.setSelected(getPresenter().isSelected(getAdapterPosition()));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().onListClick2(itemView, getAdapterPosition(),adapter);

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
}





