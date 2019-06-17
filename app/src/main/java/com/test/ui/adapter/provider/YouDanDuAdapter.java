package com.test.ui.adapter.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.R;
import com.test.model.result.dandu.Item;
import com.test.utils.GlideUtils;

import java.util.List;

/**
 * Created by 简言 on 2019/2/14.
 * 努力吧 ！ 少年 ！
 */
public class YouDanDuAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Item> items;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public YouDanDuAdapter(Context context, List<Item> items){

        this.mContext = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_art, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myHolder = (MyViewHolder) holder;
        GlideUtils.load(mContext, items.get(position).getThumbnail(), myHolder.iconIv);
        myHolder.authorTv.setText(items.get(position).getAuthor());
        myHolder.titleTv.setText(items.get(position).getTitle());

        myHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(myHolder, position);
            }
        });

        myHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                onItemLongClickListener.onLongClick(myHolder, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout relativeLayout;
        private ImageView iconIv;
        private TextView titleTv;
        private TextView authorTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            iconIv = itemView.findViewById(R.id.image_iv);
            titleTv = itemView.findViewById(R.id.title_tv);
            authorTv = itemView.findViewById(R.id.author_tv);
            relativeLayout = itemView.findViewById(R.id.type_container);
        }
    }

    public interface OnItemClickListener{

        void onClick(MyViewHolder holder, int position);
    }

    public interface OnItemLongClickListener{

        void onLongClick(MyViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){

        this.onItemLongClickListener = longClickListener;
    }

    public String getLastItemId() {
        if (items.size() == 0) {
            return "0";
        }
        Item item = items.get(items.size()-1);
        return item.getId();
    }

    public String getLastItemCreateTime() {
        if (items.size() == 0) {
            return "0";
        }
        Item item = items.get(items.size()-1);
        return item.getCreate_time();
    }
}
