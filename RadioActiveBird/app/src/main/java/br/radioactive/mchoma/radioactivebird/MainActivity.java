package br.radioactive.mchoma.radioactivebird;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import br.radioactive.mchoma.radioactivebird.engine.Game;

public class MainActivity extends Activity {

    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout layout = (FrameLayout) findViewById(R.id.container);
        game = new Game(this);
        layout.addView(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.start();
        new Thread(game).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }
}
