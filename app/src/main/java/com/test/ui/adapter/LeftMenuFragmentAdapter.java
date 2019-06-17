package com.test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 简言 on 2019/2/6.
 * 努力吧 ！ 少年 ！
 */

public class LeftMenuFragmentAdapter extends BaseAdapter {

    private List<String> nameList;


    private Context context;

    private int[] icons = {R.drawable.ic_today_black_24dp, R.drawable.ic_music_video_black_24dp,
            R.drawable.ic_photo_album_black_24dp, R.drawable.ic_settings_applications_black_24dp};

    public LeftMenuFragmentAdapter(Context context) {

        nameList = new ArrayList<>();
        nameList.add("我的日记");
        nameList.add("我的音乐");
        nameList.add("我的相册");
        nameList.add("我的设置");
        this.context = context;
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.left_menu_item, parent, false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取控件
        holder.iconIv = convertView.findViewById(R.id.menu_item_icon_iv);
        holder.nameTv = convertView.findViewById(R.id.menu_item_name_tv);

        holder.iconIv.setImageResource(icons[position]);
        holder.nameTv.setText(nameList.get(position));
        //绑定数据

        return convertView;
    }

    class ViewHolder {

        private ImageView iconIv;
        private TextView nameTv;
    }
}
