package ru.handh.training.voteonoffice.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.base.BasePresenter;
import ru.handh.training.voteonoffice.ui.voteslist.VoteListActivity;

public class LogInPresenter extends BasePresenter<LogInMvpView> {



    @Inject
    public LogInPresenter() {
    }



    public void userLogin(String email, String password) {


        if (email.isEmpty()) {
            getMvpView().showEmailEmptyError();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getMvpView().showEmailIncorrectError();
            return;
        }

        if (password.isEmpty()) {
            getMvpView().showPasswordEmptyError();
            return;
        }

        if (password.length() < 6) {
            getMvpView().showPasswordLenghtError();
            return;
        }

        getMvpView().showProgressbar();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                getMvpView().hideProgressbar();
                if (task.isSuccessful()) {

                    getMvpView().showLoginSuccess();

                } else {
                    String errorMessage = task.getException().getMessage();
                    getMvpView().showLoginError(errorMessage);
                }
            }
        });
    }


}
