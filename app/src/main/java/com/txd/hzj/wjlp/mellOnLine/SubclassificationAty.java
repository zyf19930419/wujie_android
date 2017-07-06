package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.fgt.SubClassifyListFgt;

import java.util.ArrayList;
/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：上午 11:34
 * 描述：子分类(1-1-2)二级分类
 * ===============Txunda===============
 */
public class SubclassificationAty extends BaseAty {
    /**
     * TabLayout
     */
    @ViewInject(R.id.sub_classify_stl)
    private SlidingTabLayout sub_classify_stl;

    @ViewInject(R.id.sub_classify_vp)
    private ViewPager sub_classify_vp;

    private String titles[] = {"全部", "零食1", "零食2", "零食3", "零食4", "零食5", "零食6", "零食7", "零食8", "零食9", "零食10", "零食11",
            "零食12", "零食13", "零食14"};

    private ArrayList<Fragment> mFragments;

    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        sub_classify_vp.setAdapter(myPagerAdapter);
        sub_classify_stl.setViewPager(sub_classify_vp);
        sub_classify_vp.setCurrentItem(0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_subclassification;
    }

    @Override
    protected void initialized() {
        mFragments = new ArrayList<>();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        for (String string : titles) {
            mFragments.add(SubClassifyListFgt.getInstance(string));
        }
    }

    @Override
    protected void requestData() {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
