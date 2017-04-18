package es.ulpgc.eite.clean.mvp.sample.listForgottenMaster;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.TaskForgotten;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoMaster;

public class ListForgottenViewMaster
        extends GenericActivity<ListForgottenMaster.PresenterToView, ListForgottenMaster.ViewToPresenter, ListForgottenPresenterMaster>
        implements ListForgottenMaster.PresenterToView {

    private Toolbar toolbar;
    private Button button;
    private TextView text;
    private ArrayList<TaskForgotten> tasksSelected = new ArrayList<>();
    private ArrayList<Integer> posSelected = new ArrayList<>();
    private ListView list;
    private FloatingActionButton bin;
    private FloatingActionButton add;
    private FloatingActionButton done;

    private Task_Adapter adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listforgotten);


        ////////////////////////////////////////////////////////////
        list = (ListView) findViewById(R.id.list);
        bin = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);

        ///////////////////////////////////////////////////////////////////
        list = (ListView) findViewById(R.id.list);

        adapter = new Task_Adapter(this, R.layout.item_list, TaskRepository.getInstance().getTasks());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getPresenter().onListClick(position, adapter);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                getPresenter().onLongListClick(pos, adapter);
                return true;
            }
        });

        bin.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       getPresenter().onBinBtnClick(adapter);
                                       adapter.notifyDataSetChanged();
                                   }

            }
        );
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListForgottenPresenterMaster.class, this);
    }


    //Este metodo sirve para inflar el menu en la action bar
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
        if (id == R.id.action_delete) {
            return true;
        }else if (id ==R.id.menuToDo){
            Navigator app = (Navigator) getApplication();
            app.goToListToDoScreen((ListForgottenMaster.ListForgottenTo) getPresenter());


            Toast.makeText(getApplicationContext(),"ToDo",Toast.LENGTH_SHORT).show();
        }

        else if (id ==R.id.menucalendar){
            Navigator app = (Navigator) getApplication();
            app.goToScheduleScreen((ListForgottenMaster.ListForgottenTo) getPresenter());
            Toast.makeText(getApplicationContext(),"Calendar",Toast.LENGTH_SHORT).show();
        }
        else if (id ==R.id.menuDone){
            Navigator app = (Navigator) getApplication();
            app.goToListDoneScreen((ListForgottenMaster.ListForgottenTo) getPresenter());
            Toast.makeText(getApplicationContext(),"Forgotten",Toast.LENGTH_SHORT).show();

      } else if (id ==R.id.menuPreferences) {
          Navigator app = (Navigator) getApplication();
          app.goToPreferencesScreen((ListForgottenMaster.ListForgottenTo) getPresenter());
          Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();

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
    public void hideText() {
        text.setVisibility(View.GONE);
    }

    @Override
    public void showText() {
        text.setVisibility(View.VISIBLE);
    }

    @Override
    public void deselect(int i, boolean b) {
        list.setItemChecked(i,b);
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
    public void setText(String txt) {
        text.setText(txt);
    }

    @Override
    public void setLabel(String txt) {
        button.setText(txt);
    }

    @Override
    public boolean isItemListChecked(int pos) {
        return list.isItemChecked(pos);
    }

    @Override
    public void setItemChecked(int pos, boolean checked) {
        list.setItemChecked(pos, checked);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startSelection() {
        list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

    }

    @Override
    public void setChoiceMode(int i) {
        if (i == 0) {               //Modo de seleccion nulo
            list.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
            list.invalidateViews();

        } else if (i == 1) {             //Modo de seleccion unico
            list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        } else if (i == 2) {             ///Modo de seleccion multiple
            list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);


        } else {
            Log.d("error msg", "error desconocido de al seleccionar modo de seleccionamiento");
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
}
