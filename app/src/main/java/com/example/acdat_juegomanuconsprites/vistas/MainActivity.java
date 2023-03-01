package com.example.acdat_juegomanuconsprites.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
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

        setContentView(new JuegoSV(MainActivity.this));
    }

    public int getMaxPuntuacion() {
        return maxPuntuacion;
    }

    public void guardarPuntuacion(int puntuacion) {
        SharedPreferences preferencias = getSharedPreferences("datosApp", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("maxPuntuacion", puntuacion);

        editor.commit();
    }
}