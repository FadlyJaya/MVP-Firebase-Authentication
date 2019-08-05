package com.example.mvp_firebase_authentication.Home;

import com.example.mvp_firebase_authentication.Base.BasePresenter;

public interface HomePresenter extends BasePresenter<HomeView> {

    void deleteUser();
    void signOut();
}
