package com.example.mvp_firebase_authentication.Register;

import com.example.mvp_firebase_authentication.Base.BaseView;

public interface RegisterView extends BaseView {

    void notifUsername();
    void notifEmailPass();
    void notifPassword();
    void registerSuccess();
    void registerError();
}
