package es.ulpgc.eite.clean.mvp.sample.preferences;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kizitonwose.colorpreference.ColorDialog;
import com.kizitonwose.colorpreference.ColorShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;


public class PreferencesView extends GenericActivity<Preferences.PresenterToView, Preferences.ViewToPresenter, PreferencesPresenter> implements Preferences.PresenterToView, ColorDialog.OnColorSelectedListener {

  private Toolbar toolbar;
    private String [] prefItems;
    private String [] descriptionItems;
    private int[] imageItems;
    private ListView list;
    private SimpleAdapter adapter;
    private GoogleApiClient client;
    private int toolbarColour;
    private int fabColor;
    private boolean toolbarColorChanged;

    SharedPreferences preferences;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    List<String> colorPrimaryList;
    List<String> colorPrimaryDarkList;

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


    ///////////////// TO CHANGE COLOUR OF TOOLBAR /////////////////////
    @Override
    public void onChangeColourDialog(Preferences.PresenterToView view) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        toolbarColour = preferences.getInt(TOOLBAR_COLOR_KEY, ContextCompat.getColor(this, R.color.colorPrimary));
        colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
    showColorDialog(this);
    }


    private void showColorDialog(final Preferences.PresenterToView view) {
        new ColorDialog.Builder(this)
                .setColorShape(view instanceof Toolbar ? com.kizitonwose.colorpreference.ColorShape.SQUARE : ColorShape.CIRCLE)
                .setColorChoices(R.array.default_color_choice_values)
                .setTag(String.valueOf(view instanceof Toolbar ? R.id.toolbar : R.id.fab))
                .setSelectedColor(view instanceof Toolbar ? toolbarColour : fabColor)
                .show();
    }

    private void updateStatusBarColor(int newColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            String newColorString = getColorHex(newColor);
            getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(newColorString)))));
            setToolbarColorChanged(true);
            setNewToolbarColor(newColor);
            toolbarChanged();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Sistema Operativo no compatible";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }

    private void toolbarChanged() {
        getPresenter().toolbarChanged();
    }

    @Override
    public String getColorHex(int color) {
        return String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
    }

    @Override
    public void onColorSelected(int newColor, String tag) {
        View view = findViewById(Integer.parseInt(tag));
            toolbar.setBackgroundColor(newColor);
            toolbarColour = newColor;
            preferences.edit().putInt(TOOLBAR_COLOR_KEY, newColor).apply();
            //change the status bar color
            updateStatusBarColor(newColor);
        }

    @Override
    public void setNewToolbarColor(int newColor) {
        getPresenter().setNewToolbarColor(newColor);
    }

    @Override
    public void setToolbarColorChanged(boolean toolbarColorChanged) {
       getPresenter().setToolbarColorChanged(toolbarColorChanged);
    }

    @Override
    public void toolbarChanged(String colour) {
        List<String> colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        List<String> colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
    }

   /* @Override
    public void launchBrowser(AlertDialog alertDialog) {
        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("www.github.com/xDroidInc"));
        startActivity(intent);
        alertDialog.dismiss();
    }*/


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
                .setName("Preferences Page") // TODO: Define a title for the content shown.
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






