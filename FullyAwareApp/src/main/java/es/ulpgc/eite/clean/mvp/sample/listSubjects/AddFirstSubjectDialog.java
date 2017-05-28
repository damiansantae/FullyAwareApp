package es.ulpgc.eite.clean.mvp.sample.listSubjects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.welcome.PrefManager;


public class AddFirstSubjectDialog extends DialogFragment implements View.OnClickListener {



  private Button btFinish;
  private Button btAddSubject;
  private PrefManager prefManager;
  private EditText etSubjectName;
    private OnAddSubjectClickListener listener;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
  }



    @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.dialog_first_add_subjects, container);
    initComponents(view);
    return view;
  }

  private void initComponents(View view) {
    etSubjectName = (EditText) view.findViewById(R.id.et_subject_name);
    btAddSubject = (Button) view.findViewById(R.id.bt_add_subject);
    etSubjectName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    getDialog().setCanceledOnTouchOutside(false);
    btAddSubject = (Button) view.findViewById(R.id.bt_add_subject);
    btAddSubject.setOnClickListener(this);
    btFinish = (Button) view.findViewById(R.id.bt_finish);
    btFinish.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {

   if (v.getId() == R.id.bt_add_subject){
       if (isSubjectInfoValid())
           listener.onAddSubjectClickListener(etSubjectName.getText().toString());
   } else if(v.getId() == R.id.bt_finish){
       listener.onAddSubjectClickListener("Finish");
   }
   }



    private boolean isSubjectInfoValid() {
    return !etSubjectName.getText().toString().isEmpty();
  }

    private boolean isDayInfoValid() {

        return !etSubjectName.getText().toString().isEmpty();
    }


  public void setListener(OnAddSubjectClickListener listener) {
    this.listener = listener;
  }

  public interface OnAddSubjectClickListener {
    void onAddSubjectClickListener(String subject);
  }
}
