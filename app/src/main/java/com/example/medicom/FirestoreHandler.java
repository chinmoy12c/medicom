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

import java.util.ArrayList;

public class FirestoreHandler {

    private Context context;
    private FirebaseFirestore db;
    ArrayList<String> fakeNews=new ArrayList<>();


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
        fakeNews.add("A video claiming to show people in China tearing down a 5G mobile antenna was shared on social media and has generated thousands of views.");
        fakeNews.add(" It appears to come from a Facebook user in Durham, who wrote that he heard first hand that a doctor who had Corona virus recovered in double quick time after inhaling steam. A similar message has taken different forms - coming from someone's sister in LondonQueens NY and a sister from Pakistan There's no evidence that steam inhalation works as a treatment for coronavirus");
        fakeNews.add("Social media posts, claiming the 'Raam convertible bearer bond is the world's most expensive currency are false, as it is a bearer bond issued by United States-based non-profit Global Country Of World Peace as an instrument of global development");
        fakeNews.add("A photo of a derogatory painting from 2015 showing Hindu deity Krishna along with women in bikinis as 'Gopis', was revived online on Monday and passed off as a recent incident demanding the arrest of the artist who had been arrested by the Assam Police over five years ago.");
        fakeNews.add("Posts with images of hospital beds and patients being treated on the streets are going viral on social media with the claim that hospitals in Italy have run out of space due to the spread of coronavirus.");
        fakeNews.add("A Russian scientist has said that a man-made bacterium has somehow merged with the new coronavirus and is the reason people are dying - and recommends baking soda to treat it.");
        fakeNews.add("Some mainstream Indian news outlets aired a video, that turned out to be fake, claiming the crown prince of Abu Dhabi chanted a Hindu prayer greeting at a ceremony organised by a Hindu group. Despite that, the video went viral on social media.");
        newsList.setAdapter(new FakeNewsAdapter(context,fakeNews));
    }

    public void fetchNeedHelp(RecyclerView needHelpList) {
        needHelpList.setAdapter(new NeedHelpAdapter(context));
    }
}
