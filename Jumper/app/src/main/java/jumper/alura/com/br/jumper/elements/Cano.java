package jumper.alura.com.br.jumper.elements;

import android.graphics.Canvas;

import jumper.alura.com.br.graphic.Tela;
import jumper.alura.com.br.jumper.graphic.Cores;

/**
 * Created by marco on 27/08/2016.
 */
public class Cano {

    private Tela tela;
    private float posicaoLateral;
    private float posicaoVertical;
    public static final float LARGURA = 100;
    private final float TAMANHO;

    public Cano(Tela tela, float tamanho, float posicaoInicialLateral, float posicaoInicialVertical) {
        this.tela = tela;
        this.posicaoLateral = posicaoInicialLateral;
        TAMANHO = tamanho;
        this.posicaoVertical = posicaoInicialVertical;
    }

    public void desenhaNo(Canvas canvas) {
        //drawRect(left, top, right, bottom, ...paint) {
        canvas.drawRect(posicaoLateral, posicaoVertical, posicaoLateral + LARGURA, posicaoVertical + TAMANHO, Cores.getCorDoCano());
    }

    public void anda() {
        this.posicaoLateral -= 5;
    }

    public float getPosicaoLateral() {
        return posicaoLateral;
    }

    public boolean temColisao(Passaro passaro) {
        return Math.abs(passaro.getPosicaoVertical() - this.posicaoVertical) < Passaro.ALTURA / 2
                && Math.abs(passaro.getPosicaoLateral() - this.posicaoLateral) < Passaro.ALTURA / 2;
//        return (passaro.getPosicaoVertical() -  <
//                || passaro.getPosicaoVertical() + Passaro.ALTURA > this.posicaoVertical)
//                && this.posicaoLateral - Passaro.posicaoLateral < Passaro.ALTURA;
    }
}
