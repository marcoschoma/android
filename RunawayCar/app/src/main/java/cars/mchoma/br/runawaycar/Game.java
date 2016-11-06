package cars.mchoma.br.runawaycar;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by marco on 07/09/2016.
 */
public class Game extends SurfaceView implements Runnable, View.OnTouchListener {
    private Screen screen;
    private SurfaceHolder holder;
    private Context context;
    private boolean isRunning;

    public Car character;
    private CarFactory carFactory;
    private Background background;
    private List<Car> enemies;
    private final float fixedCharacterY;
    private float currentTouchX;
    private List<Explosion> explosions;
    private ExplosionFactory explosionFactory;

    public Game(Context context) {
        super(context);
        this.context = context;
        this.screen = new Screen(this.context);

        holder = getHolder();

        this.screen = new Screen(this.context);
        carFactory = new CarFactory(screen.getWidth(), screen.getHeight(), totalFaixas, getResources());

        character = carFactory.createJogador();
        background = new Background(totalFaixas, this.screen.getWidth() / totalFaixas);

        this.currentTouchX = (this.screen.getWidth() + this.character.getWidth())/2;
        this.fixedCharacterY = this.screen.getHeight() - this.character.getHeight()*2;
        character.setDestination(this.currentTouchX, this.fixedCharacterY);


        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            enemies.add(carFactory.createRandomEnemyOutsideScreen(carFactory.generateCarType(i)));

        setOnTouchListener(this);

        this.explosions = new ArrayList<>();
        this.explosionFactory = new ExplosionFactory(getResources());
    }

    private int totalFaixas = 5;


    AtomicBoolean actionDownFlag = new AtomicBoolean(true);

    Thread movementThread;
    public Thread createMovementThread() {
        return new Thread(new Runnable(){
            public void run(){
                while(actionDownFlag.get()){
                    character.setDestination(currentTouchX, fixedCharacterY);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
            currentTouchX = event.getX();
            if(movementThread == null || !movementThread.isAlive()) {
                movementThread = createMovementThread();
                actionDownFlag.set(true);
                movementThread.start();
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            actionDownFlag.set(false);
            return true;
        }

        return false;
    }

    private int ticksPerSecond = 25;
    private int skipTicks = 1000/ticksPerSecond;
    private int maxFrameskip = 5;
    private long nextGameTick;
    private int loops;
    float interpolation;

    @Override
    public void run() {
        nextGameTick = System.currentTimeMillis();
        while(isRunning) {
            loops = 0;

            while (System.currentTimeMillis() > nextGameTick && loops < maxFrameskip) {
                updateGame();

                nextGameTick += skipTicks;
                loops++;
            }
            interpolation = (float)(System.currentTimeMillis() + skipTicks - nextGameTick) / (float)skipTicks;
            displayGame(interpolation);
        }
    }

    private void displayGame(float interpolation) {
        if(holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();

            background.draw(canvas, interpolation);
            character.draw(canvas, interpolation);
            for(Car enemy : enemies) {
                enemy.draw(canvas, interpolation);
            }

            //for(Explosion expl : explosions) {
            if(explosions.size() > 0) {
                Explosion expl = explosions.get(0);
                expl.draw(canvas);
                if(expl.isTerminated)
                    explosions.remove(0);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void updateGame() {
        character.moveToDestination();

        for(int j = 0; j < enemies.size(); j++) {
            Car enemy = enemies.get(j);
            if(enemy != null) {
                enemy.moveToDestination();
                if(enemy.collidesWith(this.character)) {
                    this.createExplosion((int)enemy.getHorizontalPosition(), (int)enemy.getVerticalPosition());
                    enemies.set(j, null);
                    continue;
                }
            }

            if (!enemy.isInsideScreen(screen.getHeight())) {
                enemies.set(j, null);
            }
        }
        enemies.removeAll(Collections.singleton(null));
        if(enemies.size() < 4) {
            enemies.add(carFactory.createRandomEnemyOutsideScreen(carFactory.generateCarType(enemies.size())));
        }
    }

    private void createExplosion(int horizontalPosition, int verticalPosition) {
        this.explosions.add(explosionFactory.createExplosion(horizontalPosition, verticalPosition));
    }

    public void start() {
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
    }
}
