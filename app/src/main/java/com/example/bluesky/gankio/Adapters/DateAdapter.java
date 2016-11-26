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
 * Created by localhost on 2016/11/15.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {
    List<AIWDate.ResultsBean> list;
    private static final String TAG = "DateAdapter";
    Context context;
    private ForTool mt = new ForTool();
    private LoadView mLoadview;
    private OnitemClick mClick;
    public void setOnitemClick(OnitemClick on){
        mClick=on;
    }
    public DateAdapter(Context m, LoadView t) {
        context = m;
        mLoadview=t;
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
        holder.Iv_InfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getImages()==null)
                    Toast.makeText(context,"图片被外星人劫走了",Toast.LENGTH_SHORT).show();
               else mLoadview.getImageurl(list.get(position).getImages().get(0));

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

    public void addList(List<AIWDate.ResultsBean> l) {
        list = l;
        Log.i(TAG, "addList: " + list.get(2).getDesc());
        notifyDataSetChanged();
    }
}
