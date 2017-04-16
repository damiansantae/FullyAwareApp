package es.ulpgc.eite.clean.mvp.sample.preferences;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDonePresenterMaster;


public class PreferencesView extends GenericActivity<Preferences.PresenterToView, Preferences.ViewToPresenter, PreferencesPresenter> implements Preferences.PresenterToView {

  private Toolbar toolbar;
    private String [] prefItems;
    private String [] descriptionItems;
    private int[] imageItems;
    private ListView list;
    private SimpleAdapter adapter;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preferences);
    preferencesListView();

    }

    private void preferencesListView(){
        // Creamos lista de elementos
       prefItems = new String[]{"App Colour","Edit Subjects","Donate","About"};
       descriptionItems = new String[]{"Change the colour of the App!", "Add subjects or make changes", "To contribute", "All about FullyAware App and xDroidInc"};
        imageItems = new int[]{
                android.R.drawable.ic_menu_edit,
                android.R.drawable.ic_menu_manage,
                android.R.drawable.ic_menu_share,
                android.R.drawable.ic_menu_info_details,
        };

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<4;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("prefItem", "" + prefItems[i]);
            hm.put("descriptionItem","" + descriptionItems[i]);
            hm.put("imageItem", Integer.toString(imageItems[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "imageItem","descriptionItem","prefItem" };

        // Ids of views in listview_layout
        int[] to = { R.id.tag,R.id.description,R.id.title};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.item_preferences, from, to);

        // Getting a reference to listview of main.xml layout file
        list = (ListView) findViewById(R.id.preferences_list);

        // Setting the adapter to the listView
        list.setAdapter(adapter);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
          getPresenter().onListClick(position,adapter);
        }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }









    @Override
    public void finishScreen() {

    }

    @Override
    public void hideToolbar() {

    }

    @Override
    public void setDateText(String txt) {

    }

    @Override
    public void setTimeText(String txt) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public String getTaskTitle() {
        return null;
    }

    @Override
    public String getTaskSubject() {
        return null;
    }


@Override
public void onStart(){
    super.onStart();

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client.connect();
    AppIndex.AppIndexApi.start(client, getIndexApiAction());
}

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
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(PreferencesPresenter.class, this);
    }

}



        //Creamos el adapter
       //ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this, R.layout.item_preferences, R.id.title, prefItems);
        //ArrayAdapter<String> descriptionAdapter = new ArrayAdapter<String>(this, R.layout.item_preferences, R.id.description, descriptionItems);
       // ArrayAdapter<ImageView> imageItemAdapter = new ArrayAdapter<ImageView>(this, R.layout.item_preferences, R.id.tag, imageItems);

        //Lo enlazamos al layout



       // list.setAdapter(itemAdapter);
        //list.setAdapter(descriptionAdapter);

        //list.setAdapter(imageItemAdapter);







  /*
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_dummy, menu);
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






