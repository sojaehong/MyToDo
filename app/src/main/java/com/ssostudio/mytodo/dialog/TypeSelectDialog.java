package com.ssostudio.mytodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.model.ToDoModel;

public class TypeSelectDialog implements View.OnClickListener {

    private Context _context;
    private ToDoModel _toDoModel;
    private Dialog _dialog;

    public TypeSelectDialog(Context context, ToDoModel toDoModel){
        _context = context;
        _toDoModel = toDoModel;
    }

    public void onShowDialog(){

    }

    private void init(){

        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.dialog_type_select);

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


    }

}
