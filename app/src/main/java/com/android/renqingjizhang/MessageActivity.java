package com.android.renqingjizhang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.renqingjizhang.db.MessageDataBase;
import com.android.renqingjizhang.db.dao.MessageDao;
import com.android.renqingjizhang.db.entity.MessageBean;


public class MessageActivity extends AppCompatActivity {
    private TextView tv_note_title;
    private TextView tv_note_content;
    private TextView tv_note_create_time;
    private MessageBean messageBean;
    private String myTitle;
    private String myContent;
    private String myCreate_time;
    private MessageDao noteDao;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    private void init() {


        noteDao = MessageDataBase.getDatabase(this).getMessageDao();


        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("数据加载中...");
        loadingDialog.setCanceledOnTouchOutside(false);

        tv_note_title = (TextView) findViewById(R.id.tv_note_title);//标题
        tv_note_title.setTextIsSelectable(true);
        tv_note_content = (TextView) findViewById(R.id.tv_note_content);//内容
        tv_note_create_time = (TextView) findViewById(R.id.tv_note_create_time);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        messageBean = (MessageBean) bundle.getSerializable("note");

        myTitle = messageBean.getName()+"-"+messageBean.getMoney();
        myContent = messageBean.getReason();
        myCreate_time = messageBean.getCreateTime();

        tv_note_title.setText(myTitle);
        tv_note_content.setText(myContent);
        tv_note_create_time.setText(myCreate_time);
        setTitle("详情");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_note_edit://编辑笔记
                Intent intent = new Intent(MessageActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", messageBean);
                intent.putExtra("data", bundle);
                intent.putExtra("flag", 1);//编辑笔记
                startActivity(intent);
                finish();
                break;
            case R.id.action_note_delete://删除笔记
                AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定删除？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int ret = noteDao.deleteMessage(messageBean);
                        if (ret > 0) {
                            Toast.makeText(MessageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;

        }
        return super.onOptionsItemSelected(item);
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
