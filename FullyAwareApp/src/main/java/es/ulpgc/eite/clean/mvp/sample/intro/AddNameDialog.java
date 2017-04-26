package es.ulpgc.eite.clean.mvp.sample.intro;

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

/**
 * Created by roma on 05.11.15.
 */
public class AddNameDialog extends DialogFragment implements View.OnClickListener {

  private EditText etUserName;
  private Button btAddName;

  private OnAddUniversityClickListener listener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.dialog_add_name, container);
    initComponents(view);
    return view;
  }

  private void initComponents(View view) {
    etUserName = (EditText) view.findViewById(R.id.et_user_name);
    etUserName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    btAddName = (Button) view.findViewById(R.id.bt_add_user);
    btAddName.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_add_user: {
        if (isUniversityInfoValid())
          listener.onAddUserNameClickListener(etUserName.getText().toString());
        break;
      }
    }
  }

  private boolean isUniversityInfoValid() {
    return !etUserName.getText().toString().isEmpty();
  }

  public void setListener(OnAddUniversityClickListener listener) {
    this.listener = listener;
  }

  public interface OnAddUniversityClickListener {

    void onAddUserNameClickListener(String universityName);
  }
}
