package br.radioactive.mchoma.radioactivebird.elements;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by MChoma on 30/08/2016.
 */
public class Background {

    private Paint paint;

    public Background() {
        this.paint = new Paint();
        paint.setColor(0xFF000000);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

}
