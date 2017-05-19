package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.Collections;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.intro.PrefManager;
import es.ulpgc.eite.clean.mvp.sample.intro.WelcomeActivity;

public class ListToDoViewMasterTesting
        extends GenericActivity<ListToDoMaster.PresenterToView, ListToDoMaster.ViewToPresenter, ListToDoPresenterMaster>
        implements ListToDoMaster.PresenterToView {

    private Toolbar toolbar;
    private Toolbar downtoolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton bin;
    private FloatingActionButton add;
    private FloatingActionButton done;
    private TextView textWhenIsEmpty;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;

    enum Direction {LEFT, RIGHT}

    private SharedPreferences prefs;
    private SparseBooleanArray tasksSelected;
    private PrefManager prefManager;
    private TaskRecyclerViewAdapter adapter;
    SharedPreferences preferences;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";
    private WelcomeActivity welcome;

    private Paint paint = new Paint();

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

        textWhenIsEmpty = (TextView)findViewById(R.id.textWhenIsEmpty);

        bin.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       // getPresenter().onBinBtnClick(adapter);
                                       getPresenter().onBinBtnClick2(adapter);

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
                                        getPresenter().onDoneBtnClick(adapter);
                                        // recyclerView.clearChoices();
                                        adapter.notifyDataSetChanged();
                                    }

                                }
        );

        ////////////////////////////////////////////////////////


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //downtoolbar= (Toolbar) findViewById(R.id.toolbardown);
        // setSupportActionBar(downtoolbar);
        //downtoolbar.inflateMenu(R.menu.menu_down_bar);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //////////////////////////
        loadSharePreferences();

        initSwipe();

    }

    private void loadSharePreferences() {
        Log.d(TAG, "calling loadSharePreferences");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String colour = prefs.getString(TOOLBAR_COLOR_KEY, null);
        Log.d(TAG, "" + colour);
        if (colour != null) {
            toolbarChanged(colour);
        }
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


        if (id == R.id.menuDone) {
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
            Log.d(TAG, "Pasando a pantalla Preferencias");


        }else if(id==R.id.filter_icon){
            getPresenter().subjectFilter();
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
    public void hideTextWhenIsEmpty(){
        textWhenIsEmpty.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTextWhenIsEmpty(){
        textWhenIsEmpty.setVisibility(View.VISIBLE);
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
    public void setRecyclerAdapterContent(List<Task> items) {
        if (recyclerView != null) {

            TaskRecyclerViewAdapter recyclerAdapter =
                    (TaskRecyclerViewAdapter) recyclerView.getAdapter();
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
    public void setToastDelete() {
        Toast.makeText(getApplicationContext(), "Tarea Eliminada", Toast.LENGTH_LONG).show();

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

    private void initSwipe(){
        ItemTouchHelper.Callback callback =new ItemTouchHelper.Callback() {

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ANIMATION_TYPE_DRAG;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Task currentTask = adapter.getItems().get(viewHolder.getAdapterPosition());

                if (direction == ItemTouchHelper.LEFT){
                    getPresenter().swipeLeft(currentTask);
                    adapter.notifyDataSetChanged();


                } else {
                    getPresenter().swipeRight(currentTask);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.checkmark_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.garbage);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }



    ///////////////////////////////////////////////////////////////


    public class TaskRecyclerViewAdapter
            extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>
    {


        private List<Task> items;


        public TaskRecyclerViewAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        private void setItemList(List<Task> items) {
            this.items = items;
            notifyDataSetChanged();
        }


        public List<Task> getItems() {
            return this.items;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Task task = items.get(position);
            holder.bindView(task);

        }

        @Override
        public int getItemCount() {
            return items.size();
        }


        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(getItems(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(getItems(), i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View itemView;
            private ImageView tag;
            private TextView abrv;
            private Subject subject;
            private TextView title;
            private TextView description;
            private TextView date;

            public Task item;

            public ViewHolder(View view) {
                super(view);

                itemView = view;

            }


            public void bindView(final Task task) {
                subject = task.getSubject();
                Integer color = subject.getColor();
                tag = (ImageView) itemView.findViewById(R.id.color_subject);
                abrv = (TextView) itemView.findViewById(R.id.tag_subjectc);
                title = (TextView) itemView.findViewById(R.id.title);
                description = (TextView) itemView.findViewById(R.id.description);
                date = (TextView) itemView.findViewById(R.id.date);

                Drawable drawable = getDrawable(R.drawable.circle);
                drawable.setColorFilter(getColor(color), PorterDuff.Mode.SRC_OVER);
                String abrev = getPresenter().getCases(task);
                title.setText(task.getTitle());
                description.setText(task.getDescription());
                date.setText(task.getDate());
                tag.setImageDrawable(drawable);
                abrv.setText(abrev);


                //Selecciona si estaba seleccionado
                itemView.setSelected(getPresenter().isSelected(getAdapterPosition()));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().onListClick2(itemView, getAdapterPosition(), adapter, task);
                        adapter.notifyDataSetChanged();

                    }
                });
                itemView.setLongClickable(true);
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        getPresenter().onLongListClick2(v, getAdapterPosition());

                        return true;
                    }
                });

            }
        }


    }


}





