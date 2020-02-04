package com.banketree.refresh.testactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banketree.pullrefreshlayout.PRLCommonUtils;
import com.banketree.pullrefreshlayout.PullRefreshLayout;
import com.banketree.refresh.R;
import com.banketree.refresh.widget.house.StoreHouseHeader;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ScrollingActivity extends BaseActivity {
    private static final String TAG = "NestedActivity";
    private PullRefreshLayout refreshLayout;
    private AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        initRecyclerView();
        initRefreshLayout();

        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        }, 150);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<SimpleItem> datas = new ArrayList<>();
        datas.add(new SimpleItem(R.drawable.img1, "夏目友人帐"));
        datas.add(new SimpleItem(R.drawable.img2, "夏目友人帐"));
        datas.add(new SimpleItem(R.drawable.img3, "夏目友人帐"));
        datas.add(new SimpleItem(R.drawable.img4, "夏目友人帐"));
        datas.add(new SimpleItem(R.drawable.img5, "夏目友人帐"));
        datas.add(new SimpleItem(R.drawable.img6, "夏目友人帐"));
        SimpleAdapter adapter = new SimpleAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initRefreshLayout() {
        this.refreshLayout = findViewById(R.id.refreshLayout);
        findViewById(R.id.container).setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
        StoreHouseHeader header = new StoreHouseHeader(getBaseContext());
        header.setPadding(0, PRLCommonUtils.INSTANCE.dipToPx(getApplicationContext(), 20), 0, PRLCommonUtils.INSTANCE.dipToPx(getApplicationContext(), 20));
        header.initWithString("PullRefreshLayout");
        refreshLayout.setHeaderView(header);

        this.refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "refreshLayout onRefresh: ");
                ScrollingActivity.this.refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScrollingActivity.this.refreshLayout.refreshComplete();
                    }
                }, 3000);
            }
        });

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                appBarLayout.setTag(verticalOffset);
            }
        });

        refreshLayout.setOnTargetScrollCheckListener(new PullRefreshLayout.OnTargetScrollCheckListener() {
            @Override
            public boolean onScrollUpAbleCheck() {
                int appbarOffset = ((appBar.getTag() instanceof Integer)) ? (int) appBar.getTag() : 0;
                return PRLCommonUtils.INSTANCE.canChildScrollUp(refreshLayout.getTargetView()) || appbarOffset != 0;
            }

            @Override
            public boolean onScrollDownAbleCheck() {
                return PRLCommonUtils.INSTANCE.canChildScrollDown(refreshLayout.getTargetView());
            }
        });
    }


}
