package com.example.acdat_juegomanuconsprites.vistas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
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
    private Bitmap bmpCaida, bmpPersona, bmpCorazon;
    private HiloJuego hiloJuego;
    private Persona persona = null;
    private HiloPersona hiloPersona;
    private int maxPuntuacion, idPow, puntuacion;
    private SoundPool soundPool;
    private ArrayList<Platano> platanos;
    private ArrayList<HiloAnimacionPlatano> hilosAniPlatanos;
    private HiloPlatano hiloPlatano;
    private final Object lock = new Object();
    private HiloColisionPlatano hiloColisionPlatano;
    private MediaPlayer mp;

    public JuegoSV(Context context) {
        super(context);

        platanos = new ArrayList<Platano>();
        hilosAniPlatanos = new ArrayList<HiloAnimacionPlatano>();

        mp = MediaPlayer.create(getContext(), R.raw.soundtrack);
        mp.setLooping(true);
        mp.setVolume(0.3f, 0.3f);
        mp.start();

        maxPuntuacion = ((JuegoAct) context).getMaxPuntuacion();

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC , 0);
        idPow = soundPool.load(context, R.raw.platano, 0);

        getHolder().addCallback(this);
        setBackgroundResource(R.drawable.carretera_fondo);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        bmpCaida = BitmapFactory.decodeResource(getResources(), R.drawable.caida);
        bmpPersona = BitmapFactory.decodeResource(getResources(), R.drawable.persona);
        bmpCorazon = BitmapFactory.decodeResource(getResources(), R.drawable.vida);

        persona = new Persona(this, bmpPersona);
        hiloPersona = new HiloPersona(persona);
        hiloPersona.setRunning(true);
        hiloPersona.start();

        hiloJuego = new HiloJuego(this);
        hiloJuego.setRunning(true);
        hiloJuego.start();

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
        cerrarJuego();
    }

    public void cerrarJuego() {
        mp.stop();
        boolean retry = true;
        hiloJuego.setRunning(false);
        hiloPersona.setRunning(false);
        hiloPlatano.setRunning(false);
        hiloColisionPlatano.setRunning(false);

        while (retry) {
            try {
                hiloJuego.join();
                hiloPersona.join();
                hiloPlatano.join();
                hiloColisionPlatano.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void caida() {
        soundPool.play(idPow, 1, 1, 0, 0, 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        persona.setXSpeed();

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (persona.getVidas() > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.rgb(255, 221, 1));
            paint.setTextSize(77);

            for (int i = 0; i < persona.getVidas(); i++) {
                canvas.drawBitmap(bmpCorazon, bmpCorazon.getWidth() * i, 0, null);
            }

            canvas.drawText("Max. Puntuación: " + maxPuntuacion, 7, getHeight() - 40, paint);

            persona.onDraw(canvas);

            for (int i = 0; i < platanos.size(); i++) {
                platanos.get(i).onDraw(canvas);
            }

            if (hiloColisionPlatano.isColision()) {
                canvas.drawBitmap(bmpCaida, persona.getX(), persona.getY(), null);
            }
        }
        else {
            ((JuegoAct) getContext()).guardarPuntuacion(puntuacion);

            cerrarJuego();
            ((JuegoAct) getContext()).cerrar();
        }
    }

    public void eliminaPlatano() {
        platanos.remove(0);
        hilosAniPlatanos.get(0).setRunning(false);
        hilosAniPlatanos.remove(0);
    }

    public Boolean colisionPlatano() {
        for (int i = 0; i < platanos.size(); i++) {
            if (persona.isHover(platanos.get(i))) {
                caida();
                persona.vidaMenos();
                platanos.remove(i);
                hilosAniPlatanos.get(i).setRunning(false);
                hilosAniPlatanos.remove(i);
                return true;
            }
        }

        return false;
    }
}
