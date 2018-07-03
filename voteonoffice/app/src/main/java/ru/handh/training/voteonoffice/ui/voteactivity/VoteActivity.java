package ru.handh.training.voteonoffice.ui.voteactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.voteslist.VoteListActivity;


public class VoteActivity extends BaseActivity implements View.OnClickListener, VoteMvpView {

    @Inject VotePresenter votePresenter;

    PieChart chart;
    RecyclerView recyclerViewVariantList;
    Button buttonVote;
    Vote vote;
    TextView textViewVoteActivityTitle, textViewVoteActivityDescription;
    List<VoteVariant> mVoteVariants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_vote);
        votePresenter.attachView(this);

        chart = findViewById(R.id.chart);

        textViewVoteActivityTitle = findViewById(R.id.textViewVoteActivityTitle);
        textViewVoteActivityDescription = findViewById(R.id.textViewVoteActivityDescription);

        recyclerViewVariantList = findViewById(R.id.recyclerViewVariantList);

        buttonVote = findViewById(R.id.buttonVote);
        buttonVote.setOnClickListener(this);

        vote = (Vote) getIntent().getParcelableExtra("vote");

        votePresenter.initItemVote(vote);

        // написать адаптер и логику голосования 

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        votePresenter.detachView();
        super.onDestroy();
    }


    @Override
    public void setData(String voteTitle, String voteDescription, List<VoteVariant> voteVariants) {

        textViewVoteActivityTitle.setText(voteTitle);
        textViewVoteActivityDescription.setText(voteDescription);

    }
}
