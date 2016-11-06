package jumper.alura.com.br.jumper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import jumper.alura.com.br.jumper.engine.Game;

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
        game.inicia();
        new Thread(game).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }
}
