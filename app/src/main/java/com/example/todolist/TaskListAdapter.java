package com.example.todolist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


//import com.google.protobuf.Internal;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class TaskListAdapter extends ArrayAdapter<TaskModel> {
    private DatabaseReference database;
    public TaskListAdapter(Context context, ArrayList<TaskModel> arrayList) {
        super(context, 0, arrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        TaskModel currentNumberPosition = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.name);
        textView1.setText(currentNumberPosition.getName());
        TextView textView2 = currentItemView.findViewById(R.id.dueDate);
        textView2.setText(currentNumberPosition.getDueDate());
        return currentItemView;
    }

    public void deleteTask(int position){
        TaskModel taskModel = getItem(position);
        database.child("test").child(taskModel.name).removeValue();
    }
}

