package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final Button[][] buttons = new Button[3][3];
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
        buttons[0][0] = findViewById(R.id.button00);
        buttons[0][1] = findViewById(R.id.button01);
        buttons[0][2] = findViewById(R.id.button02);
        buttons[1][0] = findViewById(R.id.button10);
        buttons[1][1] = findViewById(R.id.button11);
        buttons[1][2] = findViewById(R.id.button12);
        buttons[2][0] = findViewById(R.id.button20);
        buttons[2][1] = findViewById(R.id.button21);
        buttons[2][2] = findViewById(R.id.button22);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }
    }


    private void onButtonClick(View view) {
        Button clickedButton = (Button) view;
        if (!clickedButton.getText().toString().isEmpty()) return;

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
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].isEmpty())
                return true;
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].isEmpty())
                return true;
        }
        // Check diagonals
        return (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].isEmpty()) ||
                (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].isEmpty());
    }

    private void updateScore() {
        scoreBoard.setText(String.format(getString(R.string.x_d_o_d), scoreX, scoreO));
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
        resetBoard();
    }
}
