package com.example.acdat_juegomanuconsprites.hilos;

import android.graphics.BitmapFactory;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.clases.Moneda;
import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

public class HiloAnimacionMoneda extends Thread{
    private Moneda moneda;
    private long tiempoDeUpdate;
    boolean running;
    private JuegoSV juegoSV;

    public HiloAnimacionMoneda(JuegoSV juegoSV, Moneda moneda) {
        tiempoDeUpdate = 200;
        running = false;

        this.moneda = moneda;
        this.juegoSV = juegoSV;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            while (running){
                moneda.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda1));
                Thread.sleep(tiempoDeUpdate);
                moneda.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda2));
                Thread.sleep(tiempoDeUpdate);
                moneda.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda3));
                Thread.sleep(tiempoDeUpdate);
                moneda.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda4));
                Thread.sleep(tiempoDeUpdate);
                moneda.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda5));
                Thread.sleep(tiempoDeUpdate);
                moneda.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda6));
                Thread.sleep(tiempoDeUpdate);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
