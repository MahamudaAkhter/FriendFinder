package com.mahamuda.friendfinder.activities;

import androidx.annotation.NonNull;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.mahamuda.friendfinder.R;
import com.mahamuda.friendfinder.ui.login_sign_up.LoginFragment;
import com.mahamuda.friendfinder.ui.login_sign_up.SignUpFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class LoginSingupActivity extends AppCompatActivity {

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


