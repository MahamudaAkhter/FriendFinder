package com.example.friendfinder.ui.friend_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.friendfinder.models.Badge;
import com.example.friendfinder.models.Friend;
import com.example.friendfinder.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendListViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private List<Friend> friends = new ArrayList<>();

    public User user;

    public FriendListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Friend List fragment");
        friends.add(new Friend("1",0, "Alex", "Alex123", false, 0, 0, null,false));
        friends.add(new Friend("2", 0, "Beth", "Beth234", false, 0, 0, null,false));
        friends.add(new Friend("3", 0, "Carl", "Carl345", false, 0, 0, null,false));
        friends.add(new Friend("4", 0, "Dean", "Dean456", false, 0, 0, null,false));

        List<Badge> badges = new ArrayList<>();
        user = new User("5",0, "Maha", "Maha123", false, 0, 0, badges, friends);

    }

    public LiveData<String> getText() {
        return mText;
    }

    public User getUser() {
        return user;
    }
}