package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Navigator;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;

/**
 * View of a subject list.
 * make a swipe on it in order to delete or edit them
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListSubjectView
        extends GenericActivity<ListSubject.PresenterToView, ListSubject.ViewToPresenter, ListSubjectPresenter>
        implements ListSubject.PresenterToView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SubjectRecyclerViewAdapter adapter;
    private PrefManager prefManager;
    private Paint paint;
    private Subject currentSubject;
    ArrayList<String> subjectList;
    private AlertDialog.Builder alertDialogEdit;
    private AlertDialog.Builder alertDialogDelete;
    int numberOfSubjects;
    private View view;
    private EditText et_subject;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsubject);

        recyclerView = (RecyclerView) findViewById(R.id.item_list_recycler_for_subject);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        adapter = new SubjectRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        prefManager = new PrefManager(getActivityContext());
    }

    /**
     * Method that initialized MVP objects
     * {@link super#onResume(Class, Object)} should always be called
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume(ListSubjectPresenter.class, this);
    }

    /**
     * Method that create the option Menu.
     * {@link super#onResume(Class, Object)} should always be called
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listtodo_master_forgotten, menu);
        return true;
    }

    /**
     * Method called when an element of the option menu is pressed.
     *
     * @param item item selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuToDo) {
            Navigator app = (Navigator) getApplication();
            app.goToListToDoScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(), "ToDo", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.menuDone) {
            Navigator app = (Navigator) getApplication();
            app.goToListDoneScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.menuPreferences) {
            Navigator app = (Navigator) getApplication();
            app.goToPreferencesScreen((ListSubject.ListSubjectTo) getPresenter());
            Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Presenter To View /////////////////////////////////////////////////////////////

    /**
     * Method to finish the current screen.
     */
    @Override
    public void finishScreen() {
        finish();
    }

    /**
     * Method to hide the toolbar.
     */
    @Override
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    /**
     * Method to set a Delete Toast.
     */
    @Override
    public void setToastDelete() {
        Toast.makeText(getApplicationContext(), "Tarea Eliminada", Toast.LENGTH_LONG).show();
    }

    /**
     * It sets the recylcer Adapter Content.
     */
    @Override
    public void setRecyclerAdapterContent(List<Subject> items) {
        if (recyclerView != null) {
            SubjectRecyclerViewAdapter recyclerAdapter =
                    (SubjectRecyclerViewAdapter) recyclerView.getAdapter();
            recyclerAdapter.setItemList(items);
        }
    }

    /**
     * Method that changes the toolbar colour
     *
     * @param colour String: the new toolbar colour.
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
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ListSubjectView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * Auto-generated method.
     */
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    /**
     * Auto-generated method.
     */
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    ///////////////////////////////////////////////////////////////


    /**
     * Method that shows a dialog to enter the user name.
     */
    @Override
    public void showAddUserNameDialog() {
        final AddNameDialog dialog = new AddNameDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new AddNameDialog.OnAddUserClickListener() {

            @Override
            public void onAddUserNameClickListener(String userName) {
                getPresenter().onAddUserBtnClicked(userName);
                dialog.dismiss();
                showAddSubjectsDialog();
            }
        });
    }

    /**
     * Method that shows a dialog to enter the different subjects.
     */
    @Override
    public void showAddSubjectsDialog() {
        final AddFirstSubjectDialog dialog = new AddFirstSubjectDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        numberOfSubjects = 0;
        subjectList = new ArrayList<>();
        prefManager.setFirstTimeLaunch(false);

        dialog.setListener(new AddFirstSubjectDialog.OnAddSubjectClickListener() {
            @Override
            public void onAddSubjectClickListener(String label) {
                //The label is passed from the dialog, and it indicates what to do in each case
                EditText etSubjectName = (EditText) dialog.getView().findViewById(R.id.et_subject_name);
                if (label.equals(getPresenter().getFinishLabel())) {
                    if (numberOfSubjects == 0) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "No subjects added", Toast.LENGTH_SHORT).show();
                        launchHomeScreen();
                        finish();
                    } else {
                        getPresenter().addSubjectsToDataBase(subjectList);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "All subjects added", Toast.LENGTH_SHORT).show();
                        launchHomeScreen();
                        finish();
                    }

                } else {
                    switch (numberOfSubjects) {

                        case 0:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_1).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 1:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_2).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 2:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_3).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 3:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_4).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 4:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_5).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 5:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_6).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 6:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_7).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 7:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_8).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 8:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_9).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 9:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_10).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 10:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_11).setVisibility(View.VISIBLE);
                            numberOfSubjects++;
                            etSubjectName.setText("");
                            break;

                        case 11:
                            subjectList.add(label);
                            dialog.getView().findViewById(R.id.subject_12).setVisibility(View.VISIBLE);
                            etSubjectName.setText("");
                            dialog.getView().findViewById(R.id.bt_add_subject).setVisibility(View.GONE);
                            break;

                        case 13:
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     * Method that initiates the swipe option.
     */
    @Override
    public void initSwipe() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ANIMATION_TYPE_DRAG;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                currentSubject = adapter.getItems().get(viewHolder.getAdapterPosition());

                if (direction == 32) {
                    Log.d(TAG, "Swipe left");
                    initDialogEdit();
                    alertDialogEdit.setTitle("Edit Subject");
                    alertDialogEdit.show();
                    adapter.notifyDataSetChanged();


                } else if (direction == 16) {
                    Log.d(TAG, "Swipe right");
                    initDialogDelete();
                    alertDialogDelete.setTitle("Are you sure to delete this Subject PERMANENTLY?");
                    alertDialogDelete.show();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (paint == null) {
                    paint = new Paint();
                }

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#2ecc71"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    } else {
                        paint.setColor(Color.parseColor("#e74c3c"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.garbage);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    /**
     * Method that initiates the edit dialog.
     */
    private void initDialogEdit() {
        alertDialogEdit = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.edit_subject_dialog, null);
        alertDialogEdit.setView(view);
        alertDialogEdit.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().saveEditSubject(et_subject.getText().toString(), currentSubject);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        et_subject = (EditText) view.findViewById(R.id.et_subject_name);
    }

    /**
     * Method that initiates the process to delete the dialog.
     */
    private void initDialogDelete() {
        alertDialogDelete = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.delete_confirmation_dialog, null);
        alertDialogDelete.setView(view);
        alertDialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().swipeLeft(currentSubject);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        alertDialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
    }

    /**
     * Method that launches the home screen (ListTodo).
     */
    private void launchHomeScreen() {
        Navigator app = (Navigator) getApplication();
        app.goToListToDoScreen((ListSubject.ListSubjectTo) getPresenter());

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The subject recyclerView adapter class.
     */
    public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.ViewHolder> {


        private List<Subject> subjects;


        public SubjectRecyclerViewAdapter() {
            subjects = new ArrayList<>();
        }

        /**
         * Creates the view holder of the adapter.
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subject_row, parent, false);
            return new ViewHolder(view);
        }

        /**
         * Method that sets the different items of the subject list.
         *
         * @param items Subject: items of the list.
         */
        public void setItemList(List<Subject> items) {
            this.subjects = items;
            notifyDataSetChanged();
        }

        /**
         * Method that returns the items of the subject list.
         *
         * @return subjects : array list of subjects.
         */
        public List<Subject> getItems() {
            return this.subjects;
        }

        /**
         * BindView holder method of the list subject adapter.
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Subject task = subjects.get(position);
            holder.bindView(task);

        }

        /**
         * Method that counts the items of the list subject.
         *
         * @return subjects int: number of items.
         */
        @Override
        public int getItemCount() {
            return subjects.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            final View itemView;
            private TextView subjectName;
            public Subject subject;

            public ViewHolder(View view) {
                super(view);
                itemView = view;
            }

            public void bindView(final Subject subject) {
                subjectName = (TextView) itemView.findViewById(R.id.subject_name);
                subjectName.setText(subject.getName());
            }
        }

    }

}



