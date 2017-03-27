package com.gdmec.jacky.filemanager;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final File ROOT = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);

        FileAdapter fileAdapter = new FileAdapter(this);

        List<File> fileList = new ArrayList<>();

        File[] files = ROOT.listFiles();

        for (File temp : files) {

            fileList.add(temp);

        }

        fileAdapter.setDataList(fileList);

        listView.setAdapter(fileAdapter);

    }
}
