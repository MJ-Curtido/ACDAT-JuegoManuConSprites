package com.example.acdat_juegomanuconsprites.hilos;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.clases.Platano;
import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

import java.util.ArrayList;

public class HiloPlatano extends Thread{
    private long tiempoDeUpdate;
    boolean running;
    private ArrayList<Platano> platanos;
    private JuegoSV juegoSV;
    private ArrayList<HiloAnimacionPlatano> hilosAniPlatanos;

    public HiloPlatano(JuegoSV juegoSV) {
        tiempoDeUpdate = 1170;
        running = false;

        this.juegoSV = juegoSV;
        platanos = juegoSV.getPlatanos();
        hilosAniPlatanos = juegoSV.getHilosAniPlatanos();
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            while (running){
                Thread.sleep(tiempoDeUpdate);

                Log.i("HiloPlatano", "Platano creado");

                platanos.add(new Platano(juegoSV, BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.platano1)));
                HiloAnimacionPlatano hiloAnimacionPlatano = new HiloAnimacionPlatano(juegoSV, platanos.get(platanos.size() - 1));
                hilosAniPlatanos.add(hiloAnimacionPlatano);
                hiloAnimacionPlatano.setRunning(true);
                hiloAnimacionPlatano.start();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
