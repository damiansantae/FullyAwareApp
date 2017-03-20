package es.ulpgc.eite.clean.mvp.sample.addTask;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;

public class AddTaskView
    extends GenericActivity<AddTask.PresenterToView, AddTask.ViewToPresenter, AddTaskPresenter>
    implements AddTask.PresenterToView {

  private Toolbar toolbar;
  private Button button;
  private TextView subjectLabel;
  private TextView titleLabel;
  private TextView descriptionLabel;
  private TextView dateLabel;
  private EditText subject;
  private EditText title;
  private EditText description ;
  private DatePicker date;
  private DialogFragment dateFragment;
    //dateFragment.show(getSupportFragmentManager(), "date");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_addtask);

    //TextView
    subjectLabel = (TextView) findViewById(R.id.subjectLabel);
    titleLabel = (TextView) findViewById(R.id.titleLabel);
    descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
    dateLabel = (TextView) findViewById(R.id.dateLabel);

    //EditText
    subject = (EditText) findViewById(R.id.subject);
    title = (EditText) findViewById(R.id.title);
    description = (EditText) findViewById(R.id.description);

    //DatePicker
    date = (DatePicker) findViewById(R.id.date);

    //Toolbar
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //Button
    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getPresenter().onButtonClicked();
      }
    });
  }

  /**
   * Method that initialized MVP objects
   * {@link super#onResume(Class, Object)} should always be called
   */
  @Override
  protected void onResume() {
    super.onResume(AddTaskPresenter.class, this);
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
  public void hideText() {

  }

  @Override
  public void showText() {

  }

  @Override
  public void setText(String txt) {

  }

  @Override
  public void setLabel(String txt) {

  }

}
