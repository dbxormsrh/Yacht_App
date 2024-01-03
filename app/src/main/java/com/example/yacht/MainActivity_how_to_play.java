package com.example.yacht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity_how_to_play extends AppCompatActivity {
    Intent home_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_how_to_play);
    }

    public void back4Home(View v) {
        switch (v.getId()) {

            case R.id.goHome:
                home_page = new Intent(this, MainActivity.class);
                startActivity(home_page);
                break;
        }
    }
}