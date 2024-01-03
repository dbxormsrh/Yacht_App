package com.example.yacht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent next_page;   //다음 페이지에 넘어 갈 수 있게 변수 선언
    Intent rank_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view){
        switch (view.getId()){
           case R.id.play:
                next_page = new Intent(this, activity_input_name.class);
                startActivity(next_page);
                break;
            case R.id.how2play:
                next_page = new Intent(this, MainActivity_how_to_play.class);
                startActivity(next_page);
                break;
            case R.id.goRank:
                rank_page = new Intent(this, MainActivity4.class);
                startActivity(rank_page);
                break;
        }
    }
}

