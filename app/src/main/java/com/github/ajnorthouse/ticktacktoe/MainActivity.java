package com.github.ajnorthouse.ticktacktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] button = new Button[3][3];
    private TextView textViewPlayer, textViewComp;
    private boolean isPlayerTurn = true;
    private int playerScore, compScore, numOfTurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is the default code to make this view work
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this will give you control of the score displays
        textViewPlayer = findViewById(R.id.text_view_player);
        textViewComp = findViewById(R.id.text_view_comp);

        //Nest loop (eww) for getting all the buttons and assigning their logic
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String buttonId = "button_" + row + col;
                // resId = "resource Id".
                // It's an int value because the ids we use in findViewById are numeric.
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                button[row][col] = findViewById(resId);
                button[row][col].setOnClickListener(this);
            }
        }

        //Reset button logic
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (isPlayerTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        numOfTurns++;

        if (checkForWin()) {
            if (isPlayerTurn) {
                playerWins();
            } else {
                comWins();
            }
        } else if (numOfTurns == 9) {
            draw();
        } else {
            isPlayerTurn = !isPlayerTurn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                field[row][col] = button[row][col].getText().toString();
            }
        }

        //checks for horizontal matches
        for (int row = 0; row < 3; row++) {
            //first checks for an empty field, then checks for horizontal equality
            if (!field[row][0].equals("")) {
                //checks if 1st position equals the next one, and then the one after that
                if (field[row][0].equals(field[row][1]) && field[row][0].equals(field[row][2])) {
                    return true;
                }
            }
        }

        //checks for vertical matches
        for (int col = 0; col < 3; col++) {
            //first checks for an empty field, then checks for vertical equality
            if (!field[0][col].equals("")) {
                //checks if 1st position equals the one below it, and then the one below that
                if (field[0][col].equals(field[1][col]) && field[0][col].equals(field[2][col])) {
                    return true;
                }
            }
        }

        //checks for left to right diagonal matches
        //first checks for empty spaces tho
        if (!field[0][0].equals("")) {
            //checks top left equals middle, then top left equals bottom right.
            if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])) {
                return true;
            }
        }

        //checks for right to left diagonal matches
        //first checks for empty spaces tho
        if (!field[0][2].equals("")) {
            //checks top right equals middle, then top right equals bottom left.
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])) {
                return true;
            }
        }

        return false;
    }

    private void playerWins() {
        playerScore++;
        Toast.makeText(this, "You Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void comWins() {
        compScore++;
        Toast.makeText(this, "Computer Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer.setText("Player Wins: " + playerScore);
        textViewComp.setText("Comp Wins: " + compScore);
    }

    private void resetBoard() {
        isPlayerTurn = true;
        numOfTurns = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                button[row][col].setText("");
            }
        }
    }
}