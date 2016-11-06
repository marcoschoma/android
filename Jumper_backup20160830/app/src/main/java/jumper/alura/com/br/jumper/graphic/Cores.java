package jumper.alura.com.br.jumper.graphic;

import android.graphics.Paint;

public class Cores {

    public static Paint getCorDoPassaro() {
        Paint paint = new Paint();
        paint.setColor(0xFFFF0000);
        return paint;
    }

    public static Paint getCorDoCano() {
        Paint paint = new Paint();
        paint.setColor(0xFF00FF00);
        return paint;
    }
}
