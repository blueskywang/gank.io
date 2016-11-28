package com.example.bluesky.gankio.View.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bluesky.gankio.Adapters.DateAdapter;
import com.example.bluesky.gankio.Adapters.OnitemClick;
import com.example.bluesky.gankio.DateBean.AIWDate;
import com.example.bluesky.gankio.DateBean.LoveDate;
import com.example.bluesky.gankio.Presenter.MainPresenter;
import com.example.bluesky.gankio.R;
import com.example.bluesky.gankio.Model.LoadView;
import com.example.bluesky.gankio.View.WebActivity;
import com.example.bluesky.gankio.newView.floatingmenu;
import com.example.bluesky.gankio.util.ForTool;
import com.example.bluesky.gankio.util.net;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by localhost on 2016/11/15.
 */

public class MyFraments extends Fragment implements LoadView, OnitemClick {
    private static final String TAG = "MyFraments";
    public static final String FLAG="no Image here";
    @BindView(R.id.id_recyclerview)
    RecyclerView idRecyclerview;
    @BindView(R.id.ershow)
    RelativeLayout IvErshow;
    private MainPresenter mainPresenter;
    private Dialog mDialog;
    private boolean isfirst = true;
    private DateAdapter arr_adapter;
    private List<AIWDate.ResultsBean> InfoList;
    private RecyclerView list;
    private ForTool mtool = new ForTool();
    private int kind;

    public static MyFraments MyFramentsgetInstance(int n) {
        MyFraments m = new MyFraments();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", n);
        m.setArguments(bundle);
        return m;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kind = getArguments().getInt("kind");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.itemframgnt, container, false);
        ButterKnife.bind(this, view);
        mainPresenter = new MainPresenter(this, getContext(), kind);
        IvErshow.setVisibility(View.GONE);
        IvErshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.refreshDate();
            }
        });
        idRecyclerview.setHasFixedSize(true);
        idRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //必须设置 否则不显示
        idRecyclerview.setItemAnimator(new DefaultItemAnimator());
        arr_adapter = new DateAdapter(getContext(), this);
        arr_adapter.setOnitemClick(this);
        idRecyclerview.setAdapter(arr_adapter);

        //新建一个数组适配器ArrayAdapter绑定数据，参数(当前的Activity，布局文件，数据源)
        return view;
    }

    @Override
    public void loadviewshow() {
        if (isfirst) {
            mDialog = mtool.ShowProgress(getContext());
            mDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        //延迟操作
        if (isfirst) {
            new Handler().postDelayed(new Runnable() {

                public void run() {
                    mDialog.dismiss();
                    arr_adapter.addList(InfoList);
                    isfirst = false;
                    //execute the task

                }

            }, 1000);
        } else {
            arr_adapter.addList(InfoList);
        }

    }

    @Override
    public void showFailedError(String g) {
        if (!net.isNetworkReachable(getContext())) {
            mtool.ShowShackBar(idRecyclerview, getContext());
        }
        IvErshow.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshlist(AIWDate m) {
        InfoList = m.getResults();
        IvErshow.setVisibility(View.GONE);

    }

    @Override
    public void LoadMore(AIWDate m) {
        InfoList.addAll(m.getResults());
        arr_adapter.notifyDataSetChanged();
    }

    @Override
    public void getImageurl(String url) {
        mtool.ShowDialog(getContext(), url);
    }

    @Override
    public void OnforitemClick(int postion) {
        AIWDate.ResultsBean m=InfoList.get(postion);
        Intent intent=new Intent();
        LoveDate loveDate=new LoveDate();
        loveDate.setDesc(m.getDesc());
        if(m.getImages()==null){
            loveDate.setImages(FLAG);
        }else {
            loveDate.setImages(m.getImages().get(0));
        }
        loveDate.setType(m.getType());
        loveDate.setWho(m.getWho());
        loveDate.setUrl(m.getUrl());
        intent.putExtra(WebActivity.Tag,loveDate);
        intent.setClass(getContext(), WebActivity.class);
        startActivity(intent);
        //获取activity中的控件
        floatingmenu menu=(floatingmenu)getActivity().findViewById(R.id.menu);
        menu.closeAll();
    }

    @Override
    public void OnforitemLongClik(int postion) {

    }
}
