package jumper.alura.com.br.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by marco on 27/08/2016.
 */
public class Pontuacao {

    private Paint paint;
    private int pontos = 0;

    public Pontuacao() {
        paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(0xFFFFFFFF);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setShadowLayer(3, 5, 5, 0xFF000000);
    }

    public void desenha(Canvas canvas) {
        canvas.drawText("" + this.pontos, 100, 100, paint);
    }

    public void aumenta() {
        pontos++;
    }

    public int getPontos() {
        return pontos;
    }
}
