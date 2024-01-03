package com.example.yacht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    int[] dice_result = new int[5];                 //전단계에서 받아오기 위해
    int[] dices = new int[5];                       //this activity에서 쓰기 위해
    int[] dices_count = new int[6];                 //주사위 눈의 개수를 새귀 위해
    ImageView[] dices_Image = new ImageView[5];     //주사위 결과를 이미지로 보여주기 위해

    int subtotal_P1 = 0;                            //Player1의 subtotal
    int subtotal_P2 = 0;                            //Player2의 subtotal

    int total_P1 = 0;                               //Player1의 total
    int total_P2 = 0;                               //Player2의 total

    String name1;                                   //Player1의 이름
    String name2;                                   //Player2의 이름

    int player1_turn;                           //player1 turn
    int player2_turn;                           //player2 turn

    int turn_count = 1;                             //턴 횟수 확인용
    TextView turn;                                  //턴 보여 줄 textView

    int input_count = 0;                            //점수 넣을 수 있는 횟수

    TextView[] P1_score_tV = new TextView[14];      //P1 scoreboard textview Aces ~ Total
    TextView[] P2_score_tV = new TextView[14];      //P2 scoreboard textview Aces ~ Total

    int[] P1_score = new int[14];                   //P1 score값들
    int[] P2_score = new int[14];                   //P2 scre값들

    int for_scoreboard = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        //dice값 가져오기
        Intent get_dice_result = getIntent();
        dice_result = get_dice_result.getIntArrayExtra("dice_result");

        //score_board_btn으로 눌리면 그냥 값만 확인할 수 있게 그리고 go back button으로 변경

        //이름 넣기
        name1 = get_dice_result.getStringExtra("name1");
        name2 = get_dice_result.getStringExtra("name2");

        //턴값 가져오기
        player1_turn = get_dice_result.getIntExtra("player1_turn_count", player1_turn);
        player2_turn = get_dice_result.getIntExtra("player2_turn_count", player2_turn);
        turn_count = get_dice_result.getIntExtra("turn_count", 1);
        turn = (TextView) findViewById(R.id.turn2);
        turn.setText((int) Math.ceil((double) turn_count / 2) + "/12");

        //subtotal 가져와서 출력
        subtotal_P1 = get_dice_result.getIntExtra("Player1_subtotal", 0);
        subtotal_P2 = get_dice_result.getIntExtra("Player2_subtotal", 0);
        TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
        TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);

        Button btn = (Button) findViewById(R.id.back_to_rolling_btn);
        if(player1_turn == 0 && turn_count == 2) {
            btn.setText("게임 끝!");
        }

        //turn 알려주기
        if (player1_turn == 1) {
            Toast.makeText(getApplicationContext(), name1 + "님 점수를 넣어주세요", Toast.LENGTH_SHORT).show();
        } else if (player2_turn == 1) {
            Toast.makeText(getApplicationContext(), name2 + "님 점수를 넣어주세요", Toast.LENGTH_SHORT).show();
        }

        P1_subtotal.setText(subtotal_P1 + "/63");
        P2_subtotal.setText(subtotal_P2 + "/63");

        //total 가져와서 출력
        total_P1 = get_dice_result.getIntExtra("Player1_total", 0);
        total_P2 = get_dice_result.getIntExtra("Player2_total", 0);
        TextView P1_total = (TextView) findViewById(R.id.player1_Total);
        TextView P2_total = (TextView) findViewById(R.id.player2_Total);
        P1_total.setText(total_P1 + "");
        P2_total.setText(total_P2 + "");

        int[] get_P1_score = get_dice_result.getIntArrayExtra("Player1_score");
        int[] get_P2_score = get_dice_result.getIntArrayExtra("Player2_score");

        set_P1_scores(get_P1_score);
        set_P2_scores(get_P2_score);


        //scoreboard 값 넣기
        //P1 scoreboard
        P1_score_tV[0] = (TextView) findViewById(R.id.player1_Aces);
        P1_score_tV[1] = (TextView) findViewById(R.id.player1_Deuces);
        P1_score_tV[2] = (TextView) findViewById(R.id.player1_Threes);
        P1_score_tV[3] = (TextView) findViewById(R.id.player1_Fours);
        P1_score_tV[4] = (TextView) findViewById(R.id.player1_Fives);
        P1_score_tV[5] = (TextView) findViewById(R.id.player1_Sixes);
        P1_score_tV[6] = (TextView) findViewById(R.id.player1_Plus35);
        P1_score_tV[7] = (TextView) findViewById(R.id.player1_Choices);
        P1_score_tV[8] = (TextView) findViewById(R.id.player1_4ofakind);
        P1_score_tV[9] = (TextView) findViewById(R.id.player1_Fullhouse);
        P1_score_tV[10] = (TextView) findViewById(R.id.player1_SStraight);
        P1_score_tV[11] = (TextView) findViewById(R.id.player1_LStraight);
        P1_score_tV[12] = (TextView) findViewById(R.id.player1_Yacht);
        P1_score_tV[13] = (TextView) findViewById(R.id.player1_Total);

        //P2 scoreboard
        P2_score_tV[0] = (TextView) findViewById(R.id.player2_Aces);
        P2_score_tV[1] = (TextView) findViewById(R.id.player2_Deuces);
        P2_score_tV[2] = (TextView) findViewById(R.id.player2_Threes);
        P2_score_tV[3] = (TextView) findViewById(R.id.player2_Fours);
        P2_score_tV[4] = (TextView) findViewById(R.id.player2_Fives);
        P2_score_tV[5] = (TextView) findViewById(R.id.player2_Sixes);
        P2_score_tV[6] = (TextView) findViewById(R.id.player2_Plus35);
        P2_score_tV[7] = (TextView) findViewById(R.id.player2_Choices);
        P2_score_tV[8] = (TextView) findViewById(R.id.player2_4ofakind);
        P2_score_tV[9] = (TextView) findViewById(R.id.player2_Fullhouse);
        P2_score_tV[10] = (TextView) findViewById(R.id.player2_SStraight);
        P2_score_tV[11] = (TextView) findViewById(R.id.player2_LStraight);
        P2_score_tV[12] = (TextView) findViewById(R.id.player2_Yacht);
        P2_score_tV[13] = (TextView) findViewById(R.id.player2_Total);

        //scoreboard에 값 넣기
        set_scoreboard(P1_score_tV, P1_score);
        set_scoreboard(P2_score_tV, P2_score);

        //주사위 출력하기
        dices_Image[0] = (ImageView) findViewById(R.id.dice1_result);
        dices_Image[1] = (ImageView) findViewById(R.id.dice2_result);
        dices_Image[2] = (ImageView) findViewById(R.id.dice3_result);
        dices_Image[3] = (ImageView) findViewById(R.id.dice4_result);
        dices_Image[4] = (ImageView) findViewById(R.id.dice5_result);

        set_dices(dice_result, dices_Image);


    }


    //값 받아서 P1_score에 넣기
    public void set_P1_scores(int[] scores) {
        for (int i = 0; i < scores.length; i++) {
            P1_score[i] = scores[i];
        }
    }

    //값 받아서 P2_score에 넣기
    public void set_P2_scores(int[] scores) {
        for (int i = 0; i < scores.length; i++) {
            P2_score[i] = scores[i];
        }
    }

    //받아온 값을 scoreboard에 넣기
    public void set_scoreboard(TextView[] tv, int[] scores) {
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] == 0) {
                continue;
            } else {
                tv[i].setText(scores[i] + "");
            }
        }
    }


    //주사위 값 넣기
    public void set_dices(int[] dices_result, ImageView[] dices_Image) {
        for (int i = 0; i < 5; i++) {
            dices[i] = dices_result[i];
            show_dice(dices[i], dices_Image[i]);
        }

    }

    //주사위 값에 맞춰 출력하기기
    public void show_dice(int dice, ImageView v) {
        switch (dice) {
            case 1:
                v.setImageResource(R.drawable.dice1);
                break;
            case 2:
                v.setImageResource(R.drawable.dice2);
                break;
            case 3:
                v.setImageResource(R.drawable.dice3);
                break;
            case 4:
                v.setImageResource(R.drawable.dice4);
                break;
            case 5:
                v.setImageResource(R.drawable.dice5);
                break;
            case 6:
                v.setImageResource(R.drawable.dice6);
                break;
        }
    }



    public void player1_Aces_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[0] = (TextView) findViewById(R.id.player1_Aces);
                TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[0] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {


                    P1_score[0] = 0;
                    int Aces_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 1) {
                            Aces_count++;
                            P1_score[0] = P1_score[0] + 1;
                        }
                    }


                    if (Aces_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P1 = subtotal_P1 + P1_score[0];
                        total_P1 = total_P1 + P1_score[0];
                        P1_score_tV[0].setText(P1_score[0] + "");
                        P1_subtotal.setText(subtotal_P1 + "/63");
                        P1_total.setText(total_P1 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }
    public void player2_Aces_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[0] = (TextView) findViewById(R.id.player2_Aces);
                TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[0] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[0] = 0;
                    int Aces_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 1) {
                            Aces_count++;
                            P2_score[0] = P2_score[0] + 1;
                        }
                    }

                    if (Aces_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P2 = subtotal_P2 + P2_score[0];
                        total_P2 = total_P2 + P2_score[0];
                        P2_score_tV[0].setText(P2_score[0] + "");
                        P2_subtotal.setText(subtotal_P2 + "/63");
                        P2_total.setText(total_P2 + "");
                        give_Bonus();
                        input_count++;
                    }
                }
            }
        }
    }

    public void player1_Deuces_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[1] = (TextView) findViewById(R.id.player1_Deuces);
                TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[1] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {


                    P1_score[1] = 0;
                    int Deuces_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 2) {
                            Deuces_count++;
                            P1_score[1] = P1_score[1] + 2;
                        }
                    }

                    if (Deuces_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P1 = subtotal_P1 + P1_score[1];
                        total_P1 = total_P1 + P1_score[1];
                        P1_score_tV[1].setText(P1_score[1] + "");
                        P1_subtotal.setText(subtotal_P1 + "/63");
                        P1_total.setText(total_P1 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }
    public void player2_Deuces_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[1] = (TextView) findViewById(R.id.player2_Deuces);
                TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[1] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[1] = 0;
                    int Deuces_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 2) {
                            Deuces_count++;
                            P2_score[1] = P2_score[1] + 2;
                        }
                    }

                    if (Deuces_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P2 = subtotal_P2 + P2_score[1];
                        total_P2 = total_P2 + P2_score[1];
                        P2_score_tV[1].setText(P2_score[1] + "");
                        P2_subtotal.setText(subtotal_P2 + "/63");
                        P2_total.setText(total_P2 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }
    public void player1_Threes_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[2] = (TextView) findViewById(R.id.player1_Threes);
                TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[2] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[2] = 0;
                    int Threes_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 3) {
                            Threes_count++;
                            P1_score[2] = P1_score[2] + 3;
                        }
                    }

                    if (Threes_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P1 = subtotal_P1 + P1_score[2];
                        total_P1 = total_P1 + P1_score[2];
                        P1_score_tV[2].setText(P1_score[2] + "");
                        P1_subtotal.setText(subtotal_P1 + "/63");
                        P1_total.setText(total_P1 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }

    public void player2_Threes_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[2] = (TextView) findViewById(R.id.player2_Threes);
                TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);
                if (P2_score[2] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[2] = 0;
                    int Threes_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 3) {
                            Threes_count++;
                            P2_score[2] = P2_score[2] + 3;
                        }
                    }

                    if (Threes_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P2 = subtotal_P2 + P2_score[2];
                        total_P2 = total_P2 + P2_score[2];
                        P2_score_tV[2].setText(P2_score[2] + "");
                        P2_subtotal.setText(subtotal_P2 + "/63");
                        P2_total.setText(total_P2 + "");
                        give_Bonus();
                        input_count++;
                    }
                }
            }
        }
    }
    public void player1_Fours_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[3] = (TextView) findViewById(R.id.player1_Fours);
                TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[3] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[3] = 0;
                    int Fours_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 4) {
                            Fours_count++;
                            P1_score[3] = P1_score[3] + 4;
                        }
                    }

                    if (Fours_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P1 = subtotal_P1 + P1_score[3];
                        total_P1 = total_P1 + P1_score[3];
                        P1_score_tV[3].setText(P1_score[3] + "");
                        P1_subtotal.setText(subtotal_P1 + "/63");
                        P1_total.setText(total_P1 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }

    public void player2_Fours_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[3] = (TextView) findViewById(R.id.player2_Fours);
                TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[3] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[3] = 0;
                    int Fours_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 4) {
                            Fours_count++;
                            P2_score[3] = P2_score[3] + 4;
                        }
                    }

                    if (Fours_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P2 = subtotal_P2 + P2_score[3];
                        total_P2 = total_P2 + P2_score[3];
                        P2_score_tV[3].setText(P2_score[3] + "");
                        P2_subtotal.setText(subtotal_P2 + "/63");
                        P2_total.setText(total_P2 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }

    public void player1_Fives_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[4] = (TextView) findViewById(R.id.player1_Fives);
                TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[4] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[4] = 0;
                    int Fives_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 5) {
                            Fives_count++;
                            P1_score[4] = P1_score[4] + 5;
                        }
                    }

                    if (Fives_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P1 = subtotal_P1 + P1_score[4];
                        total_P1 = total_P1 + P1_score[4];
                        P1_score_tV[4].setText(P1_score[4] + "");
                        P1_subtotal.setText(subtotal_P1 + "/63");
                        P1_total.setText(total_P1 + "");
                        give_Bonus();
                        input_count++;
                    }
                }
            }
        }
    }

    public void player2_Fives_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[4] = (TextView) findViewById(R.id.player2_Fives);
                TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[4] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[4] = 0;
                    int Fives_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 5) {
                            Fives_count++;
                            P2_score[4] = P2_score[4] + 5;
                        }
                    }

                    if (Fives_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P2 = subtotal_P2 + P2_score[4];
                        total_P2 = total_P2 + P2_score[4];
                        P2_score_tV[4].setText(P2_score[4] + "");
                        P2_subtotal.setText(subtotal_P2 + "/63");
                        P2_total.setText(total_P2 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }

    public void player1_Sixes_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[5] = (TextView) findViewById(R.id.player1_Sixes);
                TextView P1_subtotal = (TextView) findViewById(R.id.player1_Subtotal);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[5] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {

                    P1_score[5] = 0;
                    int Sixes_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 6) {
                            Sixes_count++;
                            P1_score[5] = P1_score[5] + 6;
                        }
                    }

                    if (Sixes_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P1 = subtotal_P1 + P1_score[5];
                        total_P1 = total_P1 + P1_score[5];
                        P1_score_tV[5].setText(P1_score[5] + "");
                        P1_subtotal.setText(subtotal_P1 + "/63");
                        P1_total.setText(total_P1 + "");
                        give_Bonus();
                        input_count++;
                    }
                }
            }
        }
    }

    public void player2_Sixes_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[5] = (TextView) findViewById(R.id.player2_Sixes);
                TextView P2_subtotal = (TextView) findViewById(R.id.player2_Subtotal);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[5] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[5] = 0;
                    int Sixes_count = 0;

                    for (int i = 0; i < 5; i++) {
                        if (dices[i] == 6) {
                            Sixes_count++;
                            P2_score[5] = P2_score[5] + 6;
                        }
                    }

                    if (Sixes_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        subtotal_P2 = subtotal_P2 + P2_score[5];
                        total_P2 = total_P2 + P2_score[5];
                        P2_score_tV[5].setText(P2_score[5] + "");
                        P2_subtotal.setText(subtotal_P2 + "/63");
                        P2_total.setText(total_P2 + "");
                        give_Bonus();
                        input_count++;
                    }
                }

            }
        }
    }

    public void give_Bonus() {

        P1_score_tV[6] = (TextView) findViewById(R.id.player1_Plus35);
        P2_score_tV[6] = (TextView) findViewById(R.id.player2_Plus35);
        TextView P1_total = (TextView) findViewById(R.id.player1_Total);
        TextView P2_total = (TextView) findViewById(R.id.player2_Total);

        if (subtotal_P1 > 63) {
            P1_score_tV[6].setText("+35");
            P1_score[6] = 35;
            total_P1 = total_P1 + 35;
            P1_total.setText(total_P1 + "");
        }

        if (subtotal_P2 > 63) {
            P2_score_tV[6].setText("+35");
            P2_score[6] = 35;
            total_P2 = total_P2 + 35;
            P2_total.setText(total_P2 + "");
        }
    }

    public void player1_Choices_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[7] = (TextView) findViewById(R.id.player1_Choices);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[7] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[7] = 0;
                    int Chocies_count = 0;

                    for (int i = 0; i < 5; i++) {
                        P1_score[7] = P1_score[7] + dices[i];
                    }
                    total_P1 = total_P1 + P1_score[7];
                    P1_score_tV[7].setText(P1_score[7] + "");
                    P1_total.setText(total_P1 + "");
                    input_count++;
                }
            }
        }
    }

    public void player2_Choices_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[7] = (TextView) findViewById(R.id.player2_Choices);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[7] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[7] = 0;
                    int Chocies_count = 0;

                    for (int i = 0; i < 5; i++) {
                        P2_score[7] = P2_score[7] + dices[i];
                    }
                    total_P2 = total_P2 + P2_score[7];
                    P2_score_tV[7].setText(P2_score[7] + "");
                    P2_total.setText(total_P2 + "");
                    input_count++;
                }
            }
        }
    }

    public void player1_4ofakind_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[8] = (TextView) findViewById(R.id.player1_4ofakind);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[8] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[8] = 0;
                    int Fourofakind_count = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 4) {
                            for (int j = 0; j < 6; j++) {
                                if (dices_count[j] == 1) {
                                    P1_score[8] = ((i + 1) * 4) + ((j + 1));
                                    Fourofakind_count++;
                                }
                            }
                        } else if (dices_count[i] == 5) {
                            P1_score[8] = ((i + 1) * 5);
                        }
                    }

                    if (Fourofakind_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P1_score_tV[8].setText(P1_score[8] + "");
                        total_P1 = total_P1 + P1_score[8];
                        P1_total.setText(total_P1 + "");
                        input_count++;
                    }
                }

            }
        }
    }

    public void player2_4ofakind_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[8] = (TextView) findViewById(R.id.player2_4ofakind);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[8] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {

                    P2_score[8] = 0;
                    int Fourofakind_count = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 4) {
                            for (int j = 0; j < 6; j++) {
                                if (dices_count[j] == 1) {
                                    P2_score[8] = ((i + 1) * 4) + ((j + 1));
                                    Fourofakind_count++;
                                }
                            }
                        } else if (dices_count[i] == 5) {
                            P2_score[8] = ((i + 1) * 5);
                            Fourofakind_count++;
                        }
                    }

                    if (Fourofakind_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P2_score_tV[8].setText(P2_score[8] + "");
                        total_P2 = total_P2 + P2_score[8];
                        P2_total.setText(total_P2 + "");
                        input_count++;
                    }
                }
            }
        }
    }

    public void player1_Fullhouse_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[9] = (TextView) findViewById(R.id.player1_Fullhouse);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[9] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[9] = 0;
                    int Fullhouse_count = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 3 || dices_count[i] == 2) {
                            for (int j = 0; j < 6; j++) {
                                if (dices_count[j] == 2 || dices_count[j] == 3) {
                                    if (dices_count[j] == 2) {
                                        if (dices_count[i] == 3) {
                                            Fullhouse_count++;
                                            P1_score[9] = ((j + 1) * 2) + ((i + 1) * 3);
                                        }
                                    }
                                    if (dices_count[j] == 3) {
                                        if (dices_count[i] == 2) {
                                            Fullhouse_count++;
                                            P1_score[9] = ((j + 1) * 3) + ((i + 1) * 2);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (Fullhouse_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P1_score_tV[9].setText(P1_score[9] + "");
                        total_P1 = total_P1 + P1_score[9];
                        P1_total.setText(total_P1 + "");
                        input_count++;
                    }
                }

            }
        }
    }
    public void player2_Fullhouse_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[9] = (TextView) findViewById(R.id.player2_Fullhouse);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[9] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[9] = 0;
                    int Fullhouse_count = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 3 || dices_count[i] == 2) {
                            for (int j = 0; j < 6; j++) {
                                if (dices_count[j] == 2 || dices_count[j] == 3) {
                                    if (dices_count[j] == 2) {
                                        if (dices_count[i] == 3) {
                                            Fullhouse_count++;
                                            P2_score[9] = ((j + 1) * 2) + ((i + 1) * 3);
                                        }
                                    }
                                    if (dices_count[j] == 3) {
                                        if (dices_count[i] == 2) {
                                            Fullhouse_count++;
                                            P2_score[9] = ((j + 1) * 3) + ((i + 1) * 2);
                                        }
                                    }
                                }

                            }
                        }
                    }

                    if (Fullhouse_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P2_score_tV[9].setText(P2_score[9] + "");
                        total_P2 = total_P2 + P2_score[9];
                        P2_total.setText(total_P2 + "");
                        input_count++;
                    }
                }

            }
        }
    }

    public void player1_SStraight_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[10] = (TextView) findViewById(R.id.player1_SStraight);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[10] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[10] = 0;
                    int SStraight_count = 0;

                    int x = 0;
                    int y = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 1) {
                            x++;
                        }
                        if (dices_count[i] == 2) {
                            y++;

                        }
                    }

                    if (x >= 3 && y == 1) {
                        P1_score[10] = 15;
                        SStraight_count++;
                    }


                    if (SStraight_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P1_score_tV[10].setText(P1_score[10] + "");
                        total_P1 = total_P1 + P1_score[10];
                        P1_total.setText(total_P1 + "");
                        input_count++;
                    }
                }
            }
        }
    }
    public void player2_SStraight_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[10] = (TextView) findViewById(R.id.player2_SStraight);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[10] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[10] = 0;
                    int SStraight_count = 0;

                    int x = 0;
                    int y = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 1) {
                            x++;
                        }
                        if (dices_count[i] == 2) {
                            y++;

                        }
                    }

                    if (x >= 3 && y == 1) {
                        P2_score[10] = 15;
                        SStraight_count++;
                    }


                    if (SStraight_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P2_score_tV[10].setText(P2_score[10] + "");
                        total_P2 = total_P2 + P2_score[10];
                        P2_total.setText(total_P2 + "");
                        input_count++;
                    }
                }

            }
        }
    }
    public void player1_LStraight_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[11] = (TextView) findViewById(R.id.player1_LStraight);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[11] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P1_score[11] = 0;
                    int LStraight_count = 0;
                    int x = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 1) {
                            x++;
                        }
                    }

                    if (x == 5) {
                        P1_score[11] = 30;
                        LStraight_count++;
                    }


                    if (LStraight_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P1_score_tV[11].setText(P1_score[11] + "");
                        total_P1 = total_P1 + 30;
                        P1_total.setText(total_P1 + "");
                        input_count++;
                    }
                }

            }
        }
    }

    public void player2_LStraight_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[11] = (TextView) findViewById(R.id.player2_LStraight);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[11] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[11] = 0;
                    int LStraight_count = 0;
                    int x = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] == 1) {
                            x++;
                        }
                    }

                    if (x == 5) {

                        P2_score[11] = 30;
                        LStraight_count++;
                    }


                    if (LStraight_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P2_score_tV[11].setText(P2_score[11] + "");
                        total_P2 = total_P2 + P2_score[11];
                        P2_total.setText(total_P2 + "");
                        input_count++;
                    }
                }

            }
        }
    }

    public void player1_Yacht_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player1_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P1_score_tV[12] = (TextView) findViewById(R.id.player1_Yacht);
                TextView P1_total = (TextView) findViewById(R.id.player1_Total);

                if (P1_score[12] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {

                    P1_score[12] = 0;
                    int yacht_count = 0;

                    int x = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] != 0) {
                            x++;
                        }
                    }

                    if (x == 1) {
                        P1_score[12] = 50;
                        yacht_count++;
                    }


                    if (yacht_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P1_score_tV[12].setText(P1_score[12] + "");
                        total_P1 = total_P1 + P1_score[12];
                        P1_total.setText(total_P1 + "");
                        input_count++;
                    }
                }
            }
        }
    }

    public void player2_Yacht_score(View v) {
        if (input_count != 0) {
            Toast.makeText(getApplicationContext(), "이미 값을 넣었습니다", Toast.LENGTH_SHORT).show();
        } else {
            if (player2_turn == 0) {
                Toast.makeText(getApplicationContext(), "아직 턴이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                P2_score_tV[12] = (TextView) findViewById(R.id.player2_Yacht);
                TextView P2_total = (TextView) findViewById(R.id.player2_Total);

                if (P2_score[12] != 0) {
                    Toast.makeText(getApplicationContext(), "이미 값이 들어가 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    P2_score[12] = 0;
                    int yacht_count = 0;

                    int x = 0;

                    for (int i = 0; i < 5; i++) {
                        switch (dices[i]) {
                            case (1):
                                dices_count[0]++;
                                break;
                            case (2):
                                dices_count[1]++;
                                break;
                            case (3):
                                dices_count[2]++;
                                break;
                            case (4):
                                dices_count[3]++;
                                break;
                            case (5):
                                dices_count[4]++;
                                break;
                            case (6):
                                dices_count[5]++;
                                break;
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (dices_count[i] != 0) {
                            x++;
                        }
                    }

                    if (x == 1) {
                        if (dices_count[0] == 0) {
                            P2_score[12] = 50;
                            yacht_count++;
                        }
                    }


                    if (yacht_count == 0) {
                        Toast.makeText(getApplicationContext(), "여기에는 점수를 넣을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        P2_score_tV[12].setText(P2_score[12] + "");
                        total_P2 = total_P2 + P2_score[12];
                        P2_total.setText(total_P2 + "");
                        input_count++;
                    }
                }
            }
        }
    }

    public void back_to_rolling1(View v) {
        finish();
    }

    public void back_to_rolling2(View v) {

        if (turn_count == 2 && player1_turn == 0) {
            end_game();
        } else {
            turn_count++;
            Intent i3 = new Intent(this, MainActivity2.class);

            i3.putExtra("name1", name1);
            i3.putExtra("name2", name2);

            if (player1_turn == 1) {
                player1_turn = 0;
                player2_turn = 1;
                i3.putExtra("player1_turn_count", player1_turn);
                i3.putExtra("player2_turn_count", +player2_turn);
            } else if (player2_turn == 1) {
                player1_turn = 1;
                player2_turn = 0;
                i3.putExtra("player1_turn_count", player1_turn);
                i3.putExtra("player2_turn_count", player2_turn);
            }
            i3.putExtra("turn_count", turn_count);

            i3.putExtra("Player1_score", P1_score);
            i3.putExtra("Player2_score", P2_score);

            i3.putExtra("Player1_subtotal", subtotal_P1);
            i3.putExtra("Player2_subtotal", subtotal_P2);

            i3.putExtra("Player1_total", total_P1);
            i3.putExtra("Player2_total", total_P2);

            startActivity(i3);
        }
    }

    //    }
    public void end_game() {

        Toast.makeText(getApplicationContext(), "게임이 끝났습니다", Toast.LENGTH_SHORT).show();
        if(total_P1 > total_P2) {
            Toast.makeText(getApplicationContext(), name1+"님이 이겼습니다!", Toast.LENGTH_SHORT).show();
        }
        else if(total_P2 == total_P1){
            Toast.makeText(getApplicationContext(), "무승부!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), name2+"님이 이겼습니다!", Toast.LENGTH_SHORT).show();
        }
        Intent go_rank = new Intent(this, MainActivity4.class);

        go_rank.putExtra("name1", name1);
        go_rank.putExtra("name2", name2);

        go_rank.putExtra("Player1_total", total_P1);
        go_rank.putExtra("Player2_total", total_P2);


        startActivity(go_rank);
    }


}