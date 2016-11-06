package cars.mchoma.br.runawaycar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by marco on 07/09/2016.
 */
public class Car extends Character {
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 202;

    private Bitmap bitmap;

    public Car(float x, float y, Bitmap bitmap , Resources resources) {
        super(x, y);

        this.height = this.DEFAULT_HEIGHT;
        this.width = this.DEFAULT_WIDTH;
        this.setVerticalSpeed(20);
        this.setHorizontalSpeed(20);
        this.paint.setColor(0xFF00FF00);

        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas, float interpolation) {
        super.draw(canvas);
        float fac = (this.getMovingSpeed() * interpolation);
        canvas.drawBitmap(bitmap, X+fac, Y, paint);
    }

    public enum CarType {
        Jogador,
        Inimigo1,
        Inimigo2,
        Inimigo3
    }
}
