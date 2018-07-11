package ru.handh.training.voteonoffice.ui.voteslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.data.votesmodel.Vote;

public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.VoteListViewHolder> {

    private List<Vote> votesList;
    private Context context;

    private VoteClickListener clickListener;

    public VoteListAdapter(Context context, List<Vote> votesList) {
        this.context = context;
        this.votesList = votesList;
    }

    public class VoteListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageVoteStatus;
        TextView textViewVoteDate, textViewVoteTitle;

        public VoteListViewHolder(View itemView) {
            super(itemView);

            imageVoteStatus = itemView.findViewById(R.id.imageVoteStatus);
            textViewVoteDate = itemView.findViewById(R.id.textViewVoteDate);
            textViewVoteTitle = itemView.findViewById(R.id.textViewVoteTitle);

        }

        public void bindVote(int position, final Vote vote) {


            if (vote.isVoteStatus() == true) {
                imageVoteStatus.setImageResource(R.drawable.ic_done_black_36dp);
            }

            textViewVoteDate.setText(vote.getVoteDate());
            textViewVoteTitle.setText(vote.getVoteTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(vote);
                    }

                }
            });


        }

    }


    @Override
    public VoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VoteListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_vote, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(VoteListViewHolder voteListViewHolder, int position) {
        Vote vote = votesList.get(position);
        voteListViewHolder.bindVote(position, vote);
    }

    @Override
    public int getItemCount() {
        return votesList.size();
    }

    public interface VoteClickListener {

        void onItemClick(Vote vote);

    }

    public void setClickListener(VoteClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
