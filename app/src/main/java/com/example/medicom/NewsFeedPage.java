package com.example.medicom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class NewsFeedPage extends Fragment {

    private RecyclerView newsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed_page, container, false);
        newsList = rootView.findViewById(R.id.newsFeedRecycler);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        new FirestoreHandler(getContext()).fetchNewsFeed(newsList);
        return rootView;

    }
}