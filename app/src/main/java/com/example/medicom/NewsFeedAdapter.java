package com.example.medicom;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QuerySnapshot;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder>{

    private Context context;
    private QuerySnapshot issuesList;
    private BottomNavigationView bottomNavigationView;

    NewsFeedAdapter(Context context, QuerySnapshot issuesList, BottomNavigationView bottomNavigationView) {
        this.context = context;
        this.issuesList = issuesList;
        this.bottomNavigationView = bottomNavigationView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_feed_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView postImage;
        private TextView postUsername, postTime, postDescription, answersCount;
        private Button consultPrivate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.postImage);
            postUsername = itemView.findViewById(R.id.postUsername);
            postTime = itemView.findViewById(R.id.postTime);
            postDescription = itemView.findViewById(R.id.postDescription);
            answersCount = itemView.findViewById(R.id.answersCount);
            consultPrivate = itemView.findViewById(R.id.consultPrivate);
            consultPrivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomNavigationView.findViewById(R.id.messagesPage).performClick();
                }
            });
        }

        public void bind(int position) {
            IssueObject currentIssue = new IssueObject(issuesList.getDocuments().get(position));
            new FirestoreHandler(context).setImage(postImage, currentIssue.getUserDp());
            postUsername.setText(currentIssue.getUserId());
            postTime.setText(DateUtils.getRelativeTimeSpanString(currentIssue.getTime().getSeconds() * 1000));
            postDescription.setText(currentIssue.getDescription());
            answersCount.setText(String.valueOf(currentIssue.getResponses().size()));
        }
    }
}
