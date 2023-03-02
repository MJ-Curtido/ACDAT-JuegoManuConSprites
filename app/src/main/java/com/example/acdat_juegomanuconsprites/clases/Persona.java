package com.example.acdat_juegomanuconsprites.clases;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

public class Persona {
    private static final int BMP_ROWS = 4, BMP_COLUMNS = 4, SPEED = 27;
    private static final double CESPED_PORCENTAJE = 0.15;
    private int x, y, xSpeed, currentFrame, width, height, vidas;
    private JuegoSV juegoSV;
    private Bitmap bmp;
    private int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};

    public Persona(JuegoSV juegoSV, Bitmap bmp) {
        this.juegoSV = juegoSV;
        currentFrame = 0;
        vidas = 3;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.y = juegoSV.getAltoPantalla() - (juegoSV.getHeight() / 5);
        this.x = (juegoSV.getAnchoPantalla() / 2) - (width / 2);
        xSpeed = SPEED;
    }

    public int getVidas() {
        return vidas;
    }

    public void setXSpeed() {
        this.xSpeed = -this.xSpeed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, 0) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public void update(){
        x = x + xSpeed;

        if(x > juegoSV.getWidth() - width - xSpeed - (juegoSV.getWidth() * CESPED_PORCENTAJE) || (x + xSpeed) < (juegoSV.getWidth() * CESPED_PORCENTAJE)) {
            vidaMenos();
            this.y = juegoSV.getAltoPantalla() - (juegoSV.getHeight() / 5);
            this.x = (juegoSV.getAnchoPantalla() / 2) - (width / 2);
        }

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void vidaMenos() {
        --vidas;
        juegoSV.caida(x, y);
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isHover(Platano platano) {
        double centroX = (width / 2) + x;
        double centroY = (height / 2) + y;

        double centroXR = (platano.getWidth() / 2) + platano.getX();
        double centroYR = (platano.getHeight() / 2) + platano.getY();

        double distanciaPuntos = Math.sqrt(Math.pow(centroXR - centroX, 2) + Math.pow(centroYR - centroY, 2));

        if(distanciaPuntos < (width / 1.7)){
            return true;
        }

        return false;
    }
}