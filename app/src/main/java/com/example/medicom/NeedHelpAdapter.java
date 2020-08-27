package com.example.medicom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NeedHelpAdapter extends RecyclerView.Adapter<NeedHelpAdapter.MyViewHolder>{

    private Context context;
    private BottomNavigationView bottomNavigationView;

    public NeedHelpAdapter(Context context, BottomNavigationView bottomNavigationView) {
        this.context = context;
        this.bottomNavigationView = bottomNavigationView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stress_signal_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.helpUser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomNavigationView.findViewById(R.id.messagesPage).performClick();
                }
            });
        }
    }
}
