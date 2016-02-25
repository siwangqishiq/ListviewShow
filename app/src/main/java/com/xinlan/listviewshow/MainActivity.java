package com.xinlan.listviewshow;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Bean> mList;
    private ListView mListView;
    private LayoutInflater mInfalter;
    private ListAdapter mAdapter;

    private View mInputLayout;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        mInfalter = LayoutInflater.from(this);
        mEditText = (EditText)findViewById(R.id.eidt_view);
        mInputLayout = findViewById(R.id.input_layout);
        mListView = (ListView)findViewById(R.id.list_view);

        mInputLayout.setVisibility(View.GONE);

        ImageView headImage = new ImageView(this);
        headImage.setImageResource(R.drawable.xiaoze);

        mAdapter = new ListAdapter();
        mListView.addHeaderView(headImage);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(mInputLayout.getVisibility()==View.VISIBLE){
                    hideSoftKeyboard();
                    mInputLayout.setVisibility(View.GONE);
                }//end if
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private final class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = mInfalter.inflate(R.layout.view_item,null);

                ViewHolder holder = new ViewHolder();
                holder.imageView = (ImageView)convertView.findViewById(R.id.image_view);
                holder.contentView = (TextView)convertView.findViewById(R.id.content_view);
                holder.commentView = (TextView)convertView.findViewById(R.id.comment_btn);
                convertView.setTag(holder);
            }
            ViewHolder holder = (ViewHolder)convertView.getTag();
            final Bean data = (Bean)getItem(position);
            holder.imageView.setImageResource(data.getPic());
            holder.contentView.setText(data.getContent());
            //holder.imageView.setVisibility(View.GONE);
            //handle comment click
            holder.commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInputLayout.setVisibility(View.VISIBLE);
                    mEditText.setFocusable(true);
                    mEditText.setFocusableInTouchMode(true);
                    mEditText.requestFocus();
                    showSoftKeyboard();
                }
            });

            return convertView;
        }
    }//end inner class

    class ViewHolder{
        ImageView imageView;
        TextView contentView;
        TextView commentView;
    }

    private void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText,InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(),0);
    }

    private void initData(){
        mList = new ArrayList<Bean>();

        Bean bean1 = new Bean();
        bean1.setContent("苍井空hahahhahahahs 哈哈哈哈 ");
        bean1.setPic(R.drawable.hehe);

        Bean bean2 = new Bean();
        bean2.setContent("武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean2.setPic(R.drawable.shufu1);

        Bean bean3 = new Bean();
        bean3.setContent("小泽   答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean3.setPic(R.drawable.shufu2);

        Bean bean4 = new Bean();
        bean4.setContent("小泽   我的世界  藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean4.setPic(R.drawable.mo);

        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
    }
}//end class
