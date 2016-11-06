package jumper.alura.com.br.jumper.elements;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import jumper.alura.com.br.jumper.R;

/**
 * Created by marco on 27/08/2016.
 */
public class Passaro {

    private final float VELOCIDADE_BASE = 9;
    private float velocidade = VELOCIDADE_BASE;

    public static int LARGURA = 120;
    public static int ALTURA = 80;

    public static final float posicaoLateral = 200;
    private float posicaoVertical;
    private final byte STATUS_PULA = 1, STATUS_CAI = 2, STATUS_NULO = 3;
    private byte status;

    private Paint paint;
    private Bitmap spriteSheet;

    public float getPosicaoVertical() {
        return posicaoVertical;
    }

    public Passaro(Resources resources, float posicaoVertical){
        this.status = STATUS_NULO;
        this.posicaoVertical = posicaoVertical;

        paint = new Paint();
        Bitmap rawImage = BitmapFactory.decodeResource(resources, R.drawable.passaro);
        spriteSheet = Bitmap.createScaledBitmap(rawImage, LARGURA*4, ALTURA*2, false);

//        paintDebug = new Paint();
//        paintDebug.setStyle(Paint.Style.STROKE);
//        paintDebug.setColor(0xFF000000);
//        paintDebug.setStrokeWidth(3);
    }
//    private Paint paintDebug;

    private int rotationDegrees = 0, rotationVariation = -5;
    public void desenhaNoCanvas(Canvas canvas, int quadro){
        Rect quadroDesenho = getQuadroDesenho(quadro);

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationDegrees);

        Bitmap imagem = Bitmap.createBitmap(spriteSheet, quadroDesenho.left, quadroDesenho.top, quadroDesenho.width(), quadroDesenho.height());
        Bitmap rotated = Bitmap.createBitmap(imagem, 0, 0, LARGURA, ALTURA, matrix, true);

        Rect posicaoDesenho = getPosicaoDesenho();
        canvas.drawBitmap(rotated, posicaoDesenho.left, posicaoDesenho.top, paint);

//        canvas.drawRect(getRectColisao(), paintDebug);
    }


    public void pula() {
        this.rotationDegrees = 0;
        this.status = STATUS_PULA;

        this.velocidade = -20;
    }

    public float getPosicaoLateral() {
        return posicaoLateral;
    }

    public Rect getQuadroDesenho(int quadro) {
        int qX = quadro % 4;
        int qY = quadro % 2;

        return new Rect(qX * LARGURA, qY * ALTURA,
                qX * LARGURA + LARGURA, qY * ALTURA + ALTURA);
    }
    public Rect getPosicaoDesenho() {
        return new Rect((int)posicaoLateral, (int)posicaoVertical, LARGURA, ALTURA);
    }

    private final int MAX_ROTACAO_PULO = -35;
    private final int MAX_ROTACAO_CAI = 30;
    public void move() {
        if(status == STATUS_NULO) {
            rotationVariation = 0;
        } else if(status == STATUS_PULA) {
            this.velocidade++;

            if(this.velocidade > 0){
                this.status = STATUS_CAI;
            }
            rotationVariation = MAX_ROTACAO_PULO;
        } else if(status == STATUS_CAI) {
            this.velocidade = VELOCIDADE_BASE;
            rotationVariation = MAX_ROTACAO_CAI;
        }

        rotationDegrees = rotationVariation;
        this.posicaoVertical += this.velocidade;
    }

    public void para() {
        this.status = STATUS_NULO;
    }

    public void cai() {
        this.status = STATUS_CAI;
        this.rotationDegrees = 0;
        this.velocidade = VELOCIDADE_BASE;
    }

    private Rect criaRetanguloColisao(int graus) {
        RectF rectColisao = new RectF((int)this.getPosicaoLateral()+15, (int)this.getPosicaoVertical()+15, (int)this.getPosicaoLateral() + this.LARGURA-15, (int)this.getPosicaoVertical() + this.ALTURA-15);
        Matrix m = new Matrix();
        m.setRotate(graus, rectColisao.centerX(), rectColisao.centerY());
        m.mapRect(rectColisao);
        return new Rect((int)rectColisao.left, (int)rectColisao.top, (int)rectColisao.right, (int)rectColisao.bottom);
    }

    public Rect getRectColisao() {
        if(this.status == STATUS_CAI) {
            return criaRetanguloColisao(MAX_ROTACAO_CAI);
        } else if (this.status == STATUS_PULA) {
            return criaRetanguloColisao(MAX_ROTACAO_PULO);
        } else {
            return criaRetanguloColisao(0);
        }
    }
}
