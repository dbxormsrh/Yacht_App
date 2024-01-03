package com.example.yacht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class activity_input_name extends AppCompatActivity {

    int[] P1_score = new int[14];                   //P1 score값들
    int[] P2_score = new int[14];                   //P2 score값들

    int subtotal_P1 =0;
    int subtotal_P2 = 0;

    int total_P1 = 0;
    int total_P2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);


    }

    public void playGame(View v){
        Intent i1 = new Intent(this, MainActivity2.class);

        //플레이어 이름 넣기
        EditText player1_name = (EditText)findViewById(R.id.player1_Name1);
        EditText player2_name = (EditText)findViewById(R.id.player2_Name1);

        //플레이어 이름 다음 activity에 넘기기
        i1.putExtra("name1", player1_name.getText().toString());
        i1.putExtra("name2", player2_name.getText().toString());
        i1.putExtra("Player1_score", P1_score);
        i1.putExtra("Player2_score", P2_score);

        //플레이어 턴 랜덤으로


        i1.putExtra("Player1_subtotal", subtotal_P1);
        i1.putExtra("Player2_subtotal", subtotal_P2);

        i1.putExtra("Player1_total", total_P1);
        i1.putExtra("Player2_total", total_P2);

        switch(v.getId()){
            case R.id.playgame_Btn:
                startActivity(i1);
                break;
        }
    }
}