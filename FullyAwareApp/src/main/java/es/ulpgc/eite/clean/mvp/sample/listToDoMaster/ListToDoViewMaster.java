package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

//public  abstract class ListToDoViewMaster
//        extends GenericActivity<ListSubject.PresenterToView, ListSubject.ViewToPresenter, ListSubjectsPresenter>
//        implements ListSubject.PresenterToView {
//
//    private Toolbar toolbar;
//    private ListView list;
//    private FloatingActionButton bin;
//    private FloatingActionButton add;
//    private FloatingActionButton done;
//    float historicX = Float.NaN, historicY = Float.NaN;
//    static final int DELTA = 50;
//    enum Direction {LEFT, RIGHT}
//
//    private Subject_Adapter adapter;
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_listtodo);
//
//        ////////////////////////////////////////////////////////////
//        bin = (FloatingActionButton) findViewById(R.id.floatingDeleteButton);
//        add = (FloatingActionButton) findViewById(R.id.floatingAddButton);
//        done= (FloatingActionButton) findViewById(R.id.floatingDoneButton);
//        ///////////////////////////////////////////////////////////////////
//        list = (ListView) findViewById(R.id.list);
//        adapter = new Subject_Adapter(this, R.layout.item_list, TaskRepository.getInstance().getTasks());
//       list.setAdapter(adapter);
//
//
//        list.setOnTouchListener(new View.OnTouchListener() {
//
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        historicX = event.getX();
//                        historicY = event.getY();
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        if (event.getX() - historicX < -DELTA) {
//                            int posicion = 0;
//                            getPresenter().onSwipeMade(posicion, adapter);
//                            adapter.notifyDataSetChanged();
//                            Log.d("error msg", "SWIPE MODE");
//                            return true;
//                        }
//                        else if (event.getX() - historicX > DELTA) {
//                //Swipe hacia el otro lado;
//                            return true;
//                        }
//                        break;
//
//                    default:
//                        return false;
//                }
//                return false;
//            }
//        });
//
//
//
//
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                getPresenter().onListClick(position, adapter);
//
//
//                Log.d("error msg", "Click Simple");
//               /* Task currentTask = adapter.getTask(position);
//                Toast toast = Toast.makeText(getBaseContext(), currentTask.getTaskTitle(), Toast.LENGTH_SHORT);
//                toast.show();*/
//
//            }
//        });
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//
//
//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//                                           int pos, long id) {
//                getPresenter().onLongListClick(pos, adapter);
//                Log.d("error msg", "Click Largo");
//                return true;
//            }
//        });
//
//        bin.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {
//                                       getPresenter().onBinBtnClick(adapter);
//                                       adapter.notifyDataSetChanged();
//                                   }
//
//                               }
//        );
//
//        add.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {
//                                       getPresenter().onAddBtnClick(adapter);
//                                       //adapter.notifyDataSetChanged();
//                                   }
//
//                               }
//        );
//        done.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {
//                                       getPresenter().onDoneBtnClick(adapter);
//                                       list.clearChoices();
//                                       adapter.notifyDataSetChanged();
//                                   }
//
//                               }
//        );
//
//        ////////////////////////////////////////////////////////
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//    }
//
//
//    /**
//     * Method that initialized MVP objects
//     * {@link super#onResume(Class, Object)} should always be called
//     */
//    @SuppressLint("MissingSuperCall")
//    @Override
//    protected void onResume() {
//        super.onResume(ListSubjectsPresenter.class, this);
//    }
//
//
//    //Este metodo sirve para inflar el menu en la action bar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_listtodo_master_todo, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_delete) {
//            return true;
//        }else if (id ==R.id.menuToDo){
//
//            Toast.makeText(getApplicationContext(),"ToDo",Toast.LENGTH_SHORT).show();
//        }
//        else if (id ==R.id.menuDone){
//            Navigator app = (Navigator) getApplication();
//            app.goToListDoneScreen((ListSubject.ListSubjectTo) getPresenter());
//            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
//            Log.d("error msg", "PULSADO");
//        }
//        else if (id ==R.id.menucalendar){
//           Navigator app = (Navigator) getApplication();
//          // app.goToCalendarScreen();
//            Toast.makeText(getApplicationContext(),"Calendar",Toast.LENGTH_SHORT).show();
//        }
//        else if (id ==R.id.menuForgotten){
//            Navigator app = (Navigator) getApplication();
//            app.goToListForgottenScreen((ListSubject.ListSubjectTo) getPresenter());
//            Toast.makeText(getApplicationContext(),"Forgotten",Toast.LENGTH_SHORT).show();
//        } else if (id ==R.id.menuPreferences) {
//           // Navigator app = (Navigator) getApplication();
//           // app.goToPreferencesScreen((ListSubject.ListSubjectTo) getPresenter());
//            Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//
//
//    ///////////////////////////////////////////////////////////////////////////////////
//    // Presenter To View /////////////////////////////////////////////////////////////
//
//    @Override
//    public void finishScreen() {
//        finish();
//    }
//
//    @Override
//    public void hideToolbar() {
//        toolbar.setVisibility(View.GONE);
//    }
//
//
//
//    @Override
//    public void hideAddBtn() {
//        add.setVisibility(View.INVISIBLE);
//
//
//    }
//
//    @Override
//    public void hideDoneBtn() {
//        done.setVisibility(View.INVISIBLE);
//
//
//
//    }
//
//    @Override
//    public void showDoneBtn() {
//        done.setVisibility(View.VISIBLE);
//
//
//    }
//
//    @Override
//    public void deselect(int i, boolean b) {
//        list.setItemChecked(i,b);
//    }
//
//
//
//    //TODO:Este metodo no hace nada aqui, es para la RecyclerView
//    @Override
//    public void setRecyclerAdapterContent(List<Task> items) {
//    }
//
//    @Override
//    public void toolbarChanged(String colour) {
//
//    }
//
//    @Override
//    public void showAddBtn() {
//        add.setVisibility(View.VISIBLE);
//
//
//    }
//
//    @Override
//    public void hideDeleteBtn() {
//        bin.setVisibility(View.INVISIBLE);
//
//
//    }
//
//    @Override
//    public void showDeleteBtn() {
//        bin.setVisibility(View.VISIBLE);
//
//
//    }
//
//    @Override
//    public boolean isItemListChecked(int pos) {
//        return list.isItemChecked(pos);
//    }
//
//    @Override
//    public void setItemChecked(int pos, boolean checked) {
//        list.setItemChecked(pos, checked);
//        adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void startSelection() {
//        list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
//
//    }
//
//    @Override
//    public void setChoiceMode(int i) {
//        if (i == 0) {               //Modo de seleccion nulo
//            list.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
//            list.invalidateViews();
//
//        } else if (i == 1) {             //Modo de seleccion unico
//            list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
//
//        } else if (i == 2) {             ///Modo de seleccion multiple
//            list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
//
//
//        } else {
//            Log.d("error msg", "error desconocido de al seleccionar modo de seleccionamiento");
//        }
//    }
//
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("ListForgottenView Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }
//}
