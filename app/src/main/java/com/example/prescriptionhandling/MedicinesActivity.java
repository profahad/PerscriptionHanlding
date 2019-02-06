package com.example.prescriptionhandling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.prescriptionhandling.MainActivity.hideKeyboard;

public class MedicinesActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private List<MedData> list;
    private MedicineDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        initView();
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new MedicineDataAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnMedicineSelectListener(new OnMedicineSelectListener() {
            @Override
            public void onMedicineSelected(MedData data) {
                Intent intent = new Intent();
                intent.putExtra("medicine", data);
                intent.putExtra("pos", getIntent().getIntExtra("pos", 0));
                setResult(RESULT_OK, intent);
                hideKeyboard(MedicinesActivity.this);
                finish();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        mSearchView.onActionViewExpanded();
        loadMedicines();
    }

    private void loadMedicines() {
        for (int i = 0; i < 100; i++) {
            list.add(new MedData(i,"Medicine Name # " + i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            hideKeyboard(this);
            setResult(RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
