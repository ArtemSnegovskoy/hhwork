package ru.handh.training.voteonoffice.ui.votecreate;

import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface VoteCreateMvpView extends MvpView {

    void showTitleError();
    void showVariantsIsEmptyError();

    void showAddVoteSuccess();
    void showAddVoteFailed(String errorMessage);

    void showProgressbar();
    void hideProgressbar();

}
