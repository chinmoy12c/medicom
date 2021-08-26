package com.example.medicom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorSearchFragment extends Fragment {

    private RecyclerView availableDoctorsList;
    private FirestoreHandler firestoreHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_search_fragment_layout, container, false);
        availableDoctorsList = rootView.findViewById(R.id.availableDoctorsView);
        availableDoctorsList.setLayoutManager(new LinearLayoutManager(getContext()));

        firestoreHandler = new FirestoreHandler(getContext());
        firestoreHandler.getAvailableDoctors(availableDoctorsList);

        return  rootView;
    }
}
