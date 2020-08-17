package com.example.medicom;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class IssueObject implements Serializable {

    private String description;
    private String userId;
    private String issueId;
    private String userDp;
    private boolean isOpen;
    private Timestamp time;
    private ArrayList<HashMap<String, Object>> responses;

    IssueObject(DocumentSnapshot issue) {
        description = (String) issue.get("description");
        userId = (String) issue.get("userId");
        issueId = (String) issue.get("issueId");
        userDp = (String) issue.get("userDp");
        isOpen = (boolean) issue.get("isOpen");
        time = (Timestamp) issue.get("time");
        responses = (ArrayList<HashMap<String, Object>>) issue.get("responses");
    }

    public ArrayList<HashMap<String, Object>> getResponses() {
        return responses;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getUserDp() {
        return userDp;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getTime() {
        return time;
    }
}
