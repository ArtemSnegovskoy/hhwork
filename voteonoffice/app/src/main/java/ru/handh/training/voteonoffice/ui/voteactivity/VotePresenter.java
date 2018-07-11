package ru.handh.training.voteonoffice.ui.voteactivity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.data.usermodel.User;
import ru.handh.training.voteonoffice.data.usermodel.UserVotes;
import ru.handh.training.voteonoffice.data.votesmodel.Vote;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;
import ru.handh.training.voteonoffice.ui.base.BasePresenter;

public class VotePresenter extends BasePresenter<VoteMvpView> {


    @Inject
    public VotePresenter() {
    }

    public void initItemVote(Vote vote) {

        String voteTitle;
        if (vote.getVoteTitle() == null) {
            voteTitle = "";
        } else {
            voteTitle = vote.getVoteTitle();
        }

        String voteDescription;

        if (vote.getVoteDescription() == null) {
            voteDescription = "";
        } else {
            voteDescription = vote.getVoteDescription();
        }

        List<VoteVariant> voteVariants = vote.getVoteVariants();

        getMvpView().setData(voteTitle, voteDescription, voteVariants);

    }

    public void voteForVariant(final Vote vote, final int votedVariantID) {

        getMvpView().showProgressbar();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.runTransaction(new Transaction.Function<Void>() {

            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentReference currentVoteDocRef = db
                        .collection("Votes").document(vote.getVoteUUID());
                // пишем данные в  Votes
                Vote currentVote = transaction.get(currentVoteDocRef).toObject(Vote.class);
                List<VoteVariant> currentVoteVAriants = currentVote.getVoteVariants();
                VoteVariant currentVotedVariant = currentVoteVAriants.get(votedVariantID);
                currentVotedVariant.setVariantVoteStatus(currentVotedVariant.getVariantVoteStatus() + 1);

                //теперь пишем в юзерс
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference currentUserRef = db.collection("Users")
                        .document(currentFirebaseUser.getEmail());

                User currentUser = transaction.get(currentUserRef).toObject(User.class);
                //получаем текущий список голосований
                List<UserVotes> currentUserVotes = currentUser.getUserVotesList();
                // создаем новый объект
                UserVotes currentUserVote = new UserVotes(vote.getVoteUUID(), votedVariantID);
                // пишем новую строку юзеру в список его голосований
                currentUserVotes.add(currentUserVote);

                transaction.set(currentUserRef, currentUser);
                transaction.set(currentVoteDocRef, currentVote);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                getVoteResult(vote);

                getMvpView().hideProgressbar();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getMvpView().hideProgressbar();
                getMvpView().showTransactionError(e.getMessage());
            }
        });

    }

    // в этом методе вычитаем единицу из старого варианта голосования, и доавляем единицу к новому
    public void updateUserVotes(Vote vote, int votedVariantID) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        getMvpView().showProgressbar();

        db.runTransaction(new Transaction.Function<Void>() {

            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                //int oldVariantIndex = 0;
                int oldVariantId = 0;

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String voteUUID = vote.getVoteUUID();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                DocumentReference currentUserRef = db.collection("Users")
                        .document(currentFirebaseUser.getEmail());
                User currentUser = transaction.get(currentUserRef).toObject(User.class);
                //получаем текущий список голосований
                List<UserVotes> currentUserVotes = currentUser.getUserVotesList();
                // надо найти старое голосование через UUID и получить оттуда номер варианта

                DocumentReference currentVoteDocRef = db
                        .collection("Votes").document(voteUUID);
                Vote currentVote = transaction.get(currentVoteDocRef).toObject(Vote.class);

                List<VoteVariant> currentVotedVariants = currentVote.getVoteVariants();


                boolean hasUUID = false;

                for (UserVotes userVotes : currentUserVotes) {
                    if (userVotes.getVoteUUID().equals(voteUUID)) {
                        hasUUID = true;
                        //oldVariantIndex = currentUserVotes.indexOf(userVotes);
                        oldVariantId = userVotes.getVoteVariantID();
                        break;
                    }
                }

                if (hasUUID) {
                    VoteVariant oldVariant = currentVotedVariants.get(oldVariantId);
                    oldVariant.setVariantVoteStatus(oldVariant.getVariantVoteStatus() - 1);
                }

                UserVotes currentUserVote = new UserVotes(vote.getVoteUUID(), votedVariantID);
                // добавляем новый объект в список и ищем совпадение UUID, его надо заменить


                for (UserVotes userVotes : currentUserVotes) {
                    if (userVotes.getVoteUUID().equals(currentUserVote.getVoteUUID())) {
                        currentUserVotes.set(currentUserVotes.indexOf(userVotes), currentUserVote);
                        break;
                    }

                }


                transaction.set(currentUserRef, currentUser);
                transaction.set(currentVoteDocRef, currentVote);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                db.runTransaction(new Transaction.Function<Void>() {
                    // тут нас надо только обновить Votes добавив новое значение
                    @Nullable
                    @Override
                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String voteUUID = vote.getVoteUUID();

                        DocumentReference currentVoteDocRef = db
                                .collection("Votes").document(vote.getVoteUUID());
                        Vote currentVote = transaction.get(currentVoteDocRef).toObject(Vote.class);
                        List<VoteVariant> currentVoteVAriants = currentVote.getVoteVariants();

                        VoteVariant currentVotedVariant = currentVoteVAriants.get(votedVariantID);
                        currentVotedVariant.setVariantVoteStatus(currentVotedVariant.getVariantVoteStatus() + 1);


                        transaction.set(currentVoteDocRef, currentVote);

                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getVoteResult(vote);

                        getMvpView().hideProgressbar();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getMvpView().hideProgressbar();
                        getMvpView().showTransactionError(e.getMessage());
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getMvpView().hideProgressbar();
                getMvpView().showTransactionError(e.getMessage());
            }
        });

    }

    // метод проверяет голосовал ли пользователь за этот опрос, получая список голосований пользователя и проверяя вхождение
    // строки voteUUID текущего голосования, и в зависимости от этого обновляет или создает голосование
    public void checkVoteStatus(final Vote vote, final int votedVariantID) {

        String voteUUID = vote.getVoteUUID();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference userDocRef = db.collection("Users")
                .document(currentFirebaseUser.getEmail());

        userDocRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isComplete()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                User user = document.toObject(User.class);
                                List<UserVotes> userVotesList = user.getUserVotesList();

                                if (userVotesList.isEmpty()) {
                                    voteForVariant(vote, votedVariantID);
                                } else {
                                    boolean hasVotes = false;
                                    for (UserVotes userVotes : userVotesList) {

                                        if (userVotes.getVoteUUID().equals(voteUUID)) {
                                            hasVotes = true;
                                        }
//
                                    }

                                    if (hasVotes) {
                                        updateUserVotes(vote, votedVariantID);
                                    } else {
                                        voteForVariant(vote, votedVariantID);
                                    }
                                }

                            }
                        }
                    }
                });

    }

    public void getVoteResult(Vote vote) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference currentVoteDocRef = db
                .collection("Votes").document(vote.getVoteUUID());

        currentVoteDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Vote vote = documentSnapshot.toObject(Vote.class);

                List<VoteVariant> voteVariantList = vote.getVoteVariants();

                ArrayList<PieEntry> entries = new ArrayList<>();

                int votesCount = 0;

                for (VoteVariant voteVariant : voteVariantList) {
                    int a = voteVariant.getVariantVoteStatus();
                    votesCount = votesCount + a;
                    float b = a;
                    String c = String.valueOf(voteVariant.getVariantId() + " (" + voteVariant.getVariantVoteStatus() +")");

                    entries.add(new PieEntry(b, c));

                }

                if (votesCount == 0) {
                    entries = null;
                }

                getMvpView().showChart(entries);

            }
        });


    }


}
