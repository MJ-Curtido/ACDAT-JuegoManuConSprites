package com.example.acdat_juegomanuconsprites.clases;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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
        if(x > juegoSV.getWidth() - width - xSpeed - (juegoSV.getWidth() * CESPED_PORCENTAJE) || (x + xSpeed) < (juegoSV.getWidth() * CESPED_PORCENTAJE)) {
            //mostrar el pow
            vidaMenos();
        }

        x = x + xSpeed;

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void vidaMenos() {
        --vidas;
        juegoSV.caida();
        this.y = juegoSV.getAltoPantalla() - (juegoSV.getHeight() / 5);
        this.x = (juegoSV.getAnchoPantalla() / 2) - (width / 2);
    }

    public Rect getBounds(){
        return new Rect(x, y, x + bmp.getWidth(), y + bmp.getHeight());
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isTouched(float x2, float y2) {
        if(x2 > x && x2 < x + width && y2 > y && y2 < y + height){
            return true;
        }
        return false;
    }
}