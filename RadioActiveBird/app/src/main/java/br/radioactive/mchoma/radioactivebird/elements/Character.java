package br.radioactive.mchoma.radioactivebird.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import br.radioactive.mchoma.radioactivebird.graphics.HealthBar;

/**
 * Created by MChoma on 30/08/2016.
 */
public class Character {

    protected float speed;
    protected float X, Y, width, height, collisionDamage;
    private float destinationX, destinationY;
    protected float health;
    protected float maxHealth;
    private long lastTimeHealthChanged;

    protected Paint paint;

    public Character(float x, float y){
        paint = new Paint();
        paint.setColor(0xFF00FF00);
        this.X = x;
        this.Y = y;
        this.speed = 1;
        this.maxHealth = this.health = 1;
    }

    public void draw(Canvas canvas) {
        //canvas.drawRect(X, Y, X+50, Y+50, paint);
        if(hasToShowHealthBar())
            HealthBar.draw(canvas, this);
    }

    public void moveTowards(float x, float y) {
        float distance = (float)Math.sqrt(Math.pow(this.X - x, 2) + Math.pow(this.Y - y, 2));
        float vx = speed * (x - this.X) / distance;
        float vy = speed * (y - this.Y) / distance;

        if(!Float.isNaN(vx) && vx != 0)
            if(Math.abs(x - this.X) < vx) {
                this.X = x;
            } else {
                this.X += vx;
            }
        if(!Float.isNaN(vy) && vy != 0)
            if(Math.abs(y - this.Y) < vy) {
                this.Y = y;
            } else {
                this.Y += vy;
            }
    }

    public boolean collidesWith(Character character) {
        return new Rect((int)character.X, (int)character.Y, (int)(character.X + character.getWidth()), (int)(character.Y + character.getHeight()))
                .intersects((int)this.X, (int)this.Y, (int)(this.X + this.getWidth()), (int)(this.Y + this.getHeight()));
    }

    public void notifyCollisionWith(Character character) {
        if(character.doesCollisionDealsDamage()) {
            this.updateHealth(character.collisionDamage);
        }
    }

    private void updateHealth(float amount) {
        this.health += amount;
        setLastTimeHealthChanged(System.currentTimeMillis());
    }

    public boolean isDead() {
        return health <= 0;
    }

    protected boolean collisionDealsDamage;
    protected boolean doesCollisionDealsDamage() {
        return collisionDealsDamage && !isDead();
    }

    public float getHorizontalPosition() {
        return X;
    }

    public float getVerticalPosition() {
        return Y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean hasToShowHealthBar() {
        return System.currentTimeMillis() > getLastTimeHealthChanged() + HealthBar.getShowDuration();
    }

    public long getLastTimeHealthChanged() {
        return lastTimeHealthChanged;
    }

    public void setLastTimeHealthChanged(long lastTimeHealthChanged) {
        this.lastTimeHealthChanged = lastTimeHealthChanged;
    }

    public boolean isInsideScreen(int screenW) {
        return this.X < screenW && this.X + this.getWidth() > 0;
    }

    public void setDestination(float destinationX, float destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public void moveToDestination() {
        this.moveTowards(destinationX, destinationY);
    }
}
