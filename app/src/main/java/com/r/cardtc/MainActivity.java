package com.r.cardtc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.r.cardtc.Api.MyRetrofit;
import com.r.cardtc.Model.Data;
import com.r.cardtc.Model.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.gms.common.internal.ImagesContract.URL;

public class MainActivity extends AppCompatActivity {
    ImageButton back2;

    public static int poss ;
    com.google.android.material.tabs.TabLayout homePageTab;
    ViewPager homePageViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back2=findViewById(R.id.back2);



        back2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intent);
            }
        });

        homePageTab = findViewById(R.id.homePageTabLayout);
        homePageViewPage = findViewById(R.id.homePageViewPager);

        homePageViewPage.setAdapter(new MyPagerAdeptar(getSupportFragmentManager()));
        homePageTab.setupWithViewPager(homePageViewPage);

        homePageTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homePageViewPage.setCurrentItem(tab.getPosition());
                poss = tab.getPosition();
                Log.e("Possition",""+poss);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    class MyPagerAdeptar extends FragmentPagerAdapter
    {

        String[] content = {"India","Asian","Europe","America"};

        public MyPagerAdeptar(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0)
            {
                return new IndiaFragment();
            }

            if (position == 1)
            {
                return new AsiaFragment();
            }
            if (position == 2)
            {
                return new europeFragment();
            }
            if (position == 3)
            {
                return new AmericanFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return content.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return content[position];
        }
    }

}