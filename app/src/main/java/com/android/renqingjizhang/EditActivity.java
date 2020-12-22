package com.android.renqingjizhang;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.renqingjizhang.db.MessageDataBase;
import com.android.renqingjizhang.db.dao.MessageDao;
import com.android.renqingjizhang.db.entity.MessageBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private EditText et_new_name;
    private EditText et_new_content;
    private EditText et_new_money;

    private TextView tv_time;
    private Spinner spinner, typeSpinner;
    private MessageDao noteDao;
    private MessageBean note;
    private Long myID;
    private String myName;
    private String myContent;
    private String myCreate_time;
    private String myType;
    private String myReason;

    private String myMoney;
    private Calendar calendar;
    private int flag;//区分是新建还是修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getNowTime();

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });

    }

    private void selectTime() {


        calendar = Calendar.getInstance();
        DatePickerDialog dpdialog = new DatePickerDialog(EditActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int day) {
                        // TODO Auto-generated method stub
                        // 更新EditText控件日期 小于10加0
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        final TimePickerDialog tpdialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                tv_time.setText(format.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        dpdialog.show();
        dpdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                tpdialog.show();
            }
        });
    }

    private void init() {
        et_new_name = (EditText) findViewById(R.id.et_new_title);
        et_new_content = (EditText) findViewById(R.id.et_new_content);
        et_new_money = (EditText) findViewById(R.id.et_new_money);
        tv_time = (TextView) findViewById(R.id.tv_remindtime);
        spinner = (Spinner) findViewById(R.id.reason_select);
        typeSpinner = (Spinner) findViewById(R.id.type_select);

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);//0新建，1编辑

        if (flag == 0) {//0新建
            setTitle("新建");
            myCreate_time = getNowTime();
        } else if (flag == 1) {//1编辑
            Bundle bundle = intent.getBundleExtra("data");
            note = (MessageBean) bundle.getSerializable("note");
            myID = note.getId();
            myName = note.getName();
            myContent = note.getDesc();
            myCreate_time = note.getCreateTime();
            myType = note.getType();
            myReason = note.getReason();

            myMoney = note.getMoney();
            setTitle("编辑笔记");
            for (int i = 0; i < 2; i++) {
                if (typeSpinner.getItemAtPosition(i).toString().equals(myType)) {
                    typeSpinner.setSelection(i);
                }
            }
            for (int i = 0; i < 5; i++) {
                if (spinner.getItemAtPosition(i).toString().equals(myReason)) {
                    spinner.setSelection(i);
                }
            }
            et_new_name.setText(note.getName());
            et_new_content.setText(note.getDesc());
            et_new_money.setText(myMoney);
            tv_time.setText(myCreate_time);

        }
    }

    private String getNowTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_save://保存
                saveNoteDate();
                break;
            case R.id.action_new_giveup://放弃保存
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNoteDate() {
        String noteremindTime = tv_time.getText().toString();
        if (noteremindTime.equals("点击设置时间")) {
            Toast.makeText(EditActivity.this, "请设置时间", Toast.LENGTH_SHORT).show();
            return;
        }
        String noteTitle = et_new_name.getText().toString();
        if (noteTitle.isEmpty()) {
            Toast.makeText(EditActivity.this, "人名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String noteContent = et_new_content.getText().toString();
        if (noteContent.isEmpty()) {
            Toast.makeText(EditActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String notemoney = et_new_money.getText().toString();

        if (notemoney.isEmpty()) {
            Toast.makeText(EditActivity.this, "金额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Long noteID = myID;
        if (note == null) {
            note = new MessageBean();
        }
        noteDao = MessageDataBase.getDatabase(this).getMessageDao();
        note.setName(noteTitle);
        note.setDesc(noteContent);
        note.setMoney(notemoney);
        note.setCreateTime(noteremindTime);
        note.setReason(spinner.getSelectedItem().toString());
        note.setType(typeSpinner.getSelectedItem().toString());
        if (calendar != null) {
            note.setYear(calendar.get(Calendar.YEAR) + "");

        }
        if (flag == 0) {//新建笔记
            noteDao.insertMessage(note);
        } else if (flag == 1) {//修改笔记
            note.setId(noteID);
            noteDao.updateMessage(note);
        }

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
