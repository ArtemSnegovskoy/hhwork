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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.login.LogInActivity;
import ru.handh.training.voteonoffice.ui.voteactivity.VoteActivity;
import ru.handh.training.voteonoffice.ui.votecreate.VoteCreateActivity;

public class VoteListActivity extends BaseActivity implements View.OnClickListener, VoteListMvpView, VoteListAdapter.VoteClickListener {

    @Inject
    VoteListPresenter voteListPresenter;
    VoteListAdapter voteListAdapter;

    RecyclerView recyclerViewVotes;
    Button buttonAddVote, buttonEditUserRole;
    List<Vote> votesList;
    MaterialDialog dialogProgressBar;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        voteListPresenter.attachView(this);
        setContentView(R.layout.activity_vote_list);

        buttonAddVote = findViewById(R.id.buttonAddVote);
        buttonEditUserRole = findViewById(R.id.buttonEditUserRole);
        buttonAddVote.setOnClickListener(this);
        buttonEditUserRole.setOnClickListener(this);

        recyclerViewVotes = findViewById(R.id.recyclerViewVoteList);
        recyclerViewVotes.setLayoutManager(new LinearLayoutManager(this));

        votesList = new ArrayList<>();

        voteListPresenter.getVoteData();
//        voteListAdapter = new VoteListAdapter(this, votesList);
//        voteListAdapter.setClickListener(this);
//        recyclerViewVotes.setAdapter(voteListAdapter);

        showProgressbar();

        //TODO вынести метод в презентер, подсветить проголосованное в списке



//        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//        // отсортировал голосования, самые новые - наверху
//        firebaseFirestore.collection("Votes").orderBy("voteDate").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        hideProgressbar();
//
//                        if (!queryDocumentSnapshots.isEmpty()) {
//
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                            for (DocumentSnapshot d : list) {
//
//                                Vote vote = d.toObject(Vote.class);
//                                votesList.add(vote);
//
//                            }
//                            Collections.reverse(votesList);
//                            voteListAdapter.notifyDataSetChanged();
//
//                        }
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), R.string.error_get_data, Toast.LENGTH_LONG).show();
//                        hideProgressbar();
//                    }
//                });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddVote:

                startActivity(new Intent(this, VoteCreateActivity.class));
                break;
            case R.id.buttonEditUserRole:


                FirebaseAuth.getInstance().signOut();

                finish();
                startActivity(new Intent(this, LogInActivity.class));
                break;
        }


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

    @Override
    public void showErrorGetData(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapterData(List<Vote> votesList) {


        voteListAdapter = new VoteListAdapter(this, votesList);
        voteListAdapter.setClickListener(this);
        recyclerViewVotes.setAdapter(voteListAdapter);
        voteListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(Vote vote) {

        Intent intent = new Intent(VoteListActivity.this, VoteActivity.class);

        intent.putExtra("vote", vote);

        startActivity(intent);
    }
}
