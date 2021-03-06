package com.txd.hzj.wjlp.login;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.OrderInfoUtil2_0;
import com.ants.theantsgo.util.FileUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mob.tools.utils.UIHandler;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 7:10
 * 描述：登录，注册(50-1 登录，50-5 注册)
 */
public class LoginAty extends BaseAty implements Handler.Callback, PlatformActionListener, AliPay.OnAuthInterface {
    /**
     * 登录
     */
    @ViewInject(R.id.to_login_tv)
    private TextView to_login_tv;
    /**
     * 注册
     */
    @ViewInject(R.id.to_register_tv)
    private TextView to_register_tv;

    /**
     * 手机号
     */
    @ViewInject(R.id.phone_ev)
    private EditText phone_ev;
    /**
     * 密码
     */
    @ViewInject(R.id.pwd_ev)
    private EditText pwd_ev;

    /**
     * 登录，注册第一步
     */
    @ViewInject(R.id.to_login_or_register_tv)
    private TextView to_login_or_register_tv;

    /**
     * 三方登录提示
     */
    @ViewInject(R.id.use_trilateral_lin_layout)
    private LinearLayout use_trilateral_lin_layout;
    private int type = 0;

    /**
     * 三方登录方式
     * 1.微信
     * 2.微博
     * 3.QQ
     * 7支付宝
     */
    private String loginType = "";

    @ViewInject(R.id.for_third_layout)
    private LinearLayout for_third_layout;

    @ViewInject(R.id.share_to_alipay)
    private LinearLayout share_to_alipay;

    /**
     * 服务条款
     */
    @ViewInject(R.id.terms_of_service_tv)
    private TextView terms_of_service_tv;
    private Bundle bundle;

    private RegisterPst registerPst;
    private String phone = "";
    /**
     * 跳转方式
     * 0.推出跳转到登录页，登陆成功，跳转到首页
     * 1.跳转到登录页，直接返回
     */
    private int skip_type = 0;
    private String password = "";
    /**
     * 三方登录openId
     */
    private String openid = "";
    /**
     * 昵称
     */
    private String nick = "";
    /**
     * 头像路径
     */
    private String head_pic = "";


    // 三方登陆
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    /**
     * 邀请码
     */
    private String invite_code = "";

    // 注册登录的时候不显示微信朋友圈和QQ空间
    @ViewInject(R.id.share_to_WechatMoments)
    private LinearLayout share_to_WechatMoments;
    @ViewInject(R.id.share_to_QZone)
    private LinearLayout share_to_QZone;

    @ViewInject(R.id.login_go_back_tv)
    private ImageView login_go_back_tv;
    private String registrationID;

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016052801453164";
//    public static final String APPID = "2016091900544420";


    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088711981544066";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "11";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIICXAIBAAKBgQCcImOej6Yf0SIH/b1ZpILORIPqB9sEhuiQ3pBFkcAMluRvJL9Ch6ZqRzrisRB/2XLY3ctt4qjQzmOaSG51CvgzXExkLXboeO7mNK+fqnIH7hq4na2Vv26J/OB4vo5DyNbw2NBL6+iAxZm+axHl70wIIgcvAissttVSNdylOrt8KQIDAQABAoGAfGfeTooRCQr+/bUNOa8eVrI7Fa+asLm59yyPcg9XIfDdJT33c1BYphgJcHU7O1OM8gWPwQe4EWBR9q297V6HMk6xZnayJbI2NpdlINVPU7OkvEs2IXeNWiYYzrS2gKVGyH/BGfxwyXESa2vWMyEiQy4PY63ieIJEWUrtCXXP53ECQQDLt0TcBD1rnXKXq4cn7VgkwcAkpPIHxjiqqTy4rxlhYDYlv5hn5wrVUaLq3RmIfbtHespU4ZGQPF2yYrz5OryFAkEAxDTfRbfjeOrKcTRQnvOdKEzx+3N1zENnEO4o6SR5dPkINszm4rPAuxOGxQYFBeSH/Dhs/DTDjtGmJFkLW6aUVQJAXk/xpD6eROU2uTsjLnv2c8Xzc8OdfbXqZDBHuWTvRiKpzt4d6/SOEmZiG4PTH1q+SoBxjcyEfJdF9aE6xdPIlQJBAI8q+WR5Cw33XSL/hniF0L5Qbx4JIQKciD9NWSLOD6Yv9TNvjmYLve2EEQoBG3cyS5vaXIQnyC6VggLxypzkz0UCQBEjsdfjJOCnLgKDqt6OrRnd011xBiClAEiMj8zJN5Dkkd/FxD1TG9oigiJY8ZP2+XBN8pf7Mdoq+3OP5wPuQZs=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.longin_title_layout);
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        // 注册登录的时候不显示微信朋友圈和QQ空间
        share_to_WechatMoments.setVisibility(View.GONE);
        share_to_QZone.setVisibility(View.GONE);

