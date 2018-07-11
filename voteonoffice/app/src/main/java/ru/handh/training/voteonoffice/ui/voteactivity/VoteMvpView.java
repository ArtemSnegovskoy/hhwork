package ru.handh.training.voteonoffice.ui.voteactivity;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface VoteMvpView extends MvpView {


    void setData(String voteTitle, String voteDescription, List<VoteVariant> voteVariants);

    void showErrorVote();
    void showTransactionError(String errorTransaction);

    void showProgressbar();
    void hideProgressbar();

    void showChart(ArrayList<PieEntry> entries);
}
