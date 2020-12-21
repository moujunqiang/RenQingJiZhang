package com.android.renqingjizhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.renqingjizhang.adapter.BaseFragmentAdapter;
import com.android.renqingjizhang.fragment.CalenderFragment;
import com.android.renqingjizhang.fragment.IncomeFragment;
import com.android.renqingjizhang.fragment.OutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    private BaseFragmentAdapter<Fragment> mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.vp_home_pager);
        mBottomNavigationView = findViewById(R.id.bv_home_navigation);

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mPagerAdapter = new BaseFragmentAdapter<>(this);

        mPagerAdapter.addFragment(OutFragment.newInstance());

        mPagerAdapter.addFragment(CalenderFragment.newInstance());
        mPagerAdapter.addFragment(IncomeFragment.newInstance());

        // 设置成懒加载模式
        mPagerAdapter.setLazyMode(true);
        mViewPager.setAdapter(mPagerAdapter);
        findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_out:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.menu_calender:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.menu_in:
                mViewPager.setCurrentItem(2);
                return true;
            default:
                break;
        }
        return false;
    }


}