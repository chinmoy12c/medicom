package com.example.medicom;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class FirestoreHandler {

    private Context context;
    private FirebaseFirestore db;

    private static final String ISSUE_COLLECTION = "issues";

    FirestoreHandler(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    void showError(Exception e) {
        e.printStackTrace();
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    void setImage(ImageView imageView, String path) {
        Picasso.get().load(path).into(imageView);
    }

    void fetchNewsFeed(final RecyclerView newsList) {
        db.collection(ISSUE_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        newsList.setAdapter(new NewsFeedAdapter(context, queryDocumentSnapshots));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e);
                    }
                });
    }
    void fetchFakeNews(RecyclerView newsList){
        newsList.setAdapter(new FakeNewsAdapter(context));
    }
}
