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

public class ToDoAddDialog implements View.OnClickListener {

    private Context _context;
    private ToDoModel _toDoModel;
    private Dialog _dialog;
    private int _type;
    private TextView titleTextVIew;
    private MaterialButton cancelBtn, countEditBtn, okBtn;

    public ToDoAddDialog(Context context){
        _context = context;
    }

    public void onShowDialog(int type){
        _type = type;
        init();
    }

    private void init(){
        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.dialog_to_do_add);

        titleTextVIew = _dialog.findViewById(R.id.title_text_view);
        titleTextVIew.setText(getTitleText());

        cancelBtn = _dialog.findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(this);

        okBtn = _dialog.findViewById(R.id.ok_button);
        okBtn.setOnClickListener(this);

        countEditBtn = _dialog.findViewById(R.id.count_edit_button);
        countEditBtn.setOnClickListener(this);

        _dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(_dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = _dialog.getWindow();
        window.setAttributes(layoutParams);
    }

    private String getTitleText(){
        String titleText = "null";

        switch (_type){
            case 0:
                titleText = _context.getString(R.string.today);
                break;
            case 1:
                titleText = _context.getString(R.string.this_week);
                break;
            case 2:
                titleText = _context.getString(R.string.this_month);
                break;
            case 3:
                titleText = _context.getString(R.string.this_year);
                break;
        }

        titleText = titleText + " " + _context.getString(R.string.todo);

        return titleText;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_button:
                onClickCancelBtn();
                break;
            case R.id.ok_button:
                onClickOkBtn();
                break;
            case R.id.count_edit_button:
                onClickCountEditBtn();
                break;
        }
    }

    private void onClickCancelBtn(){
        _dialog.dismiss();
    }

    private void onClickOkBtn(){

    }

    private void onClickCountEditBtn(){

    }
}
