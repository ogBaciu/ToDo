package com.example.todolist;
public class TaskModel {
    public String name;
    public String dueDate;

    public TaskModel() {}

    public TaskModel(String name, String dueDate){
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }
}
