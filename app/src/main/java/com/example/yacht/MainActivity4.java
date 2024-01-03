package com.example.yacht;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    int[] dice_result = new int[5];
    String name1;
    String name2;
    int total_P1 = 0;                               //Player1의 total
    int total_P2 = 0;                               //Player2의 total
    SQLiteDatabase db = null;
    String db_name = "rank";
    String table_name = "rank";
    TextView result;
    Intent home_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        result = (TextView) findViewById(R.id.result);

        //db 생성

        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);
        if (db != null) {
            Toast.makeText(getApplicationContext(), db_name + "db 연결 성공", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), db_name + "db 연결 오류", Toast.LENGTH_LONG).show();

        }

        //이름 불러오기

        Intent i1 = getIntent();

        name1 = i1.getStringExtra("name1");
        name2 = i1.getStringExtra("name2");


        //점수 불러오기
        total_P1 = i1.getIntExtra("Player1_total", 0);
        total_P2 = i1.getIntExtra("Player2_total", 0);


    }


    public void makeTABLE(String name) {
        // 테이블 생성
        try {
            String query = "create table if not exists " + table_name + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " name text, "
                    + " total integer) ";

            db.execSQL(query);
            Toast.makeText(getApplicationContext(), db_name + "테이블 생성 성공", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), db_name + "테이블 생성 오류", Toast.LENGTH_LONG).show();
        }
    }


    public void showRank(View v) {


        if(total_P1 != 0 && total_P2 !=0) {

            makeTABLE(table_name);
            try {                                                               //테이블에 값입력
                db.execSQL("insert into " + table_name + " values (null, '" +   // 사용자 이름 , 점수 가져와야함
                        name1 + "'," + total_P1 + ");");
                db.execSQL("insert into " + table_name + " values (null, '" +
                        name2 + "'," + total_P2 + ");");
                Toast.makeText(getApplicationContext(), db_name + "레코드 삽입 성공", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), db_name + "레코드 삽입 오류", Toast.LENGTH_LONG).show();
            }

        }
        int i = 1;
        try {
            String query1 = " select * from " + table_name
                    + " order by total desc ";  //  점수를 기준으로 내림차순 정렬


            StringBuffer sb = new StringBuffer(100);

            Cursor cs = db.rawQuery(query1, null);
            if (cs.moveToFirst()) {
                do {
                    sb.append(i);
                    sb.append("등");
                    sb.append(cs.getString(cs.getColumnIndexOrThrow("name")));
                    sb.append(',');
                    sb.append(cs.getString(cs.getColumnIndexOrThrow("total")));
                    sb.append('\n');
                    i++;

                } while (cs.moveToNext());
            }
            result.setText(sb);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


        public void dropTable(View v) {
        //테이블 삭제
        String query2 = " drop table " + table_name;
        db.execSQL(query2);
        result.setText(" ");
    }

    public void back4Home(View view) {
        switch (view.getId()) {

            case R.id.goHome:
                home_page = new Intent(this, MainActivity.class);
                startActivity(home_page);
                break;
        }
    }
}