package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

/**
 * View of a task done list. It can click on a specific task to see its details,
 * Also it can multiselect several tasks to delete or done simultaneously
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListDoneViewMaster
        extends GenericActivity<ListDoneMaster.PresenterToView, ListDoneMaster.ViewToPresenter, ListDonePresenterMaster>
        implements ListDoneMaster.PresenterToView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton bin;
    private TaskRecyclerViewAdapter adapter;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;




    ///////////////////////////////////////////////////////////////////////////////////
    // App's life cycle methods and Auto-generated////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdone);

        ////////////////////////////////////////////////////////////
        bin = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);
        ///////////////////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.item_list_recycler);
        adapter = new TaskRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        bin.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       getPresenter().onBinBtnClick(adapter);
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
        super.onResume(ListDonePresenterMaster.class, this);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listtodo_master_done, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuToDo) {
            Toast.makeText(getApplicationContext(), "ToDo", Toast.LENGTH_SHORT).show();
            Navigator app = (Navigator) getApplication();
            app.goToListToDoScreen((ListDoneMaster.ListDoneTo) getPresenter());
        } else if (id == R.id.menucalendar) {
            Toast.makeText(getApplicationContext(), "Calendar", Toast.LENGTH_SHORT).show();
            Navigator app = (Navigator) getApplication();
            app.goToScheduleScreen((ListDoneMaster.ListDoneTo) getPresenter());

        } else if (id == R.id.menuPreferences) {
            Navigator app = (Navigator) getApplication();
            app.goToPreferencesScreen((ListDoneMaster.ListDoneTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toolbarChanged(String colour) {
        List<String> colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        List<String> colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
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
    public void hideDeleteBtn() {
        bin.setVisibility(View.INVISIBLE);


    }

    @Override
    public void showDeleteBtn() {
        bin.setVisibility(View.VISIBLE);


    }


    @Override
    public void showToastDelete() {
        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();

    }


    @Override
    public void setRecyclerAdapterContent(List<Task> items) {
        if (recyclerView != null) {
            TaskRecyclerViewAdapter recyclerAdapter =
                    (TaskRecyclerViewAdapter) recyclerView.getAdapter();
            recyclerAdapter.setItemList(items);
        }
    }

}
