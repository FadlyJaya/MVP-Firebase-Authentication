package com.example.mvp_firebase_authentication.NewPass;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class NewPassPresenterImpl implements NewPassPresenter {

    private FirebaseAuth firebaseAuth;
    private NewPassView newPassView;

    @Override
    public void newPass(String email) {
        if (TextUtils.isEmpty(email)){
            newPassView.showValidationError("Masukkan Email");
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           newPassView.resetSuccess("Password Berhasil di Reset");
                       }else {
                            newPassView.resetError("Kesalahan Pengiriman");
                       }
                    }
                });
    }

    @Override
    public void onAttachView(NewPassView View) {
        newPassView = View;
    }

    @Override
    public void onDetachView() {
        newPassView = null;
    }
}
