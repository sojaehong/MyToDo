package com.ssostudio.mytodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.model.ToDoModelList;

public class IncompleteActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private MaterialButton backButton;
    private ToDoListVIewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomplete);

        init();
    }

    private void init(){
        setListVIew();
        setBackButton();
    }

    private void setListVIew(){
        int lastPosition = 0;
        int top = 0;

        new DBManager(this).selectIncompleteToDo();

        listView = findViewById(R.id.to_do_list);

        lastPosition = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());

        adapter = new ToDoListVIewAdapter(this, ToDoModelList.incompleteToDoModels);
        listView.setAdapter(adapter);

        listView.setSelectionFromTop(lastPosition, top);
    }

    public void listViewRefresh(){
        setListVIew();
    }

    private void setBackButton(){
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_button:
                onBackBtnClick();
                break;
        }
    }

    private void onBackBtnClick() {
        finish();
    }

    @Override
    protected void onResume() {
        listViewRefresh();
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
