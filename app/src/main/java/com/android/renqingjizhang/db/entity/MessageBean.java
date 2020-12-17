package com.android.renqingjizhang.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity
public class MessageBean  {
    @PrimaryKey(autoGenerate = true)
    public static final int OTHER = 1;
    public static final int MINE = 2;
    private Long id;
    private String type; //
    private String reason;
    private String money;
    private String createTime;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
