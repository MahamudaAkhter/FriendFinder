package com.example.friendfinder.ui.friend_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.friendfinder.models.Badge;
import com.example.friendfinder.models.Friend;
import com.example.friendfinder.models.User;

import java.util.ArrayList;
import java.util.List;

public class FriendListViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private List<Friend> friends = new ArrayList<>();

    public User user;

    public FriendListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Friend List fragment");
        friends.add(new Friend(0, "Alex", "Alex123", true, false));
        friends.add(new Friend(0, "Beth", "Beth234", false, true));
        friends.add(new Friend(0, "Carl", "Carl345", true, true));
        friends.add(new Friend(0, "Dean", "Dean456", false, false));

        List<Badge> badges = new ArrayList<>();
        user = new User(0, "Maha", "Maha123", false, friends, 0, 0, badges);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public User getUser() {
        return user;
    }
}