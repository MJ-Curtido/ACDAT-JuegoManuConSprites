package com.example.acdat_juegomanuconsprites.hilos;

import com.example.acdat_juegomanuconsprites.vistas.JuegoSV;

public class HiloColisionPlatano extends Thread{
    private boolean running;
    private JuegoSV juegoSV;
    private boolean colision;

    public HiloColisionPlatano(JuegoSV juegoSV) {
        running = false;
        this.juegoSV = juegoSV;
        colision = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void run() {
        while (running) {
            try {
                if(juegoSV.colisionPlatano()){
                    colision = true;
                }
                else {
                    colision = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isColision() {
        return colision;
    }
}
