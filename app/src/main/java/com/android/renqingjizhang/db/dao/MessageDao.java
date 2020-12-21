package com.android.renqingjizhang.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.android.renqingjizhang.db.entity.MessageBean;

import java.util.List;

/**
 * 消息数据库 实体类
 */
@Dao
public interface MessageDao {
    @Insert
    void insertMessage(MessageBean... messageBeans);

    @Update
    void updateMessage(MessageBean... messageBeans);

    /**
     * 根据日期查询某一天的
     *
     * @return
     */
    @Query("select * from messagebean where createTime >= :startTime and createTime< :endTime")
    List<MessageBean> queryAllMessage(String startTime, String endTime);

    @Query("select * from messagebean")
    List<MessageBean> queryAllMessage();

    @Query("select * from messagebean where year=:year")
    List<MessageBean> queryAllMessage(String year);

    @Delete
    int deleteMessage(MessageBean messageBean);
}
