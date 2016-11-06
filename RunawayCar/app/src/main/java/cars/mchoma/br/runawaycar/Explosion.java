package cars.mchoma.br.runawaycar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by marco on 11/09/2016.
 */
public class Explosion {

    protected Paint paint;
    protected int X, Y;
    private Bitmap bitmap;

    public boolean isTerminated = false;

    public Explosion(int x, int y, Bitmap bitmap){
        this.bitmap = bitmap;
        this.paint = new Paint();
        this.X = x;
        this.Y = y;
    }

    private int frame = 0;
    public void draw(Canvas canvas) {
        //canvas.drawBitmap(bitmap, getSourceRect(frame++), getDestRect(),  paint);
        canvas.drawBitmap(bitmap, getTestRect(), getTestRect(),  paint);
        isTerminated = frame == 9*9;
    }

    private Rect getTestRect(){
        return new Rect(0, 0, 100, 100);
    }
    private Rect getDestRect(){
        return new Rect(X, Y, X+100, Y+100);
    }

    private Rect getSourceRect(int frame) {
        int row = frame % 9;
        int col = frame / 9;

        return new Rect(bitmap.getWidth()/9 * row,
                bitmap.getHeight()/9 * col,
                bitmap.getWidth()/9 * row + 1,
                bitmap.getHeight()/9 * col + 1);
    }

}
