package es.ulpgc.eite.clean.mvp.sample.listToDoDetail;

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

public class ListToDoViewDetail
        extends GenericActivity<ListToDoDetail.PresenterToView, ListToDoDetail.ViewToPresenter, ListToDoPresenterDetail>
        implements ListToDoDetail.PresenterToView {

    private Toolbar toolbar;
    private Task Task;
    private CollapsingToolbarLayout toolbarLayout;
    private AppBarLayout appbarLayout;
    private final String TOOLBAR_COLOR_KEY = "toolbar-key";
    public static final String MY_PREFS = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_relative);
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

loadSharePreferences();

    }
    private void loadSharePreferences() {
        Log.d(TAG, "calling loadSharePreferences");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String colour = prefs.getString(TOOLBAR_COLOR_KEY, null);
        Log.d(TAG, "" + colour);
        if (colour != null) {
            toolbarChanged(colour);
        }
    }

    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListToDoPresenterDetail.class, this);

        Task = getPresenter().getTask();

        if (toolbarLayout != null && Task != null) {
            toolbarLayout.setTitle(Task.getTitle());
        }

        // Show the dummy content as text in a TextView.
        if (Task != null) {
            ((TextView) findViewById(R.id.date_txt)).setText(Task.getDate());
            ((TextView) findViewById(R.id.subject_txt)).setText(Task.getSubjectId());
            ((TextView) findViewById(R.id.task_description)).setText(Task.getDescription());
        }
    }


  //Este metodo sirve para inflar el menu en la action bar
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_listtodo_detail, menu);
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
    } else if(id==R.id.action_done){
        //TODO: codigo para pasar tarea a done

    }else if(id==R.id.action_change){
        //TODO: ir acitvity modificar tarea

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
        getWindow().setStatusBarColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        toolbar.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        toolbarLayout.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        toolbarLayout.setContentScrimColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));
        appbarLayout.setBackgroundColor((Color.parseColor(colorPrimaryDarkList.get(colorPrimaryList.indexOf(colour)))));

    }




}
