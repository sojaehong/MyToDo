package com.ssostudio.mytodo.todo;

import com.ssostudio.mytodo.model.ToDoModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToDoDataManager {

    // to-do 리스트를 완료, 실패로 분류
    public Map<String,ArrayList<ToDoModel>> toDoCompletedSort(ArrayList<ToDoModel> list){
        if (list == null)
            return null;

        Map<String,ArrayList<ToDoModel>> map = new HashMap<>();
        ArrayList<ToDoModel> completedList = new ArrayList<>();
        ArrayList<ToDoModel> failList = new ArrayList<>();

        for (ToDoModel toDoModel: list) {
            if (toDoModel.getTodo_max_count() <= toDoModel.getTodo_now_count()){
                completedList.add(toDoModel);
            }else{
                failList.add(toDoModel);
            }
        }

        map.put("completedList", completedList);
        map.put("failList", failList);

        return map;
    }

}
