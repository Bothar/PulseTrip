package activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import edu.ub.pis2016.dmiguel.pulsetrip.R;

public class SettingsActivity extends Activity {

    private TextView settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView txt = (TextView) findViewById(R.id.title);

        initComponents();
        initListeners();
    }

    private void initComponents() {
        settingsBtn = (TextView) findViewById(R.id.reset_btn);

    }

    private void initListeners() {
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("SCORE", 0);
                editor.commit();
                settingsBtn.setBackgroundResource(R.drawable.blank_btn_pressed);
            }
        });

}

}
