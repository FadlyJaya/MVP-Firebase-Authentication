package com.example.mvp_firebase_authentication.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_firebase_authentication.Base.BaseActivity;
import com.example.mvp_firebase_authentication.Login.LoginActivity;
import com.example.mvp_firebase_authentication.R;
import com.example.mvp_firebase_authentication.Register.RegisterActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends BaseActivity implements HomeView {

    TextView tvWelcome, tvData;
    Button btnDelete, btnLogout;
    FirebaseAuth firebaseAuth;
    LoginManager loginManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.txt_view_welcome);
        tvData = findViewById(R.id.txt_view);
        btnDelete = findViewById(R.id.btn_delete);
        btnLogout = findViewById(R.id.btn_logout);

        firebaseAuth = FirebaseAuth.getInstance();
        homePresenter = new HomePresenterImpl(firebaseAuth);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user==null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        tvData.setText(" "+user.getEmail());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null);
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"User Terhapus", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                            finish();
                        }
                    }
                });

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                loginManager.getInstance().logOut();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.onDetachView();
    }

    @Override
    public Context getContext() {
        return null;
    }
}
