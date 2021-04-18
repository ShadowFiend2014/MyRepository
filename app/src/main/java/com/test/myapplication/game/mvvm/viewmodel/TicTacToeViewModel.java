package com.test.myapplication.game.mvvm.viewmodel;

import android.os.Build;
import android.util.ArrayMap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.myapplication.game.mvc.model.Board;
import com.test.myapplication.game.mvc.model.Player;

public class TicTacToeViewModel extends ViewModel {
    private Board mModel;

    public final MutableLiveData<ArrayMap<String, String>> mCells = new MutableLiveData<>();
    public final MutableLiveData<String> mWinner = new MutableLiveData<>();

    public TicTacToeViewModel() {
        mModel = new Board();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mCells.postValue(new ArrayMap<String, String>());
        }
    }

    public void onResetSelected() {
        mModel.restart();
        mWinner.postValue(null);
        mCells.getValue().clear();
        mCells.postValue(mCells.getValue());
    }

    public void onCellClickedAt(int row, int col) {
        Player playerThatMoved = mModel.mark(row, col);
        if (playerThatMoved != null) {
            mCells.getValue().put("" + row + col, playerThatMoved.toString());
            mCells.postValue(mCells.getValue());
            mWinner.postValue(mModel.getWinner() == null ? null : mModel.getWinner().toString());
        }
    }
}
