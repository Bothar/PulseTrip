package model;

import android.app.DialogFragment;

/**
 * Created by David on 29/06/2016.
 */
public interface NoticeDialogListener {

        void onDialogReplayClick(DialogFragment dialog);
        void onDialogExitClick(DialogFragment dialog);
        void onSoundClick(DialogFragment dialog);


}
