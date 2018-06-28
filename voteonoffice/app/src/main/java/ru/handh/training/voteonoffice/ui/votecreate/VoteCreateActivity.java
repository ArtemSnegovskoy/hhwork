package ru.handh.training.voteonoffice.ui.votecreate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.Calendar;

import ru.handh.training.voteonoffice.R;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.voteslist.VoteListActivity;

public class VoteCreateActivity extends BaseActivity implements View.OnClickListener, VoteCreateMvpView {

    @Inject
    VoteCreatePresenter voteCreatePresenter;
    @Inject
    AddVariantAdapter addVariantAdapter;

    TextView textViewAnnotation;
    EditText editTitle, editDescription;
    Button buttonAddVariant, buttonCreateVote;
    RecyclerView recyclerViewVariants;

    MaterialDialog dialogProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        voteCreatePresenter.attachView(this);
        setContentView(R.layout.activity_vote_create);

        textViewAnnotation = findViewById(R.id.textViewAnnotation);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        buttonAddVariant = findViewById(R.id.buttonAddVariant);
        buttonCreateVote = findViewById(R.id.buttonCreateVote);

        buttonAddVariant.setOnClickListener(this);
        buttonCreateVote.setOnClickListener(this);

        recyclerViewVariants = findViewById(R.id.recyclerViewVariants);


        addVariantAdapter = new AddVariantAdapter();
        recyclerViewVariants.setAdapter(addVariantAdapter);
        recyclerViewVariants.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddVariant:

                VoteVariant voteVariant = new VoteVariant();
                addVariantAdapter.addItemVariant(voteVariant);

                break;

            case R.id.buttonCreateVote:
                String voteDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                voteCreatePresenter.createVote(voteDate, editTitle.getText().toString().trim(), editDescription.getText().toString().trim(), addVariantAdapter.getVariants());

                // и тут должен случиться редирект на лист голосований
                finish();
                startActivity(new Intent(this, VoteListActivity.class));
                break;
        }


    }


    @Override
    protected void onDestroy() {
        voteCreatePresenter.detachView();
        super.onDestroy();
    }

    //MVP metods

    @Override
    public void showTitleError() {
        editTitle.setError(getText(R.string.error_need_title));
        editTitle.requestFocus();
    }

    @Override
    public void showVariantsIsEmptyError() {
        Toast.makeText(getApplicationContext(), getText(R.string.error_need_variant), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showAddVoteSuccess() {
        Toast.makeText(getApplicationContext(), getText(R.string.message_vote_created), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddVoteFailed(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

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
