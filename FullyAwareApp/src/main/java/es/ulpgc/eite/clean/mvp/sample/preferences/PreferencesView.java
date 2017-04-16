package es.ulpgc.eite.clean.mvp.sample.preferences;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;

public class PreferencesView
    extends GenericActivity<Preferences.PresenterToView, Preferences.ViewToPresenter, PreferencesPresenter>
    implements Preferences.PresenterToView {

  private Toolbar toolbar;
  private Button selectDateBtn;
  private Button selectTimeBtn;
  private Button addTaskBtn;
  private TextView subjectLabel;
  private TextView titleLabel;
  private TextView descriptionLabel;
  private EditText subject;
  private EditText title;
  private EditText description ;
  private EditText date;
  private EditText time;
private ListView list;
  private Adapter adapter;
    private String [] prefItems;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preferences);
    preferencesListView();

    }

    private void preferencesListView(){
        // Creamos lista de elementos
       prefItems = new String[]{"Change App Coulour", "Change Subjects", "Add Subjects"};

        //Creamos el adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_preferences, prefItems);

        //Lo enlazamos al layout

        ListView list = (ListView) findViewById(R.id.preferences_list);
        list.setAdapter(adapter);

    }





  /**
   * Method that initialized MVP objects
   * {@link super#onResume(Class, Object)} should always be called
   */
  @Override
  protected void onResume() {
    super.onResume(PreferencesPresenter.class, this);
  }

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

  @Override
  public void finishScreen() {
    finish();
  }

  @Override
  public void hideToolbar() {
    toolbar.setVisibility(View.GONE);
  }


  @Override
  public void setDateText(String txt) {
    date.setText(txt);
  }

  @Override
  public void setTimeText(String txt) {
    time.setText(txt);
  }

  @Override
  public String getDescription() {
    return description.getText().toString();
  }

  @Override
  public String getDate() {
    return date.getText().toString();

  }

  @Override
  public String getTime() {
    return time.getText().toString();
  }

    @Override
    public String getTaskTitle() {
        return title.getText().toString();
    }

    @Override
    public String getTaskSubject() {
        return subject.getText().toString();
    }


}