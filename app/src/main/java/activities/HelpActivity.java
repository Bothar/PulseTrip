package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import edu.ub.pis2016.dmiguel.pulsetrip.R;

/**
 * Created by David on 29/06/2016.
 */
public class HelpActivity extends Activity {

    TextView bckg;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        i = 0;
        bckg = (TextView) findViewById(R.id.image);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (i == 0) {
                    bckg.setBackgroundDrawable(getDrawable(R.drawable.help_2));
                    i++;
                }
                else if (i == 1) {
                    bckg.setBackgroundDrawable(getDrawable(R.drawable.help_3));
                    i++;
                }
                else {
                    finish();
                }

                return true;
        }return super.onTouchEvent(event);
    }
}
