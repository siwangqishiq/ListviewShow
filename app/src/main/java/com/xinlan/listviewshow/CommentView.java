package com.xinlan.listviewshow;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

/**
 * 基于TextView  通过HTML标签 实现ListView效果
 * 展示列表数据
 * 处理列表项每个Item的点击时间  插入事件
 * Created by panyi on 16/2/27.
 */
public class CommentView extends TextView {
    private List<Comment> mDataList;
    private ICommentClick mCommentClick;

    private static final String REPLY_TEXT=" 回复 ";

    public CommentView(Context context) {
        super(context);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){

    }

    public void setCommentClick(ICommentClick click){
        this.mCommentClick = click;
    }

    /**
     *
     * @param mDataList
     */
    public void setList(List<Comment> list){
        if(mDataList == list){
            return;
        }

        this.mDataList = list;
        this.setText("");
        for(int i = 0,len = mDataList.size();i < len;i++){
            Comment comment = mDataList.get(i);
            StringBuffer sb = new StringBuffer(comment.name);

            if(comment.replyId > 0){
                sb.append(REPLY_TEXT);
                sb.append(comment.replyName);
            }
            sb.append(" : ").append(comment.text);
            SpannableString itemSpan = new SpannableString(sb.toString());
            this.append(itemSpan);
            this.append("\n");
        }//end for i
    }

    public interface ICommentClick{
        void OnCommentItemClick(int position,Comment comment);
    }
}//end class
