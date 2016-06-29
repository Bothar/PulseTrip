package activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.ub.pis2016.dmiguel.pulsetrip.R;
import model.GamePanel;
import model.NoticeDialogListener;
import android.media.MediaPlayer;

public class GameActivity extends Activity implements NoticeDialogListener {


    private GamePanel game;
    private MediaPlayer player;
    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new GamePanel(this);
        setContentView(game);
        sound = getSound();
        player = MediaPlayer.create(GameActivity.this, R.raw.pulsetrip);
        player.setLooping(true);
    }

    @Override
    protected void onPause(){
        super.onPause();
        player.pause();
        DialogFragment dialog = new PauseDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "");
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

    public void onSoundClick(DialogFragment dialog){
        sound = getSound();
    }

    public void onPlaying(){
        if (sound) player.start();
    }


    public void onGameFinished(int score){
        Bundle data = new Bundle();
        data.putInt("score", (score));
        DialogFragment dialog = new FinalDialog();
        dialog.setCancelable(false);
        dialog.setArguments(data);
        dialog.show(getFragmentManager(), "");
        player.pause();
        player = MediaPlayer.create(GameActivity.this, R.raw.pulsetrip);
        player.setLooping(true);

    }

    public boolean getSound() {
        SharedPreferences pref = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        return pref.getBoolean("SOUND", true);
    }


}
