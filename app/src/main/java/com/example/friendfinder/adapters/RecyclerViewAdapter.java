package com.example.friendfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfinder.R;
import com.example.friendfinder.interfaces.FriendListItemClickListener;
import com.example.friendfinder.models.Friend;
import com.example.friendfinder.models.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> implements Filterable {

    private User user;
    private final FriendListItemClickListener mOnClickListener;
    private int x = 1;
    private List<Friend> friendList;
    private List<Friend> friendListFull;

    public RecyclerViewAdapter(User user, FriendListItemClickListener onClickListener) {
        this.user = user;
        this.mOnClickListener = onClickListener;
        this.friendList = user.getFriends();
        friendListFull = new ArrayList<>(user.getFriends());
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.person_card;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        List<Friend> friends = user.getFriends();
        holder.getView().setText(String.valueOf(friends.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Friend> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(friendListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                filterPattern = "" + filterPattern;
                for (Friend friend : friendListFull) {
                    if (friend.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(friend);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            friendList.clear();
            friendList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView view;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.randomText);
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
