package br.radioactive.mchoma.radioactivebird.elements;

import android.graphics.Canvas;

import br.radioactive.mchoma.radioactivebird.graphics.HealthBar;

/**
 * Created by MChoma on 30/08/2016.
 */
public class Enemy extends Character{

    public Enemy(float x, float y) {
        super(x, y);
        paint.setColor(0xFFFF0000);
        this.maxHealth = this.health = 20;
        this.height = 200;
        this.width = 200;
        this.speed = 5;
        collisionDealsDamage = true;
        collisionDamage = -this.health;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.X+this.width/2, this.Y+this.height/2, this.height/2, paint);
        HealthBar.draw(canvas, this);
    }

    @Override
    public boolean isInsideScreen(int screenW) {
        return this.X + this.getWidth() > 0;
    }
}
