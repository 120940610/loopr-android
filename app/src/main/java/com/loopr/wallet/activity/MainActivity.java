package com.loopr.wallet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.loopr.wallet.R;
import com.loopr.wallet.adapter.HomeFragmentPagerAdapter;
import com.loopr.wallet.fragment.BaseFragment;
import com.loopr.wallet.fragment.TabAssetsFragment;
import com.loopr.wallet.fragment.TabMarketFragment;
import com.loopr.wallet.fragment.TabSettingFragment;
import com.loopr.wallet.fragment.TabTradeFragment;
import com.loopr.wallet.utils.tools.AppUtils;
import com.loopr.wallet.utils.tools.LogUtils;
import com.loopr.wallet.view.indicator.FragmentTabPageIndicator;

/**
 * Created by snow on 2018/3/6.
 */
public class MainActivity extends BaseActivity {

    public ViewPager mViewPager;
    private HomeFragmentPagerAdapter mPagerAdapter;
    private FragmentTabPageIndicator indicator;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerFragments();
        initViewPager();
    }

    private void registerFragments() {
        //four tabs of the home page
        getFragmentHandler().registerFragment(TabAssetsFragment.class, R.id.fragment_tab_container, false);
        getFragmentHandler().registerFragment(TabMarketFragment.class, R.id.fragment_tab_container, false);
        getFragmentHandler().registerFragment(TabTradeFragment.class, R.id.fragment_tab_container, false);
        getFragmentHandler().registerFragment(TabSettingFragment.class, R.id.fragment_tab_container, false);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                try {
                    if (fragmentHandler != null && fragmentHandler.getCurrentFragment() != null) {
                        ((BaseFragment) fragmentHandler.getCurrentFragment()).onFragmentSwitched(MainActivity.this);
                    }
                } catch (Exception ex) {
                    LogUtils.e(TAG, ex.getMessage());
                }
            }
        });
    }

    private void initViewPager() {
        if (mViewPager == null) {
            mViewPager = (ViewPager) findViewById(R.id.fragment_tab_container);
            mViewPager.setOffscreenPageLimit(3);
            mPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            initIndicator();
        }
    }


    private void initIndicator() {
        indicator = (FragmentTabPageIndicator) findViewById(R.id.fragment_tab_indicator);
        indicator.setViewPager(mViewPager);
        indicator.setOnTabSelectedListener(new FragmentTabPageIndicator.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                AppUtils.hideSoftKeyboard(MainActivity.this);
            }
        });
    }
}
