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

    private static final File ROOT = Environment.getExternalStorageDirectory();
    private OnCheckBoxOnClick activity;
    private RelativeLayout topLayout;

    private TextView topTextView;

    private FileAdapter fileAdapter;

    private TextView emptyView;

    private TextView topSelectedCount;

    private Button selectAll;

    private Button selectNothing;

    private LinearLayout buttonLayout1;

    private LinearLayout buttonLayout2;

    private List<File> listFile = new ArrayList<File>();
    private File currentFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);

        topLayout = (RelativeLayout) findViewById(R.id.topLayout);

        emptyView = (TextView) findViewById(R.id.emptyView);

        listView.setEmptyView(emptyView);

        buttonLayout1 = (LinearLayout) findViewById(R.id.buttonLayout1);

        buttonLayout2 = (LinearLayout) findViewById(R.id.buttonLayout2);

        topSelectedCount = (TextView) findViewById(R.id.topSelectedCount);

        selectAll = (Button) findViewById(R.id.selectAll);

        selectNothing = (Button) findViewById(R.id.selectNothing);

        topTextView = (TextView) findViewById(R.id.topTextView);

        fileAdapter = new FileAdapter(this, this);

        List<File> fileList = new ArrayList<>();

        File[] files = ROOT.listFiles();

        listFile.clear();

        currentFile = ROOT;
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
    }
}
