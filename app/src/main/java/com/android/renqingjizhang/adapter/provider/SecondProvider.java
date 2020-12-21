package com.android.renqingjizhang.adapter.provider;

import com.android.renqingjizhang.R;
import com.android.renqingjizhang.db.entity.MessageBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SecondProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_out_leave2;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        MessageBean entity = (MessageBean) data;
        helper.setText(R.id.tv_year, entity.getName());
        helper.setText(R.id.tv_account, entity.getMoney());
        helper.setText(R.id.tv_time, entity.getCreateTime());

    }
}