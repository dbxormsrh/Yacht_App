package com.example.yacht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity {
    int count;                                      // 주사위 굴린 횟수
    int[] dice = new int[5];                        //주사위 객체
    TextView[] t = new TextView[5];                 //택스트 뷰 객체
    ImageView[] unchecked_dice = new ImageView[5];  //이미지뷰 객체
    ImageView[] checked_dice = new ImageView[5];    //굴릴 주사위 정할 객체
    int checked_index = 0;                          //굴릴 주사위 index
    boolean[] checked = new boolean[5];             //checked
    TextView info;                                  //info textView
    TextView count_result;                          //주사위 굴린 횟수 보여주는 textView

    int[] dice_img = new int[6];                    //주사위 이미지를 보여주기 위한 배열

    String name1;                                   //Player1 이름
    String name2;                                   //player2 이름

    int player1_turn = 1;                           //player1 turn
    int player2_turn = 0;                           //player2 turn
    int turn_count = 1;                             //turn 횟수
    TextView turn;                                  //turn 보여줄 textView

    int subtotal_P1 = 0;                            //P1 subtotal
    int subtotal_P2 = 0;                            //P2 subtotal

    int total_P1=0;                                 //P1 total
    int total_P2=0;                                 //P2 total

    int[] P1_score = new int[14];                   //P1 score값들
    int[] P2_score = new int[14];                   //P2 score값들


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //player NAME
        TextView t1 = (TextView) findViewById(R.id.player1_Name2);
        TextView t2 = (TextView) findViewById(R.id.player2_Name2 );

        //info (몇번 째 ~~~~)
        info = (TextView)findViewById(R.id.info);

        //주사위 굴리는 count
        count = 0;
        count_result = (TextView) findViewById(R.id.count);

        //선택되지 않은 주사위들
        unchecked_dice[0] = (ImageView)findViewById(R.id.dice1);
        unchecked_dice[1] = (ImageView)findViewById(R.id.dice2);
        unchecked_dice[2] = (ImageView)findViewById(R.id.dice3);
        unchecked_dice[3] = (ImageView)findViewById(R.id.dice4);
        unchecked_dice[4] = (ImageView)findViewById(R.id.dice5);

        //처음에는는 모든 주사위 check X
        checked[0] = false;
        checked[1] = false;
        checked[2] = false;
        checked[3] = false;
        checked[4] = false;

        //선택한 주사위들
        checked_dice[0] = (ImageView)findViewById(R.id.checked_dice1);
        checked_dice[1] = (ImageView)findViewById(R.id.checked_dice2);
        checked_dice[2] = (ImageView)findViewById(R.id.checked_dice3);
        checked_dice[3] = (ImageView)findViewById(R.id.checked_dice4);
        checked_dice[4] = (ImageView)findViewById(R.id.checked_dice5);

        //플레이어 이름 가져오기
        Intent i1 = getIntent();

        name1 = i1.getStringExtra("name1");
        t1.setText(name1);
        name2 = i1.getStringExtra("name2");
        t2.setText(name2);

        player1_turn = i1.getIntExtra("player1_turn_count", player1_turn);
        player2_turn = i1.getIntExtra("player2_turn_count", player2_turn);
        if(player1_turn == 1) {
            Toast.makeText(getApplicationContext(), name1 + "님의 턴입니다.", Toast.LENGTH_SHORT).show();
        }
        else if(player2_turn ==1) {
            Toast.makeText(getApplicationContext(), name2 + "님의 턴입니다.", Toast.LENGTH_SHORT).show();
        }
        turn_count = i1.getIntExtra("turn_count", turn_count);
        turn = (TextView)findViewById(R.id.turn1);
        turn.setText((int)Math.ceil((double)turn_count/ 2) + "/12");

        subtotal_P1 = i1.getIntExtra("Player1_subtotal", subtotal_P1);
        subtotal_P2 = i1.getIntExtra("Player2_subtotal", subtotal_P2);

        total_P1 = i1.getIntExtra("Player1_total", total_P1);
        total_P2 = i1.getIntExtra("Player2_total", total_P2);

        P1_score = i1.getIntArrayExtra("Player1_score");
        P2_score = i1.getIntArrayExtra("Player2_score");



        //초기화면
        info.setText("게임을 시작합니다.");
        count_result.setText("버튼을 눌러 주사위를 굴리세요!");
    }
    
    //check된 주사위 값 설정
    public void set_dice(){
        for(int i=0;i<5;i++){
            if(!checked[i]) {
                dice[i] = (int) (Math.random() * 6) + 1;
                System.out.println(dice[i]);
                show_dice(dice[i], unchecked_dice[i]);
            }
        }
    }
    
    //주사위 이미지 불러오기
    public void show_dice(int dice, ImageView v){
        switch(dice){
            case 1:v.setImageResource(R.drawable.dice1);break;
            case 2:v.setImageResource(R.drawable.dice2);break;
            case 3:v.setImageResource(R.drawable.dice3);break;
            case 4:v.setImageResource(R.drawable.dice4);break;
            case 5:v.setImageResource(R.drawable.dice5);break;
            case 6:v.setImageResource(R.drawable.dice6);break;
        }
    }
    
    public void fix_dice(int i){
            checked_dice[i].setVisibility(checked_dice[i].VISIBLE);
            show_dice(dice[i], checked_dice[i]);
            checked[i] = true;
    }
    
    public void checked(View v){
        if(v.getVisibility() == v.VISIBLE) {
            v.setVisibility(v.INVISIBLE);
            switch(v.getId()){
                case R.id.dice1:fix_dice(0);break;
                case R.id.dice2:fix_dice(1);break;
                case R.id.dice3:fix_dice(2);break;
                case R.id.dice4:fix_dice(3);break;
                case R.id.dice5:fix_dice(4);break;
          }
        }
    }
    public void unfixed_dice(int i){
        unchecked_dice[i].setVisibility(unchecked_dice[i].VISIBLE);
        show_dice(dice[i],unchecked_dice[i]);
        checked[i]=false;
    }

    public void unchecked(View v){
        if(v.getVisibility() == v.VISIBLE) {
            v.setVisibility(v.INVISIBLE);
            switch (v.getId()){
                case R.id.checked_dice1: unfixed_dice(0);break;
                case R.id.checked_dice2: unfixed_dice(1);break;
                case R.id.checked_dice3: unfixed_dice(2);break;
                case R.id.checked_dice4: unfixed_dice(3);break;
                case R.id.checked_dice5: unfixed_dice(4);break;
            }
        }
    }
    
    //주사위 굴리기
    public void onClick(View v) {
        count++;
        // roll 버튼
        Button b1 = (Button) findViewById(R.id.roll_Btn);
        if (count == 1) {
            set_dice();
            info.setText("몇번째 주사위를 바꿀지 정해주세요");
            count_result.setText("2번 남았습니다.");
        }

        else if (count == 2) {
            set_dice();
            count_result.setText("1번 남았습니다.");
        }

        else if (count == 3) {
            set_dice();
            info.setText("주사위를 다 굴렸습니다.");
            count_result.setText("끝!");
            b1.setText("점수판 가기");
            for(int i=0;i<5;i++)
                fix_dice(i);
                dont_move_dices();
        }
        else{
            show_scoreboard(v);
        }
    }

    //점수판으로 넘어가기
    public void show_scoreboard(View v){
        Intent i1 = new Intent(this, MainActivity3.class);

        i1.putExtra("dice_result", dice);
        i1.putExtra("name1", name1);
        i1.putExtra("name2", name2);
        i1.putExtra("player1_turn_count", player1_turn);
        i1.putExtra("player2_turn_count", player2_turn);
        i1.putExtra("turn_count", turn_count);
        i1.putExtra("Player1_score", P1_score);
        i1.putExtra("Player2_score", P2_score);
        i1.putExtra("Player1_subtotal", subtotal_P1);
        i1.putExtra("Player2_subtotal", subtotal_P2);
        i1.putExtra("Player1_total", total_P1);
        i1.putExtra("Player2_total", total_P2);
        startActivity(i1);
    }

    public void show_scoreboard_btn(View v){
        switch (v.getId()) {
            case R.id.show_score:
                Intent i2 = new Intent(this, MainActivity3.class);
                i2.putExtra("dice_result", dice);
                i2.putExtra("name1", name1);
                i2.putExtra("name2", name2);
                i2.putExtra("player1_turn_count", player1_turn);
                i2.putExtra("player2_turn_count", player2_turn);
                i2.putExtra("turn_count", turn_count);
                i2.putExtra("Player1_score", P1_score);
                i2.putExtra("Player2_score", P2_score);
                i2.putExtra("Player1_subtotal", subtotal_P1);
                i2.putExtra("Player2_subtotal", subtotal_P2);
                i2.putExtra("Player1_total", total_P1);
                i2.putExtra("Player2_total", total_P2);
                startActivity(i2);
        }
    }


    //마지막 roll때 주사위 못 움직이게 그리고 unchecked dice 안보이게
    public void dont_move_dices() {
        for (int i = 0; i<5; i++) {
            checked_dice[i].setEnabled(false);
            unchecked_dice[i].setEnabled(false);
            unchecked_dice[i].setVisibility(View.INVISIBLE);
        }
    }
}