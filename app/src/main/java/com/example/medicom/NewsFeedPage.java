package com.example.medicom;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class NewsFeedPage extends Fragment {

    private RecyclerView newsList;
    private RecyclerView needHelpList;
    private FirestoreHandler firestoreHandler;
    private CardView popupCard;
    private View background;
    private Button postProb;
    private EditText probDesc;
    private ImageView addIssue;
    private BottomNavigationView bottomNavigationView;

    public NewsFeedPage(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed_page, container, false);
        newsList = rootView.findViewById(R.id.newsFeedRecycler);
        needHelpList = rootView.findViewById(R.id.needHelpContainer);
        popupCard = rootView.findViewById(R.id.popupCard);
        postProb=rootView.findViewById(R.id.postProblem);
        probDesc=rootView.findViewById(R.id.problemDescription);
        background = rootView.findViewById(R.id.backgroundView);
        addIssue = rootView.findViewById(R.id.addIssue);

        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        needHelpList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        firestoreHandler = new FirestoreHandler(getContext());
        firestoreHandler.fetchNewsFeed(newsList, bottomNavigationView);
        firestoreHandler.fetchNeedHelp(needHelpList);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setVisibility(View.VISIBLE);
                popupCard.setVisibility(View.VISIBLE);
            }
        });

        addIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setVisibility(View.VISIBLE);
                popupCard.setVisibility(View.VISIBLE);
            }
        });
        postProb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prob;
                prob=probDesc.getText().toString();
                IssueObject issueObject=new IssueObject();
                issueObject.setDescription(prob);
                issueObject.setIssueId(UUID.randomUUID().toString());
                issueObject.setOpen(true);
                issueObject.setTime(Timestamp.now());
                issueObject.setResponses(new ArrayList<HashMap<String, Object>>());
                issueObject.setUserDp("https://us.123rf.com/450wm/nerthuz/nerthuz1608/nerthuz160800059/62345951-caduceus-medical-symbol.jpg?verhttps://us.123rf.com/450wm/nerthuz/nerthuz1608/nerthuz160800059/62345951-caduceus-medical-symbol.jpg?ver=6=6");
                issueObject.setUserId("chinmoy@gmail.com");

                firestoreHandler.sendIssueToFb(issueObject, (NewsFeedAdapter) newsList.getAdapter());

                Toast.makeText(getContext(),"This does", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}