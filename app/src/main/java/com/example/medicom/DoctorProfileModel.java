package com.example.medicom;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class DoctorProfileModel implements Serializable {

    private String name;
    private String id;
    private String degrees;
    private String specialization;

    DoctorProfileModel(DocumentSnapshot documentSnapshot) {
        name = documentSnapshot.getString("name");
        id = documentSnapshot.getString("userId");
        degrees = documentSnapshot.getString("degrees");
        specialization = documentSnapshot.getString("specialization");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDegrees() {
        return degrees;
    }

    public String getSpecialization() {
        return specialization;
    }
}
