package com.example.mvp_firebase_authentication.Register;

import com.example.mvp_firebase_authentication.Base.BasePresenter;

public interface RegisterPresenter extends BasePresenter<RegisterView> {

  void register(String username, String email, String password);

}
