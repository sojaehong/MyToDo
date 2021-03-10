package com.ssostudio.mytodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.utility.DateManager;

public class ToDoSelectDialog implements View.OnClickListener {
    private Context _context;
    private ToDoModel _toDoModel = new ToDoModel();
    private Dialog _dialog;
    private MaterialButton cancelButton, updateButton, deleteButton, continueButton;
    private TextView dateTextView, contentTextView, countTextView;
    private boolean _isFailed = false;

    public ToDoSelectDialog(Context context) {
        _context = context;
    }

    public void onShowDialog(ToDoModel toDoModel, boolean isFailed) {
        _toDoModel = toDoModel;
        _isFailed = isFailed;
        init();
    }

    // todo 언어 현지화 필요
    private void init() {
        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.dialog_to_do_select);

        cancelButton = _dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);

        updateButton = _dialog.findViewById(R.id.update_button);
        updateButton.setOnClickListener(this);

        deleteButton = _dialog.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        String dateText = "";

        if (_toDoModel.getTodo_type() == 2) {
            dateText = "버킷리스트";
        } else {
            dateText = DateManager.dateTimeZoneFormat(_toDoModel.getStart_date())
                    + " ~ " + DateManager.dateTimeZoneFormat(_toDoModel.getDeadline_date());
        }

        dateTextView = _dialog.findViewById(R.id.date_text);
        dateTextView.setText(dateText);

        String contentText = _toDoModel.getTodo_title();
        contentTextView = _dialog.findViewById(R.id.content_text);
        contentTextView.setText(contentText);

        String countText = _toDoModel.getTodo_now_count() + " / " + _toDoModel.getTodo_max_count();
        countTextView = _dialog.findViewById(R.id.count_text);
        countTextView.setText(countText);

        continueButton = _dialog.findViewById(R.id.continue_button);
        if (_isFailed && _toDoModel.getTodo_type() == 0){
            continueButton.setVisibility(View.VISIBLE);
            continueButton.setOnClickListener(this);
        }

        _dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(_dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = _dialog.getWindow();
        window.setAttributes(layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                onCancelButtonClick();
                break;
            case R.id.delete_button:
                onDeleteBtnClick();
                break;
            case R.id.update_button:
                onUpdateBtnClick();
                break;
            case R.id.continue_button:
                onContinueBtnClick();
                break;
        }
    }

    private void onContinueBtnClick() {
        long todayEndTimestamp = DateManager.dayEndTimestamp(DateManager.getTimestamp());

        _toDoModel.setDeadline_date(todayEndTimestamp);

        new DBManager(_context).updateTodoDB(_toDoModel);

        _dialog.dismiss();
    }

    private void onCancelButtonClick() {
        _dialog.dismiss();
    }

    private void onDeleteBtnClick() {
        new ConfirmationDialog(_context).onShowDialog(0, _toDoModel);
        _dialog.dismiss();
    }

    private void onUpdateBtnClick() {
        new ToDoAddDialog(_context).onShowUpdateDialog(_toDoModel);
        _dialog.dismiss();
    }
}
