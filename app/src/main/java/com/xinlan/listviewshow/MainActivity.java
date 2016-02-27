package com.xinlan.listviewshow;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
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
    private View mRootView;

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener;
    private boolean keyboardListenersAttached = false;

    private Handler uiHandler = new Handler(Looper.getMainLooper());
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        initData();

        mInfalter = LayoutInflater.from(this);
        mEditText = (EditText)findViewById(R.id.eidt_view);
        mInputLayout = findViewById(R.id.input_layout);
        mListView = (ListView)findViewById(R.id.list_view);

        mInputLayout.setVisibility(View.GONE);

        ImageView headImage = new ImageView(this);
        headImage.setImageResource(R.drawable.xiaoze);
        headImage.setScaleType(ImageView.ScaleType.FIT_XY);

        mAdapter = new ListAdapter();
        mListView.addHeaderView(headImage);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mInputLayout.getVisibility() == View.VISIBLE) {
                    hideSoftKeyboard();
                    mInputLayout.setVisibility(View.GONE);
                }//end if
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //attachKeyboardListeners();
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        mRootView = (ViewGroup) findViewById(R.id.root_view);
        keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] inputLayoutLoc = new int[2];
                mInputLayout.getLocationOnScreen(inputLayoutLoc);
                System.out.println("actionBar height = "+getSupportActionBar().getHeight());
                System.out.println("input init loc y = "+inputLayoutLoc[1]+"  userHeight = "+mRootView.getHeight()
                        +"  rootHeight = "+mRootView.getRootView().getHeight());


                int heightDiff = mRootView.getRootView().getHeight() - mRootView.getHeight();
                System.out.println("heightDiff ---> "+heightDiff);
                int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();

                if (heightDiff <= contentViewTop) {
                    onHideKeyboard();
                } else {
                    int keyboardHeight = heightDiff - contentViewTop;
                    onShowKeyboard(keyboardHeight);
                }
            }
        };

        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (keyboardListenersAttached) {
            mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }

    private void onHideKeyboard(){
        System.out.println("hide");
    }

    private void onShowKeyboard(int keyboardHeight){
        //System.out.println("show = " + keyboardHeight);
        //mListView.setSelectionFromTop(index,0);

        //adjustListviewPosition(pos);
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
        public View getView(final int position,View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = mInfalter.inflate(R.layout.view_item,null);

                ViewHolder holder = new ViewHolder();
                holder.imageView = (ImageView)convertView.findViewById(R.id.image_view);
                holder.contentView = (TextView) convertView.findViewById(R.id.content_view);
                holder.commentView = (TextView)convertView.findViewById(R.id.comment_btn);
                holder.commentListView = (TextView)convertView.findViewById(R.id.comment_list_view);
                holder.collapseBtn = (TextView)convertView.findViewById(R.id.collapse_btn);

                convertView.setTag(holder);
            }

            final ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.imageView.setVisibility(View.VISIBLE);
            holder.commentListView.setVisibility(View.VISIBLE);

            final Bean data = (Bean)getItem(position);
            holder.imageView.setImageResource(data.getPic());
            holder.contentView.setText(data.getContent());

            if(data.type == 0){
                holder.imageView.setVisibility(View.GONE);
                holder.commentListView.setVisibility(View.GONE);
            }

            //holder.contentView.getPaint()
            //StaticLayout staticLayout = new StaticLayout()
            int line = 1;
            if(holder.contentView.getLineCount() == 0){
                StaticLayout layout = new StaticLayout(data.getContent(),
                        holder.contentView.getPaint(),mListView.getMeasuredWidth(), Layout.Alignment.ALIGN_NORMAL,
                        1.0f, 0.0f, true);

                //System.out.println(mListView.getMeasuredWidth()+" init   pos = "+position +
                //        "  line = "+layout.getLineCount());
                line = layout.getLineCount();
            }else{
                //System.out.println("pos = "+position +
                //        "  line = "+holder.contentView.getLineCount());
                line = holder.contentView.getLineCount();
            }

            if(line > 3){//大文本内容
                holder.collapseBtn.setVisibility(View.VISIBLE);
                if(data.expand){//展示展开状态
                    holder.contentView.setMaxLines(Integer.MAX_VALUE);
                    holder.collapseBtn.setText("点击收缩");
                }else{//展示收缩状态
                    holder.contentView.setMaxLines(3);
                    holder.collapseBtn.setText("点击展开");
                }//end if
            }else{
                holder.collapseBtn.setVisibility(View.GONE);
            }


            holder.collapseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.expand){//展开
                        holder.contentView.setMaxLines(3);
                        data.expand = false;
                        holder.collapseBtn.setText("点击展开");
                    }else{//收缩操作
                        holder.contentView.setMaxLines(Integer.MAX_VALUE);
                        data.expand = true;
                        holder.collapseBtn.setText("点击收缩");
                    }//end if
                }
            });


            //holder.imageView.setVisibility(View.GONE);
            //handle comment click
            final View view = convertView;
            holder.commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInputLayout.setVisibility(View.VISIBLE);
                    mEditText.setFocusable(true);
                    mEditText.setFocusableInTouchMode(true);
                    mEditText.requestFocus();
                    showSoftKeyboard();
                    pos = position + 1;
                    adjustListviewPosition(position + 1,view.getHeight());
                }
            });

            return convertView;
        }
    }//end inner class

    class ViewHolder{
        ImageView imageView;
        TextView contentView;
        TextView commentView;
        TextView commentListView;
        TextView collapseBtn;
    }

    /**
     * 调整Listiew item位置
     * @param pos
     */
    private void adjustListviewPosition(final int pos,final int itemViewHeight){
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //System.out.println("adjust");
                int[] inputLayoutLoc = new int[2];
                mInputLayout.getLocationOnScreen(inputLayoutLoc);
                System.out.println("input loc y = " + inputLayoutLoc[1]);

                Rect rectangle= new Rect();
                Window window= getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight= rectangle.top;

                int deltaY = inputLayoutLoc[1] -
                        getSupportActionBar().getHeight() - statusBarHeight - itemViewHeight;
                mListView.setSelectionFromTop(pos, deltaY);
            }
        },200);
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
        String longStr = "tangAAAAAAAAAAAAAAAAAAAA<TextView\n" +
                "        android:id=\"@+id/content_view\"\n" +
                "        android:text=\"content 我是内容\"\n" +
                "        android:textSize=\"18sp\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        />";
        Bean bean1 = new Bean();
        bean1.setContent("苍井空hahahhahahahs 哈哈哈哈 ");
        bean1.setPic(R.drawable.hehe);

        Bean bean2 = new Bean();
        bean2.setContent("武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 "+longStr);
        bean2.setPic(R.drawable.shufu1);

        Bean bean3 = new Bean();
        bean3.setContent("小泽   答答滴滴答答的苍"+longStr+"井武藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean3.setPic(R.drawable.shufu2);

        Bean bean4 = new Bean();
        bean4.setContent("小泽   我的世界  藤蓝滴滴答答滴+"+longStr+"+滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean4.setPic(R.drawable.mo);

        Bean bean5 = new Bean();
        bean5.setContent("就一行字");
        bean5.type = 0;


        Bean bean6 = new Bean();
        bean6.setContent("苍井空hahahhahahahs 哈哈哈哈 ");
        bean6.setPic(R.drawable.hehe);

        Bean bean7 = new Bean();
        bean7.setContent("武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 "+longStr);
        bean7.setPic(R.drawable.shufu1);

        Bean bean8 = new Bean();
        bean8.setContent("小泽   答答滴滴答答的苍"+longStr+"井武藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean8.setPic(R.drawable.shufu2);

        Bean bean9 = new Bean();
        bean9.setContent("小泽   我的世界  藤蓝滴滴答答滴+"+longStr+"+滴答答的苍井武藤蓝滴滴答答滴滴答答的苍井空hahahhahahahs 哈哈哈哈 ");
        bean9.setPic(R.drawable.mo);

        Bean bean10 = new Bean();
        bean10.setContent("就一行字");
        bean10.type = 0;

        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
        mList.add(bean5);
        mList.add(bean6);
        mList.add(bean7);
        mList.add(bean8);
        mList.add(bean9);
        mList.add(bean10);

    }
}//end class
