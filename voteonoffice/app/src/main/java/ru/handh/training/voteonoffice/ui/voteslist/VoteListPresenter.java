package ru.handh.training.voteonoffice.ui.voteslist;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.ui.base.BasePresenter;

public class VoteListPresenter extends BasePresenter<VoteListMvpView> {

    @Inject
    public VoteListPresenter() {

    }

    public void getVoteData(){

        List<Vote> votesList = new ArrayList<>();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        // отсортировал голосования, самые новые - наверху
        firebaseFirestore.collection("Votes").orderBy("voteDate").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        getMvpView().hideProgressbar();

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Vote vote = d.toObject(Vote.class);
                                votesList.add(vote);

                            }
                            Collections.reverse(votesList);

                            getMvpView().setAdapterData(votesList);

                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


            }
        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getMvpView().showErrorGetData(e.getMessage());
                        getMvpView().hideProgressbar();
                    }
                });


    }
}

