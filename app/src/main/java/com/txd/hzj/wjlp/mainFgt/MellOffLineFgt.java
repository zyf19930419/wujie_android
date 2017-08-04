package com.txd.hzj.wjlp.mainFgt;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Mell;
import com.txd.hzj.wjlp.citySelect.CitySelectAty;
import com.txd.hzj.wjlp.citySelect.MellCitySelectAty;
import com.txd.hzj.wjlp.mainFgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.mellOffLine.OffLineDetailsAty;
import com.txd.hzj.wjlp.mellOffLine.point.PointWjAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:52
 * 描述：线下商铺
 * ===============Txunda===============
 */
public class MellOffLineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    /**
     * 标题栏
     */
    @ViewInject(R.id.off_line_title_layout)
    public RelativeLayout off_line_title_layout;
    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;

    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;

    private int allHeight = 0;

    /**
     * 附近商家列表
     */
    @ViewInject(R.id.mell_near_by_lv)
    private ListViewForScrollView mell_near_by_lv;

    private List<Mell> mells;

    private MellNearByHzjAdapter mellNearByHzjAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        off_line_title_layout.setBackgroundColor(Color.TRANSPARENT);
        allHeight = Settings.displayWidth * 2 / 3;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);
        // 轮播图
        forBanner();
        // 改变标题栏颜色
        off_line_to_change_sc.setScrollViewListener(MellOffLineFgt.this);
        mell_near_by_lv.setAdapter(mellNearByHzjAdapter);
        mell_near_by_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(OffLineDetailsAty.class, null);
            }
        });

    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setPageCount(image.size());
        online_carvouse_view.setImageListener(imageListener);
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageResource(image.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    @Override
    @OnClick({R.id.to_location_tv, R.id.point_by_wj_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_location_tv:// 当前位置
                startActivity(MellCitySelectAty.class, null);
                break;
            case R.id.point_by_wj_tv:// 无界驿站
                startActivity(PointWjAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mell_off_line;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        mells = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if(i%2==0){
                mells.add(new Mell(true, false));
            } else {
                mells.add(new Mell(false, false));
            }
        }
        mellNearByHzjAdapter = new MellNearByHzjAdapter(getActivity(), mells);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.off_line_title_layout);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            off_line_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            off_line_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
        } else {
            off_line_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
        }
        immersionInit();
    }
}
