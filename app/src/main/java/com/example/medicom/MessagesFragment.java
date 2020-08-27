package com.example.medicom;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    private Toolbar messagesToolbar;
    private RecyclerView messagesRecycler;
    private ImageView sendMessage;
    private EditText userMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_messages, container, false);
        messagesToolbar = rootView.findViewById(R.id.messagesToolbar);
        messagesRecycler = rootView.findViewById(R.id.messagesRecycler);
        sendMessage = rootView.findViewById(R.id.sendMessage);
        userMessage = rootView.findViewById(R.id.userMessage);

        messagesToolbar.setTitle("Anonymous");
        ((AppCompatActivity)getActivity()).setSupportActionBar(messagesToolbar);

        final MessagesAdapter adapter = new MessagesAdapter(getContext(), new ArrayList<String>());
        messagesRecycler.setAdapter(adapter);
        messagesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMsg = userMessage.getText().toString();
                adapter.addMessage(userMsg);
                userMessage.setText("");
            }
        });

        return rootView;
    }
}