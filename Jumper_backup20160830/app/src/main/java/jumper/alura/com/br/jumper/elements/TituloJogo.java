package jumper.alura.com.br.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import jumper.alura.com.br.graphic.Tela;

/**
 * Created by MChoma on 29/08/2016.
 */
public class TituloJogo {

    private Paint paint;
    private int tamanhoTexto = 140;
    private Tela tela;

    public TituloJogo(Tela tela) {
        this.tela = tela;
        paint = new Paint();
        paint.setTextSize(tamanhoTexto);
        paint.setColor(0xFFFFFFFF);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setShadowLayer(3, 5, 5, 0xFF000000);
    }

    public void desenha(Canvas canvas) {
        canvas.drawText("Crazy Bird", tela.getLargura() / 2 - tamanhoTexto, tela.getAltura() / 2 - tamanhoTexto, paint);
    }
}
