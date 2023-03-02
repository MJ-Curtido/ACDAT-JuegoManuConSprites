package com.example.acdat_juegomanuconsprites.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnJugar.setOnClickListener(this);

        mp = MediaPlayer.create(this, R.raw.opening);
        mp.setLooping(true);
        mp.setVolume(0.3f, 0.3f);
        mp.start();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, JuegoAct.class);
        startActivity(intent);
        mp.stop();
        finish();
    }
}