package com.iceford.heartguard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iceford.heartguard.adapter.PagerAdapter;
import com.iceford.heartguard.monitor.MonitorFragment;
import com.iceford.heartguard.page.ForceOfflineActivity;
import com.iceford.heartguard.profile.ProfileFragment;
import com.iceford.heartguard.report.ReportFragment;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends ForceOfflineActivity {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
    }


    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        navigation = findViewById(R.id.nav_view);
        // 添加三个Fragment实例
        fragmentList.add(new MonitorFragment());
        fragmentList.add(new ReportFragment());
        fragmentList.add(new ProfileFragment());
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), this, fragmentList);
        // PagerAdapter实例，并传入FragmentManager、活动实例（this）和fragmentList，用于管理Fragment的显示和切换
        viewPager.setAdapter(adapter);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 根据选中的菜单项来切换ViewPager的当前页面
                switch (item.getItemId()) {
                    case R.id.navigation_monitor:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_report:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 该方法只在滑动停止时调用，position滑动停止所在页面位置
                // 当滑动到某一位置，导航栏对应位置被按下
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}

