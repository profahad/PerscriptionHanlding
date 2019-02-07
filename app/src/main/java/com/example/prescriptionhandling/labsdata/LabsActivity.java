package com.example.prescriptionhandling.labsdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.example.prescriptionhandling.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.prescriptionhandling.Utils.hideKeyboard;

public class LabsActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private List<LabData> list;
    private LabsDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs);
        initView();
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new LabsDataAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnLabTestSelectListener(new OnLabTestSelectListener() {
            @Override
            public void onLabTestSelected(LabData data) {
                Intent intent = new Intent();
                intent.putExtra("lab", data);
                intent.putExtra("pos", getIntent().getIntExtra("pos", 0));
                setResult(RESULT_OK, intent);
                hideKeyboard(LabsActivity.this);
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
            list.add(new LabData(i,"Lab Test # " + i));
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
