package com.ssostudio.mytodo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToDoModelList {
    public static Map<String,ArrayList<ToDoModel>> todayToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> selectToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> yearToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> monthToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> bucketListToDoModels = new HashMap<>();
    public static ArrayList<ToDoModel> allToDoModels = new ArrayList<>();
}
