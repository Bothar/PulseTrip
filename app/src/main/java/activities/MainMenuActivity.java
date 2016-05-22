package activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import edu.ub.pis2016.dmiguel.pulsetrip.R;

public class MainMenuActivity extends Activity {


    private TextView playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initComponents();
        initListeners();
    }

    private void initComponents() {
        playBtn = (TextView) findViewById(R.id.play_btn);

    }

    private void initListeners() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}

