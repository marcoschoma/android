package cars.mchoma.br.runawaycar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by marco on 11/09/2016.
 */
public class CarFactory {

    private int screenWidth, screenHeight;
    private int totalFaixas;
    private Resources resources;
    private Bitmap sheet;

    public Car createJogador() {
        return new Car(screenWidth/2, screenHeight, this.getCarBitmap(Car.CarType.Jogador), resources);
    }

    public CarFactory(int screenWidth, int screenHeight, int totalFaixas, Resources resources) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.totalFaixas = totalFaixas;
        this.resources = resources;

        Bitmap raw = BitmapFactory.decodeResource(resources, R.drawable.racer);
        this.sheet = Bitmap.createScaledBitmap(raw, (int)Car.DEFAULT_WIDTH*2, (int)Car.DEFAULT_HEIGHT*2, false);
    }

    public Car.CarType generateCarType(int i) {
        if(i % 3 == 0) {
            return Car.CarType.Inimigo1;
        } else if(i % 3 == 1) {
            return Car.CarType.Inimigo2;
        } else {
            return Car.CarType.Inimigo3;
        }
    }

    public Car createRandomEnemyOutsideScreen(Car.CarType carType) {
        int numeroFaixa = (int)(Math.random() * totalFaixas);

        int larguraFaixa = screenWidth / totalFaixas;

        Car enemy = new Car(numeroFaixa * larguraFaixa - larguraFaixa/2 - Car.DEFAULT_WIDTH/2, 0, this.getCarBitmap(carType), resources);
        enemy.setDestination(enemy.getHorizontalPosition(), screenHeight * 2);
        return enemy;
    }

    private Bitmap getCarBitmap(Car.CarType type) {
        if(type == Car.CarType.Jogador) {
            return Bitmap.createBitmap(sheet, 0, 0, Car.DEFAULT_WIDTH, Car.DEFAULT_HEIGHT);
        } else if (type == Car.CarType.Inimigo1) {
            return Bitmap.createBitmap(sheet, Car.DEFAULT_WIDTH, 0, Car.DEFAULT_WIDTH, Car.DEFAULT_HEIGHT);
        } else if (type == Car.CarType.Inimigo2) {
            return Bitmap.createBitmap(sheet, 0, Car.DEFAULT_HEIGHT, Car.DEFAULT_WIDTH, Car.DEFAULT_HEIGHT);
        } else {
            return Bitmap.createBitmap(sheet, Car.DEFAULT_WIDTH, Car.DEFAULT_HEIGHT, Car.DEFAULT_WIDTH, Car.DEFAULT_HEIGHT);
        }
    }
}
