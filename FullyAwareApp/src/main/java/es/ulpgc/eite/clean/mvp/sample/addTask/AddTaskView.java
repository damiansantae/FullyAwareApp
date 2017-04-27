package es.ulpgc.eite.clean.mvp.sample.addTask;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;

public class AddTaskView
    extends GenericActivity<AddTask.PresenterToView, AddTask.ViewToPresenter, AddTaskPresenter>
    implements AddTask.PresenterToView {

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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_addtask);

    //TextView
    subjectLabel = (TextView) findViewById(R.id.subjectLabel);
    titleLabel = (TextView) findViewById(R.id.titleLabel);
    descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);

    //EditText
    subject = (EditText) findViewById(R.id.subject);
    title = (EditText) findViewById(R.id.title);
    description = (EditText) findViewById(R.id.description);
    date = (EditText) findViewById(R.id.date);
    time = (EditText) findViewById(R.id.time);

    //Toolbar
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //Buttons
    selectDateBtn = (Button)findViewById(R.id.selectDateBtn);
    selectDateBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getPresenter().onSelectDateBtnClicked();
      }
    });

    selectTimeBtn = (Button) findViewById(R.id.selectTimeBtn);
    selectTimeBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getPresenter().onSelectTimeBtnClicked();
      }
    });

    addTaskBtn = (Button) findViewById(R.id.addTaskBtn);
    addTaskBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getPresenter().onAddTaskBtnClicked();

      }
    });

    Calendar c = Calendar.getInstance();
    String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
    String month = String.valueOf(c.get(Calendar.MONTH));
    String year = String.valueOf(c.get(Calendar.YEAR));

    date.setText(day+"/"+month+"/"+year);

  }

  /**
   * Method that initialized MVP objects
   * {@link super#onResume(Class, Object)} should always be called
   */
  @Override
  protected void onResume() {
    super.onResume(AddTaskPresenter.class, this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
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

  @Override
  public void toolbarChanged(String colour) {
    List<String> colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
    List<String> colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
    getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
    toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
  }


}
