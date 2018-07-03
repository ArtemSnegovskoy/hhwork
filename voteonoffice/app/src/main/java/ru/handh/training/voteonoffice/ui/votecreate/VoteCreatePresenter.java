package ru.handh.training.voteonoffice.ui.votecreate;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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

        // этот способ записи в базу данных генерирует айди voteUUID по которому я могу получить документ
        dbVotes.document(voteUUID).set(vote).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getMvpView().hideProgressbar();
                getMvpView().showAddVoteSuccess();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getMvpView().hideProgressbar();
                getMvpView().showAddVoteFailed(e.getMessage());

            }
        });
            // этот способ записи в базу гененрирует для докуменат свой айди, которого я не знаю
//        dbVotes.add(vote)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                getMvpView().hideProgressbar();
//                getMvpView().showAddVoteSuccess();
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        getMvpView().hideProgressbar();
//                        getMvpView().showAddVoteFailed(e.getMessage());
//                    }
//                });

    }

}



