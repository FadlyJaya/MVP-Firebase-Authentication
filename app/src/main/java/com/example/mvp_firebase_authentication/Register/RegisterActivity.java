package com.example.mvp_firebase_authentication.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mvp_firebase_authentication.Base.BaseActivity;
import com.example.mvp_firebase_authentication.Login.LoginActivity;
import com.example.mvp_firebase_authentication.Home.HomeActivity;
import com.example.mvp_firebase_authentication.R;
import com.example.mvp_firebase_authentication.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends BaseActivity implements RegisterView{

    EditText etUsername, etEmail, etPassword;
    Button btnRegister, btnLogin;
    FirebaseAuth firebaseAuth;
    private RegisterPresenter registerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        firebaseAuth = FirebaseAuth.getInstance();
        registerPresenter = new RegisterPresenterImpl(firebaseAuth);
        registerPresenter.onAttachView(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    public void onRegister(){
       registerPresenter.register(etUsername.getText().toString().trim(),
                                    etEmail.getText().toString().trim(),
                                    etPassword.getText().toString().trim());
    }

    @Override
    public Context getContext() {
        return null;
    }


    @Override
    public void notifUsername() {
        Utils.showMessage(this, "Masukkan Username");
    }

    @Override
    public void notifEmailPass() {
        Utils.showMessage(this, "Masukkan Email atau Password");
    }


    @Override
    public void notifPassword() {
        Utils.showMessage(this, "Password Tidak Boleh Kurang dari 6 Digit");
    }

    @Override
    public void registerSuccess() {
        Utils.showMessage(this, "Register Sukses");
        Utils.setIntent(this, LoginActivity.class);
    }

    @Override
    public void registerError() {
        Utils.showMessage(this, "Register Error");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.onDetachView();
    }
}
