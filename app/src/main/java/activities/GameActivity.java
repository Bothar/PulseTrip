package activities;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;

import edu.ub.pis2016.dmiguel.pulsetrip.R;
import model.GamePanel;

public class GameActivity extends Activity {

    private Canvas canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GamePanel(this));
    }
}
