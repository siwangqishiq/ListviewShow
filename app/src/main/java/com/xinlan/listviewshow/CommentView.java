package com.xinlan.listviewshow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
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
    private static final String COMMENT_SPEX=" : ";

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
     * @param list
     */
    public void setList(List<Comment> list){
        if(mDataList == list){
            return;
        }

        this.mDataList = list;
        this.setText("");

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int pos = 0;
        for(int i = 0,len = mDataList.size();i < len;i++){
            //final int index = i;

            final Comment data = mDataList.get(i);
            final int index = i;

            int[] indicates = new int[5];
            StringBuffer sb = new StringBuffer();
            if(i != 0){
                sb.append("\n");
                indicates[0] = 1;
            }else{
                indicates[0] = 0;
            }//end if

            sb.append(data.name);
            indicates[1] = indicates[0] + data.name.length();

            if(data.replyId > 0){
                sb.append(REPLY_TEXT);
                indicates[2] = indicates[1] + REPLY_TEXT.length();
                sb.append(data.replyName);
                indicates[3] = indicates[2] + data.replyName.length();
            }

            sb.append(COMMENT_SPEX);
            sb.append(data.text);

            indicates[4] = sb.toString().length();
            pos += sb.toString().length();

            SpannableString itemSpan = new SpannableString(sb);



            //add color click action
            itemSpan.setSpan(new ForegroundColorSpan(Color.BLUE),indicates[0],indicates[1],
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            itemSpan.setSpan(new ClickableSpan() {
                                 @Override
                                 public void onClick(View widget) {
                                     mCommentClick.OnNameClick(data.replyId, data.name);
                                     System.out.println("name = " + data.name+" "+index);
                                 }
                             },indicates[0],indicates[1], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            if(data.replyId > 0){
                itemSpan.setSpan(new ForegroundColorSpan(Color.BLUE),indicates[2],indicates[3],
                        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                itemSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        //System.out.println("replay name = " + data.replyName);
                        mCommentClick.OnNameClick(data.replyId,data.replyName);
                    }
                },indicates[2],indicates[3],
                        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            //System.out.println("index = "+index);

            //System.out.println(indicates[0]+"   "+indicates[4]);

            itemSpan.setSpan(new ClickableSpan() {
                                 @Override
                                 public void onClick(View widget) {
                                     System.out.println("item Click " + index);
                                     //mCommentClick.OnCommentItemClick();
                                 }
                             }, indicates[0], indicates[4],
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            append(itemSpan);
        }//end for i

        //this.setText(ssb);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);

        //setLinkTextColor(Color.TRANSPARENT);
    }

    public interface ICommentClick{
        void OnCommentItemClick(int position,Comment comment,int viewPos);
        void OnNameClick(int nameId,String name);
    }
}//end class
