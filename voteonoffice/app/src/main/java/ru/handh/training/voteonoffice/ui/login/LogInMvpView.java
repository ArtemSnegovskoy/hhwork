package ru.handh.training.voteonoffice.ui.login;

import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface LogInMvpView extends MvpView {


    void showEmailEmptyError();

    void showEmailIncorrectError();

    void showPasswordEmptyError();

    void showPasswordLenghtError();

    void showProgressbar();

    void hideProgressbar();

    void showLoginError(String errorMessage);

    //временный метод - заглушка до введения голосовалок
    //голосовалки ввели а имя метода менть лень - переписал метод
    void showLoginSuccess();

}
