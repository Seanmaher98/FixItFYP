package com.example.fixitfyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    //This TabLayout was created by me with the help of a YouTube Tutorial -  https://youtu.be/NHBO87ZxGgs
    //The tutorial was How to Implement Tablayout With Viewpager in Android Studio Tablayout+Viewpager, Android Coding.
    //First I set my variables
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialise Variables
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        //Initialise array
        ArrayList<String> arrayList = new ArrayList<>();
        //Add Title of Tabs
        arrayList.add("User Sign Up");
        arrayList.add("Trade Sign Up");
        //Prepare view pager
        prepareViewPager(viewPager, arrayList);

        //Set up with view pager
        tabLayout.setupWithViewPager(viewPager);


    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        //Initialise main adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        //initialise main fragment
        TradeFragment fragment = new TradeFragment();
        //use for loop
        for (int i = 0; i < arrayList.size(); i++) {
            //Initialise bundle
            Bundle bundle = new Bundle();
            //Put string
            bundle.putString("title", arrayList.get(i));
            //Set argument
            fragment.setArguments(bundle);
            //Add fragment
            adapter.addFragment(fragment, arrayList.get(i));
            //Define new fragment
            fragment = new TradeFragment();
        }
        //Set adapter
        viewPager.setAdapter(adapter);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        //Initialise array list
        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        //Create Constructor
        public void addFragment(Fragment fragment, String title) {
            //add title
            arrayList.add(title);
            //add fragment
            fragmentList.add(fragment);

        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new UserFragment();
                    break;
                case 1:
                    fragment = new TradeFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            //return fragment list size
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //return array list position
            return arrayList.get(position);
        }
    }
    //END YOUTUBE CODE

}