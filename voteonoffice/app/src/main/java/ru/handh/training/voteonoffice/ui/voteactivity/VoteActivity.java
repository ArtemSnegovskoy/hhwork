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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
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

        votePresenter.getVoteResult(vote);



    }

    @Override
    public void onClick(View v) {
        // проверка на выбранный вариант
        if (voteAdapter.checkBoxChekedStatus()){
            //проверка на то, голосовал ли пользователь за этот опрос
            votePresenter.checkVoteStatus(vote, voteAdapter.checkBoxSelected);
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

    @Override
    public void showChart(ArrayList<PieEntry> entries) {

        chart = findViewById(R.id.chart);

        chart.setRotationEnabled(true);
        chart.setUsePercentValues(true);
        chart.setDescription(null);
        chart.setCenterText(getString(R.string.vote_result));
        chart.setCenterTextSize(10);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);



        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(14);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(Color.BLACK);

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        chart.setData(pieData);
        chart.invalidate();

    }


}
