package com.example.medicom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private ArrayList<String> messages;
    private Context context;

    MessagesAdapter(Context context, ArrayList<String> messages) {
        this.messages = messages;
        this.context = context;
    }

    void addMessage(String message) {
        messages.add(message);
        notifyItemInserted(messages.size()-1);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private EditText messageText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }

        public void bind(int position) {
            String userMessage = messages.get(position);
            if (userMessage.contains("user ")){
                userMessage = userMessage.substring(userMessage.indexOf("user ")+5);
                messageText.setBackground(context.getResources().getDrawable(R.drawable.message_back_white));
            }
            messageText.setText(userMessage);
        }
    }
}
