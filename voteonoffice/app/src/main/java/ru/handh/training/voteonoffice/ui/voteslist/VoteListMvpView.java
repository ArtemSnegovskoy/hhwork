package ru.handh.training.voteonoffice.ui.voteslist;


import java.util.List;

import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface VoteListMvpView extends MvpView {

    void showProgressbar();

    void hideProgressbar();

    void showErrorGetData (String errorMessage);

    void setAdapterData(List<Vote> votesList);
}
