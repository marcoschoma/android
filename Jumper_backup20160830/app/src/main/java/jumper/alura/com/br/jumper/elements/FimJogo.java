package jumper.alura.com.br.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by marco on 28/08/2016.
 */
public class FimJogo {

    private final float tamanhoTexto = 80;
    public FimJogo() {

    }

    private Paint criaPaint() {
        Paint paint = new Paint();
        paint.setTextSize(tamanhoTexto);
        paint.setColor(0xFFFFFFFF);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setShadowLayer(3, 5, 5, 0xFF000000);
        return paint;
    }


    public void DesenhaFimJogo(Canvas canvas, int pontuacao) {
        Paint paintSup = criaPaint();
        canvas.drawText("Eita bateu", canvas.getWidth() / 2 - tamanhoTexto * 2, canvas.getHeight() / 2 - tamanhoTexto, paintSup);

        Paint paintInf = criaPaint();
        String msgPontuacao = getMensagemPontuacao(pontuacao);
        canvas.drawText(msgPontuacao, canvas.getWidth() / 2 - tamanhoTexto * 4, canvas.getHeight() / 2, paintInf);
    }

    public String getMensagemPontuacao(int pontuacao) {
        return "Você fez " + pontuacao + " ponto" + (pontuacao != 1 ? "s" : "") +"!";
    }

}
