package com.example.acdat_juegomanuconsprites.hilos;

import android.graphics.BitmapFactory;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.clases.Platano;
import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

public class HiloAnimacionPlatano extends Thread
{
    private Platano platano;
    private long tiempoDeUpdate;
    boolean running;
    private JuegoSV juegoSV;

    public HiloAnimacionPlatano(JuegoSV juegoSV, Platano platano) {
        tiempoDeUpdate = 200;
        running = false;

        this.platano = platano;
        this.juegoSV = juegoSV;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            while (running){
                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano1));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano2));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano3));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano4));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano5));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano6));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano7));
                Thread.sleep(tiempoDeUpdate);

                platano.setBmp(BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano8));
                Thread.sleep(tiempoDeUpdate);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
