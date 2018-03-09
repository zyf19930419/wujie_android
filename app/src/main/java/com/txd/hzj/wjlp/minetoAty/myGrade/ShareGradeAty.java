package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.cityselect1.ac.EasySideBarBuilder;
import com.txd.hzj.wjlp.http.user.UserPst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：上午 10:44
 * 描述：工作成绩
 * ===============Txunda===============
 */
public class ShareGradeAty extends BaseAty {


    /**
     * AppBarLAyout
     */
    @ViewInject(R.id.grade_app_bar_layout)
    private AppBarLayout app_bar_layout;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.sh_collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    /**
     * ToolBar
     */
    @ViewInject(R.id.sh_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.sh_left_tv)
    private TextView left_tv;
    @ViewInject(R.id.sh_left_view)
    private View left_view;

    @ViewInject(R.id.sh_righr_tv)
    private TextView right_tv;
    @ViewInject(R.id.sh_right_view)
    private View right_view;

    @ViewInject(R.id.my_share_grade_lv)
    private ListViewForScrollView my_share_grade_lv;

    private RankingListAdapter rankingListAdapter;

    private UserPst userPst;
    private String city_name = "天津";
    private int p = 1;
    private String city_id = "";

    private String type = "share";

    @ViewInject(R.id.grade_location_tv)
    private TextView grade_location_tv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

//    @ViewInject(R.id.grade_ssr_layout)
//    private SuperSwipeRefreshLayout swipe_refresh;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    private List<Map<String, String>> rankList;


    @ViewInject(R.id.user_head_iv)
    private ShapedImageView user_head_iv;

    private int size = 0;
    private int size2 = 0;

    @ViewInject(R.id.nick_name_tv)
    private TextView nick_name_tv;
    @ViewInject(R.id.share_num_tv)
    private TextView share_num_tv;
    @ViewInject(R.id.recommend_num_tv)
    private TextView recommend_num_tv;
    @ViewInject(R.id.nestedScrollView)
    private NestedScrollView nestedScrollView;

    ShareAdapter shareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 沉浸式
        showStatusBar(R.id.sh_toolbar);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });

//        if (!city_name.equals("")) {
//            grade_location_tv.setText(city_name);
//        }

        my_share_grade_lv.setEmptyView(no_data_layout);

        changeViewStatus(0);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) ) {
                    frist = false;
                    p++;
                    userPst.gradeRank(p, city_id, type, city_name, false);
                    showProgressDialog();
                }
            }


        });
