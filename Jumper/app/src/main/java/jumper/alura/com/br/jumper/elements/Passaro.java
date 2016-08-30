package jumper.alura.com.br.jumper.elements;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import jumper.alura.com.br.jumper.R;

/**
 * Created by marco on 27/08/2016.
 */
public class Passaro {

    private static final int NUM_SPRITES = 8;
    private static final int SPRITE_WIDTH = 598;
    private static final int SPRITE_HEIGHT = 402;
    public static int LARGURA = SPRITE_WIDTH / 5;
    public static int ALTURA = SPRITE_HEIGHT / 5;

    public static final float posicaoLateral = 200;
    private float posicaoVertical;

    private Paint paint;
    private Bitmap spriteSheet;
    private Rect frameToDraw = new Rect(0, 0, LARGURA, ALTURA);

    public float getPosicaoVertical() {
        return posicaoVertical;
    }

    public Passaro(Resources resources, float posicaoVertical){
        this.posicaoVertical = posicaoVertical;

        paint = new Paint();
        spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.passaro);
    }

    public void desenhaNoCanvas(Canvas canvas, int quadro){
        Rect quadroDesenho = getQuadroDesenho(quadro);

        Bitmap raw = Bitmap.createBitmap(spriteSheet, quadroDesenho.left, quadroDesenho.top, quadroDesenho.width(), quadroDesenho.height());
        Bitmap imagem = Bitmap.createScaledBitmap(raw, LARGURA, ALTURA, false);

        Rect posicaoDesenho = getPosicaoDesenho();
        canvas.drawBitmap(imagem, posicaoDesenho.left, posicaoDesenho.top, paint);
    }

    public void cai() {
        this.posicaoVertical += 7;
    }

    public void pula() {
        this.posicaoVertical -= 120;
    }

    public float getPosicaoLateral() {
        return posicaoLateral;
    }

    public Rect getQuadroDesenho(int quadro) {
        int qX = quadro % 4;
        int qY = quadro % 2;

        return new Rect(qX * SPRITE_WIDTH, qY * SPRITE_HEIGHT,
                qX * SPRITE_WIDTH + SPRITE_WIDTH, qY * SPRITE_HEIGHT + SPRITE_HEIGHT);
    }
    public Rect getPosicaoDesenho() {
        return new Rect((int)posicaoLateral, (int)posicaoVertical, LARGURA, ALTURA);
    }

    public void crescer(int variacaoDificuldade) {
        this.LARGURA += variacaoDificuldade;
        this.ALTURA += variacaoDificuldade;
    }
}
