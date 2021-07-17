package com.mahamuda.friendfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import com.mahamuda.friendfinder.R;
import com.mahamuda.friendfinder.interfaces.FriendSmallListItemClickListener;

public class SmallRecyclerViewAdapter extends RecyclerView.Adapter<SmallRecyclerViewAdapter.SmallRecyclerViewHolder> {

    private final Random random;
    private final FriendSmallListItemClickListener mOnClickListener;

    public SmallRecyclerViewAdapter(int seed, FriendSmallListItemClickListener onClickListener) {
        this.random = new Random(seed);
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.person_card_small_live;
    }

    @NonNull
    @Override
    public SmallRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SmallRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallRecyclerViewHolder holder, int position) {
//        holder.getView().setText(String.valueOf(random.nextInt()));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class SmallRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView view;

        public SmallRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
//            view = itemView.findViewById(R.id.randomText);
            itemView.setOnClickListener(this);
        }

        public TextView getView() {
            return view;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }


    }


}