//        swipe_refresh.setHeaderViewBackgroundColor(0xff888888);
//        swipe_refresh.setHeaderView(createHeaderView());// add headerView
//        swipe_refresh.setFooterView(createFooterView());
//        swipe_refresh.setTargetScrollWithLayout(true);
//
//        swipe_refresh
//                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
//
//                    @Override
//                    public void onRefresh() {
//                        frist = false;
//                        textView.setText("正在刷新");
//                        imageView.setVisibility(View.GONE);
//                        progressBar.setVisibility(View.VISIBLE);
//                        p = 1;
//                        userPst.gradeRank(p, city_id, type, city_name, false);
//                    }
//
//                    @Override
//                    public void onPullDistance(int distance) {
//                    }
//
//                    @Override
//                    public void onPullEnable(boolean enable) {
//                        textView.setText(enable ? "松开刷新" : "下拉刷新");
//                        imageView.setVisibility(View.VISIBLE);
//                        imageView.setRotation(enable ? 180 : 0);
//                    }
//                });
//
//        swipe_refresh
//                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
//
//                    @Override
//                    public void onLoadMore() {
//                        frist = false;
//                        footerTextView.setText("正在加载...");
//                        footerImageView.setVisibility(View.GONE);
//                        footerProgressBar.setVisibility(View.VISIBLE);
//
//                        p++;
//                        userPst.gradeRank(p, city_id, type, city_name, false);
//                    }
//
//                    @Override
//                    public void onPushEnable(boolean enable) {
//                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
//                        footerImageView.setVisibility(View.VISIBLE);
//                        footerImageView.setRotation(enable ? 0 : 180);
//                    }
//
//                    @Override
//                    public void onPushDistance(int distance) {
//
//                    }
//
//                });
        userPst.gradeRank(p, "", "share", grade_location_tv.getText().toString(), true);
    }

    public void shareHttp(){
//        http://wjyp.txunda.com/index.php/Api/User/gradeRank
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("p", "1");
        parameters.put("city_name", "天津 ");
        parameters.put("type", "share");

        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .build()
                .rxPost("User/gradeRank", parameters, new RxStringCallback() {


                    @Override
                    public void onNext(Object tag, String response) {
//                        Toast.makeText(ShareGradeAty.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            JSONArray jsonArray = jsonObject2.getJSONArray("rank_list");
                            ArrayList<ShareBean> list = new ArrayList<ShareBean>();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String num =jsonObject1.getString("num");
                                String nickname = jsonObject1.getString("nickname");
                              String head_pic =jsonObject1.getString("head_pic");
                                list.add(new ShareBean(num,nickname,head_pic));
                            }
                            shareAdapter = new ShareAdapter(ShareGradeAty.this,list);
                            my_share_grade_lv.setAdapter(shareAdapter);
                            shareAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }

                });






    }
    @Override
    @OnClick({R.id.recommend_success_layout, R.id.sh_left_lin_layout, R.id.sh_right_lin_layout, R.id
            .grade_location_tv, R.id.share_time_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_time_layout:// 分享次数
                startActivity(ShareTimesAty.class, null);
                break;
            case R.id.recommend_success_layout:// 推荐成功
                startActivity(RecommendSuccessAty.class, null);
                break;
            case R.id.sh_left_lin_layout:// 左(分享榜)
                changeViewStatus(0);
//                shareHttp();
                userPst.gradeRank(p, "", "share", grade_location_tv.getText().toString(), true);
                break;
            case R.id.sh_right_lin_layout:// 右(推荐榜)
                changeViewStatus(1);
                userPst.gradeRank(p, "", "recommend", grade_location_tv.getText().toString(), true);
                break;
            case R.id.grade_location_tv:// 地址选择
                download();
                break;
        }
    }

    private void changeViewStatus(int i) {
        left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        if (0 == i) {
            left_tv.setTextColor(Color.parseColor("#E60012"));
            left_view.setBackgroundColor(Color.parseColor("#E60012"));
            type = "share";
        } else {
            right_tv.setTextColor(Color.parseColor("#E60012"));
            right_view.setBackgroundColor(Color.parseColor("#E60012"));
            type = "recommend";
        }
        p = 1;
        userPst.gradeRank(p, city_id, type, city_name, true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_grade;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        city_name = DemoApplication.getInstance().getLocInfo().get("city");
        rankingListAdapter = new RankingListAdapter();
        rankList = new ArrayList<>();
        size = ToolKit.dip2px(this, 80);
        size2 = ToolKit.dip2px(this, 60);
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("gradeRank")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    Glide.with(this).load(data.get("head_pic"))
                            .override(size, size)
                            .placeholder(R.drawable.ic_default)
                            .error(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(user_head_iv);
                    nick_name_tv.setText(data.get("nickname") + "\n推荐人：" + data.get("parent_name"));
                    share_num_tv.setText(data.get("share_num"));
                    recommend_num_tv.setText(data.get("recommend_num"));
                    if (ToolKit.isList(data, "rank_list")) {
                        rankList = JSONUtils.parseKeyAndValueToMapList(data.get("rank_list"));
                        my_share_grade_lv.setAdapter(rankingListAdapter);

                    }

                    if (!frist) {
//                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    if (ToolKit.isList(data, "rank_list")) {
                        rankList.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("rank_list")));
                        my_share_grade_lv.setAdapter(rankingListAdapter);

                    }
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
//                    swipe_refresh.setLoadMore(false);
                }
            } else {
                if (1 == p) {
                    if (!frist) {
//                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
//                    swipe_refresh.setLoadMore(false);
                }
            }
        }
    }

    private class RankingListAdapter extends BaseAdapter {

        private RLVH rlvh;
        private int imageId;

        @Override
        public int getCount() {
            return rankList.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return rankList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Map<String, String> rank = getItem(i);

            if (view == null) {
                view = LayoutInflater.from(ShareGradeAty.this).inflate(R.layout.item_share_grade_lv, viewGroup, false);
                rlvh = new RLVH();
                ViewUtils.inject(rlvh, view);
                view.setTag(rlvh);
            } else {
                rlvh = (RLVH) view.getTag();
            }

            if (5 > i && 3 <= i) {// 第四名和第五名
                rlvh.top_from_four_to_five_iv.setVisibility(View.VISIBLE);
                rlvh.top_three_iv.setVisibility(View.GONE);
                imageId = getResources().getIdentifier("icon_ranking_" + i, "drawable", getPackageName());
                rlvh.top_from_four_to_five_iv.setImageResource(imageId);
            } else if (3 > i) {// 前三名
                rlvh.top_from_four_to_five_iv.setVisibility(View.GONE);
                rlvh.top_three_iv.setVisibility(View.VISIBLE);
                imageId = getResources().getIdentifier("icon_ranking_" + i, "drawable", getPackageName());
                rlvh.top_three_iv.setImageResource(imageId);
            } else {
                rlvh.top_from_four_to_five_iv.setVisibility(View.GONE);
                rlvh.top_three_iv.setVisibility(View.GONE);
            }


            Glide.with(ShareGradeAty.this).load(rank.get("head_pic"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(rlvh.rank_item_haed_iv);

            rlvh.rank_nickname_Tv.setText(rank.get("nickname"));

            rlvh.rank_num_tv.setText(rank.get("num"));

            return view;
        }

        class RLVH {
            /**
             * 前三的标签
             */
            @ViewInject(R.id.top_three_iv)
            private ImageView top_three_iv;
            /**
             * 第四第五的标签
             */
            @ViewInject(R.id.top_from_four_to_five_iv)
            private ImageView top_from_four_to_five_iv;

            /**
             * 头像
             */
            @ViewInject(R.id.rank_item_haed_iv)
            private ShapedImageView rank_item_haed_iv;

            @ViewInject(R.id.rank_nickname_Tv)
            private TextView rank_nickname_Tv;

            @ViewInject(R.id.rank_num_tv)
            private TextView rank_num_tv;

        }
    }

//    private View createFooterView() {
//        View footerView = LayoutInflater.from(swipe_refresh.getContext())
//                .inflate(R.layout.layout_footer, null);
//        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
//        footerImageView = footerView.findViewById(R.id.footer_image_view);
//        footerTextView = footerView.findViewById(R.id.footer_text_view);
//        footerProgressBar.setVisibility(View.GONE);
//        footerImageView.setVisibility(View.VISIBLE);
//        footerImageView.setImageResource(R.drawable.down_arrow);
//        footerTextView.setText("上拉加载更多...");
//        return footerView;
//    }
//
//    private View createHeaderView() {
//        View headerView = LayoutInflater.from(swipe_refresh.getContext())
//                .inflate(R.layout.layout_head, null);
//        progressBar = headerView.findViewById(R.id.pb_view);
//        textView = headerView.findViewById(R.id.text_view);
//        textView.setText("下拉刷新");
//        imageView = headerView.findViewById(R.id.image_view);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setImageResource(R.drawable.down_arrow);
//        progressBar.setVisibility(View.GONE);
//        return headerView;
//    }


    private final String[] mIndexItems = {"热门"};//头部额外的索引
    private void download() {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("region_id", "");
        new Novate.Builder(this)
                .baseUrl("http://test.wujiemall.com/index.php/Api/")
                .build()
                .rxPost("Address/getRegion", parameters, new RxStringCallback() {


                    @Override
                    public void onNext(Object tag, String response) {
//                        Toast.makeText(ShareGradeAty.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            JSONArray jsonArray = jsonObject2.getJSONArray("hot_list");
                            ArrayList<String> list = new ArrayList<String>();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String region_id =jsonObject1.getString("region_id");
                                String region_name = jsonObject1.getString("region_name");
//                              String letter =jsonObject1.getString("letter");
                                list.add(region_name);
                            }

                            new EasySideBarBuilder(ShareGradeAty.this)
                                    .setTitle("城市选择")
                        /*.setIndexColor(Color.BLUE)*/
                                    .setIndexColor(0xFF0095EE)
                       /* .isLazyRespond(true) //懒加载模式*/
                                    .setHotCityList(list)//热门城市列表
                                    .setIndexItems(mIndexItems)//索引字母
                                    .setLocationCity("天津")//定位城市
                                    .setMaxOffset(60)//索引的最大偏移量
                                    .start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }

                });

    }
    //数据回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EasySideBarBuilder.CODE_SIDEREQUEST:
                if (data!=null){
                    String city = data.getStringExtra("selected");
//                    Toast.makeText(this,"选择的城市："+city,Toast.LENGTH_SHORT).show();
                    grade_location_tv.setText(city);
//                    changeViewStatus(0);


                    left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
                    right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
                    left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                    right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                    left_tv.setTextColor(Color.parseColor("#E60012"));
                    left_view.setBackgroundColor(Color.parseColor("#E60012"));
                    type = "share";

                    L.e(grade_location_tv.getText().toString()+"当前城市");

                    userPst.gradeRank(p, "", "share", grade_location_tv.getText().toString(), true);
                }
                break;

            default:
                break;
        }

    }

}
