package com.android.renqingjizhang.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.renqingjizhang.R;
import com.android.renqingjizhang.db.entity.MessageBean;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView适配器
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context mContext;
    private List<MessageBean> messageBeans;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private int position;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MessageListAdapter() {
        messageBeans = new ArrayList<>();
    }

    public void setmessageBeans(List<MessageBean> notes) {
        this.messageBeans = notes;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (MessageBean) v.getTag());
        }
    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, MessageBean note);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.i(TAG, "###onCreateViewHolder: ");
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_message, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        //view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Log.i(TAG, "###onBindViewHolder: ");
        final MessageBean messageBean = messageBeans.get(position);
        holder.itemView.setTag(messageBean);
        holder.tvMoney.setText(messageBean.getMoney());
        holder.tvName.setText(messageBean.getName());
        holder.tvTime.setText(messageBean.getCreateTime());
        holder.tvType.setText(messageBean.getType());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.i(TAG, "###getItemCount: ");
        if (messageBeans != null && messageBeans.size() > 0) {
            return messageBeans.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvTime;
        public TextView tvType;
        public TextView tvMoney;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_message_name);
            tvTime = (TextView) view.findViewById(R.id.tv_message_time);
            tvType = (TextView) view.findViewById(R.id.tv_message_type);
            tvMoney = (TextView) view.findViewById(R.id.tv_message_money);
        }
    }
}
