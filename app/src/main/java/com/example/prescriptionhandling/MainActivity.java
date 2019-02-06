package com.example.prescriptionhandling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    /**
     * Done
     */
    private Button mButton;
    private List<Medicine> list;
    private MedicinesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mButton = (Button) findViewById(R.id.button);
        list = new ArrayList<>();
        adapter = new MedicinesAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        adapter.setListener(new MedicineItemListener() {
            @Override
            public void onMedicineNameClickListener(int pos) {
                hideKeyboard(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, MedicinesActivity.class);
                intent.putExtra("pos", pos);
                startActivityForResult(intent, 100);
            }

            @Override
            public void onDeleteItem(int pos) {
                list.remove(pos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMedicineQuantityChanged(int pos, String quantity) {
                list.get(pos).setQuantity(quantity);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new Medicine());
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("pos", 0);
                MedData medData = (MedData) data.getSerializableExtra("medicine");
                list.get(pos).setMedData(medData);
                list.get(pos).setName(medData.getName());
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    public static void hideKeyboard(Context context) {
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
