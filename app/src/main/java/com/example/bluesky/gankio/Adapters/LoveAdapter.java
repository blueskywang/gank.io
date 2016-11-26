package com.example.bluesky.gankio.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluesky.gankio.DateBean.AIWDate;
import com.example.bluesky.gankio.DateBean.LoveDate;
import com.example.bluesky.gankio.R;
import com.example.bluesky.gankio.View.LoadView;
import com.example.bluesky.gankio.util.ForTool;

import java.util.List;

/**
 *
 * adapter 重复了
 *只是一两个并看不出来
 * 一旦多了就显得臃肿
 *万能的适配正在路上
 * Created by localhost on 2016/11/15.
 */

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.MyViewHolder> {
    List<LoveDate> list;
    private static final String TAG = "LoveAdapter";
    Context context;
    private ForTool mt = new ForTool();
    private OnitemClick mClick;
    public void setOnitemClick(OnitemClick on){
        mClick=on;
    }
    public LoveAdapter(Context m) {
        context = m;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.Tv_writer.setText(list.get(position).getWho());
        holder.Tv_Info.setText(list.get(position).getDesc().trim());
        holder.Cd_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.OnforitemClick(position);
            }
        });
        holder.Cd_cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClick.OnforitemLongClik(position);
                return true;
            }
        });
        holder.Iv_InfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getImages()==null)
                    Toast.makeText(context,"图片被外星人劫走了",Toast.LENGTH_SHORT).show();
            }
        });
        mt.loadImage(list.get(position).getImages(), holder.Iv_InfoImage, context);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Tv_Info;
        TextView Tv_writer;
        ImageView Iv_InfoImage;
        CardView Cd_cd;
        public MyViewHolder(View view) {
            super(view);
            Tv_Info = (TextView) view.findViewById(R.id.Tv_Info);
            Tv_writer = (TextView) view.findViewById(R.id.writer);
            Iv_InfoImage = (ImageView) view.findViewById(R.id.Iv_InfoImage);
            Cd_cd=(CardView)view.findViewById(R.id.card);
        }

    }

    public void addList(List<LoveDate> l) {
        list=l;
        notifyDataSetChanged();
    }
}
