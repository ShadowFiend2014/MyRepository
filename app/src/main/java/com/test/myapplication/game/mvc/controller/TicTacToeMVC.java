package com.test.myapplication.game.mvc.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.test.myapplication.R;
import com.test.myapplication.game.mvc.model.Board;
import com.test.myapplication.game.mvc.model.Cell;
import com.test.myapplication.game.mvc.model.GameState;
import com.test.myapplication.game.mvc.model.Player;

public class TicTacToeMVC extends AppCompatActivity {

    private static final String TAG = TicTacToeMVC.class.getSimpleName();

    private Board mBoard;

    private ViewGroup mButtonGrid;
    private View mWinnerPlayerViewGroup;
    private TextView mWinnerPlayerLabel;

    private Cell[][] mCells = new Cell[3][3];

    private Player mWinner;
    private Player mCurrentTurn;
    private GameState mGameState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe);
        mWinnerPlayerLabel = findViewById(R.id.winner_player_label);
        mWinnerPlayerViewGroup = findViewById(R.id.winner_payer_view_group);
        mButtonGrid = findViewById(R.id.button_grid);
        mBoard = new Board();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tic_tac_toe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tic_tac:
                mBoard.restart();
                resetView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetView() {
        mWinnerPlayerViewGroup.setVisibility(View.INVISIBLE);
        mWinnerPlayerLabel.setText("");

        for (int i = 0; i < mButtonGrid.getChildCount(); i++) {
            ((Button) mButtonGrid.getChildAt(i)).setText("");
        }
    }

    public void onCellClicked(View view) {
        Button button = (Button) view;

        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        Log.i(TAG, "Click Cell : [" + row + ", " + col + "]");

        Player playerThatMoved = mBoard.mark(row, col);

        if (playerThatMoved != null) {
            button.setText(playerThatMoved.toString());
            if (mBoard.getWinner() != null) {
                mWinnerPlayerLabel.setText(playerThatMoved.toString());
                mWinnerPlayerViewGroup.setVisibility(View.VISIBLE);
            }
        }
    }
}
