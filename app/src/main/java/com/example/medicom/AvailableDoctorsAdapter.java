package com.example.medicom;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class AvailableDoctorsAdapter extends RecyclerView.Adapter<AvailableDoctorsAdapter.MyViewHolder> {

    private Context context;
    private QuerySnapshot queryDocumentSnapshots;

    AvailableDoctorsAdapter(Context context, QuerySnapshot queryDocumentSnapshots) {
        this.context = context;
        this.queryDocumentSnapshots = queryDocumentSnapshots;
    }

    @NonNull
    @Override
    public AvailableDoctorsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.available_doctor_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableDoctorsAdapter.MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return queryDocumentSnapshots.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView doctorName, degrees, specialization;
        private Button bookDoctor;
        private FirestoreHandler firestoreHandler;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctorName);
            degrees = itemView.findViewById(R.id.degrees);
            specialization = itemView.findViewById(R.id.specialization);
            bookDoctor = itemView.findViewById(R.id.bookDoctor);
            firestoreHandler = new FirestoreHandler(context);
        }

        public void bind(int position) {
            final DoctorProfileModel currentDoctor = new DoctorProfileModel(queryDocumentSnapshots.getDocuments().get(position));
            doctorName.setText(currentDoctor.getName());
            degrees.setText(currentDoctor.getDegrees());
            specialization.setText(currentDoctor.getSpecialization());
            bookDoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chatIntent = new Intent(context, ChatScreen.class);
                    chatIntent.putExtra("pat", firestoreHandler.getUser());
                    chatIntent.putExtra("doc", currentDoctor.getId());
                    chatIntent.putExtra("type", "NORM");
                    context.startActivity(chatIntent);
                }
            });
        }
    }
}
