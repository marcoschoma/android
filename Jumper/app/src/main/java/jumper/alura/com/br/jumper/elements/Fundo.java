package jumper.alura.com.br.jumper.elements;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import jumper.alura.com.br.jumper.R;

/**
 * Created by marco on 28/08/2016.
 */
public class Fundo {

    private Bitmap imagem;

    public Fundo(Resources resources, int largura, int altura) {
        Bitmap back = BitmapFactory.decodeResource(resources, R.drawable.background);
        imagem = Bitmap.createScaledBitmap(back, largura, altura, false);
    }


    public void desenhaNoCanvas(Canvas canvas) {
        canvas.drawBitmap(imagem, 0, 0, null);
    }
}
