package com.chl.cllibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chl.cllibrary.ui.activity.ImageActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private String array[] = new String[]{"Image 相关"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(this, ImageActivity.class));
                break;
            case 1:
                break;
        }
    }
}
