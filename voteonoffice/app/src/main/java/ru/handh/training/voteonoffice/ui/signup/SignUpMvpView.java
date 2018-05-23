package ru.handh.training.voteonoffice.ui.signup;

import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface SignUpMvpView extends MvpView {

    void showEmailEmptyError();
    void showEmailIncorrectError();

    void showPasswordEmptyError();
    void showPasswordLenghtError();
    void showAlredyRegisterError();

    void showProgressbar();
    void hideProgressbar();

    void showSignUpSuccessful();
    void showSignUpFailed(String errorMessage);





}
