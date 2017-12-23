package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.CreateGroupAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.txunda_lh.http.AuctionOrder;
import com.txd.hzj.wjlp.txunda_lh.http.BalancePay;
import com.txd.hzj.wjlp.txunda_lh.http.GroupBuyOrder;
import com.txd.hzj.wjlp.txunda_lh.http.IntegralBuyOrder;
import com.txd.hzj.wjlp.txunda_lh.http.IntegralOrder;
import com.txd.hzj.wjlp.txunda_lh.http.IntegralPay;
import com.txd.hzj.wjlp.txunda_lh.http.Order;
import com.txd.hzj.wjlp.txunda_lh.http.Pay;
import com.txd.hzj.wjlp.txunda_lh.http.PreOrder;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：下午 1:06
 * 描述：支付(会员支付)
 * ===============Txunda===============
 */
public class PayForAppAty extends BaseAty {

    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 传递使者，传递大使(选中标识)
     */
    private int top_type = 0;

    /**
     * 传递使者CheckBox
     */
    @ViewInject(R.id.top_cb)
    private CheckBox top_cb;
    /**
     * 传递大使CheckBox
     */
    @ViewInject(R.id.bottom_cb)
    private CheckBox bottom_cb;
    /**
     * 微信支付
     */
    @ViewInject(R.id.pay_by_wechat_cb)
    private CheckBox pay_by_wechat_cb;
    /**
     * 支付宝支付
     */
    @ViewInject(R.id.pay_by_ali_cb)
    private CheckBox pay_by_ali_cb;
    /**
     * 余额支付
     */
    @ViewInject(R.id.pay_by_balance_cb)
    private CheckBox pay_by_balance_cb;

    private int bottom_type = 0;

    /**
     * 传递使者，传递大使
     */
//    @ViewInject(R.id.for_member_layout)
//    private LinearLayout for_member_layout;
    /**
     * 购买商品
     */
//    @ViewInject(R.id.for_order_layout)
//    private LinearLayout for_order_layout;
    @ViewInject(R.id.tv_shopname)
    private TextView tv_shopname;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    String order_type;
    String address_id;
    String shop_name;
    String cart_id;
    String order_id;
    private CommonPopupWindow commonPopupWindow;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.im3)
    private ImageView im3;
    @ViewInject(R.id.im4)
    private ImageView im4;
    @ViewInject(R.id.im5)
    private ImageView im5;
    @ViewInject(R.id.im6)
    private ImageView im6;
    @ViewInject(R.id.im7)
    private ImageView im7;
    @ViewInject(R.id.im8)
    private ImageView im8;
    @ViewInject(R.id.im9)
    private ImageView im9;
    private boolean is_r, is_y, is_b;//红黄蓝
    private CheckBox r;
    private CheckBox y;
    private CheckBox b;
    @ViewInject(R.id.cb_jfzf)
    private CheckBox cb_jfzf;

    String goods_id, num, ordertype, product_id;
    private String type;
    private String group_buy_id;
    @ViewInject(R.id.layout_wx)
    private RelativeLayout layout_wx;
    @ViewInject(R.id.layout_ali)
    private RelativeLayout layout_ali;
    private String inte_id;
    @ViewInject(R.id.layout_yue)
    private RelativeLayout layout_yue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("支付");
//        if (0 == order_type) {
//            for_member_layout.setVisibility(View.VISIBLE);
//            for_order_layout.setVisibility(View.GONE);
//        } else {
//            for_member_layout.setVisibility(View.GONE);
//            for_order_layout.setVisibility(View.VISIBLE);
//        }
//        selectCheckBoxTop(top_type);
//        selectCheckBoxBottom(bottom_type);

    }

    @Override
    @OnClick({R.id.top_lin_layout, R.id.top_cb, R.id.bottom_lin_layout, R.id.bottom_cb, R.id.pay_by_wechat_cb,
            R.id.pay_by_ali_cb, R.id.pay_by_balance_cb, R.id.tv_submit, R.id.cb_jfzf})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.top_lin_layout:
