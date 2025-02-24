package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount = 0;
    private int scoreX = 0;
    private int scoreO = 0;
    private TextView scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreBoard = findViewById(R.id.scoreBoard);
        initializeGrid();

        Button resetBoardButton = findViewById(R.id.resetBoardButton);
        Button resetScoreButton = findViewById(R.id.resetScoreButton);

        resetBoardButton.setOnClickListener(v -> resetBoard());
        resetScoreButton.setOnClickListener(v -> resetScore());
    }

    private void initializeGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }
    }

    private void onButtonClick(View view) {
        Button clickedButton = (Button) view;
        if (!clickedButton.getText().toString().equals("")) return;

        clickedButton.setText(playerXTurn ? "X" : "O");
        roundCount++;

        if (checkWin()) {
            if (playerXTurn) {
                scoreX++;
                Toast.makeText(this, "Player X Wins!", Toast.LENGTH_SHORT).show();
            } else {
                scoreO++;
                Toast.makeText(this, "Player O Wins!", Toast.LENGTH_SHORT).show();
            }
            updateScore();
            resetBoard();
        } else if (roundCount == 9) {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
            resetBoard();
        } else {
            playerXTurn = !playerXTurn;
        }
    }

    private boolean checkWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows & columns
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
                return true;
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                return true;
        }

        // Check diagonals
        return (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) ||
                (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""));
    }

    private void updateScore() {
        scoreBoard.setText("X: " + scoreX + "  O: " + scoreO);
    }

    private void resetBoard() {
        roundCount = 0;
        playerXTurn = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void resetScore() {
        scoreX = 0;
        scoreO = 0;
        updateScore();
    }
}
