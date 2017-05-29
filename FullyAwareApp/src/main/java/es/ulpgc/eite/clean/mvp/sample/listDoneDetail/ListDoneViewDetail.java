package es.ulpgc.eite.clean.mvp.sample.listDoneDetail;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Task;

public class ListDoneViewDetail
        extends GenericActivity<ListDoneDetail.PresenterToView, ListDoneDetail.ViewToPresenter, ListDonePresenterDetail>
        implements ListDoneDetail.PresenterToView {

    private Toolbar toolbar;
    private Task Task;
    private CollapsingToolbarLayout toolbarLayout;
    private AppBarLayout appbarLayout;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appbarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        appbarLayout.setExpanded(true);
    }


    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListDonePresenterDetail.class, this);

        Task = getPresenter().getTask();

        if (toolbarLayout != null && Task != null) {
            toolbarLayout.setTitle(Task.getTitle());
            appbarLayout.setBackgroundColor(getColor(getPresenter().getTask().getSubject().getColor()));
        }

        // Show the dummy content as text in a TextView.
        if (Task != null) {
            if(Task.getSubject().getName().compareTo("None") != 0) {
                ((TextView) findViewById(R.id.subject_from_detail)).setText("Subject: " + Task.getSubject().getName());
            }
            ((TextView) findViewById(R.id.date_txt)).setText("Deadline: " +  Task.getDate());
            if(!Task.getDescription().isEmpty()) {
                ((TextView) findViewById(R.id.task_description)).setText("Description: " + Task.getDescription());
            }
        }
    }



  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_listdone_detail, menu);
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
        getPresenter().onDeleteActionClicked();

        return true;
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
    public void toolbarChanged(String colour) {
            List<String> colorPrimaryList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
            List<String> colorPrimaryDarkList = Arrays.asList(getResources().getStringArray(R.array.default_color_choice_values));
            if (colorPrimaryList.indexOf(colour) != (-1)) {
                getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
                toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
                toolbarLayout.setContentScrimColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        }
    }


}
