package com.xinlan.listviewshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SubItemActivity extends AppCompatActivity {
    public static void start(Context context,String name){
        Intent it = new Intent(context,SubItemActivity.class);
        it.putExtra("name",name);
        context.startActivity(it);
    }

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        name = getIntent().getStringExtra("name");
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle(name);
    }

}//end class
