package ru.handh.training.voteonoffice.ui.votecreate;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.BasePresenter;

public class VoteCreatePresenter extends BasePresenter<VoteCreateMvpView> {

    @Inject
    public VoteCreatePresenter() {
    }

    public void createVote(String voteDate, String voteTitle, String voteDescription, List<VoteVariant> voteVariantList) {

        if (voteTitle.isEmpty()) {
            getMvpView().showTitleError();
            return;
        }

        if (voteVariantList.isEmpty()) {
            getMvpView().showVariantsIsEmptyError();
            return;
        }

        if (voteVariantList.size() < 2){
            getMvpView().showVariantsIsEmptyError();
            return;
        }

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        // добавить генерацию айди и прочей херни и поправить конструктор
        // тут мы добавляем недостающие данные для конструктора

        // UUID мб заменить на Long uuid = UUID.randomUUID().getMostSignificantBits()

        //String voteUUID = UUID.randomUUID().toString().replaceAll("-", "");
        String voteUUID = UUID.randomUUID().toString();



        if (voteDescription == null) {
            voteDescription = "";
        }

        boolean voteStatus = false;

        Vote vote = new Vote(voteUUID, voteDate, voteTitle, voteDescription, voteStatus, voteVariantList);

        CollectionReference dbVotes = firebaseFirestore.collection("Votes");

        getMvpView().showProgressbar();

        dbVotes.document(voteUUID).set(vote);

//        .addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                getMvpView().showAddVoteSuccess();
//                //getMvpView().hideProgressbar();
//
//            }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                getMvpView().showAddVoteFailed(e.getMessage());
//                //getMvpView().hideProgressbar();
//            }
//        });
//
//        getMvpView().hideProgressbar();


    }

}



