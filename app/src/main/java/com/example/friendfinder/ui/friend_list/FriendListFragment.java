package com.example.friendfinder.ui.friend_list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfinder.FriendActionListDialogFragment;
import com.example.friendfinder.R;
import com.example.friendfinder.activities.DynamicLinksUtil;
import com.example.friendfinder.activities.InviteContent;
import com.example.friendfinder.adapters.RecyclerViewAdapter;
import com.example.friendfinder.interfaces.FriendListItemClickListener;

import com.example.friendfinder.activities.InviteFriends;


public class FriendListFragment extends Fragment implements FriendListItemClickListener {

    private FriendListViewModel friendListViewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SearchView searchView;
    Button inviteBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InviteContent content = DynamicLinksUtil.generateInviteContent();
        friendListViewModel =
                new ViewModelProvider(this).get(FriendListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_list, container, false);
        // final TextView textView = root.findViewById(R.id.text_dashboard);
        friendListViewModel.getText().observe(getViewLifecycleOwner(), s -> {
//                textView.setText(s);
        });

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

        inviteBtn = root.findViewById(R.id.btnInvite);
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClicked();
            }
        });

        return root;
    }

    @Override
    public void onListItemClick(int position) {
        FriendActionListDialogFragment.newInstance(4).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    public void onShareClicked() {
        Uri link = DynamicLinksUtil.generateContentLink();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());

        startActivity(Intent.createChooser(intent, "Share Link"));
    }
}