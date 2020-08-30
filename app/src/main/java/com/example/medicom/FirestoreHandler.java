package com.example.medicom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class FirestoreHandler {

    private Context context;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    ArrayList<String> fakeNews=new ArrayList<>();


    private static final String ISSUE_COLLECTION = "issues";
    private static final String USERS_COLLECTION = "users";
    private static final String MESSAGES_COLLECTION = "messages";
    public static final String USER_TYPE = "*(doc)*";    //TODO:: change
    public static final String DOC_ID = "*(doc)*";
    public static final String PAT_ID = "*(pat)*";

    FirestoreHandler(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    void showError(Exception e) {
        e.printStackTrace();
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    void showMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    void setImage(ImageView imageView, String path) {
        Picasso.get().load(path).into(imageView);
    }

    String getUser() {
        //return firebaseAuth.getCurrentUser().getEmail();
        return "testdoc@gmail.com";
        //TODO:: Change this
    }

    void fetchNewsFeed(final RecyclerView newsList) {
        db.collection(ISSUE_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        newsList.setAdapter(new NewsFeedAdapter(context, queryDocumentSnapshots, newsList));
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

    public void fetchNeedHelp(RecyclerView needHelpList, BottomNavigationView bottomNavigationView) {
        needHelpList.setAdapter(new NeedHelpAdapter(context, bottomNavigationView));
    }

    public void createUser(final String userId, final String userPass) {
        db.collection(USERS_COLLECTION).whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            showMessage("User already exists with this id!");
                            return;
                        }

                        firebaseAuth.createUserWithEmailAndPassword(userId, userPass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        HashMap<String, Object> userData = new HashMap<>();
                                        userData.put("userId", userId);
                                        userData.put("userPass", userPass);
                                        userData.put("userType", "PATIENT");
                                        userData.put("consultAccess", false);
                                        db.collection(USERS_COLLECTION).add(userData);
                                        context.startActivity(new Intent(context, MainActivity.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        showError(e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e);
                    }
                });
    }

    public void loginUser(String userId, String userPass) {
        firebaseAuth.signInWithEmailAndPassword(userId, userPass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e);
                    }
                });
    }
    public void sendIssueToFb(final IssueObject issueObject, final RecyclerView newsList){
        db.collection(ISSUE_COLLECTION).add(issueObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                NewsFeedAdapter newsFeedAdapter = (NewsFeedAdapter) newsList.getAdapter();
                newsFeedAdapter.addContent(issueObject);
            }})
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Data","Not Added");
                    showError(e);
                }
            });
    }


    public void connectChat(final String doctor, final String pat, final MessagesAdapter messagesAdapter) {
        final String chatId = doctor + "_" + pat;
        db.collection(MESSAGES_COLLECTION).whereEqualTo("chatId", chatId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (!value.isEmpty()) {
                            ArrayList<String> messages = (ArrayList<String>) value.getDocuments().get(0).get("messages");
                            messagesAdapter.refreshMessages(messages);
                            messagesAdapter.setChatDoc(value.getDocuments().get(0).getId());
                        }
                        else {
                            HashMap<String, Object> chatData = new HashMap<>();
                            chatData.put("chatId", doctor + "_" + pat);
                            chatData.put("messages", new ArrayList<String>());
                            db.collection(MESSAGES_COLLECTION).add(chatData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            messagesAdapter.setChatDoc(documentReference.getId());
                                        }
                                    });
                        }
                    }
                });
    }

    public void sendMessage(String message, MessagesAdapter messagesAdapter) {
        ArrayList<String> messages = messagesAdapter.getMessages();
        messages.add(USER_TYPE + message);
        db.collection(MESSAGES_COLLECTION).document(messagesAdapter.getChatDoc())
                .update("messages", messages);
    }

    public void getChats(final RecyclerView inboxRecycler) {
        String currentUser = getUser();
        db.collection(MESSAGES_COLLECTION)
                .whereGreaterThanOrEqualTo("chatId", currentUser)
        .get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("Size", String.valueOf(queryDocumentSnapshots.size()));
                InboxAdapter inboxAdapter = new InboxAdapter(context, queryDocumentSnapshots);
                inboxRecycler.setLayoutManager(new LinearLayoutManager(context));
                inboxRecycler.setAdapter(inboxAdapter);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showError(e);
            }
        });
    }
}
