package com.xinlan.listviewshow;

import java.util.List;

/**
 * Created by panyi on 16/2/25.
 */
public class Bean {
    private String content;
    private int pic;
    private List<Comment> commentList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }


}