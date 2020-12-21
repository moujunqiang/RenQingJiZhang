package com.android.renqingjizhang.adapter;

import android.view.View;

import com.android.renqingjizhang.R;
import com.android.renqingjizhang.adapter.provider.FirstProvider;
import com.android.renqingjizhang.adapter.provider.SecondProvider;
import com.android.renqingjizhang.bean.FirstMode;
import com.android.renqingjizhang.db.entity.MessageBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NodeTreeAdapter extends BaseNodeAdapter {

    public NodeTreeAdapter() {
        super();
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof FirstMode) {
            return 1;
        } else if (node instanceof MessageBean) {
            return 2;
        }
        return -1;
    }

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;
}