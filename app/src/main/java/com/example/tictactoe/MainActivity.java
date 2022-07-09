package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.tictactoe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPre sharedPre = new SharedPre(MainActivity.this);
        binding.edtPlayer1.setText(sharedPre.getPlayer1());
        binding.edtPlayer2.setText(sharedPre.getPlayer2());

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String player1 = binding.edtPlayer1.getText().toString();
                String player2 = binding.edtPlayer2.getText().toString();

                if (player1.equals("")) {
                    binding.edtLayPlayer1.setError("Please Enter Your Name");
                } else if (player2.equals("")) {
                    binding.edtLayPlayer2.setError("Please Enter Your Name");
                } else if (player1.equals(player2)) {
                    binding.edtLayPlayer1.setError("Do Not Use Same name");
                    binding.edtLayPlayer2.setError("Do Not Use Same name");
                } else {
                    sharedPre.saveUserInfo(player1, player2);
                    binding.edtLayPlayer1.setErrorEnabled(false);
                    binding.edtLayPlayer2.setErrorEnabled(false);

                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.start_effect);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });

                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("player1", player1);
                    intent.putExtra("player2", player2);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, new Pair<>(findViewById(R.id.edtLayPlayer1), "tr_player1"), new Pair<>(findViewById(R.id.edtLayPlayer2), "tr_player2"));
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}