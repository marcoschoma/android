package br.radioactive.mchoma.radioactivebird.elements;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.radioactive.mchoma.radioactivebird.Screen;

/**
 * Created by MChoma on 30/08/2016.
 */
public class Shot extends Character{

    public Shot(float x, float y){
        super(x, y);
        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        this.speed = 10;
        this.collisionDamage = -10;
        this.collisionDealsDamage = true;
        this.maxHealth = this.health = 10;
        this.width = this.height = 10;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        if(!isDead())
            canvas.drawCircle(X, Y, 5, paint);
    }
}
