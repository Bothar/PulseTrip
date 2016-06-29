package activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;


import edu.ub.pis2016.dmiguel.pulsetrip.R;
import model.GamePanel;
import model.NoticeDialogListener;

public class GameActivity extends Activity implements NoticeDialogListener {


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
    public void onDialogReplayClick(DialogFragment dialog){
        game.newGame();
    }

    @Override
    public void onDialogExitClick(DialogFragment dialog){
        finish();

    }


    public void onGameFinished(int score){
        Bundle data = new Bundle();
        data.putInt("score", (score));
        DialogFragment dialog = new FinalDialog();
        dialog.setCancelable(false);
        dialog.setArguments(data);
        dialog.show(getFragmentManager(),"");

    }


}
