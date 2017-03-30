package com.gdmec.jacky.filemanager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnCheckBoxOnClick {
    //ROOT目录
    private static final File ROOT = Environment.getExternalStorageDirectory();

    private OnCheckBoxOnClick activity;
    //顶部布局
    private RelativeLayout topLayout;
    //顶部标题
    private TextView topTextView;
    //文件adapter fileadapter
    private FileAdapter fileAdapter;
    //选择文件数
    private TextView topSelectedCount;
    //全选按钮
    private Button selectAll;
    //全不选按钮
    private Button selectNothing;
    //五按钮布局
    private LinearLayout buttonLayout1;
    //四按钮布局
    private LinearLayout buttonLayout2;
    //当前文件
    private File currentFile;
    //文件目录
    private File[] files;
    //文件列表视图
    private ListView listView;
    //文件列表data
    private List<File> fileList;
    //文件列表_空布局
    private TextView emptyView;
    //文件列表
    private List<File> listFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //声明对象
        fileList = new ArrayList<>();
        listFile = new ArrayList<File>();
        fileAdapter = new FileAdapter(this, this);
        listView = (ListView) findViewById(R.id.listview);
        topLayout = (RelativeLayout) findViewById(R.id.topLayout);
        emptyView = (TextView) findViewById(R.id.emptyView);
        buttonLayout1 = (LinearLayout) findViewById(R.id.buttonLayout1);
        buttonLayout2 = (LinearLayout) findViewById(R.id.buttonLayout2);
        topSelectedCount = (TextView) findViewById(R.id.topSelectedCount);
        selectAll = (Button) findViewById(R.id.selectAll);
        selectNothing = (Button) findViewById(R.id.selectNothing);
        topTextView = (TextView) findViewById(R.id.topTextView);
        files = ROOT.listFiles();
        listFile.clear();
        currentFile = ROOT;
        listView.setEmptyView(emptyView);
        for (File temp : files) {
            listFile.add(temp);
        }
        fileAdapter.setDataList(listFile);
        listView.setAdapter(fileAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = fileAdapter.getDataList().get(position);
                currentFile = file;
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    listFile.clear();
                    for (File temp : files) {
                        listFile.add(temp);
                    }
                    fileAdapter.setDataList(listFile);
                    fileAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onCheckBoxClick() {
        int count = fileAdapter.getCheckedFiles().size();
        if (count > 0) {
            topLayout.setVisibility(View.VISIBLE);
            topTextView.setVisibility(View.GONE);
            buttonLayout1.setVisibility(View.VISIBLE);
            buttonLayout2.setVisibility(View.GONE);
            topSelectedCount.setText("已选择" + count + "项");
            if (count == fileAdapter.getDataList().size()) {
                selectAll.setVisibility(View.GONE);
                selectNothing.setVisibility(View.VISIBLE);
            } else {
                selectAll.setVisibility(View.VISIBLE);
                selectNothing.setVisibility(View.GONE);
            }
        } else {
            topLayout.setVisibility(View.GONE);
            topTextView.setVisibility(View.VISIBLE);
            buttonLayout2.setVisibility(View.VISIBLE);
            buttonLayout1.setVisibility(View.GONE);
            selectAll.setVisibility(View.VISIBLE);
            selectNothing.setVisibility(View.GONE);
        }
    }

    //顶部布局返回键和取消按钮的监听事件
    public void backOnClick(View v) {
        fileAdapter.selectNothing();
    }

    //全选按钮的监听事件
    public void selectAll(View v) {
        fileAdapter.selectAll();
    }

    //全不选按钮的监听事件
    public void selectNothing(View v) {
        fileAdapter.selectNothing();
    }

    @Override
    public void onBackPressed() {
        if (currentFile.equals(ROOT)) {
            super.onBackPressed();
        } else {
            currentFile = currentFile.getParentFile();
            File[] files = currentFile.listFiles();
            listFile.clear();
            for (File temp : files) {
                listFile.add(temp);
            }
            fileAdapter.setDataList(listFile);
            fileAdapter.notifyDataSetChanged();
        }
        activity.onCheckBoxClick();
    }
}
