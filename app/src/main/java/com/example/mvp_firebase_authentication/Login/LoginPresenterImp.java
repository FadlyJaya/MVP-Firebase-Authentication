package com.example.mvp_firebase_authentication.Login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImp implements LoginPresenter {

    private FirebaseAuth firebaseAuth;
    private LoginView loginView;

    public LoginPresenterImp(FirebaseAuth firebaseAuth){
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void login(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            loginView.showVaidationError("Email dan Password Tidak Boleh Kosong");
        }else
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) loginView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                loginView.loginError();
                            } else {
                                loginView.loginSuccess();
                            }
                        }
                    });
    }

    @Override
    public void checkLogin() {
        if (firebaseAuth.getCurrentUser() != null){
            loginView.isLogin(true);
        }else
           loginView.isLogin(false);
    }

    @Override
    public void onAttachView(LoginView View) {
        loginView = View;
    }

    @Override
    public void onDetachView() {
        loginView = null;
    }

}
