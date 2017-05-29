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
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;


public class AddNameDialog extends DialogFragment implements View.OnClickListener {

    private EditText etUserName;
    private Button btAddName;
    private OnAddUserClickListener listener;

    /**
     * Auto-generated method to create the addName dialog.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    /**
     * Creates the view itself (inflater, container).
     */
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_add_name, container);
        initComponents(view);
        return view;
    }

    /**
     * Method that initializes the components of the view.
     *
     * @param view -> the current view.
     */
    private void initComponents(View view) {
        etUserName = (EditText) view.findViewById(R.id.et_subject_name);
        etUserName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setCanceledOnTouchOutside(false);
        btAddName = (Button) view.findViewById(R.id.bt_add_user);
        btAddName.setOnClickListener(this);
    }

    /**
     * Method called when a button is pressed.
     *
     * @param v -> the current view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_user: {
                if (isUserInfoValid())
                    listener.onAddUserNameClickListener(etUserName.getText().toString());
                break;
            }
        }
    }

    /**
     * Method that checks if the info inserted is valid.
     *
     * @return boolean true: if it is valid & false: if not.
     */
    private boolean isUserInfoValid() {
        return !etUserName.getText().toString().isEmpty();
    }

    /**
     * Sets the listener to the dialog.
     */
    public void setListener(OnAddUserClickListener listener) {
        this.listener = listener;
    }

    /**
     * Interface of the Click Listener (AddName).
     */
    public interface OnAddUserClickListener {

        void onAddUserNameClickListener(String userName);
    }
}
