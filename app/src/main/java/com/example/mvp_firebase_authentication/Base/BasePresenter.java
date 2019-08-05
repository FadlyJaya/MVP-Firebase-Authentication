package com.example.mvp_firebase_authentication.Base;

public interface BasePresenter<T extends BaseView> {

    void onAttachView(T View);
    void onDetachView();
}
