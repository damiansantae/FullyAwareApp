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
import android.widget.LinearLayout;

import es.ulpgc.eite.clean.mvp.sample.R;


public class AddSubjectDialog extends DialogFragment implements View.OnClickListener {

  private EditText etSubjectName;
  private Button btAddSubject;
  private FloatingActionButton floatingButtonAdd;
  private FloatingActionButton floatingButtonDelete;
  private OnAddSubjectClickListener listener;
  private String floatingAdd;
  private String floatingDelete;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
      setFloatingButtonsLabels();
  }

    private void setFloatingButtonsLabels() {
    this.floatingAdd = getResources().getString(R.string.floatingAdd_label);
    this.floatingDelete = getResources().getString(R.string.floatingDelete_label);
        Log.d("LABEL TAG", floatingAdd);
        Log.d("LABEL TAG", floatingDelete);
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
    etSubjectName = (EditText) view.findViewById(R.id.et_subject_name);
    etSubjectName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    btAddSubject = (Button) view.findViewById(R.id.bt_add_subject);
    btAddSubject.setOnClickListener(this);
    floatingButtonAdd = (FloatingActionButton) view.findViewById(R.id.fb_add);
    floatingButtonAdd.setOnClickListener(this);
    floatingButtonDelete = (FloatingActionButton) view.findViewById(R.id.fb_delete);
    floatingButtonDelete.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {

   if (v.getId() == R.id.fb_add){
       if (isSubjectInfoValid())
           listener.onAddSubjectClickListener(floatingAdd);
       Log.d("LABEL TAG",floatingAdd);
   } else if(v.getId() == R.id.fb_delete){
       listener.onAddSubjectClickListener(floatingDelete);
       Log.d("LABEL TAG",floatingDelete);
   } else if (v.getId() == R.id.bt_add_subject){
       checkSelections();
   }
   }

    private void checkSelections() {
    LinearLayout l1 = (LinearLayout) getView().findViewById(R.id.time_1);
    LinearLayout l2 = (LinearLayout) getView().findViewById(R.id.time_2);
    LinearLayout l3 = (LinearLayout) getView().findViewById(R.id.time_3);
    LinearLayout l4 = (LinearLayout) getView().findViewById(R.id.time_4);
    LinearLayout l5 = (LinearLayout) getView().findViewById(R.id.time_5);

        //TODO:Pendiente analizar todo el dialog y recoger los datos para crear las Subjects.



    }


    private boolean isSubjectInfoValid() {
    return !etSubjectName.getText().toString().isEmpty();
  }



  public void setListener(OnAddSubjectClickListener listener) {
    this.listener = listener;
  }

  public interface OnAddSubjectClickListener {
    void onAddSubjectClickListener(String subject);

  }
}
