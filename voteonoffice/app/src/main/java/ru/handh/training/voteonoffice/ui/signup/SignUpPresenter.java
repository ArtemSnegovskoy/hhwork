package ru.handh.training.voteonoffice.ui.signup;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.data.usermodel.User;
import ru.handh.training.voteonoffice.data.usermodel.UserVotes;
import ru.handh.training.voteonoffice.ui.base.BasePresenter;

public class SignUpPresenter extends BasePresenter<SignUpMvpView> {
    @Inject
    public SignUpPresenter() {
    }

    public void registerUser(final String email, String password) {

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

        if(password.matches("[a-zA-Z]{1,}")){
            getMvpView().showPasswordNoLetterError();
            return;
        }

        if(password.matches("[0-9]{1,}")){
            getMvpView().showPasswordNoLetterError();
            return;
        }



        String passwordLowerCase = password.toLowerCase();
        String passwordUpperCase = password.toUpperCase();

        if (password.equals(passwordLowerCase)) {
            getMvpView().showPasswordOnlyLowerCaseError();
            return;
        }

        if (password.equals(passwordUpperCase)) {
            getMvpView().showPasswordOnlyUpperCaseError();
            return;
        }




        getMvpView().showProgressbar();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                getMvpView().hideProgressbar();
                if (task.isSuccessful()) {
                    getMvpView().showSignUpSuccessful();


                    addDbUser(email);


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

    public void addDbUser(String userEmail) {
        // при создании нового пользователя добавляем его в таблицу Users для раздачи ролей и запоминания голосований
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        // по умолчанию пользователь не админ
        boolean userRole = false;
        List<UserVotes> userVotesList = new ArrayList<>();

        User user = new User(userEmail, userRole, userVotesList);

        CollectionReference dbUsers = firebaseFirestore.collection("Users");

//        dbUsers.add(user);
        // добавляем юзера, вместо айди емейл
        dbUsers.document(userEmail).set(user);


    }
}
