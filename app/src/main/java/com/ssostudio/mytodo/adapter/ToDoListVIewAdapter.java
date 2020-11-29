package com.ssostudio.mytodo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.todo.ToDoDataManager;
import com.ssostudio.mytodo.utility.AppUtility;

import java.util.ArrayList;
import java.util.Map;

public class ToDoListVIewAdapter extends BaseAdapter {

    private Context _context;
    private int _size;
    private LayoutInflater inflater;
    private ArrayList<ToDoModel> _list;
    private Boolean isTitleSet = false;
    private TextView toDoTitleTextVIew, countTextView;
    private ImageView upImageView, downImageView;


    public ToDoListVIewAdapter(Context context, Map<String, ArrayList<ToDoModel>> listMap){
        _context = context;
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        _list = new ArrayList<>();
        _list.addAll(listMap.get("failList"));
        _list.addAll(listMap.get("completedList"));

        _size = _list.size();
    }

    @Override
    public int getCount() {
        return _size;
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        boolean isTitleItem = false;

        if (new ToDoDataManager().toDoCompletedCheck(_list.get(i)) && !isTitleSet){
            isTitleSet = true;
            isTitleItem = true;
            view = inflater.inflate(R.layout.to_do_title_item, viewGroup, false);
            Log.d("ListCheck" , "title");
        }else{
            view = inflater.inflate(R.layout.to_do_item, viewGroup, false);
            Log.d("ListCheck" , "no title");
        }

        ToDoModel toDoModel = _list.get(i);

        toDoTitleTextVIew = view.findViewById(R.id.to_do_title_text_view);
        toDoTitleTextVIew.setText(toDoModel.getTodo_title());

        String countText = toDoModel.getTodo_now_count()+" / "+toDoModel.getTodo_max_count();

        countTextView = view.findViewById(R.id.count_text);
        countTextView.setText(countText);

        upImageView = view.findViewById(R.id.count_up_button);
        upImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickVibrator();
            }
        });

        downImageView = view.findViewById(R.id.count_down_button);
        downImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickVibrator();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;

            }});

        return view;
    }

    private void onClickVibrator(){
        AppUtility.onVibrator(_context, 15);
    }


}
