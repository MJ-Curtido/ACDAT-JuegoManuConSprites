package com.example.acdat_juegomanuconsprites.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class JuegoAct extends AppCompatActivity {
    private int maxPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferencias = getSharedPreferences ("datosApp", Context.MODE_PRIVATE);

        if (preferencias.getInt("maxPuntuacion", -1) == -1) {
            maxPuntuacion = 0;
        }
        else {
            maxPuntuacion = preferencias.getInt("maxPuntuacion", -1);
        }

        setContentView(new JuegoSV(JuegoAct.this));
    }

    public int getMaxPuntuacion() {
        return maxPuntuacion;
    }

    public void guardarPuntuacion(int puntuacion) {
        if (puntuacion > maxPuntuacion) {
            SharedPreferences preferencias = getSharedPreferences("datosApp", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferencias.edit();

            editor.putInt("maxPuntuacion", puntuacion);

            editor.commit();
        }
    }

    public void cerrar() {
        Intent intent = new Intent(this, GameOverAct.class);
        startActivity(intent);
        finish();
    }
}