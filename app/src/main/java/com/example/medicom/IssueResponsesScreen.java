package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class IssueResponsesScreen extends AppCompatActivity {

    private RecyclerView responsesList;
    private ArrayList<HashMap<String, Object>> responses;
    private ResponseScreenAdapter responseScreenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_responses_screen);

        responsesList = findViewById(R.id.responsesList);
        responsesList.setLayoutManager(new LinearLayoutManager(this));

        Intent currentIntent = getIntent();
        responses = (ArrayList<HashMap<String, Object>>) currentIntent.getExtras().get("responses");
        responseScreenAdapter = new ResponseScreenAdapter(this, responses);
        responsesList.setAdapter(responseScreenAdapter);
    }
}