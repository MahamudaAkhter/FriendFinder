package com.example.friendfinder.ui.find_friend;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindFriendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FindFriendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Find Friend fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}