package activities;



import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ub.pis2016.dmiguel.pulsetrip.R;


/**
 * Created by David on 29/06/2016.
 */
public class PauseDialog extends DialogFragment {

    private TextView playBtn;
    private TextView resetBtn;
    private TextView exitBtn;

    public interface NoticeDialogListener {
        public void onDialogPlayClick(DialogFragment dialog);
        public void onDialogReplayClick(DialogFragment dialog);
        public void onDialogExitClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pause, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initComponents(view);
        initListeners();
        return view;

    }

    private void initComponents(View view){

        playBtn = (TextView) view.findViewById(R.id.play_btn);
        resetBtn = (TextView) view.findViewById(R.id.reset_btn);
        exitBtn = (TextView) view.findViewById(R.id.exit_btn);
    }

    private void initListeners(){

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPlayClick(PauseDialog.this);
                dismiss();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogReplayClick(PauseDialog.this);
                dismiss();
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogExitClick(PauseDialog.this);
                dismiss();
            }
        });
    }


}
