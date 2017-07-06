package com.txd.hzj.wjlp.mellOnLine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAty extends BaseAty {
    @ViewInject(R.id.search_title_be_back_iv)
    public ImageView search_title_be_back_iv;

    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;

    @ViewInject(R.id.search_lin_layout)
    public LinearLayout search_lin_layout;

    @ViewInject(R.id.search_title_right_tv)
    public TextView search_title_right_tv;
    @ViewInject(R.id.search_type_tv)
    public TextView search_type_tv;

    @ViewInject(R.id.message_layout)
    public FrameLayout message_layout;

    @ViewInject(R.id.title_search_ev)
    public EditText title_search_ev;

    @ViewInject(R.id.search_goods_rv)
    private RecyclerView search_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private List<String> data;
    /**
     * 分割线的高度
     */
    private int height = 0;

    private String type = "";
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        showStatusBar(R.id.search_title_layout);

        search_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        search_goods_rv.setItemAnimator(new DefaultItemAnimator());
        search_goods_rv.setHasFixedSize(true);
        search_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        search_goods_rv.setAdapter(racycleAllAdapter);

    }

    private void forTitle() {
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_search_tv.setVisibility(View.GONE);
        search_lin_layout.setVisibility(View.VISIBLE);
        message_layout.setVisibility(View.GONE);
        search_title_right_tv.setVisibility(View.VISIBLE);
        title_search_ev.setText(keyword);
        search_type_tv.setText(type);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_list;
    }

    @Override
    protected void initialized() {
        type = getIntent().getStringExtra("type");
        keyword = getIntent().getStringExtra("keyword");

        data = new ArrayList<>();
        racycleAllAdapter = new RacycleAllAdapter(this, data);
        height = ToolKit.dip2px(this, 4);
    }

    @Override
    protected void requestData() {

    }
}
