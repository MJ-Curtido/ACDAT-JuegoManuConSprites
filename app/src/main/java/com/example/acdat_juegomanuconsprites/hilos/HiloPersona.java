package com.example.acdat_juegomanuconsprites.hilos;

import com.example.acdat_juegomanuconsprites.clases.Persona;

public class HiloPersona extends Thread {
    private Persona persona;
    private long tiempoDeUpdate;
    private int cont;
    boolean running;

    public HiloPersona(Persona persona) {
        this.persona = persona;

        tiempoDeUpdate = 20;
        cont = 0;
        running = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        try {
            while (running){
                cont++;
                Thread.sleep(tiempoDeUpdate);

                if (cont == 1000) {
                    cont = 0;
                    tiempoDeUpdate--;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
