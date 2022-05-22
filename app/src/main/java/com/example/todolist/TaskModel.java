package com.example.todolist;
public class TaskModel {
    public String uuid;
    public String name;
    public String dueDate;

    public TaskModel() {}

    public TaskModel(String uuid, String name, String dueDate){
        this.uuid = uuid;
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getuuid() {
        return uuid;
    }
}
