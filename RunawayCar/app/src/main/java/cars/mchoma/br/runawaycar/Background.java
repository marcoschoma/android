package cars.mchoma.br.runawaycar;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

/**
 * Created by MChoma on 30/08/2016.
 */
public class Background {

    private Paint paint;
    private Paint paintFaixas;
    private Paint paintBordas;
    private int numFaixas;
    private int distanciaHorizontalFaixas;
    private final int alturaFaixa = 60;
    private final int expessuraFaixa = 8;

    public Background(int numFaixas, int distanciaHorizontalFaixas) {
        this.paint = new Paint();
        this.paint.setColor(0xFFAAAAAA);

        this.paintFaixas = new Paint();
        this.paintFaixas.setColor(0xFFFFFFFF);
        this.paintFaixas.setStyle(Paint.Style.STROKE);
        this.paintFaixas.setStrokeWidth(expessuraFaixa);
        this.paintFaixas.setPathEffect(new DashPathEffect(new float[] {alturaFaixa,alturaFaixa*3}, 0));

        this.paintBordas = new Paint();
        this.paintBordas.setColor(0xFFFFFFFF);
        this.paintBordas.setStyle(Paint.Style.STROKE);
        this.paintBordas.setStrokeWidth(5);

        this.numFaixas = numFaixas;
        this.distanciaHorizontalFaixas = distanciaHorizontalFaixas;
    }

    int posicaoVerticalFaixa = 0;
    public void draw(Canvas canvas, float interpolation) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        int posicaoHorizontalFaixa = distanciaHorizontalFaixas;
        if (posicaoVerticalFaixa > alturaFaixa*2)
            posicaoVerticalFaixa = 0;
        else
            posicaoVerticalFaixa += (int)(alturaFaixa * interpolation);

        for (int i = 0; i < numFaixas-1; i++) {
            canvas.drawLine(posicaoHorizontalFaixa, posicaoVerticalFaixa, posicaoHorizontalFaixa, posicaoVerticalFaixa+canvas.getHeight(), paintFaixas);
            posicaoHorizontalFaixa += distanciaHorizontalFaixas;
        }

        canvas.drawLine(0, 0, 0, canvas.getHeight(), paintBordas);
        canvas.drawLine(canvas.getWidth() - 5, 0, canvas.getWidth() - 5, canvas.getHeight(), paintBordas);
    }

}