//            case R.id.top_cb:// 传递使者
//                top_type = 0;
//                selectCheckBoxTop(top_type);
//                break;
//            case R.id.bottom_lin_layout:
//            case R.id.bottom_cb:// 传递大使
//                top_type = 1;
//                selectCheckBoxTop(top_type);
//                break;
            case R.id.cb_jfzf:
                bottom_type = 3;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.pay_by_wechat_cb:// 微信
                bottom_type = 0;
                selectCheckBoxBottom(bottom_type);
                showPop(v, 1);
                break;
            case R.id.pay_by_ali_cb:// 支付宝
                bottom_type = 1;
                showPop(v, 2);
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.pay_by_balance_cb:// 余额
                bottom_type = 2;
                if (!type.equals("8")) {
                    showPop(v, 3);
                }
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.tv_submit:
                if (pay_by_wechat_cb.isChecked()) {

                }
                if (pay_by_ali_cb.isChecked()) {
                    if (TextUtils.isEmpty(type) || type.equals("1") || type.equals("5")) {
                        Pay.getAlipayParam(data.get("order_id"), getType(), "4", this);
                    } else if (type.equals("2") || type.equals("3")) {
                        Pay.getAlipayParam(data.get("group_buy_order_id"), getType(), "6", this);
                    } else if (type.equals("6")) {
                        Pay.getAlipayParam(data.get("order_id"), getType(), "5", this);
                    } else if (type.equals("9")) {
                        Pay.getAlipayParam(data.get("order_id"), getType(), "8", this);
                    }
                    showProgressDialog();
                }
                if (pay_by_balance_cb.isChecked()) {
                    if (TextUtils.isEmpty(type) || type.equals("1") || type.equals("5")) {
                        BalancePay.BalancePay(data.get("order_id"), type, getType(), "", this);
                    } else if (type.equals("2") || type.equals("3") || type.equals("4")) {
                        BalancePay.BalancePay(data.get("group_buy_order_id"), "2", getType(), "", this);
                    } else if (type.equals("6")) {
                        BalancePay.BalancePay(data.get("order_id"), "3", getType(), "", this);
                    } else if (type.equals("7")) {
                        if (!order_type.equals("7")) {
                            BalancePay.BalancePay(data.get("order_id"), "6", getType(), num, this);
                        } else {
                            BalancePay.BalancePay(data.get("order_id"), "8", getType(), num, this);
                        }
                    } else if (type.equals("8")) {
                        BalancePay.BalancePay(order_id, "7", getType(), num, this);
                    } else if (type.equals("9")) {
                        BalancePay.BalancePay(order_id, "4", getType(), num, this);
                    }
                    showProgressDialog();
                }
                if (cb_jfzf.isChecked()) {
                    if (TextUtils.isEmpty(type) || type.equals("1") || type.equals("5")) {
                        IntegralPay.integralPay(data.get("order_id"), type, "", "", this);
                    } else if (type.equals("2") || type.equals("3") || type.equals("4")) {
                        IntegralPay.integralPay(data.get("group_buy_order_id"), "2", "", "", this);
                    } else if (type.equals("6")) {
                        IntegralPay.integralPay(data.get("order_id"), "3", "", "", this);
                    } else if (type.equals("7")) {
                        if (!order_type.equals("7")) {
                            IntegralPay.integralPay(data.get("order_id"), "6", "", num, this);
                        } else {
                            IntegralPay.integralPay(data.get("order_id"), "8", "", num, this);
                        }
                    } else if (type.equals("8")) {
                        IntegralPay.integralPay(order_id, "7", "", num, this);
                    } else if (type.equals("9")) {
                        IntegralPay.integralPay(order_id, "4", "", num, this);
                    } else if (type.equals("10")) {
                        IntegralPay.integralPay(order_id, "10", "", num, this);
                    }
                    showProgressDialog();
                }
                break;
        }
    }


