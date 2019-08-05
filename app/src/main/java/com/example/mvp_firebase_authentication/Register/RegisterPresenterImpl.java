package com.example.mvp_firebase_authentication.Register;

import android.app.Activity;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenterImpl implements RegisterPresenter {

    private FirebaseAuth firebaseAuth;
    private RegisterView registerView;

    public RegisterPresenterImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void register(String username, String email, String password) {
        if (TextUtils.isEmpty(username)) {
            registerView.notifUsername();
            return;
        }
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            registerView.notifEmailPass();
            return;
        }
        if (password.length() < 6) {
            registerView.notifPassword();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener((Activity) registerView, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    registerView.registerError();
                } else {
                    registerView.registerSuccess();
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onAttachView(RegisterView View) {
        registerView = View;
    }

    @Override
    public void onDetachView() {
        registerView = null;
    }
}
