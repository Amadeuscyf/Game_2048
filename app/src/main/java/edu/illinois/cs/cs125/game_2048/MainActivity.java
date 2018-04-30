package edu.illinois.cs.cs125.game_2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int score = 0;
    private boolean musicStart = false;
    private TextView tvScore;
    private static MainActivity mainActivity = null;
    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);

    }

        public void clearScore () {
            score = 0;
            showScore();
        }
        public void showScore () {
            tvScore.setText(score + " ");
        }

        public void addScore (int s) {
            score += s;
            showScore();
        }

        public void getHighestScore(int gameScore) {
            TextView HighestScore = (TextView) findViewById(R.id.HighestScore);
            SharedPreferences settings = getSharedPreferences("HighestScore", Context.MODE_PRIVATE);
            int highestScore = settings.getInt("Highest Score", 0);

            if (highestScore > gameScore) {
                HighestScore.setText("Highest Score" + highestScore);
            } else {
                HighestScore.setText("Highest Score" + gameScore);
                //save
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Highest Score", gameScore);
                editor.commit();
            }
        }
        public static MainActivity getMainActivity () {
            return mainActivity;
        }
}
