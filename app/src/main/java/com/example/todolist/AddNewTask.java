package com.example.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNewTask  extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private TextView setDueDate;
    private EditText mTaskEdit;
    private Button mSaveBtn;
    private FirebaseFirestore firestore;
//    private DatabaseReference firebase;
    private Context context;
    private String dueDate = "";
    private String id = "";
    private String dueDateUpdate = "";
    public TaskModel task1;
    private DatabaseReference database;
    private Boolean editable = false;
    private String uuid;
    private String taskName;

    public AddNewTask(){}

    public AddNewTask (String uuid, String name, String dueDate){
        Log.d("####### Delete", uuid + " " + name + " " + dueDate);
        this.uuid = uuid;
        this.dueDate = dueDate;
        this.taskName = name;
        this.editable = true;
//        this.mTaskEdit.setText(name);
    }

//    public static AddNewTask newInstance(){
//        return new AddNewTask();
//    }

//    public AddNewTask editInstance(TaskModel task){
//        this.mTaskEdit.setText(task.getName());
//        this.dueDate = task.getDueDate();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task , container , false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.set_due_tv);
        mTaskEdit = view.findViewById(R.id.task_edittext);
        mSaveBtn = view.findViewById(R.id.save_btn);

        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        if(editable){
            mTaskEdit.setText(taskName);
        }

        boolean isUpdate = false;

//        final Bundle bundle = getArguments();
//        if (bundle != null){
//            isUpdate = true;
//            String task = bundle.getString("task");
//            id = bundle.getString("id");
//            dueDateUpdate = bundle.getString("due");
//
//            mTaskEdit.setText(task);
//            setDueDate.setText(dueDateUpdate);
//
//            if (task.length() > 0){
//                mSaveBtn.setEnabled(false);
//                mSaveBtn.setBackgroundColor(Color.GRAY);
//            }
//        }
//
//        mTaskEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().equals("")){
//                    mSaveBtn.setEnabled(false);
//                    mSaveBtn.setBackgroundColor(Color.GRAY);
//                }else{
//                    mSaveBtn.setEnabled(true);
//                    mSaveBtn.setBackgroundColor(getResources().getColor(R.color.green_blue));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DATE);

                if(editable){
                    String[] parts = dueDate.split("/");
                    DAY = Integer.parseInt(parts[0]);
                    MONTH = Integer.parseInt(parts[1]) - 1;
                    YEAR = Integer.parseInt(parts[2]);
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        setDueDate.setText(dayOfMonth + "/" + month + "/" + year);
                        dueDate = dayOfMonth + "/" + month +"/"+year;

                    }
                } , YEAR , MONTH , DAY);

                datePickerDialog.show();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = mTaskEdit.getText().toString();
                    if (taskName.isEmpty()) {
                        Toast.makeText(context, "Empty task not Allowed !!", Toast.LENGTH_SHORT).show();
                    } else {
                        String newUuid = editable == true ? uuid : UUID.randomUUID().toString();
                        TaskModel newTask = new TaskModel( newUuid, taskName, dueDate);
                        database.child("test").child(newUuid).setValue(newTask).addOnCompleteListener(res -> {
                            Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(res -> {
                            Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

//    @Override
//    public void onDismiss(@NonNull DialogInterface dialog) {
//        super.onDismiss(dialog);
//        Activity activity = getActivity();
//        if (activity instanceof  OnDialogCloseListner){
//            ((OnDialogCloseListner)activity).onDialogClose(dialog);
//        }
//    }
}