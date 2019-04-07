package com.example.dennis.medical;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ChatFragment() {
        // Required empty public constructor
    }

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        tabLayout = view.findViewById(R.id.chat_tab_layout);
        viewPager = view.findViewById(R.id.chat_view_page);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Chat2Fragment(), "CHATS");
        viewPagerAdapter.addFragment(new DutyAlertFragment(), "NOTIFICATIONS");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
              return titles.get(position);
        }
    }

    private void userstatus(String userstatus){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userstatus", userstatus);
        databaseReference.updateChildren(hashMap);

    }

    @Override
    public void onResume() {
        super.onResume();
        userstatus("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        userstatus("offline");
    }


}
