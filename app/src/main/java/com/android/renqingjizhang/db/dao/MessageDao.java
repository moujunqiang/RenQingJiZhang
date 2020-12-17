package com.android.renqingjizhang.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

/**
 * 消息数据库 实体类
 */
@Dao
public interface MessageDao {
    @Insert
    void insertCourse(MessageBean... messageBeans);

    /**
     * 根据发送者和接收者查询
     * @return
     */
    @Query("select * from messagebean where fromName=:fromName and toName=:toName")
    List<MessageBean> queryAllMessage(String fromName, String toName);



}
