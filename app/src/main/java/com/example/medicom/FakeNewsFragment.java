package com.example.medicom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FakeNewsFragment extends Fragment {
    RecyclerView fakenews;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_fake_news,container,false);
        fakenews=rootview.findViewById(R.id.fake_news_recycler);
        fakenews.setLayoutManager(new LinearLayoutManager(getContext()));
        new FirestoreHandler(getContext()).fetchFakeNews(fakenews);
        return rootview;


    }
}