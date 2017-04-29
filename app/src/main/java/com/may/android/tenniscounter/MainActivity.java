package com.may.android.tenniscounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.may.android.tenniscounter.R.id.Set2A;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGamePointsA = (TextView) findViewById(R.id.GamePointsA);
        tvGamePointsB = (TextView) findViewById(R.id.GamePointsB);
        tvSet1PointsA = (TextView) findViewById(R.id.Set1A);
        tvSet2PointsA = (TextView) findViewById(Set2A);
        tvSet3PointsA = (TextView) findViewById(R.id.Set3A);
        tvSet1PointsB = (TextView) findViewById(R.id.Set1B);
        tvSet2PointsB = (TextView) findViewById(R.id.Set2B);
        tvSet3PointsB = (TextView) findViewById(R.id.Set3B);
    }

    public int gamePointsA = 0;
    public int gamePointsB = 0;
    public int setPointsA = 0;
    public int setPointsB = 0;
    public int matchPointsA = 0;
    public int matchPointsB = 0;
    public int currentSet = 1;
    public boolean stopped = false;
    public boolean intiebreak = false;
    public TextView tvGamePointsA;
    public TextView tvGamePointsB;
    public TextView tvSet1PointsA;
    public TextView tvSet2PointsA;
    public TextView tvSet3PointsA;
    public TextView tvSet1PointsB;
    public TextView tvSet2PointsB;
    public TextView tvSet3PointsB;


    /**
     * Show a message to user.
     */
    public void message(String text) {
        TextView messageView = (TextView) findViewById(R.id.Message);
        messageView.setText(text);
    }

    public void updateGame() {
        message("");
        if (intiebreak)
            tiebreak();
        else {
            if (updateA() || updateB()) {
                if (gamePointsA > gamePointsB)
                    setPointsA++;
                else
                    setPointsB++;
                gamePointsA = 0;
                gamePointsB = 0;
                updateA();
                updateB();
                updateSets();
            }
        }
    }

    public void tiebreak() {
        tvGamePointsA.setText(String.valueOf(gamePointsA));
        tvGamePointsB.setText(String.valueOf(gamePointsB));
        if ((gamePointsA >= 7) || (gamePointsB >= 7)) {
            if ((gamePointsB + 1) < gamePointsA) {
                setPointsA++;
                gamePointsA = gamePointsB =0;
                message("Player A wins the tiebreak!");
                updateSets();
                endSet();
                intiebreak = false;
            }
            if ((gamePointsA + 1) < gamePointsB) {
                setPointsB++;
                gamePointsA = gamePointsB =0;
                message("Player B wins the tiebreak!");
                updateSets();
                endSet();
                intiebreak = false;
            }
        }
    }

    public void updateSets() {
        TextView scoreViewA;
        TextView scoreViewB;
        switch (currentSet) {
            default:
                scoreViewA = tvSet1PointsA;
                scoreViewB = tvSet1PointsB;
                break;
            case 2:
                scoreViewA = tvSet2PointsA;
                scoreViewB = tvSet2PointsB;
                break;
            case 3:
                scoreViewA = tvSet3PointsA;
                scoreViewB = tvSet3PointsB;
        }
        scoreViewA.setText(String.valueOf(setPointsA));
        scoreViewB.setText(String.valueOf(setPointsB));
        if ((setPointsA >= 6) || (setPointsB >= 6)) {
            if (setPointsA == setPointsB) {
                message("Tiebreak!");
                intiebreak = true;
            } else {
                if (setPointsA > (setPointsB + 1)) {
                    message("Set for Player A!");
                    matchPointsA++;
                    if (matchPointsA == 2) {
                        message("Match! Player A wins!");
                        stopped = true;
                    }
                    endSet();
                }
                if ((setPointsA + 1) < setPointsB) {
                    message("Set for Player B!");
                    matchPointsB++;
                    if (matchPointsB == 2) {
                        message("Match! Player B wins!");
                        stopped = true;
                    }
                    endSet();
                }
            }
        }
    }

    public void endSet() {
        setPointsA = 0;
        setPointsB = 0;
        currentSet++;
        if (currentSet == 4){
            message("Match!");
            stopped = true;
        }
    }

    /**
     * Updates the game points of A an returns whether A has won.
     */
    public boolean updateA() {
        switch (gamePointsA) {
            case 0:
                tvGamePointsA.setText("0");
                return false;
            case 1:
                tvGamePointsA.setText("15");
                return false;
            case 2:
                tvGamePointsA.setText("30");
                return false;
            case 3:
                tvGamePointsA.setText("40");
                return false;
            default:
                switch (gamePointsA - gamePointsB){
                    case 0:
                        tvGamePointsA.setText("DE");
                        return false;
                    case -1:
                        tvGamePointsA.setText("40");
                        return false;
                    case 1:
                        tvGamePointsA.setText("AD A");
                        return false;
                    default:
                        return true;
                }
        }
    }

    /**
     * Updates the game points of B an returns whether B has won.
     */
    public boolean updateB() {
        switch (gamePointsB) {
            case 0:
                tvGamePointsB.setText("0");
                return false;
            case 1:
                tvGamePointsB.setText("15");
                return false;
            case 2:
                tvGamePointsB.setText("30");
                return false;
            case 3:
                tvGamePointsB.setText("40");
                return false;
            default:
                switch (gamePointsA - gamePointsB) {
                    case 0:
                        tvGamePointsB.setText("DE");
                        return false;
                    case -1:
                        tvGamePointsB.setText("AD B");
                        return false;
                    case 1:
                        tvGamePointsB.setText("40");
                        return false;
                    default:
                        return true;
                }
        }
    }

    public void PointA(View view) {
        if (stopped)
            message("Match is over! Tap reset!");
        else {
            gamePointsA++;
            updateGame();
        }
    }

    public void PointB(View view) {
        if (stopped)
            message("Match is over! Tap reset!");
        else {
            gamePointsB++;
            updateGame();
        }
    }

    public void reset(View view) {
        message("");
        gamePointsA = 0;
        gamePointsB = 0;
        setPointsA = 0;
        setPointsB = 0;
        matchPointsA = 0;
        matchPointsB = 0;
        currentSet = 3;
        updateSets();
        currentSet = 2;
        updateSets();
        currentSet = 1;
        updateSets();
        stopped = false;
        updateGame();
    }
}
