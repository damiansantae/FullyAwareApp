package es.ulpgc.eite.clean.mvp.sample.listForgottenDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TaskForgotten;

public class ListForgottenViewDetail
        extends GenericActivity<ListForgottenDetail.PresenterToView, ListForgottenDetail.ViewToPresenter, ListForgottenPresenterDetail>
        implements ListForgottenDetail.PresenterToView {

    private Toolbar toolbar;
    private TaskForgotten taskForgotten;
    private CollapsingToolbarLayout toolbarLayout;
    private AppBarLayout appbarLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_done);
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


    }


    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListForgottenPresenterDetail.class, this);

        taskForgotten = getPresenter().getTask();

        if (toolbarLayout != null && taskForgotten != null) {
            toolbarLayout.setTitle(taskForgotten.getTitle());
        }

        // Show the dummy content as text in a TextView.
        if (taskForgotten != null) {
            ((TextView) findViewById(R.id.task_description)).setText(taskForgotten.getDescription());
        }
    }


  //Este metodo sirve para inflar el menu en la action bar
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


}