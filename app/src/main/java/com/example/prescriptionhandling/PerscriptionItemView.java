package com.example.prescriptionhandling;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PerscriptionItemView extends LinearLayout {

    private Context context;

    private TextView textViewId;
    private TextView textViewName;
    private EditText edittextQuantity;
    private ImageButton mDeleteButton;

    public PerscriptionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, R.layout.perscription_item_view, this);
        textViewId = findViewById(R.id.textViewId);
        textViewName = findViewById(R.id.textViewName);
        edittextQuantity = findViewById(R.id.textViewQuantity);
        mDeleteButton = findViewById(R.id.buttonDelete);
    }

    public void setPosId(long id) {
        this.textViewId.setText(String.valueOf(id));
    }

    public void setMedicineName(String medicine) {
        this.textViewName.setText(medicine);
    }

    public void setQuantity(String quantity) {
        this.edittextQuantity.setText(quantity);
    }

    public String getMedicineName() {
        return textViewName.getText().toString().trim();
    }

    public String getQuantity() {
        return edittextQuantity.getText().toString().trim();
    }

    public void setDeleteButtonClickListener(View.OnClickListener listener) {
        this.mDeleteButton.setOnClickListener(listener);
    }

    public void setMedicineClickListener(View.OnClickListener listener) {
        this.textViewName.setOnClickListener(listener);
    }

    public void setTextWatcher(final OnTextChangedListener listener) {
        this.edittextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals(""))
                    listener.OnTextChanged(s.toString());
                else
                    listener.OnTextChanged("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equals(""))
                    listener.afterTextChanged(s.toString());
                else
                    listener.afterTextChanged("");

            }
        });
    }

    @Override
    public void clearFocus() {
        super.clearFocus();
        this.edittextQuantity.clearFocus();
        this.edittextQuantity.setCursorVisible(false);
    }

    public void setQuantityFocus() {
        this.edittextQuantity.clearFocus();
        this.edittextQuantity.requestFocus();
        hideKeyboard(context);
    }

    public void setNameFocus() {
        this.edittextQuantity.clearFocus();
        this.textViewName.requestFocus();
        hideKeyboard(context);
    }

    public void setIDFocus() {
        this.textViewId.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((Activity) context).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

interface OnTextChangedListener {
    public void OnTextChanged(String quantity);
    public void afterTextChanged(String quantity);
}
