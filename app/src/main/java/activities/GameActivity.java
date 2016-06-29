package activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;


import edu.ub.pis2016.dmiguel.pulsetrip.R;
import model.GamePanel;

public class GameActivity extends Activity implements PauseDialog.NoticeDialogListener {


    private GamePanel game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new GamePanel(this);
        setContentView(game);
    }

    @Override
    protected void onPause(){
        super.onPause();
        DialogFragment dialog = new PauseDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(),"");
        game.setPlaying(false);

    }

    @Override
    public void onBackPressed(){
        onPause();
    }

    @Override
    public void onDialogPlayClick(DialogFragment dialog){
        game.setPlaying(true);
    }

    @Override
    public void onDialogReplayClick(DialogFragment dialog){
        game.newGame();
    }

    @Override
    public void onDialogExitClick(DialogFragment dialog){
        finish();

    }


}
