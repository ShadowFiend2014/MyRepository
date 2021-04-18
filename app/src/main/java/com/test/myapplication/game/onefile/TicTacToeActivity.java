package com.test.myapplication.game.onefile;

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

public class TicTacToeActivity extends AppCompatActivity {

    private static final String TAG = TicTacToeActivity.class.getSimpleName();

    public enum Player {
        X,
        O
    }

    private enum GameState {
        IN_PROGRESS,
        FINISHED
    }

    public class Cell {
        private Player value;
        public Player getValue() {
            return value;
        }
        public void setValue(Player value) {
            this.value = value;
        }
    }

    private Cell[][] mCells = new Cell[3][3];

    private Player mWinner;
    private Player mCurrentTurn;
    private GameState mGameState;

    private ViewGroup mButtonGrid;
    private View mWinnerPlayerViewGroup;
    private TextView mWinnerPlayerLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe);
        mWinnerPlayerLabel = findViewById(R.id.winner_player_label);
        mWinnerPlayerViewGroup = findViewById(R.id.winner_payer_view_group);
        mButtonGrid = findViewById(R.id.button_grid);
        restart();
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
                restart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void restart() {
        clearCells();

        mWinner = null;
        mCurrentTurn = Player.X;
        mGameState = GameState.IN_PROGRESS;

        mWinnerPlayerViewGroup.setVisibility(View.INVISIBLE);
        mWinnerPlayerLabel.setText("");

        for (int i = 0; i < mButtonGrid.getChildCount(); i++) {
            ((Button) mButtonGrid.getChildAt(i)).setText("");
        }
    }

    public void clearCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j] = new Cell();
            }
        }
    }

    public void onCellClicked(View view) {
        Button button = (Button) view;

        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        Log.i(TAG, "Click Cell : [" + row + ", " + col + "]");

        Player playerThatMoved = mark(row, col);

        if (playerThatMoved != null) {
            button.setText(playerThatMoved.toString());
            if (getWinner() != null) {
                mWinnerPlayerLabel.setText(playerThatMoved.toString());
                mWinnerPlayerViewGroup.setVisibility(View.VISIBLE);
            }
        }
    }

    private Player getWinner() {
        return mWinner;
    }

    private Player mark(int row, int col) {
        Player playerThatMoved = null;

        Log.i(TAG, "Cell [" + row + ", " + col + "]" + " isValid : " + isValid(row, col));
        if (isValid(row, col)) {
            mCells[row][col].setValue(mCurrentTurn);
            playerThatMoved = mCurrentTurn;

            Log.i(TAG, "Cell [" + row + ", " + col + "]" + " isWinningMovedByPlayer : "
                    + isWinningMovedByPlayer(mCurrentTurn, row, col));
            if (isWinningMovedByPlayer(mCurrentTurn, row, col)) {
                mGameState = GameState.FINISHED;
                mWinner = mCurrentTurn;
            } else {
                flipCurrentTurn();
            }

        }

        return playerThatMoved;
    }

    private void flipCurrentTurn() {
        mCurrentTurn = mCurrentTurn == Player.X ? Player.O : Player.X;
    }

    private boolean isWinningMovedByPlayer(Player player, int row, int col) {
        return ((mCells[row][0].getValue() == player
                    && mCells[row][1].getValue() == player
                    && mCells[row][2].getValue() == player)
                || (mCells[0][col].getValue() == player
                    && mCells[1][col].getValue() == player
                    && mCells[2][col].getValue() == player)
                || (row == col
                    && mCells[0][0].getValue() == player
                    && mCells[1][1].getValue() == player
                    && mCells[2][2].getValue() == player)
                || (row + col == 2
                    && mCells[0][2].getValue() == player
                    && mCells[1][1].getValue() == player
                    && mCells[2][0].getValue() == player));
    }

    private boolean isValid(int row, int col) {
        if (mGameState == GameState.FINISHED) {
            return false;
        } else if (isCellValueAlreadySet(row, col)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isCellValueAlreadySet(int row, int col) {
        return mCells[row][col].getValue() != null;
    }
}
