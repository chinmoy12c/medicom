package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreenUser extends AppCompatActivity {

    private EditText userName, userId, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_user);

        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        userPass = findViewById(R.id.userPass);
    }

    private boolean validateInputs() {
        if (userName.getText().toString().trim().equals("")) {
            makeToast("Please enter your name");
            return false;
        }
        if (userPass.getText().toString().trim().equals("")) {
            makeToast("Please enter your password");
            return false;
        }

        if (!userId.getText().toString().trim().contains("@")) {
            makeToast("Please enter a valid email address");
        }

        if (userId.getText().toString().trim().equals("")) {
            makeToast("Please enter your email");
            return false;
        }
        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void initiateUserLogin(View view) {
        if(validateInputs()) {

        }
    }

    public void initiateUserSignup(View view) {

    }
}