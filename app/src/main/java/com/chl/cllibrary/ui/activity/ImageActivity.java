package com.chl.cllibrary.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chl.cllibrary.R;
import com.chl.cllibrary.ui.fragment.CircleFragment;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private ViewPager mPager;
    private TabLayout mTab;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTab = (TabLayout) findViewById(R.id.tab);
        mFragments = new ArrayList<>();
        mFragments.add(new CircleFragment());
        mFragments.add(new CircleFragment());
        mPager.setAdapter(new ImageFragmentAdapter(getSupportFragmentManager(), mFragments));
        mTab.setupWithViewPager(mPager);
    }

    class ImageFragmentAdapter extends FragmentPagerAdapter{

        private List<Fragment> mFragmentList;

        public ImageFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "圆形头像";
                case 1:
                    return "圆形头像";
            }
            return null;
        }
    }
}
