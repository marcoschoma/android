package br.radioactive.mchoma.radioactivebird.engine;

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

import br.radioactive.mchoma.radioactivebird.Screen;
import br.radioactive.mchoma.radioactivebird.elements.Background;
import br.radioactive.mchoma.radioactivebird.elements.Enemy;
import br.radioactive.mchoma.radioactivebird.elements.RadioactiveBird;
import br.radioactive.mchoma.radioactivebird.elements.Shot;

/**
 * Created by MChoma on 30/08/2016.
 */
public class Game extends SurfaceView implements Runnable, View.OnTouchListener {

    private Screen screen;
    private SurfaceHolder holder;
    private Context context;
    private boolean isRunning;

    private RadioactiveBird character;
    private List<Shot> friendlyShots;
    private Background background;
    private List<Enemy> enemies;

    public Game(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();

        this.screen = new Screen(this.context);

        character = new RadioactiveBird(400, 400);
        background = new Background();
        friendlyShots = new ArrayList<>();

        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            enemies.add(createRandomEnemyOutsideScreen());

        setOnTouchListener(this);
    }

    private Enemy createRandomEnemyOutsideScreen() {
        Enemy enemy = new Enemy(screen.getWidth() + character.getHorizontalPosition() + (float)(Math.random() * screen.getWidth()), (float)(Math.random() * screen.getHeight()));
        enemy.setDestination(-1000, enemy.getVerticalPosition());
        return enemy;
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

            background.draw(canvas);
            character.draw(canvas);
            for(Enemy enemy : enemies) {
                enemy.draw(canvas);
            }
            for(Shot shot : friendlyShots) {
                shot.draw(canvas);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void updateGame() {
        character.moveToDestination();

        if(character.canShootAgain()) {// && actionDownFlag.get()
            Shot shot = character.shoot();
            shot.setDestination(screen.getWidth(), character.getVerticalPosition());
            friendlyShots.add(shot);
        }

        for(int j = 0; j < enemies.size(); j++) {
            Enemy enemy = enemies.get(j);
            if(enemy != null)
                enemy.moveToDestination();

            if (enemy.isDead() || !enemy.isInsideScreen(screen.getWidth())) {
                enemies.set(j, null);
            }
        }
        enemies.removeAll(Collections.singleton(null));
        if(enemies.size() < 4) {
            enemies.add(createRandomEnemyOutsideScreen());
        }

        shots: for(int i = 0; i < friendlyShots.size(); i++) {
            Shot shot = (Shot)friendlyShots.get(i);
            if(shot.isInsideScreen(screen.getWidth())) {
                shot.moveToDestination();
                for(int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = (Enemy)enemies.get(j);

                    if(enemy == null)
                        continue;
                    if (shot.collidesWith(enemy)) {
                        enemy.notifyCollisionWith(shot);
                        shot.notifyCollisionWith(enemy);
                    }
                    if(shot.isDead()) {
                        friendlyShots.set(i, null);
                        continue shots;
                    }
                }
            } else {
                friendlyShots.set(i, null);
            }
        }
        friendlyShots.removeAll(Collections.singleton(null));
    }

    AtomicBoolean actionDownFlag = new AtomicBoolean(true);
    float currentTouchX, currentTouchY;

    Thread movementThread;
    public Thread createMovementThread() {
        return new Thread(new Runnable(){
            public void run(){
                while(actionDownFlag.get()){
                    character.setDestination(currentTouchX, currentTouchY);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
            currentTouchX = event.getX();
            currentTouchY = event.getY();
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

    public void start() {
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
    }

}
