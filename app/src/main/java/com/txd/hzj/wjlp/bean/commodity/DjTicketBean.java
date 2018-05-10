package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:52
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class DjTicketBean implements Serializable{
    /**
     * discount_desc : 本产品最多可以使用30%红券抵扣现金
     * type : 1
     */

    private String discount_desc;
    private String type;

    public String getDiscount_desc() {
        return discount_desc;
    }

    public void setDiscount_desc(String discount_desc) {
        this.discount_desc = discount_desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
