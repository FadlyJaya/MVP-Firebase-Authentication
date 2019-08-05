package com.example.mvp_firebase_authentication.Login;

import com.example.mvp_firebase_authentication.Base.BasePresenter;

public interface LoginPresenter extends BasePresenter<LoginView> {

    void login(String email, String password);
    void checkLogin();
}
