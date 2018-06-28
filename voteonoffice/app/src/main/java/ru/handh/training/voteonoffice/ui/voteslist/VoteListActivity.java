package ru.handh.training.voteonoffice.ui.voteslist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import ru.handh.training.voteonoffice.R;

import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.votecreate.VoteCreateActivity;

public class VoteListActivity extends BaseActivity implements View.OnClickListener, VoteListMvpView {

    @Inject VoteListPresenter voteListPresenter;
    VoteListAdapter voteListAdapter;

    RecyclerView recyclerViewVotes;
    Button buttonAddVote;
    List<Vote> votesList;
    MaterialDialog dialogProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        voteListPresenter.attachView(this);
        setContentView(R.layout.activity_vote_list);

        buttonAddVote = findViewById(R.id.buttonAddVote);
        buttonAddVote.setOnClickListener(this);

        recyclerViewVotes = findViewById(R.id.recyclerViewVoteList);
        recyclerViewVotes.setLayoutManager(new LinearLayoutManager(this));

        votesList = new ArrayList<>();

        voteListAdapter = new VoteListAdapter(this, votesList);
        recyclerViewVotes.setAdapter(voteListAdapter);

        showProgressbar();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Votes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        hideProgressbar();

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Vote vote = d.toObject(Vote.class);
                                votesList.add(vote);

                            }

                            voteListAdapter.notifyDataSetChanged();

                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });


    }


    @Override
    public void onClick(View v) {
                finish();
                startActivity(new Intent(this, VoteCreateActivity.class));

    }

    @Override
    protected void onDestroy() {
        voteListPresenter.detachView();
        super.onDestroy();
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
