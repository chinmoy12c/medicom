package com.example.medicom;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public class FirestoreHandler {

    private Context context;

    FirestoreHandler(Context context) {
        this.context = context;
    }

    void fetchNewsFeed(RecyclerView newsList) {
        newsList.setAdapter(new NewsFeedAdapter(context));
    }
    void fetchFakeNews(RecyclerView newsList){
        newsList.setAdapter(new FakeNewsAdapter(context));
    }
}
