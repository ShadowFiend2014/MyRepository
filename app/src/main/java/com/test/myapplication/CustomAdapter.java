package com.test.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private static final String TAG = "RecyclerViewTest";

    private Context mContext;
    private List<String> mList;

    public CustomAdapter(Context context, List<String> mList) {
        mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv, parent, false);
        Log.i(TAG, "onCreateViewHolder : " + getItemCount());
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder position : " + position);
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.rv_tv);
        }
    }
}
