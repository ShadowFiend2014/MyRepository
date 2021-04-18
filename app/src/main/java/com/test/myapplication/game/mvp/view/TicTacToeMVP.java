package com.test.myapplication.game.mvp.view;

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
import com.test.myapplication.game.mvp.presenter.TicTacToePresenter;

public class TicTacToeMVP extends AppCompatActivity implements ITicTacToeView {

    private static final String TAG = TicTacToeMVP.class.getSimpleName();

    private ViewGroup mButtonGrid;
    private View mWinnerPlayerViewGroup;
    private TextView mWinnerPlayerLabel;

    private TicTacToePresenter mPresenter = new TicTacToePresenter(this);;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe);
        mWinnerPlayerLabel = findViewById(R.id.winner_player_label);
        mWinnerPlayerViewGroup = findViewById(R.id.winner_payer_view_group);
        mButtonGrid = findViewById(R.id.button_grid);
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
                mPresenter.onResetSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCellClicked(View view) {
        Button button = (Button) view;
        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        Log.i(TAG, "Click Cell : [" + row + ", " + col + "]");

        mPresenter.onButtonSelected(row, col);
    }

    @Override
    public void showWinner(String winnerDisplayLabel) {
        mWinnerPlayerLabel.setText(winnerDisplayLabel);
        mWinnerPlayerViewGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearWinnerDisplay() {
        mWinnerPlayerViewGroup.setVisibility(View.GONE);
        mWinnerPlayerLabel.setText("");
    }

    @Override
    public void clearButtons() {
        for (int i = 0; i < mButtonGrid.getChildCount(); i++) {
            ((Button) mButtonGrid.getChildAt(i)).setText("");
        }
    }

    @Override
    public void setButtonText(int row, int col, String text) {
        Button button = mButtonGrid.findViewWithTag("" + row + col);
        if (button != null) {
            button.setText(text);
        }
    }
}
