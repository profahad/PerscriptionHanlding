package com.appslandz.prescriptionview;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.appslandz.prescriptionview.Utils.hideKeyboard;

public class LabView extends LinearLayout {

    private Context context;

    private TextView textViewId;
    private TextView textViewName;
    private ImageButton mDeleteButton;

    public LabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, R.layout.lab_view, this);
        textViewId = findViewById(R.id.textViewId);
        textViewName = findViewById(R.id.textViewName);
        mDeleteButton = findViewById(R.id.buttonDelete);
    }

    public void setPosId(long id) {
        this.textViewId.setText(String.valueOf(id));
    }

    public void setLabTestName(String medicine) {
        this.textViewName.setText(medicine);
    }

    public String getLabTestName() {
        return textViewName.getText().toString().trim();
    }

    public void setDeleteButtonClickListener(OnClickListener listener) {
        this.mDeleteButton.setOnClickListener(listener);
    }

    public void setLabTestClickListener(OnClickListener listener) {
        this.textViewName.setOnClickListener(listener);
    }


    public void setNameFocus() {
        this.textViewName.requestFocus();
        hideKeyboard(context);
    }

    public void setIDFocus() {
        this.textViewId.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}