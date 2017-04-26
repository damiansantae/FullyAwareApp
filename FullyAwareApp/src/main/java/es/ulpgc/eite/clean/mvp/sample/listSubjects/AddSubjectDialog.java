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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import es.ulpgc.eite.clean.mvp.sample.R;


public class AddSubjectDialog extends DialogFragment implements View.OnClickListener {

  private EditText etSubjectName;
  private Button btAddSubject;
  private FloatingActionButton floatingButton;
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
    floatingButton = (FloatingActionButton) view.findViewById(R.id.fb_add);
    floatingButton.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
        //case R.id.bt_add_subject: {
        //if (isSubjectInfoValid())
         //listener.onAddSubjectClickListener(etSubjectName.getText().toString());
        //break;
      //}

        case R.id.fb_add: {
            if (isSubjectInfoValid())
                listener.onAddSubjectClickListener(etSubjectName.getText().toString());

            break;
        }

    }
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
