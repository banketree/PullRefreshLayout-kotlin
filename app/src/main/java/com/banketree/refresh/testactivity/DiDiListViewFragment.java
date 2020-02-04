package com.banketree.refresh.testactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.banketree.refresh.R;
import com.banketree.refresh.widget.DiDiListHeader;

public class DiDiListViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_didi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final PullRefreshLayout prl = (PullRefreshLayout) view.findViewById(R.id.prl);
        prl.setHeaderView(new DiDiListHeader(getContext(), prl));
        prl.setOnRefreshListener(new PullRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                prl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prl.refreshComplete();
                    }
                }, 3000);
            }
        });

        RecyclerView rvData = (RecyclerView) view.findViewById(R.id.rv_data);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        rvData.setAdapter(new RecyclerView.Adapter() {
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(getLayoutInflater().inflate(R.layout.didi_item, parent, false)) {
                };
            }

            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }

            public int getItemCount() {
                return 2;
            }
        });
    }

}
