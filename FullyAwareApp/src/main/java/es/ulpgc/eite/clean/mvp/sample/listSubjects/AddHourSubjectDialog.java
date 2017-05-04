package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.intro.PrefManager;


public class AddHourSubjectDialog extends DialogFragment implements View.OnClickListener {

  private TextView subjectName;

  private FloatingActionButton floatingButtonAdd;
  private FloatingActionButton floatingButtonDelete;
  private Button btHour1;
  private Button btHour2;
  private Button btHour3;
  private Button btHour4;
  private Button btHour5;
  private OnAddHourSubjectClickListener listener;
  private String floatingAdd;
  private String floatingDelete;
  private String buttonAddSubject;
    private Button buttonNext;
  private String buttonHour;
  private String buttonFinish;
  private PrefManager prefManager;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    setButtonsLabels();
  }

    private void setButtonsLabels() {
    this.floatingAdd = "floatingAdd";
    this.floatingDelete = "floatingDelete";
    this.buttonAddSubject = "buttonAddSubject";
    this.buttonHour = "buttonHour";
    this.buttonFinish = "buttonFinish";
    }

    @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.dialog_add_subjects, container);

    initComponents(view);

    return view;
  }

  private void initComponents(View view) {
    subjectName = (TextView) view.findViewById(R.id.subject_name);
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    getDialog().setCanceledOnTouchOutside(false);

    buttonNext = (Button) view.findViewById(R.id.bt_next);
    buttonNext.setOnClickListener(this);

    floatingButtonAdd = (FloatingActionButton) view.findViewById(R.id.fb_add);
    floatingButtonAdd.setOnClickListener(this);

    floatingButtonDelete = (FloatingActionButton) view.findViewById(R.id.fb_delete);
    floatingButtonDelete.setOnClickListener(this);

    btHour1 = (Button)  view.findViewById(R.id.bt_hour_1);
    btHour1.setOnClickListener(this);
    btHour2 = (Button)  view.findViewById(R.id.bt_hour_2);
    btHour2.setOnClickListener(this);
    btHour3 = (Button)  view.findViewById(R.id.bt_hour_3);
    btHour3.setOnClickListener(this);
    btHour4 = (Button)  view.findViewById(R.id.bt_hour_4);
    btHour4.setOnClickListener(this);
    btHour5 = (Button)  view.findViewById(R.id.bt_hour_5);
    btHour5.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

   if (v.getId() == R.id.fb_add){
           listener.onAddHourSubjectClickListener(floatingAdd);
       Log.d("LABEL TAG",floatingAdd);

   } else if(v.getId() == R.id.fb_delete){
       listener.onAddHourSubjectClickListener(floatingDelete);
       Log.d("LABEL TAG",floatingDelete);

   } else if (v.getId() == R.id.bt_hour_1){
       listener.onAddHourSubjectClickListener(0);

   } else if (v.getId() == R.id.bt_hour_2){
       listener.onAddHourSubjectClickListener(1);

   } else if (v.getId() == R.id.bt_hour_3){
       listener.onAddHourSubjectClickListener(2);

   } else if (v.getId() == R.id.bt_hour_4){
       listener.onAddHourSubjectClickListener(3);

   } else if (v.getId() == R.id.bt_hour_5){
       listener.onAddHourSubjectClickListener(4);

   } else if (v.getId() == R.id.bt_next){
       listener.onAddHourSubjectClickListener(5);

    }


   }




  public void setListener(OnAddHourSubjectClickListener listener) {
    this.listener = listener;
  }

  public interface OnAddHourSubjectClickListener {
    void onAddHourSubjectClickListener(String subject);
    void onAddHourSubjectClickListener(int i);
  }
}

