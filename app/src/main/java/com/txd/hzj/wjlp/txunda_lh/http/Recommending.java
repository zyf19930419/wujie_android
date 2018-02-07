package com.txd.hzj.wjlp.txunda_lh.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;

/**
 * by Txunda_LH on 2018/2/2.
 */

public class Recommending {

    private static String url = Config.BASE_URL + "Recommending/";

    public static void businessType(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "businessType", new RequestParams(), baseView);
    }

    public static void addBusiness(String mechant_name, String user_name, String user_position, String user_phone,
                                   String city, String street, String desc, File license, File facade,
                                   File identity, String type, File apply, File logo, String rec_type_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("mechant_name", mechant_name);
        params.addBodyParameter("user_name", user_name);
        params.addBodyParameter("user_position", user_position);
        params.addBodyParameter("user_phone", user_phone);
        params.addBodyParameter("city", city);
        params.addBodyParameter("street", street);
        params.addBodyParameter("desc", desc);
        params.addBodyParameter("type", type);
        params.addBodyParameter("license", license);
        params.addBodyParameter("facade", facade);
        params.addBodyParameter("identity", identity);
        if (type.equals("2")) {
            params.addBodyParameter("apply", apply);
        }
        params.addBodyParameter("logo", logo);
        params.addBodyParameter("rec_type_id", rec_type_id);
        apiTool2.postApi(url + "addBusiness", params, baseView);

    }

    public static void businessList(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "businessList", new RequestParams(), baseView);
    }

    public static void businessInfo(String recommending_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("recommending_id", recommending_id);
        apiTool2.postApi(url + "businessInfo", params, baseView);

    }
    public static void advertImg(BaseView view){
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "advertImg", new RequestParams(), view);
    }

}