        if (DemoApplication.LOGIN_ACTIVITY_IS_RUN) {
            finish();
        }

        toChangeTextViewStatus(0);
        for_third_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_color));

        ChangeTextViewStyle.getInstance().forTextColor(this, terms_of_service_tv, "继续表示已经阅读并同意《服务条款》", 11, ContextCompat.getColor(this, R.color.theme_color));
    }

    @Override
    protected void onStart() {
        super.onStart();
        DemoApplication.LOGIN_ACTIVITY_IS_RUN = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DemoApplication.LOGIN_ACTIVITY_IS_RUN = false;
    }

    @Override
    @OnClick({R.id.to_login_tv, R.id.to_register_tv, R.id.forget_pwd_tv, R.id.to_login_or_register_tv,
            R.id.share_to_wachar, R.id.share_to_qq, R.id.share_to_sine, R.id.share_to_alipay, R.id.terms_of_service_tv, R.id.login_go_back_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_login_tv:// 登录(标题栏)
                type = 0;
                toChangeTextViewStatus(0);
                pwd_ev.setVisibility(View.VISIBLE);
                to_login_or_register_tv.setText("登录");
                use_trilateral_lin_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.to_register_tv:// 注册(标题栏)
                type = 1;
                toChangeTextViewStatus(1);
                pwd_ev.setVisibility(View.GONE);
                to_login_or_register_tv.setText("下一步");
                use_trilateral_lin_layout.setVisibility(View.GONE);
                break;
            case R.id.forget_pwd_tv:// 忘记密码
                startActivity(FindPwgBackHzjAty.class, null);
                break;
            case R.id.to_login_or_register_tv:// 登录，注册
                phone = phone_ev.getText().toString();
                if (0 == type) {//登录
                    password = pwd_ev.getText().toString();
                    registerPst.login(phone, password);
                } else {// 注册下一步
                    registerPst.checkPhone(phone);
                }
                try {
                    hideKeyBoard();
                } catch (Exception e) {
                    L.e("隐藏软键盘报空指针异常，可能是已经隐藏掉了。");
                }
                break;
            case R.id.share_to_wachar: // 微信
                loginType = "1";
                if (!LoginAty.this.isDestroyed()) {
                    showDialog();
                }
                authorize(new Wechat(this));
                break;
            case R.id.share_to_qq:// QQ
                loginType = "3";
                if (!LoginAty.this.isDestroyed()) {
                    showDialog();
                }
                Platform qq = ShareSDK.getPlatform(QZone.NAME);
                authorize(qq);
                break;
            case R.id.share_to_sine:// 新浪微博
                loginType = "2";
                if (!LoginAty.this.isDestroyed()) {
                    showDialog();
                }
                Platform wb = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(wb);
                break;
            //支付宝
            case R.id.share_to_alipay:
                loginType = "7";
                if (!LoginAty.this.isDestroyed()) {
                    showDialog();
                }
                String alipay_auth_code = PreferencesUtils.getString(LoginAty.this, "alipay_auth_code");
                if (TextUtils.isEmpty(alipay_auth_code)) {
                    boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                    Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
                    String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

                    String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                    String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
                    final String authInfo = info + "&" + sign;
                    AliPay aliPay = new AliPay(authInfo, this);
                    aliPay.setMessageWhat(AliPay.SDK_AUTH_FLAG);
                    aliPay.pay();
                } else {
                    registerPst.otherLogin(alipay_auth_code, loginType, null, "");
                }


                break;
            case R.id.terms_of_service_tv:// 服务条款
                bundle = new Bundle();
                bundle.putInt("from", 3);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
            case R.id.login_go_back_tv: // 点击X按钮退出登录界面
                if (MainAty.isExit) {
                    startActivity(MainAty.class, null);
                }
                finish();
                break;
        }
    }

    private void toChangeTextViewStatus(int i) {
        to_login_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        to_login_tv.setBackgroundResource(R.drawable.shape_login_tv_unselect);
        to_register_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        to_register_tv.setBackgroundResource(R.drawable.shape_register_tv_unselect);

        if (0 == i) {
            to_login_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            to_login_tv.setBackgroundResource(R.drawable.shape_login_tv_selected);

            String phone = PreferencesUtils.getString(this, "phone");
            if (phone != null){
                phone_ev.setText(phone);
                phone_ev.setSelection(phone.length());
            }else {
                phone_ev.setText("");
            }

            pwd_ev.setText("");
        } else {
            to_register_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            to_register_tv.setBackgroundResource(R.drawable.shape_register_tv_select);

            phone_ev.setText("");
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_login;
    }

    @Override
    protected void initialized() {
        skip_type = getIntent().getIntExtra("type", 1);
        registerPst = new RegisterPst(this);
        share_to_alipay.setVisibility(View.VISIBLE);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        if (requestUrl.contains("registerOne")) {// 注册第一步
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                if (code.equals("0")) {
                    showToast(message);
                } else {
                    bundle = new Bundle();
                    bundle.putString("phone", phone);
                    startActivity(RegisterGetCodeAty.class, bundle);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return;
        }
        if (requestUrl.contains("Register/login")) {
//            showToast("登录成功");
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            application.setUserInfo(data);
            Config.setLoginState(true);
            if (data.containsKey("invite_code")) {
                PreferencesUtils.putString(this, "invite_code", data.get("invite_code"));
            }
            PreferencesUtils.putString(this, "phone", phone);
            PreferencesUtils.putString(this, "token", data.get("token"));
            PreferencesUtils.putBoolean(this, Config.PREF_KEY_LOGIN_STATE, true); // 将登录状态设置为未登录
            // 友盟统计
            MobclickAgent.onProfileSignIn(data.get("user_id"));

            // 极光设置Tag或者别名
            JpushSetTagAndAlias.getInstance().setAlias(getApplicationContext());
            JpushSetTagAndAlias.getInstance().setTag(getApplicationContext());

            registrationID = DemoApplication.registrationID;
            if (!TextUtils.isEmpty(registrationID) ) {
                Log.e("TAG", "registrationID: " + registrationID);
                User.postRegistrationID(registrationID, this);
            }

            // 环信登录
            registerPst.toLogin(data.get("easemob_account"), data.get("easemob_pwd"));
//            if (0 == skip_type) {
//                AppManager.getInstance().killAllActivity();
//                startActivity(MainAty.class, null);
//            }
            if (MainAty.isExit) {
                startActivity(MainAty.class, null);
            }

            String noticeDetailsUrl = PreferencesUtils.getString(this, "NoticeDetailsUrl");
            if (noticeDetailsUrl != null && !noticeDetailsUrl.isEmpty()) {
                // 如果有值，则移除该值，并且将获取的值传入广告展示界面
                AppManager.getInstance().killActivity(NoticeDetailsAty.class);
                PreferencesUtils.remove(this, "NoticeDetailsUrl");
                bundle = new Bundle();
                bundle.putString("url", noticeDetailsUrl);
                startActivity(WebViewAty.class, bundle);
//                PreferencesUtils.putString(LoginAty.this, "NoticeDetailsUrl", url);
            }

            finish();
            return;
        }
        if (requestUrl.contains("Register/otherLogin")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (data.get("is_bind_phone").equals("0")) {
                showRightTip("请绑定手机号");
                bundle = new Bundle();
                bundle.putString("invite_code", invite_code);
                bundle.putInt("skip_type", skip_type);
                bundle.putString("bind_id", data.get("bind_id"));
                startActivity(BindAccountAty.class, bundle);
                finish();
            } else {
                showRightTip("登录成功");
                startActivity(MainAty.class, null);
                AppManager.getInstance().killOtherActivity();
//                boolean existMainActivity = isExistMainActivity(MainAty.class);
//                if (existMainActivity) {
//                    startActivity(MainAty.class, null);
//                }
                application.setUserInfo(data);
                Config.setLoginState(true);
                if (data.containsKey("invite_code")) {
                    PreferencesUtils.putString(this, "invite_code", data.get("invite_code"));
                }
                PreferencesUtils.putString(this, "token", data.get("token"));
                // 友盟统计
                MobclickAgent.onProfileSignIn(data.get("user_id"));
                // 极光设置Tag或者别名
                JpushSetTagAndAlias.getInstance().setAlias(getApplicationContext());
                JpushSetTagAndAlias.getInstance().setTag(getApplicationContext());
                registrationID = DemoApplication.registrationID;
                if (!TextUtils.isEmpty(registrationID)) {
                    Log.e("TAG", "registrationID: " + registrationID);
                    User.postRegistrationID(registrationID, this);
                }
                // 环信登录
                registerPst.toLogin(data.get("easemob_account"), data.get("easemob_pwd"));
//                if (0 == skip_type) {
//                    startActivity(MainAty.class, null);
//                    AppManager.getInstance().killAllActivity();
//                }
                finish();
            }
        }

        if (requestUrl.contains("add_jpush_rid")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (map.containsKey("code")) {
                Log.e("TAG", "add_jpush_rid=====code:" + map.get("code"));
                if ("0".equals(map.containsKey("code"))) {
                    if (!TextUtils.isEmpty(registrationID)) {
                        User.postRegistrationID(registrationID, this);
                    }
                }
            }
            if (map.containsKey("message")) {
                Log.e("TAG", "add_jpush_rid=====message:" + map.get("message"));
            }
        }

    }

    private boolean isExistMainActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(20);  //获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    private void authorize(Platform plat) {
        if (null != plat) {
            // 判断指定平台是否已经完成授权
            if (plat.isAuthValid()) {
                if (loginType.equals("1")) {
                    //TODO  授权三方登录的时候需要传uid
                    openid = plat.getDb().get("unionid");
                } else {
                    openid = plat.getDb().getUserId();
                }
                nick = plat.getDb().getUserName();
                if (openid != null && nick != null) {
                    head_pic = plat.getDb().getUserIcon();
                    getHeadPicAndLogin(head_pic);
                    return;
                }
                // 三方登陆
                return;
            }
            // 授权监听

            plat.setPlatformActionListener(this);
            // true不使用SSO授权，false使用SSO授权，(即true不使用客户端登录，false有客户端则使用客户端登录，没有则使用web网页登录)
            plat.SSOSetting(false);
            // 获取用户资料
            plat.showUser(null);
        }

    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (Platform.ACTION_USER_INFOR == i) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            if (loginType.equals("1")) {
                openid = platform.getDb().get("unionid");
            } else {
                openid = platform.getDb().getUserId();
            }
            nick = platform.getDb().getUserName();
            head_pic = platform.getDb().getUserIcon();
            getHeadPicAndLogin(head_pic);
            // 三方登陆
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        removeDialog();
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        removeDialog();
    }

    /**
     * 获取头像并登陆
     *
     * @param url 图片路径
     */
    private void getHeadPicAndLogin(String url) {
        HttpUtils utils = new HttpUtils();
        String path1 = Environment.getExternalStorageDirectory() + "/Txunda/img_head/";
        final String path2 = path1 + "head.png";
        FileUtils.makeFolders(path1);
        FileUtils.makeFolders(path2);
        utils.download(url, path2
                , new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        File head = new File(path2);
                        registerPst.otherLogin(openid, loginType, head, nick);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.e("login", String.valueOf(error));
                    }
                });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                String text = getString(com.ants.theantsgo.R.string.userid_found);
                showRightTip(text);
            }
            break;
            case MSG_LOGIN: {
                String text = getString(com.ants.theantsgo.R.string.logining, msg.obj);
                showRightTip(text);
            }
            break;
            case MSG_AUTH_CANCEL: {
                String text = getString(com.ants.theantsgo.R.string.auth_cancel);
                showErrorTip(text);
            }
            break;
            case MSG_AUTH_ERROR: {
                String text = getString(com.ants.theantsgo.R.string.auth_error);
                showErrorTip(text);
            }
            break;
            case MSG_AUTH_COMPLETE: {
                String text = getString(com.ants.theantsgo.R.string.auth_complete);
                showRightTip(text);
            }
            break;
        }
        return false;
    }

    @Override
    public void onSuccess(String auth_code) {
        PreferencesUtils.putString(LoginAty.this, "alipay_auth_code", auth_code);
        registerPst.otherLogin(auth_code, loginType, null, "");
    }

    @Override
    public void onFailure() {
        removeDialog();
    }

}
