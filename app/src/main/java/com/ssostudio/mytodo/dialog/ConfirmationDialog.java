package com.ssostudio.mytodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.model.ToDoModel;

public class ConfirmationDialog implements View.OnClickListener {
    private Context _context;
    private ToDoModel _toDoModel = new ToDoModel();
    private Dialog _dialog;
    private TextView contentTextView;
    private MaterialButton cancelBtn, okBtn;
    private int _type;

    public ConfirmationDialog(Context context) {
        _context = context;
    }

    public void onShowDialog(int type, ToDoModel toDoModel) {
        _type = type;
        _toDoModel = toDoModel;
        init();
    }

    private void init() {
        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.dialog_confirmation);

        setCancelBtn();
        setOkBtn();
        setContentTextView();

        _dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(_dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = _dialog.getWindow();
        window.setAttributes(layoutParams);
    }

    private void setContentTextView() {
        contentTextView = _dialog.findViewById(R.id.dialog_content_text_view);
        contentTextView.setText(getContentText());
    }

    private void setCancelBtn() {
        cancelBtn = _dialog.findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(this);
    }

    private void setOkBtn() {
        okBtn = _dialog.findViewById(R.id.ok_button);
        okBtn.setOnClickListener(this);
    }

    private String getContentText() {
        String content = "";

        switch (_type) {
            case 0:
//                content = _context.getString()
                content = "정말로 삭제하시겠습니까?";
                break;
        }
        return content;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                onCancelBtnClick();
                break;
            case R.id.ok_button:
                onOkBtnClick();
                break;
        }
    }

    private void onCancelBtnClick() {
        _dialog.dismiss();
    }

    private void onOkBtnClick() {
        switch (_type) {
            case 0:
                break;
        }
    }

}
