package com.example.friendfinder.activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.friendfinder.R;
import com.example.friendfinder.ui.find_friend.FindFriendFragment;
import com.example.friendfinder.ui.friend_list.FriendListFragment;
import com.example.friendfinder.ui.login_sign_up.LoginFragment;
import com.example.friendfinder.ui.login_sign_up.SignUpFragment;
import com.example.friendfinder.ui.places.PlacesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginSignupActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    private static final int NUM_PAGES = 2;

    public static class DemoCollectionAdapter extends FragmentStateAdapter {

        public DemoCollectionAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new LoginFragment();
                case 1:
                    return new SignUpFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.login_sign_up_view_pager);

        FragmentStateAdapter pagerAdapter = new DemoCollectionAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Log In");
                    } else if (position == 1) {
                        tab.setText("Sign Up");
                    }
                }).attach();
    }

    @Override
    public void onBackPressed() {
    }

}


