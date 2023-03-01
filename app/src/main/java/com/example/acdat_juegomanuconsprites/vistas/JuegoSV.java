package com.example.acdat_juegomanuconsprites.vistas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.clases.Persona;
import com.example.acdat_juegomanuconsprites.clases.Platano;
import com.example.acdat_juegomanuconsprites.hilos.HiloAnimacionPlatano;
import com.example.acdat_juegomanuconsprites.hilos.HiloColisionPlatano;
import com.example.acdat_juegomanuconsprites.hilos.HiloJuego;
import com.example.acdat_juegomanuconsprites.hilos.HiloPersona;
import com.example.acdat_juegomanuconsprites.hilos.HiloPlatano;

import java.util.ArrayList;

public class JuegoSV extends SurfaceView implements SurfaceHolder.Callback {
    private Boolean continua;
    private Bitmap bmpCaida, bmpPersona, bmpCorazon;
    private HiloJuego hiloJuego;
    private Persona persona = null;
    private HiloPersona hiloPersona;
    private int maxPuntuacion, idPow;
    private SoundPool soundPool;
    private ArrayList<Platano> platanos;
    private ArrayList<Moneda> monedas;
    private ArrayList<HiloAnimacionPlatano> hilosAniPlatanos;
    private HiloPlatano hiloPlatano;
    private final Object lock = new Object();
    private HiloColisionPlatano hiloColisionPlatano;

    public JuegoSV(Context context) {
        super(context);

        maxPuntuacion = ((MainActivity) context).getMaxPuntuacion();
        continua = false;
        soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC , 0);
        idPow = soundPool.load(context, R.raw.platano, 0);

        getHolder().addCallback(this);
        setBackgroundResource(R.drawable.carretera_fondo);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        bmpCaida = BitmapFactory.decodeResource(getResources(), R.drawable.caida);

        bmpPersona = BitmapFactory.decodeResource(getResources(), R.drawable.persona);
        persona = new Persona(this, bmpPersona);
        hiloPersona = new HiloPersona(persona);

        bmpCorazon = BitmapFactory.decodeResource(getResources(), R.drawable.vida);

        hiloJuego = new HiloJuego(this);
        hiloJuego.setRunning(true);
        hiloJuego.start();

        platanos = new ArrayList<Platano>();
        monedas = new ArrayList<Moneda>();
        hilosAniPlatanos = new ArrayList<HiloAnimacionPlatano>();
        hiloPlatano = new HiloPlatano(this);
        hiloPlatano.setRunning(true);
        hiloPlatano.start();

        hiloColisionPlatano = new HiloColisionPlatano(this);
        hiloColisionPlatano.setRunning(true);
        hiloColisionPlatano.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public ArrayList<Platano> getPlatanos() {
        return platanos;
    }

    public ArrayList<HiloAnimacionPlatano> getHilosAniPlatanos() {
        return hilosAniPlatanos;
    }

    public int getAnchoPantalla() {
        return getWidth();
    }

    public int getAltoPantalla() {
        return getHeight();
    }

    public void setContinua(Boolean continua) {
        this.continua = continua;
    }

    public void caida() {
        soundPool.play(idPow, 1, 1, 0, 0, 1);
        pararJuego();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (continua) {
            persona.setXSpeed();
        }
        else {
            continuarJuego();
        }

        return false;
    }

    public void pararJuego() {
        setContinua(false);

        hiloColisionPlatano.setRunning(false);
        for (HiloAnimacionPlatano hilo : hilosAniPlatanos) {
            hilo.setRunning(false);
        }
        hilosAniPlatanos.clear();
        hiloPlatano.setRunning(false);
        hiloPersona.setRunning(false);
        platanos.clear();
    }

    public void continuarJuego() {
        setContinua(true);
        hiloColisionPlatano.setRunning(true);
        hiloPlatano.setRunning(true);
        hiloPersona.setRunning(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 221, 1));
        paint.setTextSize(77);

        if (continua) {
            for (int i = 0; i < persona.getVidas(); i++) {
                canvas.drawBitmap(bmpCorazon, bmpCorazon.getWidth() * i, 0, null);
            }

            canvas.drawText("Max. Puntuación: " + maxPuntuacion, 7, getHeight() - 40, paint);

            persona.onDraw(canvas);

            synchronized (lock) {
                for (Platano platano : platanos) {
                    platano.onDraw(canvas);
                }
            }

            if (hiloColisionPlatano.isColision()) {
                canvas.drawBitmap(bmpCaida, persona.getX(), persona.getY(), null);
                //pararJuego();
            }
        }
        else {
            for (int i = 0; i < persona.getVidas(); i++) {
                canvas.drawBitmap(bmpCorazon, getWidth() / 2 - (bmpCorazon.getWidth() * (persona.getVidas() / 2)) + (bmpCorazon.getWidth() * i), getHeight() / 2, null);
            }

            canvas.drawText("Pulse para empezar", (getWidth() / 2) - 270, (getHeight() / 2), paint);
        }
    }

    public void eliminaPlatano() {
        platanos.remove(0);
        hilosAniPlatanos.get(0).setRunning(false);
        hilosAniPlatanos.remove(0);
    }

    public Boolean colisionPlatano() {
        //Rect rectPersona = persona.getBounds();

        for (int i = 0; i < platanos.size(); i++) {
            /*
            Rect rectPlatano = platanos.get(i).getBounds();
            if (rectPersona.intersect(rectPlatano)) {
                platanos.remove(i);
                hilosAniPlatanos.get(i).setRunning(false);
                hilosAniPlatanos.remove(i);
                return true;
            }
            */

            if (persona.isTouched(platanos.get(i).getX(), platanos.get(i).getY()) || persona.isTouched(platanos.get(i).getX(), platanos.get(i).getBmp().getHeight()) || persona.isTouched(platanos.get(i).getBmp().getWidth(), platanos.get(i).getY()) || persona.isTouched(platanos.get(i).getBmp().getWidth(), platanos.get(i).getBmp().getHeight())) {

                return true;
            }
        }

        return false;
    }
}
