package ru.handh.training.voteonoffice.ui.votecreate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.data.votesmodel.VoteVariant;

public class AddVariantAdapter extends RecyclerView.Adapter<AddVariantAdapter.AddVariantViewHolder> {

    private List<VoteVariant> voteVariantsList;

    @Inject
    public AddVariantAdapter() {
        voteVariantsList = new ArrayList<>();
    }

    public class AddVariantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private VoteVariant mVoteVariant;

        EditText editTextVariant;
        ImageView imageVariantImg, imageDeleteVariant;

        public AddVariantViewHolder(View itemView) {
            super(itemView);



            editTextVariant = itemView.findViewById(R.id.editTextVariant);
            imageVariantImg = itemView.findViewById(R.id.imageVariantImg);
            imageDeleteVariant = itemView.findViewById(R.id.imageDeleteVariant);

            imageDeleteVariant.setOnClickListener(this);

        }


        public void bindVariant(int position, VoteVariant voteVariant) {
            // введеный в поле текст при добавлении варианта заменяется на 1 2 3 исправить
            editTextVariant = itemView.findViewById(R.id.editTextVariant);
            mVoteVariant = voteVariant;
            //String variantPosition = String.valueOf(position + 1) + editTextVariant.getText().toString();

            //editTextVariant.setText(variantPosition);
            voteVariant.setVariantId(position + 1);
            voteVariant.setVariantName(editTextVariant.getText().toString().trim());

        }

        @Override
        public void onClick(View v) {
            removeItem(getPosition());
        }
    }

    private void removeItem(int position) {
        voteVariantsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, voteVariantsList.size());
    }

    @Override
    public AddVariantAdapter.AddVariantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_variant, parent, false);
        return new AddVariantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddVariantAdapter.AddVariantViewHolder addVariantViewHolder, int position) {
        VoteVariant voteVariant = voteVariantsList.get(position);
        addVariantViewHolder.bindVariant(position, voteVariant);

    }

    @Override
    public int getItemCount() {
        return voteVariantsList.size();
    }

    public void addItemVariant(VoteVariant voteVariant) {
        voteVariantsList.add(voteVariant);
        notifyDataSetChanged();
    }

    public List<VoteVariant> getVariants() {

        return voteVariantsList;

    }


}
