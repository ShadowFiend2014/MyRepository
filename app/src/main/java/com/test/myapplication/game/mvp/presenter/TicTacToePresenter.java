package com.test.myapplication.game.mvp.presenter;

import com.test.myapplication.game.mvc.model.Board;
import com.test.myapplication.game.mvc.model.Player;
import com.test.myapplication.game.mvp.view.ITicTacToeView;

public class TicTacToePresenter {

    private ITicTacToeView mView;
    private Board mModel;

    public TicTacToePresenter(ITicTacToeView view) {
        mView = view;
        mModel = new Board();
    }

    public void onButtonSelected(int row, int col) {
        Player playerThatMoved = mModel.mark(row, col);
        if (playerThatMoved != null) {
            mView.setButtonText(row, col, playerThatMoved.toString());
            if (mModel.getWinner() != null) {
                mView.showWinner(playerThatMoved.toString());
            }
        }
    }

    public void onResetSelected() {
        mModel.restart();
        mView.clearButtons();
        mView.clearWinnerDisplay();
    }
}
