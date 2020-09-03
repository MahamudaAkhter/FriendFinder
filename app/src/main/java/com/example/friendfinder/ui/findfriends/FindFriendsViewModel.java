package com.example.friendfinder.ui.findfriends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindFriendsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FindFriendsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is find friends fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}