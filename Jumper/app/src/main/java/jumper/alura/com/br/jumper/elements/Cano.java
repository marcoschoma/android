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
    private static final int VELOCIDADE_BASE = 6;
    private static int velocidade;

    public Cano(Tela tela, float tamanho, float posicaoInicialLateral, float posicaoInicialVertical, boolean isCanoCima) {
        this.tela = tela;
        this.posicaoLateral = posicaoInicialLateral;
        ALTURA = tamanho;
        this.posicaoVertical = posicaoInicialVertical;
        this.isCanoCima = isCanoCima;
    }

    public void desenhaNo(Canvas canvas) {
        Paint paintCor = new Paint();
        paintCor.setColor(0xFF00FF00);
        paintCor.setStyle(Paint.Style.FILL);
        canvas.drawRect(posicaoLateral, posicaoVertical, posicaoLateral + LARGURA, posicaoVertical + ALTURA, paintCor);

        Paint paintBorda = new Paint();
        paintBorda.setColor(0xFF000000);
        paintBorda.setStyle(Paint.Style.STROKE);
        paintBorda.setStrokeWidth(3);
        canvas.drawRect(posicaoLateral, posicaoVertical, posicaoLateral + LARGURA, posicaoVertical + ALTURA, paintBorda);

        if(isCanoCima)
            canvas.drawRect(posicaoLateral - 10, posicaoVertical + ALTURA - tamanhoBocaCano, posicaoLateral + LARGURA + 10, posicaoVertical + ALTURA, paintCor);
        else
            canvas.drawRect(posicaoLateral - 10, posicaoVertical, posicaoLateral + LARGURA + 10, posicaoVertical + tamanhoBocaCano, paintCor);

        if(isCanoCima)
            canvas.drawRect(posicaoLateral - 10, posicaoVertical + ALTURA - tamanhoBocaCano, posicaoLateral + LARGURA + 10, posicaoVertical + ALTURA, paintBorda);
        else
            canvas.drawRect(posicaoLateral - 10, posicaoVertical, posicaoLateral + LARGURA + 10, posicaoVertical + tamanhoBocaCano, paintBorda);
    }

    public void anda() {
        this.posicaoLateral -= velocidade;
    }

    public float getPosicaoLateral() {
        return posicaoLateral;
    }
    public float getPosicaoVertical() {
        return posicaoVertical;
    }

    public boolean temColisao(Passaro passaro) {
        //Rect a = new Rect((int)passaro.getPosicaoLateral()+20, (int)passaro.getPosicaoVertical()+20, (int)passaro.getPosicaoLateral() + passaro.LARGURA-20, (int)passaro.getPosicaoVertical() + passaro.ALTURA-20);
        Rect a = passaro.getRectColisao();
        Rect b = new Rect((int)this.getPosicaoLateral(), (int)this.getPosicaoVertical(), (int)this.getPosicaoLateral() + this.LARGURA, (int)(this.getPosicaoVertical() + this.ALTURA));

        return a.intersect(b);
    }

    public static void iniciarVelocidadeCanos() {
        velocidade = VELOCIDADE_BASE;
    }

    public static void aumentarVelocidadeCanos(int aumento) {
        velocidade = VELOCIDADE_BASE + aumento;
    }
}
