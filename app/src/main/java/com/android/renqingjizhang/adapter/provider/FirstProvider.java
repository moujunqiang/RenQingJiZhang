package com.android.renqingjizhang.adapter.provider;

import android.view.View;
import android.widget.ImageView;

import com.android.renqingjizhang.R;
import com.android.renqingjizhang.bean.FirstMode;
import com.android.renqingjizhang.db.entity.MessageBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class FirstProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_out_leave1;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        FirstMode entity = (FirstMode) data;
        helper.setText(R.id.tv_year, entity.getYear());
        helper.setText(R.id.tv_count, entity.getCount());
        ImageView ivArrow = helper.getView(R.id.iv_arrow);

        if (entity.isExpanded()) {
            ivArrow.setImageResource(R.mipmap.iv_arrow_down);
        } else {
            ivArrow.setImageResource(R.mipmap.iv_arrow_right);

        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        FirstMode entity = (FirstMode) data;
        ImageView ivArrow = helper.getView(R.id.iv_arrow);

        if (entity.isExpanded()) {
            ivArrow.setImageResource(R.mipmap.iv_arrow_down);
            getAdapter().collapse(position);
        } else {
            getAdapter().expandAndCollapseOther(position);
            ivArrow.setImageResource(R.mipmap.iv_arrow_right);

        }
    }
}