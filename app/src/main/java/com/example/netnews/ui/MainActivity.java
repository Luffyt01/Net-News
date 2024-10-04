package com.example.netnews.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.netnews.R;
import com.example.netnews.adapter.TabAdapter;
import com.example.netnews.authentication.AuthViewModel;
import com.example.netnews.databinding.ActivityMainBinding;
import com.example.netnews.ui.bookmark.BookmarkActivity;
import com.example.netnews.ui.history.HistoryActivity;
import com.example.netnews.ui.home.HomeFragment;
import com.example.netnews.ui.local.LocalFragment;
import com.example.netnews.ui.profile.ProfileFragment;
import com.example.netnews.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private AuthViewModel authViewModel;

    private TabAdapter tabAdapter;
    private ViewPager2 mainVP;

    private DrawerLayout mDrawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private TextView tabTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        tabTitle = findViewById(R.id.tabTitle);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.bookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BookmarkActivity.class);
                startActivity(i);
            }
        });

        binding.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });


        mDrawer = binding.mainDrawer;
        navigationView = binding.nvView;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tabTitle.setText(R.string.net_news);



        drawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.home);

        mainVP = binding.mainViewpager;
        mainVP.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        tabAdapter = new TabAdapter(getSupportFragmentManager(),getLifecycle());
        tabAdapter.addFragment(new HomeFragment());
        tabAdapter.addFragment(new LocalFragment());
        tabAdapter.addFragment(new SearchFragment());
        tabAdapter.addFragment(new ProfileFragment());


        mainVP.setUserInputEnabled(false);
        mainVP.setAdapter(tabAdapter);




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemID = menuItem.getItemId();

                if(itemID == R.id.home){
                    mainVP.setCurrentItem(0);
                    tabTitle.setText("Net News");
                    return true;
                }else if (itemID == R.id.local){
                    mainVP.setCurrentItem(1);
                    tabTitle.setText("Local");
                    return true;
                }else if (itemID == R.id.search){
                    mainVP.setCurrentItem(2);
                    tabTitle.setText("Search");
                    return true;
                }else if (itemID == R.id.profile){
                    mainVP.setCurrentItem(3);
                    tabTitle.setText("Profile");
                    return true;
                }
                return false;
            }
        });
    }


    private void selectDrawerItem(MenuItem menuItem) {
        int itemID = menuItem.getItemId();

        if(itemID == R.id.home){
            mainVP.setCurrentItem(0);
            bottomNavigationView.setSelectedItemId(R.id.home);
            tabTitle.setText(R.string.net_news);
        }else if(itemID == R.id.local){
            mainVP.setCurrentItem(1);
            tabTitle.setText("Local");
            bottomNavigationView.setSelectedItemId(R.id.local);
        }else if(itemID == R.id.bookmark){
            Intent i = new Intent(MainActivity.this, BookmarkActivity.class);
            startActivity(i);
        }else if(itemID == R.id.logout){
            authViewModel.logoutUser(MainActivity.this);
        }
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle!=null && drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}