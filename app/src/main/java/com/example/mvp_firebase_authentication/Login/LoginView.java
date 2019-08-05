package com.example.mvp_firebase_authentication.Login;

import com.example.mvp_firebase_authentication.Base.BaseView;

public interface LoginView extends BaseView {

    void showVaidationError(String message);
    void loginError();
    void loginSuccess();
    void isLogin(boolean isLogin);
}
