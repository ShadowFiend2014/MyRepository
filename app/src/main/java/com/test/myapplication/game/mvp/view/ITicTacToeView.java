package com.test.myapplication.game.mvp.view;

public interface ITicTacToeView {
    void showWinner(String winnerDisplayLabel);
    void clearWinnerDisplay();
    void clearButtons();
    void setButtonText(int row, int col, String text);
}
