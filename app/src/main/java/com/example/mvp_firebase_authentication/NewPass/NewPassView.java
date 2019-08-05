package com.example.mvp_firebase_authentication.NewPass;

import com.example.mvp_firebase_authentication.Base.BaseView;

public interface NewPassView extends BaseView {

    void showValidationError(String message);
    void resetSuccess(String success);
    void resetError(String error);

}
