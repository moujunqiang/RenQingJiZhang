package com.android.renqingjizhang.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * 根据年份分组
 */
public class FirstMode extends BaseExpandNode {
    private String year;
    private String count;
    private List<BaseNode> childNode;

    public String getYear() {
        return year;
    }

    public FirstMode(List<BaseNode> childNode, String year, String count) {
        this.childNode = childNode;
        this.year = year;
        this.count = count;

        setExpanded(false);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
