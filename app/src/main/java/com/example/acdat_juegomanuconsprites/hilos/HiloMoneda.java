package com.example.acdat_juegomanuconsprites.hilos;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.clases.Moneda;
import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

import java.util.ArrayList;

public class HiloMoneda extends Thread{
    private long tiempoDeUpdate;
    boolean running;
    private ArrayList<Moneda> monedas;
    private JuegoSV juegoSV;
    private ArrayList<HiloAnimacionMoneda> hilosAniMonedas;

    public HiloMoneda(JuegoSV juegoSV) {
        tiempoDeUpdate = 1700;
        running = false;

        this.juegoSV = juegoSV;
        monedas = juegoSV.getMonedas();
        hilosAniMonedas = juegoSV.getHilosAniMonedas();
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

                monedas.add(new Moneda(juegoSV, BitmapFactory.decodeResource(juegoSV.getResources(), R.drawable.moneda1)));
                HiloAnimacionMoneda hiloAnimacionMoneda = new HiloAnimacionMoneda(juegoSV, monedas.get(monedas.size() - 1));
                hilosAniMonedas.add(hiloAnimacionMoneda);
                hiloAnimacionMoneda.setRunning(true);
                hiloAnimacionMoneda.start();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
