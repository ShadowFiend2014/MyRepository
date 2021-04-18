package com.test.myapplication.game.mvvm.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.test.myapplication.R;
import com.test.myapplication.databinding.TicTacToeMvvmBinding;
import com.test.myapplication.game.mvvm.viewmodel.TicTacToeViewModel;

public class TicTacToeMVVM extends AppCompatActivity {

    private static final String TAG = TicTacToeMVVM.class.getSimpleName();

    private TicTacToeViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TicTacToeMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.tic_tac_toe_mvvm);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(TicTacToeViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
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
                mViewModel.onResetSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
