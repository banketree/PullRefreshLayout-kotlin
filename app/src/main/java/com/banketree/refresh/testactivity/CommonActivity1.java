package com.banketree.refresh.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.banketree.pullrefreshlayout.PullRefreshLayout;
import com.banketree.pullrefreshlayout.ShowGravity;
import com.banketree.refresh.R;
import com.banketree.refresh.widget.HeaderOrFooter;
import com.banketree.refresh.widget.TwoRefreshHeader;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class CommonActivity1 extends SwipeBackActivity {
    private static final String TAG = "CommonActivity1";

    protected PullRefreshLayout refreshLayout;

    protected int getViewId() {
        return R.layout.common_activity1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        initRefreshLayout();
//        ((TextView)findViewById(R.id.tv_data)) .setMovementMethod(ScrollingMovementMethod.getInstance());
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        }, 150);
        if (findViewById(R.id.iv) != null) {
            ((ImageView) findViewById(R.id.iv)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.loading_bg));
        }
    }

    protected void initRefreshLayout() {
        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "refreshLayout", Toast.LENGTH_LONG).show();
            }
        });

        refreshLayout = (PullRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.setHeaderShowGravity(ShowGravity.FOLLOW);

        refreshLayout.setHeaderView(new TwoRefreshHeader(getBaseContext(), refreshLayout));
        HeaderOrFooter footer = new HeaderOrFooter(getBaseContext(), "PacmanIndicator", Color.WHITE, false);
        footer.setTv("drag");
        refreshLayout.setFooterView(footer);

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TwoRefreshHeader twoRefreshHeader = refreshLayout.getHeaderView();
                if (!twoRefreshHeader.isTwoRefresh()) {
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();
                        }
                    }, 3000);
                }
            }

            @Override
            public void onLoading() {
                Log.e(TAG, "refreshLayout onLoading: ");
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.loadMoreComplete();
                    }
                }, 3000);
            }
        });
    }
}
