package com.txd.hzj.wjlp.new_wjyp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.txd.hzj.wjlp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/11.
 */

public class aty_invoce2BaseAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<Map<String, String>> alist = new ArrayList<>();
    public  aty_invoce2BaseAdapter(List<Map<String, String>> list,Context context){
        this.alist = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if(convertView==null){
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_invoice2,null);
            vh.tv = convertView.findViewById(R.id.tv1);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(alist.get(position).get("invoice_type"));

        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
