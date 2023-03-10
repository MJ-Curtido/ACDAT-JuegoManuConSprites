package com.example.acdat_juegomanuconsprites.clases;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

public class Moneda {
    private static final int SPEED = 27;
    private static final double CESPED_PORCENTAJE = 0.15;
    private int x, y;
    private int ySpeed;
    private JuegoSV juegoSV;
    private Bitmap bmp;
    private int width;
    private int height;

    public Moneda(JuegoSV juegoSV, Bitmap bmp) {
        this.juegoSV = juegoSV;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.y = 0 - height;
        this.x = (int) (Math.random() *
                ((juegoSV.getWidth() * (1 - CESPED_PORCENTAJE)) -
                        (juegoSV.getWidth() * CESPED_PORCENTAJE) - bmp.getWidth()) +
                (juegoSV.getWidth() * CESPED_PORCENTAJE));
        ySpeed = SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void update(){
        y = y + ySpeed;

        if (y > juegoSV.getHeight()) {
            juegoSV.eliminaMoneda();
        }
    }

    public void onDraw(Canvas canvas){
        update();

        canvas.drawBitmap(bmp, x, y, null);
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
