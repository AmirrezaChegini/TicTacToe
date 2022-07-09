package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tictactoe.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();

    String player1Name, player2Name;
    int player1Score = 0;
    int player2Score = 0;
    int playerTurn = 1;
    int winner = 0;
    boolean onBackPressed = false;

    List<ImageView> imgNumber = new ArrayList<>();
    List<Integer> imgSelect = new ArrayList<>();
    List<Integer> player1Select = new ArrayList<>();
    List<Integer> player2Select = new ArrayList<>();

    ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setAnimateToDivider();
        firstPlay();
        btnResetMatch();
        btnResetGame();

        for (int i = 1; i <= 9; i++) {
            int imgId = getResources().getIdentifier("img" + i, "id", getPackageName()); //R.id.img1, R.id.img2, ...
            imgNumber.add(findViewById(imgId));

            int finalI = i;
            imgNumber.get(i - 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSelect.add(finalI);
                    gameStyle(finalI);
                    winner = gameRule();
                    gameResult();
                }
            });
        }
    }

    private void btnResetGame() {

        binding.btnResteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1Select.clear();
                player2Select.clear();
                player1Score = 0;
                player2Score = 0;

                binding.txtPlayer1.setText(player1Name + " : " + player1Score);
                binding.txtPlayer2.setText(player2Name + " : " + player2Score);

                Animation animTapOut = AnimationUtils.loadAnimation(GameActivity.this, R.anim.anim_tap_out);

                for (int i = 0; i < 9; i++) {
                    imgNumber.get(i).setEnabled(true);
                    imgNumber.get(i).startAnimation(animTapOut);
                }
                playerTurn = 1;
                binding.txtPlayerTurn.setText(player1Name + "'s TURN");
            }
        });
    }

    private void btnResetMatch() {
        binding.btnResetMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1Select.clear();
                player2Select.clear();

                Animation animTapOut = AnimationUtils.loadAnimation(GameActivity.this, R.anim.anim_tap_out);

                for (int i = 0; i < 9; i++) {
                    imgNumber.get(i).setEnabled(true);
                    imgNumber.get(i).startAnimation(animTapOut);
                }

                if (winner == 1) {
                    playerTurn = 1;
                    binding.txtPlayerTurn.setText(player1Name + "'s Turn");
                } else if (winner == 2) {
                    playerTurn = 2;
                    binding.txtPlayerTurn.setText(player2Name + "'s Turn");
                }
            }
        });

    }

    private void gameResult() {

        if (winner == 1) {
            musicEnd();
            player1Score++;
            binding.txtPlayer1.setText(player1Name + " : " + player1Score);
            Toast.makeText(this, player1Name + " Won This Match", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < 9; i++) {
                imgNumber.get(i).setEnabled(false);
            }

        } else if (winner == 2) {
            musicEnd();
            player2Score++;
            binding.txtPlayer2.setText(player2Name + " : " + player2Score);
            Toast.makeText(this, player2Name + " Won This Match", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < 9; i++) {
                imgNumber.get(i).setEnabled(false);
            }
        }

    }

    private int gameRule() {
        int Winner = 0;

        //row 1
        if (player1Select.contains(1) && player1Select.contains(2) && player1Select.contains(3)) {
            Winner = 1;
        } else if (player2Select.contains(1) && player2Select.contains(2) && player2Select.contains(3)) {
            Winner = 2;
        }
        //row 2
        if (player1Select.contains(4) && player1Select.contains(5) && player1Select.contains(6)) {
            Winner = 1;
        } else if (player2Select.contains(4) && player2Select.contains(5) && player2Select.contains(6)) {
            Winner = 2;
        }
        //row 3
        if (player1Select.contains(7) && player1Select.contains(8) && player1Select.contains(9)) {
            Winner = 1;
        } else if (player2Select.contains(7) && player2Select.contains(8) && player2Select.contains(9)) {
            Winner = 2;
        }

        //column 1
        if (player1Select.contains(1) && player1Select.contains(4) && player1Select.contains(7)) {
            Winner = 1;
        } else if (player2Select.contains(1) && player2Select.contains(4) && player2Select.contains(7)) {
            Winner = 2;
        }
        //column 2
        if (player1Select.contains(2) && player1Select.contains(5) && player1Select.contains(8)) {
            Winner = 1;
        } else if (player2Select.contains(2) && player2Select.contains(5) && player2Select.contains(8)) {
            Winner = 2;
        }
        //column 3
        if (player1Select.contains(3) && player1Select.contains(6) && player1Select.contains(9)) {
            Winner = 1;
        } else if (player2Select.contains(3) && player2Select.contains(6) && player2Select.contains(9)) {
            Winner = 2;
        }

        //italic 1
        if (player1Select.contains(1) && player1Select.contains(5) && player1Select.contains(9)) {
            Winner = 1;
        } else if (player2Select.contains(1) && player2Select.contains(5) && player2Select.contains(9)) {
            Winner = 2;
        }
        //italic 2
        if (player1Select.contains(3) && player1Select.contains(5) && player1Select.contains(7)) {
            Winner = 1;
        } else if (player2Select.contains(3) && player2Select.contains(5) && player2Select.contains(7)) {
            Winner = 2;
        }

        return Winner;
    }

    private void gameStyle(int finalI) {

        Animation animTap = AnimationUtils.loadAnimation(this, R.anim.anim_tap);

        if (playerTurn == 1) {
            imgNumber.get(finalI - 1).setImageResource(R.drawable.x);
            imgNumber.get(finalI - 1).setEnabled(false);
            imgNumber.get(finalI - 1).startAnimation(animTap);
            player1Select.add(finalI);
            playerTurn = 2;
            binding.txtPlayerTurn.setText(player2Name + "'s TURN");
        } else if (playerTurn == 2) {
            imgNumber.get(finalI - 1).setImageResource(R.drawable.o);
            imgNumber.get(finalI - 1).setEnabled(false);
            imgNumber.get(finalI - 1).startAnimation(animTap);
            player2Select.add(finalI);
            playerTurn = 1;
            binding.txtPlayerTurn.setText(player1Name + "'s TURN");
        }

    }

    private void firstPlay() {
        player1Name = getIntent().getStringExtra("player1");
        player2Name = getIntent().getStringExtra("player2");
        binding.txtPlayer1.setText(player1Name + " : " + player1Score);
        binding.txtPlayer2.setText(player2Name + " : " + player2Score);
        binding.txtPlayerTurn.setText(player1Name + "'s TURN");
    }

    private void setAnimateToDivider() {
        Animation animDivider1 = AnimationUtils.loadAnimation(this, R.anim.anim_divider1);
        Animation animDivider2 = AnimationUtils.loadAnimation(this, R.anim.anim_divider2);
        Animation animDivider3 = AnimationUtils.loadAnimation(this, R.anim.anim_divider3);
        Animation animDivider4 = AnimationUtils.loadAnimation(this, R.anim.anim_divider4);

        binding.divider1.startAnimation(animDivider1);
        binding.divider2.startAnimation(animDivider2);
        binding.divider3.startAnimation(animDivider3);
        binding.divider4.startAnimation(animDivider4);
    }

    @Override
    public void onBackPressed() {

        if (!onBackPressed) {
            onBackPressed = true;
            Toast.makeText(this, "Press 2 Time To Quit", Toast.LENGTH_SHORT).show();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    onBackPressed = false;
                }
            }, 3000);
        } else if (onBackPressed) {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    protected void musicEnd(){
        mediaPlayer = MediaPlayer.create(this, R.raw.end_effect);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}