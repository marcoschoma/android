package jumper.alura.com.br.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import jumper.alura.com.br.graphic.Tela;

/**
 * Created by marco on 27/08/2016.
 */
public class Cano {

    private Tela tela;
    private float posicaoLateral;
    private float posicaoVertical;
    public static final int LARGURA = 140;
    private final float ALTURA;
    private final boolean isCanoCima;
    private final int tamanhoBocaCano = 60;

    public Cano(Tela tela, float tamanho, float posicaoInicialLateral, float posicaoInicialVertical, boolean isCanoCima) {
        this.tela = tela;
        this.posicaoLateral = posicaoInicialLateral;
        ALTURA = tamanho;
        this.posicaoVertical = posicaoInicialVertical;
        this.isCanoCima = isCanoCima;
    }

    public void desenhaNo(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xFF00FF00);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(posicaoLateral, posicaoVertical, posicaoLateral + LARGURA, posicaoVertical + ALTURA, paint);

        if(isCanoCima)
            canvas.drawRect(posicaoLateral - 10, posicaoVertical + ALTURA - tamanhoBocaCano, posicaoLateral + LARGURA + 10, posicaoVertical + ALTURA, paint);
        else
            canvas.drawRect(posicaoLateral - 10, posicaoVertical, posicaoLateral + LARGURA + 10, posicaoVertical + tamanhoBocaCano, paint);

        paint.setColor(0xFF000000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawRect(posicaoLateral, posicaoVertical, posicaoLateral + LARGURA, posicaoVertical + ALTURA, paint);

        if(isCanoCima)
            canvas.drawRect(posicaoLateral - 10, posicaoVertical + ALTURA - tamanhoBocaCano, posicaoLateral + LARGURA + 10, posicaoVertical + ALTURA, paint);
        else
            canvas.drawRect(posicaoLateral - 10, posicaoVertical, posicaoLateral + LARGURA + 10, posicaoVertical + tamanhoBocaCano, paint);
    }

    public void anda() {
        this.posicaoLateral -= 5;
    }

    public float getPosicaoLateral() {
        return posicaoLateral;
    }
    public float getPosicaoVertical() {
        return posicaoVertical;
    }

    public boolean temColisao(Passaro passaro) {
        Rect a = new Rect((int)passaro.getPosicaoLateral(), (int)passaro.getPosicaoVertical(), (int)passaro.getPosicaoLateral() + passaro.LARGURA, (int)passaro.getPosicaoVertical() + passaro.ALTURA);
        Rect b = new Rect((int)this.getPosicaoLateral(), (int)this.getPosicaoVertical(), (int)this.getPosicaoLateral() + this.LARGURA, (int)(this.getPosicaoVertical() + this.ALTURA));

        return a.intersect(b);
    }
}
