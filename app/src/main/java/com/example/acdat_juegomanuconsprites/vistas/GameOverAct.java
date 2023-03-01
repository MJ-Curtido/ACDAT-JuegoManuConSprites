package com.example.acdat_juegomanuconsprites.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.acdat_juegomanuconsprites.R;
import com.example.acdat_juegomanuconsprites.databinding.ActivityGameOverBinding;

public class GameOverAct extends AppCompatActivity implements View.OnClickListener {
    ActivityGameOverBinding binding;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnVolverAJugar.setOnClickListener(this);
        binding.btnSalir.setOnClickListener(this);

        mp = MediaPlayer.create(this, R.raw.game_over);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVolverAJugar:
                mp.stop();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btnSalir:
                mp.stop();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        super.onBackPressed();
    }
}