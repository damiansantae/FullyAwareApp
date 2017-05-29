package es.ulpgc.eite.clean.mvp.sample.preferences;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;


public class PreferencesView extends GenericActivity<Preferences.PresenterToView, Preferences.ViewToPresenter, PreferencesPresenter> implements Preferences.PresenterToView, ColorDialog.OnColorSelectedListener {

    private Toolbar toolbar;
    private String[] prefItems, descriptionItems;
    private int[] imageItems;
    private ListView list;
    private SimpleAdapter adapter;
    private GoogleApiClient client;
    private int toolbarColour, fabColor;
    SharedPreferences preferences;
    private PrefManager prefManager;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    List<String> colorPrimaryList, colorPrimaryDarkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        preferencesListView();
        prefManager = new PrefManager(getActivityContext());
    }

    /**
     * Private method that initializes the Preferences List.
     * It sets the name of each item of the list.
     */
    private void preferencesListView() {
        // Create list elements
        prefItems = new String[]{"App Colour", "Edit Subjects", "Donate", "About"};
        descriptionItems = new String[]{"Change the colour of the App!", "Add subjects or make changes", "To contribute", "All about FullyAware App and xDroidInc"};
        imageItems = new int[]{
                android.R.drawable.ic_menu_edit,
                android.R.drawable.ic_menu_manage,
                android.R.drawable.ic_menu_share,
                android.R.drawable.ic_menu_info_details,
        };

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("prefItem", "" + prefItems[i]);
            hm.put("descriptionItem", "" + descriptionItems[i]);
            hm.put("imageItem", Integer.toString(imageItems[i]));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"imageItem", "descriptionItem", "prefItem"};

        // Ids of views in listview_layout
        int[] to = {R.id.color_subject, R.id.description, R.id.title};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.item_preferences, from, to);

        // Getting a reference to listview of main.xml layout file
        list = (ListView) findViewById(R.id.preferences_list);

        // Setting the adapter to the listView
        list.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getPresenter().onListClick(position, adapter);
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    /**
     * Method that finish the activity.
     */
    @Override
    public void finishScreen() {
        finish();
    }

    /**
     * Method that initializes the Colour dialog.
     *
     * @param view (View) current view of the app.
     */
    @Override
    public void onChangeColourDialog(Preferences.PresenterToView view) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        toolbarColour = preferences.getInt(TOOLBAR_COLOR_KEY, ContextCompat.getColor(this, R.color.colorPrimary));
        colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        showColorDialog(this);
    }

    /**
     * Method that shows the Colour dialog.
     * It shows the different colours of the app to choose one of them.
     *
     * @param view current view of the app.
     */
    private void showColorDialog(final Preferences.PresenterToView view) {
        new ColorDialog.Builder(this)
                .setColorShape(view instanceof Toolbar ? com.kizitonwose.colorpreference.ColorShape.SQUARE : ColorShape.CIRCLE)
                .setColorChoices(R.array.default_color_choice_values)
                .setTag(String.valueOf(view instanceof Toolbar ? R.id.toolbar : R.id.fab))
                .setSelectedColor(view instanceof Toolbar ? toolbarColour : fabColor)
                .show();
    }

    /**
     * Method that updates the color of the App.
     *
     * @param newColor (int) colour of the app.
     */
    private void updateStatusBarColor(int newColor) { //TODO:METODO VALIDO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String newColorString = getColorHex(newColor);
            getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(newColorString)))));
            toolbar.setBackgroundColor(newColor);
            setToolbarColorChanged(true);
            setNewToolbarColor(newColor);
        } else {
            Context context = getApplicationContext();
            CharSequence text = getPresenter().getNotSupportedSystem();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }

    /**
     * Method that tranforms a numeric colour on a String colour.
     * It changes from int to hex.
     *
     * @param color (int) color to transform.
     */
    @Override
    public String getColorHex(int color) {
        return String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Method that is called when a colour of the Colour Dialog is pressed.
     *
     * @param newColor,tag (int) colour selected and (String) tag to identify it.
     */
    @Override
    public void onColorSelected(int newColor, String tag) {
        toolbarColour = newColor;
        updateStatusBarColor(newColor);
    }

    /**
     * Method that calls to the Presenter to change the toolbar Colour value.
     *
     * @param newColor (int) new color of the app.
     */
    @Override
    public void setNewToolbarColor(int newColor) {
        getPresenter().setNewToolbarColor(newColor);
    }

    /**
     * Method that updates a boolean that specifies if toolbar colour has been changed.
     *
     * @param toolbarColorChanged (boolean)
     */
    @Override
    public void setToolbarColorChanged(boolean toolbarColorChanged) {
        getPresenter().setToolbarColorChanged(toolbarColorChanged);
    }

    /**
     * Method that changes the colour of the actual view.
     *
     * @param colour (String) Toolbar colour.
     */
    @Override
    public void toolbarChanged(String colour) {

        List<String> colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        List<String> colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
        if (colorPrimaryList.indexOf(colour) != (-1)) {
            getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
            toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        }
    }

    /**
     * Method auto-generated.
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    /**
     * Method auto-generated.
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Preferences Page")
                .setUrl(Uri.parse("http://www.github.com/xDroidInc"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * Method auto-generated.
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    public void onStop() {
        super.onStop();
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





