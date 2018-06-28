package ru.handh.training.voteonoffice.ui.signup;

import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.ui.base.BasePresenter;
import ru.handh.training.voteonoffice.ui.signup.SignUpMvpView;

public class SignUpPresenter extends BasePresenter<SignUpMvpView> {
    @Inject
    public SignUpPresenter() {
    }

    public void registerUser(String email, String password){

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

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                getMvpView().hideProgressbar();
                if (task.isSuccessful()) {
                    getMvpView().showSignUpSuccessful();
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        getMvpView().hideProgressbar();
                        getMvpView().showAlredyRegisterError();

                    } else {
                        String errorMessage = task.getException().getMessage();
                        getMvpView().hideProgressbar();
                        getMvpView().showSignUpFailed(errorMessage);

                    }

                }
            }
        });






    }
}
