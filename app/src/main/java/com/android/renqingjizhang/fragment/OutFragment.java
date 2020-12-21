package com.android.renqingjizhang.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.renqingjizhang.R;
import com.android.renqingjizhang.adapter.NodeTreeAdapter;
import com.android.renqingjizhang.bean.FirstMode;
import com.android.renqingjizhang.db.MessageDataBase;
import com.android.renqingjizhang.db.dao.MessageDao;
import com.android.renqingjizhang.db.entity.MessageBean;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 随礼
 */
public class OutFragment extends Fragment {

    private View inflate;
    private RecyclerView rvOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_out, container, false);
        initView();
        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    public void initView() {
        rvOut = inflate.findViewById(R.id.rv_out);
        rvOut.setLayoutManager(new LinearLayoutManager(getContext()));
        NodeTreeAdapter adapter = new NodeTreeAdapter();
        adapter.setNewInstance(getData());

        rvOut.setAdapter(adapter);
    }

    public List<BaseNode> getData() {
        MessageDao messageDao = MessageDataBase.getDatabase(getContext()).getMessageDao();
        List<MessageBean> messageBeans = messageDao.queryAllMessage();
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < messageBeans.size(); i++) {
            MessageBean messageBean = messageBeans.get(i);

            if (!integers.contains(messageBean.getYear())) {
                integers.add(Integer.parseInt(messageBean.getYear()));
            }
        }
        Collections.sort(integers);
        List<BaseNode> list = new ArrayList<>();

        for (int i = 0; i < integers.size(); i++) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            List<MessageBean> messageBeans1 = messageDao.queryAllMessage(integers.get(i) + "");
            for (int n = 0; n < messageBeans1.size(); n++) {
                secondNodeList.add(messageBeans1.get(i));
            }
            FirstMode entity = new FirstMode(secondNodeList, integers.get(i) + "", messageBeans1.size() + "");

            // 模拟 默认第0个是展开的
            entity.setExpanded(i == 0);

            list.add(entity);
        }
        return list;
    }


    public static OutFragment newInstance() {

        Bundle args = new Bundle();

        OutFragment fragment = new OutFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
