package activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import edu.ub.pis2016.dmiguel.pulsetrip.R;

public class MainMenuActivity extends Activity {


    private TextView playBtn;
    private TextView optionBtn;
    private TextView soundBtn;
    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView txt = (TextView) findViewById(R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(), "game_font.ttf");
        txt.setTypeface(font);

        initComponents();
        initListeners();
    }

    private void initComponents() {
        playBtn = (TextView) findViewById(R.id.play_btn);
        optionBtn = (TextView) findViewById(R.id.option_btn);
        soundBtn = (TextView) findViewById(R.id.music_btn);

        sound = getSound();

        if (!sound){
            soundBtn.setBackgroundDrawable(getDrawable(R.drawable.btn_music_mute));
        }

    }

    private void initListeners() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound){
                    soundBtn.setBackgroundDrawable(getDrawable(R.drawable.btn_music_mute));
                    sound = false;
                    setSound(false);
                }

                else{
                    soundBtn.setBackgroundDrawable(getDrawable(R.drawable.btn_music));
                    sound = true;
                    setSound(true);
                }
            }
        });
    }

    public boolean getSound() {
        SharedPreferences pref = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        return pref.getBoolean("SOUND", true);
    }

    public void setSound(boolean sound) {
        SharedPreferences pref = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("SOUND", sound);
        editor.commit();
    }
}

