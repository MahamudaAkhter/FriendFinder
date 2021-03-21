package com.example.friendfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.friendfinder.R;
import com.example.friendfinder.models.FireStoreRepo;
import com.example.friendfinder.models.User;
import com.example.friendfinder.ui.friend_list.FriendListFragment;
import com.example.friendfinder.ui.find_friend.FindFriendFragment;
import com.example.friendfinder.ui.places.PlacesFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;
    ImageButton profile;
    SwitchMaterial liveLocationSwitch;
    FirebaseUser firebaseUser;

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FriendListFragment();
                case 1:
                    return new FindFriendFragment();
                case 2:
                    return new PlacesFragment();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = findViewById(R.id.pager);
        /**
         * The pager adapter, which provides the pages to the view pager widget.
         */
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            Intent intent = new Intent(this, LoginSignupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            FireStoreRepo.createUser(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
            //FireStoreRepo.GetInstance().setUserID(firebaseUser.getUid());
            FireStoreRepo.GetInstance().getDocument();
        }


        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setUserInputEnabled(false);

        liveLocationSwitch = findViewById(R.id.live_location_switch);
        liveLocationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
        });

        profile = findViewById(R.id.profile_icon);
        profile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.nav_default_exit_anim);
        });

        animatedBottomBar = findViewById(R.id.nav_view);
        animatedBottomBar.setupWithViewPager2(viewPager);
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

    }

}