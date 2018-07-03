package ru.handh.training.voteonoffice.ui.voteactivity;

import android.content.Intent;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.BasePresenter;

public class VotePresenter extends BasePresenter<VoteMvpView> {

    @Inject public VotePresenter() {}

    public void initItemVote(Vote vote) {

        String voteTitle;
        if (vote.getVoteTitle() == null){
            voteTitle = "";
        } else {
            voteTitle = vote.getVoteTitle();
        }



        String voteDescription;

        if (vote.getVoteDescription() == null){
            voteDescription = "";
        } else {
            voteDescription = vote.getVoteDescription();
        }

        List<VoteVariant> voteVariants = vote.getVoteVariants();

        getMvpView().setData(voteTitle, voteDescription, voteVariants);

    }

}
