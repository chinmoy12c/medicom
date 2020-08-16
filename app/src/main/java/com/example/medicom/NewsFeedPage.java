package com.example.medicom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewsFeedPage extends Fragment {

    private RecyclerView newsList;
    private RecyclerView needHelpList;
    private FirestoreHandler firestoreHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed_page, container, false);
        newsList = rootView.findViewById(R.id.newsFeedRecycler);
        needHelpList = rootView.findViewById(R.id.needHelpContainer);

        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        needHelpList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        firestoreHandler = new FirestoreHandler(getContext());
        firestoreHandler.fetchNewsFeed(newsList);
        firestoreHandler.fetchNeedHelp(needHelpList);
        return rootView;

    }
}