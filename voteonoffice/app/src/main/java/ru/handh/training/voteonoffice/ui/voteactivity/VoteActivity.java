package ru.handh.training.voteonoffice.ui.voteactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;


public class VoteActivity extends BaseActivity implements View.OnClickListener, VoteMvpView {

    @Inject VotePresenter votePresenter;
    VoteAdapter voteAdapter;

    PieChart chart;
    RecyclerView recyclerViewVariantList;
    Button buttonVote;
    Vote vote;
    TextView textViewVoteActivityTitle, textViewVoteActivityDescription;
    List<VoteVariant> voteVariantsList;
    MaterialDialog dialogProgressBar;


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

        voteVariantsList = vote.getVoteVariants();
        voteAdapter = new VoteAdapter(this, voteVariantsList);
        recyclerViewVariantList.setAdapter(voteAdapter);
        recyclerViewVariantList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {

        if (voteAdapter.checkBoxChekedStatus()){
            votePresenter.voteForVariant(vote, voteAdapter.checkBoxSelected);
        } else {
            showErrorVote();
        }


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

    @Override
    public void showErrorVote() {
        Toast.makeText(this, R.string.error_select_var, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTransactionError(String errorTransaction) {
        Toast.makeText(this, errorTransaction, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar() {

        if (dialogProgressBar == null) {


            dialogProgressBar = new MaterialDialog.Builder(this)
                    .content(R.string.progress_bar_wait)
                    .progress(true, 0)
                    .contentGravity(GravityEnum.CENTER)
                    .widgetColor(Color.RED)
                    .cancelable(false)
                    .build();
        }
        dialogProgressBar.show();
    }

    @Override
    public void hideProgressbar() {

        if (dialogProgressBar != null && dialogProgressBar.isShowing()) {
            dialogProgressBar.dismiss();
        }
    }



}
