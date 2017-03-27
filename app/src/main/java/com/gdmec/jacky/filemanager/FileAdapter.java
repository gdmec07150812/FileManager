package com.gdmec.jacky.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class FileAdapter extends MyAdapter<File> {
    public FileAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item,null);
        }
        ImageView imageView = MyViewHolder.get(convertView,R.id.imageview);
        TextView textview =  MyViewHolder.get(convertView,R.id.textview);
        File file = getDataList().get(position);
        if(file.isDirectory()){
            imageView.setImageResource(R.drawable.folder);
        }else{
            imageView.setImageResource(R.drawable.file);
        }
        textview.setText(file.getName());
        return convertView;
    }
}
