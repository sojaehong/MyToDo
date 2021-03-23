package com.ssostudio.mytodo;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;

import java.util.ArrayList;
import java.util.Map;

public class BucketListActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton addBtn;
    private ListView listView;
    private TextView statisticsTitleTextView, contentTextView;
    private ProgressBar toDoProgressBar;
    private ToDoListVIewAdapter adapter;
    private MaterialButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);
        init();
    }

    private void init(){
        setListVIew();
        setAddBtn();
        setBackBtn();
        setStatisticsView();
    }

    private void setAddBtn() {
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    private void setBackBtn(){
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
    }

    private void setListVIew() {

        int lastPosition = 0;
        int top = 0;

        new DBManager(this).selectBucketList();

        listView = findViewById(R.id.to_do_list);

        lastPosition = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());

        adapter = new ToDoListVIewAdapter(this, ToDoModelList.bucketListToDoModels);
        listView.setAdapter(adapter);

        listView.setSelectionFromTop(lastPosition, top);
    }

    public void listRefresh(){
        setListVIew();
    }

    // todo 언어 현지화 필요
    public void setStatisticsView(){

        Map<String, ArrayList<ToDoModel>> toDoModels = ToDoModelList.bucketListToDoModels;
        int completed = toDoModels.get("completedList").size();
        int incompleted = toDoModels.get("failList").size();
        int total = completed + incompleted;
        double percent = (double) completed / (double) total * 100.0;

        String titleText = getString(R.string.bucket_list);

        String contentText = getString(R.string.total) + ": " + total + " | " + getString(R.string.in_progress) + ": "
                + incompleted + " | " + getString(R.string.completed_text) + ": " + completed;

        toDoProgressBar = findViewById(R.id.to_do_progress_Bar);

        int color = 0;
        if(completed == total){
            color = getResources().getColor(R.color.orientarBlue);
        }else{
            color = getResources().getColor(R.color.bRed);
        }

        toDoProgressBar.setProgressTintList(ColorStateList.valueOf(color));
        toDoProgressBar.setProgress((int) percent);

        statisticsTitleTextView = findViewById(R.id.statistics_title_text);
        statisticsTitleTextView.setText(titleText);

        contentTextView = findViewById(R.id.content_text);
        contentTextView.setText(contentText);
    }

    private void viewRefresh(){
        listRefresh();
        setStatisticsView();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_button:
                onAddButtonClick();
                break;
            case R.id.back_button:
                onBackBtnClick();
        }

    }

    private void onBackBtnClick() {
        finish();
    }

    private void onAddButtonClick(){
        new ToDoAddDialog(this).onShowDialog(2);
    }

    @Override
    public void onResume() {
        viewRefresh();
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }

}
