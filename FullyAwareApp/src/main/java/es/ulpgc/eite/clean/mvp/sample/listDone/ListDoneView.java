package es.ulpgc.eite.clean.mvp.sample.listDone;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;

public class ListDoneView
        extends GenericActivity<ListDone.PresenterToView, ListDone.ViewToPresenter, ListDonePresenter>
        implements ListDone.PresenterToView {

    private Toolbar toolbar;
    private Button button;
    private TextView text;
    private ArrayList<Task> tasksSelected = new ArrayList<>();
    private ArrayList<Integer> posSelected = new ArrayList<>();
    private ListView list;
    private FloatingActionButton bin;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdone);

   /* text = (TextView) findViewById(R.id.text);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getPresenter().onButtonClicked();
      }
    });
*/


        ////////////////////////////////////////////////////////////
         list = (ListView) findViewById(R.id.list);
        bin = (FloatingActionButton) findViewById(R.id.floatingActionButton);


        final Task_Adapter adapter = new Task_Adapter(this, R.layout.item_list, TaskRepository.getInstance().getTasks());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Task currentTask = adapter.getItem(position);
                Toast toast = Toast.makeText(getBaseContext(), currentTask.getTitle(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();





        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                Log.v("long click","pos: " + pos);
                Task currentTask = adapter.getItem(pos);

                if(list.isItemChecked(pos)){                //Si el elemento ya estaba seleccionado
                    list.setItemChecked(pos,false);          //Se deselecciona
                    Log.v("Se deselecciona","pos: " + pos);
                    tasksSelected.remove(currentTask);       //Se elimina del Array de seleccionados
                    posSelected.remove(pos);                  //Se elimina del array de posiciones seleccionadas (
                }else {                                      //Si no estaba seleccionado
                    list.setItemChecked(pos, true);           //Se selecciona
                    Log.v("Se selecciona","pos: " + pos);
                    tasksSelected.add(currentTask);           //Se añade al array de seleccionados
                    posSelected.add(pos);                     //Se añade al array de posiciones seleccionadas (Para poder eliminarlas tras el borrado)
                }


                return true;
            }
        });

        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = tasksSelected.size();
                if(size!=0){
                for(int i=0;i<size;i++) {
                    Task task = tasksSelected.get(i);
                    adapter.remove(task);
                    //Deseleccionamos los index de las posiciones eliminadas
                }
                    for(int k=0;k<posSelected.size();k++){
                        list.setItemChecked(posSelected.get(k), false);
                    }
                    posSelected.clear();
                    tasksSelected.clear();


                    adapter.notifyDataSetChanged();


                }

            }
        });

    }


    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @Override
    protected void onResume() {
        super.onResume(ListDonePresenter.class, this);
    }

  /*
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_listDone, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
  */


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
    public void setText(String txt) {
        text.setText(txt);
    }

    @Override
    public void setLabel(String txt) {
        button.setText(txt);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ListDoneView Page") // TODO: Define a title for the content shown.
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
