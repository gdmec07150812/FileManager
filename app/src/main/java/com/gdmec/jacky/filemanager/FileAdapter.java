package com.gdmec.jacky.filemanager;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileAdapter extends MyAdapter<File> {
    private SparseBooleanArray map = new SparseBooleanArray();
    private CheckBox checkBox;
    private OnCheckBoxOnClick activity;
    private List<File> checkedFiles = new ArrayList<File>();

    public FileAdapter(Context context, OnCheckBoxOnClick activity) {
        super(context);
        this.activity = activity;
    }

    public List<File> getCheckedFiles() {
        return checkedFiles;
    }

    @Override
    public void setDataList(List<File> dataList) {
        super.setDataList(dataList);
        map.clear();
        checkedFiles.clear();
        if (getDataList() != null) {
            for (int i = 0; i < getDataList().size(); i++) {
                map.put(i, false);
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
        }
        ImageView imageView = MyViewHolder.get(convertView, R.id.imageView);
        TextView textview1 = MyViewHolder.get(convertView, R.id.textview1);
        TextView textView2 = MyViewHolder.get(convertView, R.id.textview2);
        checkBox = MyViewHolder.get(convertView, R.id.checkBox);

        File file = getDataList().get(position);
        if (file.isDirectory()) {
            imageView.setImageResource(R.drawable.folder);
        } else {
            imageView.setImageResource(R.drawable.file);
        }
        if (file.getName().startsWith(".")) {
            imageView.setAlpha(0.5f);
        } else {
            imageView.setAlpha(1f);
        }
        textview1.setText(file.getName());
        long time = file.lastModified();
        String text = DateFormat.format("yyyy-MM-dd kk:mm:ss", time).toString();
        textView2.setText(text);
        checkBox.setChecked(map.get(position));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox1 = (CheckBox) v;
                int p = (Integer) checkBox1.getTag();
                map.put(p, !map.get(p));
                checkBox.setChecked(map.get(p));
                if (map.get(p)) {
                    checkedFiles.add(getDataList().get(p));
                } else {
                    checkedFiles.remove(getDataList().get(p));
                }
                activity.onCheckBoxClick();

            }
        });
        checkBox.setTag(position);
        return convertView;
    }

    public void selectNothing() {
        checkedFiles.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            map.put(i, false);
        }
        notifyDataSetChanged();
        activity.onCheckBoxClick();
    }

    public void selectAll() {
        checkedFiles.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            checkedFiles.add(getDataList().get(i));
            map.put(i, true);
        }
        notifyDataSetChanged();
        activity.onCheckBoxClick();
    }

}
