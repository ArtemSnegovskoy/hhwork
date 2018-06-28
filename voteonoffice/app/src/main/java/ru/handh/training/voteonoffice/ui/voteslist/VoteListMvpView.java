package ru.handh.training.voteonoffice.ui.voteslist;


import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface VoteListMvpView extends MvpView {

    void showProgressbar();
    void hideProgressbar();

}
