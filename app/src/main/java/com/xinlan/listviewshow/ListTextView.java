package com.xinlan.listviewshow;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

/**
 * 基于TextView  通过HTML标签 实现ListView效果
 * 展示列表数据
 * 处理列表项每个Item的点击时间  插入事件
 * Created by panyi on 16/2/27.
 */
public class ListTextView extends TextView {
    private List<Comment> mDataList;

    public ListTextView(Context context) {
        super(context);
        init(context);
    }

    public ListTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){

    }

    /**
     *
     * @param mDataList
     */
    public void setList(List<Comment> list){
        this.mDataList = list;
    }
}//end class
