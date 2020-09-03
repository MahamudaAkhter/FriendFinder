package com.example.friendfinder;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.friendfinder.ui.findfriends.FindFriendsFragment;
import com.example.friendfinder.ui.home.HomeFragment;
import com.example.friendfinder.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    final FragmentManager FragmentManager = getSupportFragmentManager();
    final Fragment fragment1 = new HomeFragment();
    final FindFriendsFragment fragment2 = new FindFriendsFragment();
    final Fragment fragment3 = new NotificationsFragment();
    Fragment activeFragment;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelected =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            FragmentManager.beginTransaction().hide(activeFragment).show(fragment1).commit();
                            activeFragment = fragment1;
                            return true;

                        /*case R.id.navigation_findfriends:
                            FragmentManager.beginTransaction().hide(activeFragment).show(fragment2).commit();
                            activeFragment = fragment2;
                            return true;*/

                        case R.id.navigation_notifications:
                            FragmentManager.beginTransaction().hide(activeFragment).show(fragment3).commit();
                            activeFragment = fragment3;
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);

        /*bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelected);

        activeFragment = fragment1;*/

        //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_findfriends, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    /*private BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    FindFriendsFragment fragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            //fragment = new HomeFragment();
                            break;
                        case R.id.navigation_findfriends:
                            fragment = new FindFriendsFragment();
                            break;
                        case R.id.navigation_notifications:
                            //fragment = new NotificationsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

                    return true;
                }
            };*/
}