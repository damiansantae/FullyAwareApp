package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.annotation.SuppressLint;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;

public class ListToDoViewMaster
        extends GenericActivity<ListToDoMaster.PresenterToView, ListToDoMaster.ViewToPresenter, ListToDoPresenterMaster>
        implements ListToDoMaster.PresenterToView {

    private Toolbar toolbar;
    private Button button;
    private TextView text;
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
        setContentView(R.layout.activity_listtodo);

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
        bin = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);
        add = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        done= (FloatingActionButton) findViewById(R.id.floatingDoneButton);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        adapter = new Task_Adapter(this, R.layout.item_list, TaskRepository.getInstance().getTasks());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                getPresenter().onListClick(position, adapter);

               /* Task currentTask = adapter.getItem(position);
                Toast toast = Toast.makeText(getBaseContext(), currentTask.getTaskTitle(), Toast.LENGTH_SHORT);
                toast.show();*/
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

        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       getPresenter().onAddBtnClick(adapter);
                                       //adapter.notifyDataSetChanged();
                                   }

                               }
        );
        done.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       getPresenter().onDoneBtnClick(adapter);
                                       list.clearChoices();
                                       adapter.notifyDataSetChanged();
                                   }

                               }
        );

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

  /*
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_listToDo, menu);
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
        list.setItemChecked(i,b);
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
