package br.radioactive.mchoma.radioactivebird.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.radioactive.mchoma.radioactivebird.elements.Character;

/**
 * Created by marco on 30/08/2016.
 */
public class HealthBar {

    private static Paint paintBorder, paintFill;

    public static void draw(Canvas canvas, Character character) {
        if(paintBorder == null) {
            paintBorder = new Paint();
            paintBorder.setColor(0xAA0055FF);
            paintBorder.setStrokeWidth(3);
        }
        if(paintFill == null) {
            paintFill = new Paint();
            paintFill.setColor(0xAA0000FF);
        }

        canvas.drawRect(
                character.getHorizontalPosition() - 12,
                character.getVerticalPosition() - 15,
                character.getHorizontalPosition() + character.getWidth() + 12,
                character.getVerticalPosition() - 5,
                paintBorder);
        canvas.drawRect(
                character.getHorizontalPosition() - 12,
                character.getVerticalPosition() - 15,
                (character.getHorizontalPosition() + character.getWidth() + 12) * (character.getHealth() / character.getMaxHealth()),
                character.getVerticalPosition() - 5,
                paintFill);
    }

    public static long getShowDuration() {
        return 2000;
    }
}
