package com.android.renqingjizhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.renqingjizhang.R;
import com.android.renqingjizhang.db.MessageDataBase;
import com.android.renqingjizhang.db.entity.MessageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 收礼
 */
public class IncomeFragment extends Fragment {
    private View inflate;
    private RecyclerView rvIncome;
    private BaseQuickAdapter<MessageBean, BaseViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_income, container, false);
        initView();
        return inflate;
    }

    public void initView() {
        rvIncome = inflate.findViewById(R.id.rv_in);
        adapter = new BaseQuickAdapter<MessageBean, BaseViewHolder>(R.layout.item_list_message) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageBean messageBean) {
                baseViewHolder.setText(R.id.tv_message_money, messageBean.getMoney());
                baseViewHolder.setText(R.id.tv_message_time, messageBean.getCreateTime());
                baseViewHolder.setText(R.id.tv_message_money, messageBean.getMoney());
                baseViewHolder.setText(R.id.tv_message_type, messageBean.getDesc());

            }

        };
        rvIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIncome.setAdapter(adapter);
        adapter.setNewInstance(MessageDataBase.getDatabase(getContext()).getMessageDao().queryInAllMessage());

    }

    public static IncomeFragment newInstance() {

        Bundle args = new Bundle();

        IncomeFragment fragment = new IncomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
