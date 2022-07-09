package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPre {

    SharedPreferences sharedPreferences;

    public SharedPre(Context context) {
        this.sharedPreferences = context.getSharedPreferences("user_information", Context.MODE_PRIVATE);
    }

    public void saveUserInfo(String player1, String player2){
        sharedPreferences.edit().putString("player1", player1).putString("player2", player2).apply();
    }

    public String getPlayer1(){
        return sharedPreferences.getString("player1", "");
    }

    public String getPlayer2(){
        return sharedPreferences.getString("player2", "");
    }

}
