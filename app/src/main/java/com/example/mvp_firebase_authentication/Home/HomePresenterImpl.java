package com.example.mvp_firebase_authentication.Home;

import com.google.firebase.auth.FirebaseAuth;

public class HomePresenterImpl implements HomePresenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    HomeView homeView;

    public HomePresenterImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void onAttachView(HomeView View) {
        homeView = View;
    }

    @Override
    public void onDetachView() {
        homeView = null;
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void signOut() {

    }
}