//    /**
//     * 传递使者，传递大使选中状态
//     *
//     * @param type 方式
//     */
//    private void selectCheckBoxTop(int type) {
//        top_cb.setChecked(false);
//        bottom_cb.setChecked(false);
//        if (0 == type) {
//            top_cb.setChecked(true);
//        } else {
//            bottom_cb.setChecked(true);
//        }
//    }

    /**
     * 支付方式选择
     *
     * @param type 方式
     */
    private void selectCheckBoxBottom(int type) {
        pay_by_wechat_cb.setChecked(false);
        pay_by_ali_cb.setChecked(false);
        pay_by_balance_cb.setChecked(false);
        cb_jfzf.setChecked(false);
        if (0 == type) {
            pay_by_wechat_cb.setChecked(true);
        } else if (1 == type) {
            pay_by_ali_cb.setChecked(true);
        } else if (2 == type) {
            pay_by_balance_cb.setChecked(true);
        } else if (3 == type) {
            cb_jfzf.setChecked(true);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_pay_for_app;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        goods_id = getString("goods_id");
        num = getString("num");
        type = getString("type");
        //  ordertype = getString("order_type");
        product_id = getString("product_id");
        inte_id = getString("inte_id");
        order_type = getString("order_type");
        address_id = getString("address_id");
        shop_name = getString("shop_name");
        cart_id = getString("cart_id");
        order_id = getString("order_id");
        group_buy_id = getString("group_buy_id");
        tv_shopname.setText(shop_name);
        if (TextUtils.isEmpty(type) || type.equals("1") || type.equals("5")) {
            Order.setOrder(address_id, num, goods_id, product_id, cart_id, order_type, order_id, group_buy_id, this);
        } else if (type.equals("2")) {
            GroupBuyOrder.setOrder(address_id, num, goods_id, product_id, "1", order_id, group_buy_id, this);
        } else if (type.equals("3")) {
            GroupBuyOrder.setOrder(address_id, num, goods_id, product_id, "2", order_id, group_buy_id, this);
        } else if (type.equals("4")) {
            String[] strings = group_buy_id.split("-");
            GroupBuyOrder.setOrder(address_id, num, goods_id, product_id, "3", strings[1], strings[0], this);
        } else if (type.equals("6")) {
            PreOrder.preSetOrder(num, address_id, order_id, group_buy_id, this);
        } else if (type.equals("7")) {
            if (!order_type.equals("7")) {
                IntegralOrder.SetOrder(num, address_id, order_id, group_buy_id, "0", this);
            } else {
                IntegralOrder.SetOrder(num, address_id, order_id, group_buy_id, "1", this);
            }
            layout_wx.setVisibility(View.GONE);
            layout_ali.setVisibility(View.GONE);
        } else if (type.equals("8")) {
            tv_price.setText("¥" + getIntent().getStringExtra("money"));
            pay_by_balance_cb.setText("余额支付（¥" + getIntent().getStringExtra("balance") + ")");
            tv_shopname.setText(getIntent().getStringExtra("merchant_name"));
            layout_wx.setVisibility(View.GONE);
            layout_ali.setVisibility(View.GONE);
            return;
        } else if (type.equals("9")) {
            AuctionOrder.SetOrder(address_id, group_buy_id, "0", "", order_id, this);

        } else if (type.equals("10")) {
            IntegralBuyOrder.SetOrder(group_buy_id, address_id, num, order_id, this);
            layout_ali.setVisibility(View.GONE);
            layout_wx.setVisibility(View.GONE);
            layout_yue.setVisibility(View.GONE);

        }
        showProgressDialog();
    }

    private String getString(String key) {
        return TextUtils.isEmpty(getIntent().getStringExtra(key)) ? "" : getIntent().getStringExtra(key);
    }

    Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        if (requestUrl.contains("SetOrder") || requestUrl.contains("setOrder") || requestUrl.contains("preSetOrder")) {
            if (!type.equals("10")) {
                tv_price.setText("¥" + data.get("order_price"));
            } else {
                tv_price.setText(data.get("order_price") + "积分");
            }
            pay_by_balance_cb.setText("余额支付（¥" + data.get("balance") + ")");
            tv_shopname.setText(data.get("merchant_name"));
            if (data.get("is_integral").equals("1")) {
                cb_jfzf.setVisibility(View.VISIBLE);
            }
            if (type.equals("2") || type.equals("3")) {
                if (TextUtils.isEmpty(group_buy_id)) {
                    group_buy_id = data.get("group_buy_id");
                }
            } else {
                if (TextUtils.isEmpty(order_id)) {
                    order_id = data.get("order_id");
                }
            }
        }


        if (requestUrl.contains("BalancePay"))

        {
            if (type.equals("4")) {
                AppManager.getInstance().killActivity(CreateGroupAty.class);
            }
            showToast("支付成功！");
            finish();
        }
        if (requestUrl.contains("integralPay"))

        {
            if (type.equals("4")) {
                AppManager.getInstance().killActivity(CreateGroupAty.class);
            }
            showToast("支付成功！");
            finish();
        }
        if (requestUrl.contains("getAlipayParam"))

        {
            showProgressDialog();
            AliPay aliPay = new AliPay(data.get("pay_string"), new AliPayCallBack() {
                @Override
                public void onComplete() {
                    if (TextUtils.isEmpty(type) || type.equals("1") || type.equals("5")) {
                        Pay.findPayResult(order_id, "4", PayForAppAty.this);
                    } else if (type.equals("2") || type.equals("3")) {
                        Pay.findPayResult(group_buy_id, "6", PayForAppAty.this);
                    } else if (type.equals("6")) {
                        Pay.findPayResult(order_id, "5", PayForAppAty.this);
                    } else if (type.equals("9")) {
                        Pay.findPayResult(order_id, "8", PayForAppAty.this);
                    }
                }

                @Override
                public void onFailure() {
                    showToast("支付失败！");
                    removeProgressDialog();
                }

                @Override
                public void onProcessing() {

                }
            });
            aliPay.pay();
        }
        if (requestUrl.contains("findPayResult"))

        {
            if (type.equals("4")) {
                AppManager.getInstance().killActivity(CreateGroupAty.class);
            }
            showToast("支付成功！");
            removeProgressDialog();
            finish();
        }
        if (requestUrl.contains("DeleteOrder")) {
            finish();
        }

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        finish();
    }


    public void showPop(View view, final int type) {
        if (data.get("discount").equals("0") && data.get("yellow_discount").equals("0") && data.get("blue_discount").equals("0"))
            return;
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;

        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        LinearLayout layout_cb = (LinearLayout) view.findViewById(R.id.layout_cb);
                        LinearLayout layout_tv = (LinearLayout) view.findViewById(R.id.layout_tv);
                        layout_cb.setVisibility(View.VISIBLE);
                        layout_tv.setVisibility(View.GONE);
                        r = (CheckBox) view.findViewById(R.id.cb_1);
                        y = (CheckBox) view.findViewById(R.id.cb_2);
                        b = (CheckBox) view.findViewById(R.id.cb_3);
                        r.setText(data.get("red_desc"));
                        y.setText(data.get("" +
                                "yellow_desc"));
                        b.setText(data.get("blue_desc"));
                        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
                        r.setVisibility(data.get("discount").equals("1") ? View.VISIBLE : View.GONE);
                        y.setVisibility(data.get("yellow_discount").equals("1") ? View.VISIBLE : View.GONE);
                        b.setVisibility(data.get("blue_discount").equals("1") ? View.VISIBLE : View.GONE);
                        r.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setCheck(1);
                            }
                        });
                        y.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setCheck(2);
                            }
                        });
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setCheck(3);
                            }
                        });


                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                setImage(type, r.isChecked(), y.isChecked(), b.isChecked());
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void setCheck(int s) {
        r.setChecked(false);
        y.setChecked(false);
        b.setChecked(false);
        switch (s) {
            case 1:
                r.setChecked(true);
                break;
            case 2:
                y.setChecked(true);
                break;
            case 3:
                b.setChecked(true);
                break;
        }
    }

    private void setImage(int from, boolean is_r, boolean is_y, boolean is_b) {
        this.is_r = is_r;
        this.is_y = is_y;
        this.is_b = is_b;
        switch (from) {
            case 1:
                im1.setVisibility(is_r ? View.VISIBLE : View.GONE);
                im2.setVisibility(is_y ? View.VISIBLE : View.GONE);
                im3.setVisibility(is_b ? View.VISIBLE : View.GONE);
                im4.setVisibility(View.GONE);
                im5.setVisibility(View.GONE);
                im6.setVisibility(View.GONE);
                im7.setVisibility(View.GONE);
                im8.setVisibility(View.GONE);
                im9.setVisibility(View.GONE);
                break;
            case 2:
                im1.setVisibility(View.GONE);
                im2.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
                im4.setVisibility(is_r ? View.VISIBLE : View.GONE);
                im5.setVisibility(is_y ? View.VISIBLE : View.GONE);
                im6.setVisibility(is_b ? View.VISIBLE : View.GONE);
                im7.setVisibility(View.GONE);
                im8.setVisibility(View.GONE);
                im9.setVisibility(View.GONE);
                break;
            case 3:
                im1.setVisibility(View.GONE);
                im2.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
                im4.setVisibility(View.GONE);
                im5.setVisibility(View.GONE);
                im6.setVisibility(View.GONE);
                im7.setVisibility(is_r ? View.VISIBLE : View.GONE);
                im8.setVisibility(is_y ? View.VISIBLE : View.GONE);
                im9.setVisibility(is_b ? View.VISIBLE : View.GONE);
                break;

        }
    }

    private String getType() {
        StringBuffer sb = new StringBuffer();
        if (is_r && is_y && is_b) {
            return "1,2,3";
        }
        if (is_r) {
            sb.append("1,");
        }
        if (is_y) {
            sb.append("2,");
        }
        if (is_b) {
            sb.append("3");
        }
        if (TextUtils.isEmpty(sb.toString())) {
            return "0";
        }
        return sb.toString();
    }

    public void beBack(View view) {
        if (type.equals("7")) {
            new AlertDialog(this).builder().setTitle("警告").setMsg("是否取消支付？").setNegativeButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntegralOrder.DeleteOrder(order_id, PayForAppAty.this);
                    showProgressDialog();
                }
            }).setPositiveButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();

        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && type.equals("7")) {
            new AlertDialog(this).builder().setTitle("警告").setMsg("是否取消支付？").setNegativeButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntegralOrder.DeleteOrder(order_id, PayForAppAty.this);
                    showProgressDialog();
                }
            }).setPositiveButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();


            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
