package com.example.acdat_juegomanuconsprites.clases;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

import java.util.List;

public class TempPow {
    private List<TempPow> temps;
    private JuegoSV juegoSV;
    private float x;
    private float y;
    private Bitmap bmp;
    private int life = 15;

    public TempPow(List<TempPow> temps, JuegoSV juegoSV, float x, float y, Bitmap bmp) {
        this.temps = temps;
        this.juegoSV = juegoSV;
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0), juegoSV.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0), juegoSV.getHeight() - bmp.getHeight());
        this.bmp = bmp;
    }

    private void update(){
        if(--life < 1){
            temps.remove(this);
        }
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }
}
