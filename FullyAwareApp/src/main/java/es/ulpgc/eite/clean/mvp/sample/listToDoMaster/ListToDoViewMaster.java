package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.TaskRecyclerViewAdapter;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;

/**
 * View of a task to do list. It can click on a specific task to see its details,
 * make a swipe on it in order to delete or passing it to Done list.
 * Also it can multiselect several tasks to delete or done simultaneously
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListToDoViewMaster
        extends GenericActivity<ListToDoMaster.PresenterToView, ListToDoMaster.ViewToPresenter, ListToDoPresenterMaster>
        implements ListToDoMaster.PresenterToView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton bin;
    private FloatingActionButton add;
    private FloatingActionButton done;
    private TextView textWhenIsEmpty;
    private Task currentTask;
    private Paint paint;
    private View view;
    private TaskRecyclerViewAdapter adapter;

    //TODO: JORDI ESTOS ATRIBUTOS PARA QUE SON????????
    private SharedPreferences prefs;
    private PrefManager prefManager;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";
    //TODO: JORDI ESTOS ATRIBUTOS PARA QUE SON????????

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private AlertDialog.Builder alertDialog;


    ///////////////////////////////////////////////////////////////////////////////////
    // App's life cycle methods and Auto-generated////////////////////////////////////

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
        adapter = new TaskRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        textWhenIsEmpty = (TextView) findViewById(R.id.textWhenIsEmpty);
        bin.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       getPresenter().onBinBtnClick(adapter);
                                       adapter.notifyDataSetChanged();
                                   }

                               }
        );

        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       getPresenter().onAddBtnClick();
                                   }

                               }
        );
        done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getPresenter().onDoneBtnClick(adapter);
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
        //////////////////////////
        loadSharePreferences();
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


    ///////////////////////////////////////////////////////////////////////////////////
    // Toolbar actions  //////////////////////////////////////////////////////////////

    /**
     * Method that inflates toolbar with items
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listtodo_master_todo, menu);
        return true;
    }


    /**
     * Method that handle the selection of an specific toolbar's item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuDone) {
            Navigator app = (Navigator) getApplication();
            app.goToListDoneScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menucalendar) {
            Navigator app = (Navigator) getApplication();
            app.goToScheduleScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Calendar", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuPreferences) {
            Navigator app = (Navigator) getApplication();
            app.goToPreferencesScreen((ListToDoMaster.ListToDoTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Going to Preferences");


        } else if (id == R.id.filter_icon) {
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
    public void showAddBtn() {
        add.setVisibility(View.VISIBLE);
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
    public void hideTextWhenIsEmpty() {
        textWhenIsEmpty.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showTextWhenIsEmpty() {
        textWhenIsEmpty.setVisibility(View.VISIBLE);
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
    public void showToastDelete() {
        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();

    }

    /**
     * This method is called when back button is pressed on List To Do View
     */
    @Override
    public void confirmBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        getPresenter().onBtnBackPressed();
    }


    @Override
    public void initSwipe() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

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
                currentTask = adapter.getItems().get(viewHolder.getAdapterPosition());

                if (direction == 32) {
                    Log.d(TAG, "Swipe right");
                    getPresenter().swipeRight(currentTask);
                    adapter.notifyDataSetChanged();


                } else if (direction == 16) {
                    Log.d(TAG, "Swipe left");

                    initDialog();
                    alertDialog.setTitle("Do you want to delete this task permanently?");
                    alertDialog.show();
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (paint == null) {
                    paint = new Paint();
                }

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#2ecc71"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.checkmark_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    } else {
                        paint.setColor(Color.parseColor("#e74c3c"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.garbage);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Method that create a dialog and inflate it in order to ask user to
     * a delete task confirmation
     */
    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.delete_confirmation_dialog, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().swipeLeft(currentTask);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

    }

    @Override
    public void showToastBackConfirmation(String toastBackConfirmation) {

        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundResource(R.color.color_hardblue);

        TextView tv = new TextView(this);
        // set the TextView properties like color, size etc
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(15);

        tv.setGravity(Gravity.CENTER_VERTICAL);

        // set the text you want to show in  Toast
        tv.setText(toastBackConfirmation);

        ImageView img = new ImageView(this);

        // give the drawable resource for the ImageView
        img.setImageResource(R.drawable.back);

        // add both the Views TextView and ImageView in layout
        layout.addView(img);
        layout.addView(tv);

        Toast toast = new Toast(this);
        // Set The layout as Toast View
        toast.setView(layout);

        // Position you toast here toast position is 50 dp from bottom you can give any integral value
        toast.setGravity(Gravity.BOTTOM, 50, 500);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * This method load on this Activity saved preferences such as toolbarColor
     */
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
     * This method delete the Parent View of a view.
     * For example, when a dialog view is showed
     */
    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}





