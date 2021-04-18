package com.test.myapplication.game.mvc.model;

import android.util.Log;

public class Board {
    private static final String TAG =  Board.class.getSimpleName();

    private Cell[][] mCells = new Cell[3][3];

    private Player mWinner;
    private Player mCurrentTurn;
    private GameState mGameState;
    
    public Board() {
        restart();
    }

    public void restart() {
        clearCells();

        mWinner = null;
        mCurrentTurn = Player.X;
        mGameState = GameState.IN_PROGRESS;
    }

    public void clearCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j] = new Cell();
            }
        }
    }

    public Player mark(int row, int col) {
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

    public Player getWinner() {
        return mWinner;
    }
}
