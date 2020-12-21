package com.android.renqingjizhang.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.renqingjizhang.MessageActivity;
import com.android.renqingjizhang.R;
import com.android.renqingjizhang.adapter.MessageListAdapter;
import com.android.renqingjizhang.db.MessageDataBase;
import com.android.renqingjizhang.db.dao.MessageDao;
import com.android.renqingjizhang.db.entity.MessageBean;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * 日历页面
 */
public class CalenderFragment extends Fragment implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener {
    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;
    RecyclerView mRecyclerView;
    private MessageListAdapter adapter;
    private List<MessageBean> messageBeans = new ArrayList<>();
    private MessageDao messageDao;
    private String login_user;
    private View inflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_calender, container, false);
        messageDao = MessageDataBase.getDatabase(getContext()).getMessageDao();
        initView();
        initData();
        return inflate;

    }


    protected void initView() {
        mTextMonthDay = (TextView) inflate.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) inflate.findViewById(R.id.tv_year);
        mTextLunar = (TextView) inflate.findViewById(R.id.tv_lunar);
        mRelativeTool = (RelativeLayout) inflate.findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView) inflate.findViewById(R.id.calendarView);
        mTextCurrentDay = (TextView) inflate.findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        inflate.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = (CalendarLayout) inflate.findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    protected void initData() {
        List<MessageBean> messageBeans = messageDao.queryAllMessage();
        Map<String, Calendar> map = new HashMap<>();

        for (int i = 0; i < messageBeans.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            MessageBean messageBean = messageBeans.get(i);
            Date date = null;
            try {
                date = sdf.parse(messageBean.getCreateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            map.put(getSchemeCalendar(date.getYear(), date.getMonth(), date.getDay(), 0xFF40db25, messageBean.getName().substring(0, 1)).toString(),
                    getSchemeCalendar(date.getYear(), date.getMonth(), date.getDay(), 0xFF40db25, messageBean.getName().substring(0, 1)));
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.clearSchemeDate();
        mCalendarView.setSchemeDate(map);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageListAdapter();
        adapter.setOnItemClickListener(new MessageListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MessageBean messageBean) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", messageBean);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        messageBeans.clear();
        String startTime = mCalendarView.getCurYear() + "-" + mCalendarView.getCurMonth() + "-" + mCalendarView.getCurDay() + "";
        String endTime = mCalendarView.getCurYear() + "-" + mCalendarView.getCurMonth() + "-" + (mCalendarView.getCurDay() + 1) + "";

        messageBeans.addAll(messageDao.queryAllMessage(startTime, endTime));
        adapter.setmessageBeans(messageBeans);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public static CalenderFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CalenderFragment fragment = new CalenderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        String startTime = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + "";
        String endTime = calendar.getYear() + "-" + calendar.getMonth() + "-" + (calendar.getDay() + 1) + "";

        messageBeans.clear();
        messageBeans.addAll(messageDao.queryAllMessage(startTime, endTime));
        adapter.setmessageBeans(messageBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onMonthChange(int year, int month) {
        Log.e("onMonthChange", year + "///" + month);
    }

}
