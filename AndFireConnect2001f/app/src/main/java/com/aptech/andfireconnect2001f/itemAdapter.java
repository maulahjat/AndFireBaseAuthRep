package com.aptech.andfireconnect2001f;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class itemAdapter extends BaseAdapter {
    Context context;
    List<itemView> list;

    public itemAdapter(Context context, List<itemView> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public itemView getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        TextView cid = convertView.findViewById(R.id.lstID);
        TextView cname = convertView.findViewById(R.id.lstName);
        TextView cquan = convertView.findViewById(R.id.lstQuantity);
        TextView crate = convertView.findViewById(R.id.lstRate);

        cid.setText(String.valueOf(getItem(position).getID()));
        cname.setText(String.valueOf(getItem(position).getItemName()));
        cquan.setText(String.valueOf(getItem(position).getQuantity()));
        crate.setText(String.valueOf(getItem(position).getRate()));
        return convertView;
    }
}
