package ru.handh.training.voteonoffice.ui.voteactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder>{

    private List<VoteVariant> voteVariantsList;

    private Context context;

    int checkBoxSelected;

    public VoteAdapter(Context context, List<VoteVariant> voteVariantsList){
            this.context = context;
            this.voteVariantsList = voteVariantsList;
            this.checkBoxSelected = -1;
    }


    public class VoteViewHolder extends RecyclerView.ViewHolder{

        TextView textViewVoteDescription;
        CheckBox checkBoxVote;

        public VoteViewHolder(final View itemView) {
            super(itemView);

            textViewVoteDescription = itemView.findViewById(R.id.textViewVariantDescription);
            checkBoxVote = itemView.findViewById(R.id.checkboxVote);


        }

        public void bindVariant(int position, VoteVariant voteVariant){
            textViewVoteDescription.setText(voteVariant.getVariantName());

            // обнуляю слушатель чтобы после checkBoxVote.setChecked не
            checkBoxVote.setOnCheckedChangeListener(null);

            if(position != checkBoxSelected){
                checkBoxVote.setChecked(false);
            } else {
                checkBoxVote.setChecked(true);
            }

            checkBoxVote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        checkBoxSelected = getAdapterPosition();
                    } else if(checkBoxSelected == getAdapterPosition()) {
                        checkBoxSelected = -1;

                    }
                    notifyDataSetChanged();


                }
            });



        }

    }

    @Override
    public VoteAdapter.VoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vote_variant, parent, false);
        return new VoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VoteAdapter.VoteViewHolder voteViewHolder, int position) {
        VoteVariant voteVariant = voteVariantsList.get(position);
        voteViewHolder.bindVariant(position, voteVariant);

    }

    @Override
    public int getItemCount() {
        return voteVariantsList.size();
    }

    public boolean checkBoxChekedStatus(){

        if (checkBoxSelected == -1){
            return false;
        } else {
            return true;
        }

    }
}
