package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.example.tictactoe.databinding.ActivitySplashBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Create Animations
        TranslateAnimation translateAnimation = new TranslateAnimation(-100, 0, 0, 0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        //Combine Animations
        AnimationSet animation = new AnimationSet(true);
        animation.setDuration(1000);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(translateAnimation);

        //Set Animations To Views
        binding.txtTicSplash.startAnimation(animation);
        binding.txtTacSplash.startAnimation(animation);
        binding.txtToeSplash.startAnimation(animation);

        //Create Timer For Start Main Activity
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}