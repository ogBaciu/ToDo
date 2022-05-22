package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    //    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
//            "WebOS","Ubuntu","Windows7","Max OS X"};
    ArrayList<TaskModel> taskList = new ArrayList<TaskModel>();
    private FloatingActionButton newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Log.d("$$before", "before auth");
        newTask = findViewById(R.id.floatingActionButton);

        Context context = this;


        if(mAuth != null && mAuth.getCurrentUser() != null){
            Log.d("$$after", "after auth");
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            newTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
                }
            });
            mDatabase.child("test").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<TaskModel> arrayList = new ArrayList<TaskModel>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        TaskModel task =  ds.getValue(TaskModel.class);
//                        Log.d("@@@task", "" + task);
//                        Log.d("@@@task", "" + task.getName());
//                        Log.d("children", "" + ds.getKey() + " --- " + ds.getValue());
                        arrayList.add(task);
                    }

                    TaskListAdapter adapter = new TaskListAdapter(context, arrayList);
                    ListView listView = (ListView) findViewById(R.id.to_do_list);
                    listView.setAdapter(adapter);
                    Log.d("####### Test", dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Error tag", databaseError.toString()); //Don't ignore errors!
                }
            });
        }
    }
}
