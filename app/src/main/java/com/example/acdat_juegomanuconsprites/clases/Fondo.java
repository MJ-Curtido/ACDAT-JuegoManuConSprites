package com.example.acdat_juegomanuconsprites.clases;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

import java.util.LinkedList;
import java.util.Random;

public class Fondo {
    private static JuegoSV juegoSV;
    private Bitmap bmp;
    private int x = 0;
    private int y;
    private int xSpeed = 27;
    private static Random rnd;
    private static LinkedList<Fondo> fondos =new LinkedList<Fondo>();

    public Fondo(JuegoSV juegoSV, Bitmap bmp) {
        this.juegoSV = juegoSV;
        this.bmp=bmp;
        rnd=new Random();
        y = (int) (juegoSV.getWidth()*0.32);
        x = juegoSV.getWidth();
    }

    private void update(){
        x = x -xSpeed;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp, x, y,null);
    }

    public void recycle(){
        fondos.add(this);
        Log.i("Suelo","Suelo Reciclado");
    }

    public static void clear(){
        fondos.clear();
    }

    public int getX() {
        return x;
    }

    public int getAncho() {
        return bmp.getWidth();
    }

    public void setX(int x) {
        this.x = x;
    }
}

