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


    private Button btFinish, btAddSubject;
    private EditText etSubjectName;
    private OnAddSubjectClickListener listener;

    /**
     * Auto-generated method.
     */
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

    /**
     * Inits the components of the view.
     */
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

    /**
     * Method called when a button is pressed.
     *
     * @param v -> the current view
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bt_add_subject) {
            if (isSubjectInfoValid())
                listener.onAddSubjectClickListener(etSubjectName.getText().toString());
        } else if (v.getId() == R.id.bt_finish) {
            listener.onAddSubjectClickListener("Finish");
        }
    }


    /**
     * Method that checks if the info inserted is valid.
     *
     * @return boolean true: if it is valid & false: if not.
     */
    private boolean isSubjectInfoValid() {
        return !etSubjectName.getText().toString().isEmpty();
    }

    /**
     * Sets the listener to the dialog.
     */
    public void setListener(OnAddSubjectClickListener listener) {
        this.listener = listener;
    }

    /**
     * Interface of the Click Listener (AddSubject).
     */
    public interface OnAddSubjectClickListener {
        void onAddSubjectClickListener(String subject);
    }
}
