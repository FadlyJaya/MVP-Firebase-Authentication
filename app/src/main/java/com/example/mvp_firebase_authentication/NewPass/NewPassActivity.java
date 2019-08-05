package com.example.mvp_firebase_authentication.NewPass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_firebase_authentication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class NewPassActivity extends AppCompatActivity implements NewPassView{

    FirebaseAuth firebaseAuth;
    private NewPassPresenter newPassPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);

        firebaseAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void showValidationError(String message) {

    }

    @Override
    public void resetSuccess(String success) {

    }

    @Override
    public void resetError(String error) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
