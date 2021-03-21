package com.example.friendfinder.ui.friend_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfinder.FriendActionListDialogFragment;
import com.example.friendfinder.R;
import com.example.friendfinder.adapters.RecyclerViewAdapter;
import com.example.friendfinder.interfaces.FriendListItemClickListener;
import com.example.friendfinder.models.Badge;
import com.example.friendfinder.models.Friend;
import com.example.friendfinder.models.Person;
import com.example.friendfinder.models.User;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment implements FriendListItemClickListener {

    private FriendListViewModel friendListViewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendListViewModel =
                new ViewModelProvider(this).get(FriendListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_list, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
        friendListViewModel.getText().observe(getViewLifecycleOwner(), s -> {
//                textView.setText(s);
        });

        /*Replace List with list from firebase using sorting from firebase*/

        recyclerView = root.findViewById(R.id.friend_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(friendListViewModel.getUser(), this);
        recyclerView.setAdapter(recyclerViewAdapter);

        searchView = root.findViewById(R.id.friend_list_search_view);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return root;
    }

    @Override
    public void onListItemClick(int position) {
        FriendActionListDialogFragment.newInstance(4).show(getActivity().getSupportFragmentManager(), "dialog");
    }
}