package com.example.prescriptionhandling.labsdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;

import com.example.prescriptionhandling.NetworkService;
import com.example.prescriptionhandling.R;
import com.example.prescriptionhandling.ValueHandler;
import com.example.prescriptionhandling.medicinesdata.MedData;
import com.example.prescriptionhandling.medicinesdata.MedicinesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        if (ValueHandler.getInstance().getLabDataList() != null) {
            list.addAll(ValueHandler.getInstance().getLabDataList());
            adapter.notifyDataSetChanged();
            return;
        }
        new Request(this).execute();
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


    private class Request extends AsyncTask<String, Void, String> {

        private ProgressDialog progressBar;

        public Request(Context context) {
            progressBar = new ProgressDialog(context);
            progressBar.setMessage("Loading...");
            progressBar.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            NetworkService service = new NetworkService();
            return service.getRequest("https://devapi.hayaat.pk/api/home/lab-testlist");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.i("FAHAD : Response", s);
                if (jsonObject.getBoolean("success")) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new LabData(object.getLong("id"), object.getString("name")));
                    }
                    ValueHandler.getInstance().setLabDataList(list);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
