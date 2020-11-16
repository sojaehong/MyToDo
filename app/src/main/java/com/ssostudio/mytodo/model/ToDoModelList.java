package com.ssostudio.mytodo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToDoModelList {
    public static Map<String,ArrayList<ToDoModel>> todayToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> weekToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> monthToDoModels = new HashMap<>();
    public static Map<String,ArrayList<ToDoModel>> yearsToDoModels = new HashMap<>();
}
