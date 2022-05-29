package com.example.todolist;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


//import com.google.protobuf.Internal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class TaskListAdapter extends ArrayAdapter<TaskModel> {
    public DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FragmentManager getSupportFragmentManager;
    public TaskListAdapter(Context context, ArrayList<TaskModel> arrayList, FragmentManager getSupportFragmentManager) {
        super(context, 0, arrayList);
        this.getSupportFragmentManager = getSupportFragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_adapter, parent, false);
        }

        TaskModel currentNumberPosition = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.name);
        textView1.setText(currentNumberPosition.getName());
        TextView textView2 = currentItemView.findViewById(R.id.dueDate);
        textView2.setText(currentNumberPosition.getDueDate());
        Button deleteButton = currentItemView.findViewById(R.id.deleteButton);
        Button editButton = currentItemView.findViewById(R.id.editButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskModel taskModel = getItem(position);
                Intent intent = new Intent("notification.receiver");
                intent.putExtra("taskName", taskModel.getName());
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                Log.d("####### Delete", taskModel.getuuid());
                database.child("test").child(taskModel.getuuid()).removeValue();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskModel taskModel = getItem(position);
                 new AddNewTask(taskModel.getuuid(), taskModel.getName(), taskModel.getDueDate()).show(getSupportFragmentManager , AddNewTask.TAG);
            }
        });

        return currentItemView;
    }

    public void deleteTask(int position){
        TaskModel taskModel = getItem(position);
        database.child("test").child(taskModel.name).removeValue();
    }
}

