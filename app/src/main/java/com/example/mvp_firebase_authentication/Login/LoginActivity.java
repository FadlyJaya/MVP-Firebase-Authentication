package com.example.mvp_firebase_authentication.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_firebase_authentication.Base.BaseActivity;
import com.example.mvp_firebase_authentication.Home.HomeActivity;
import com.example.mvp_firebase_authentication.Home.HomePresenter;
import com.example.mvp_firebase_authentication.NewPass.NewPassActivity;
import com.example.mvp_firebase_authentication.R;
import com.example.mvp_firebase_authentication.Register.RegisterActivity;
import com.example.mvp_firebase_authentication.Utils.Utils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

public class LoginActivity extends BaseActivity implements LoginView, GoogleApiClient.OnConnectionFailedListener {

    EditText etEmail, etPassword;
    Button btnLogin;
    SignInButton signInGoogle;
    LoginButton signInFacebook;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManager;
    private LoginPresenter loginPresenter;
    private static final String TAG = "FACELOG";

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null){
            updateUI();
        }
//        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        signInGoogle = findViewById(R.id.sign_in_google);
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth != null){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        };

        loginPresenter = new LoginPresenterImp(firebaseAuth);

        loginPresenter.onAttachView(this);
        loginPresenter.checkLogin();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClick();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("450546150007-n9n2anm8v2h0avk1rrqm6vh7onh8k5jd.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        signInFacebook = findViewById(R.id.sign_in_facebook);

        signInFacebook.setReadPermissions("email");
        signInFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        toForgotPassword();
        toRegister();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                authWithGoogle(account);
            }
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void authWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Autentikasi Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onLoginButtonClick(){
        loginPresenter.login(etEmail.getText().toString(), etPassword.getText().toString());
    }

    public void toForgotPassword(){
        TextView tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewPassActivity.class));
            }
        });
    }

    public void toRegister(){
        TextView tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void googleSignIn(){
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG,"handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "SignInWithCredential:onSuccess");
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    Log.w(TAG, "SignInWithCredential:onFailure", task.getException());
                    Toast.makeText(LoginActivity.this, "Autentikasi Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI() {
        Toast.makeText(getApplicationContext(),"Kamu berhasil masuk", Toast.LENGTH_SHORT).show();
        Intent account = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(account);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDetachView();
    }

    @Override
    public void showVaidationError(String message) {
        Utils.showMessage(this, message);
    }


    @Override
    public void loginError() {
        Utils.showMessage(this, "Email atau Password Anda Salah");
    }

    @Override
    public void loginSuccess() {
        Utils.showMessage(this, "Login Sukses");
        Utils.setIntent(this, HomeActivity.class);
    }

    @Override
    public void isLogin(boolean isLogin) {
        if (isLogin){
            Utils.setIntent(this, HomeActivity.class);
            finish();
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

}
