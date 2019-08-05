package com.example.mvp_firebase_authentication.NewPass;

import com.example.mvp_firebase_authentication.Base.BasePresenter;

public interface NewPassPresenter extends BasePresenter<NewPassView>{

    void newPass(String email);
}
