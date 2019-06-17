package com.test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.R;
import com.test.model.result.DictionaryAssocitiveResult;




/**
 * Created by 简言 on 2019/2/9.
 * 努力吧 ！ 少年 ！
 */

public class AccositiveRecyclerAdapter extends RecyclerView.Adapter {


    private Context context;
    private DictionaryAssocitiveResult datas;

    public AccositiveRecyclerAdapter(Context context, DictionaryAssocitiveResult datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.accositive_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.itemEntry.setText(datas.getData().getEntries().get(position).getEntry());
        myHolder.itemExplain.setText(datas.getData().getEntries().get(position).getExplain());
    }

    @Override
    public int getItemCount() {
        return datas.getData().getEntries().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemEntry;
        TextView itemExplain;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemEntry = itemView.findViewById(R.id.item_entry);
            itemExplain = itemView.findViewById(R.id.item_explain);
        }
    }
}
