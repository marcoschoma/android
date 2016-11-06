package cars.mchoma.br.runawaycar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by marco on 11/09/2016.
 */
public class ExplosionFactory {

    private Bitmap bitmap;
    public ExplosionFactory(Resources resources) {
        this.bitmap = BitmapFactory.decodeResource(resources, R.drawable.explosion);
    }

    public Explosion createExplosion(int horizontalPosition, int verticalPosition) {
        return new Explosion(horizontalPosition, verticalPosition, bitmap);
    }

}
