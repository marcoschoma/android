package br.radioactive.mchoma.radioactivebird.elements;

import android.graphics.Canvas;

/**
 * Created by MChoma on 30/08/2016.
 */
public class RadioactiveBird extends Character {

    protected float fireSpeed = 20;
    protected long fireInterval = 750;
    long lastShotTime;

    public RadioactiveBird(float x, float y){
        super(x, y);
        lastShotTime = System.currentTimeMillis();
        this.speed = 7;
        this.maxHealth = this.health = 300;
        this.width = 50;
        this.height = 50;
    }

    @Override
    public void moveTowards(float x, float y) {
        super.moveTowards(x+120, y);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(X, Y, X+width, Y+height, paint);
    }

    public Shot shoot() {
        Shot shot = new Shot(this.X, this.Y);
        shot.setSpeed(this.fireSpeed);
        lastShotTime = System.currentTimeMillis();
        return shot;
    }

    public boolean canShootAgain() {
        return System.currentTimeMillis() > lastShotTime + fireInterval;
    }
}
