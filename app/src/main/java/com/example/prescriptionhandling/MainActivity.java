package com.example.prescriptionhandling;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.prescriptionhandling.perscriptiondialog.PerscriptionDialog;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private PerscriptionDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog = new PerscriptionDialog(MainActivity.this);
//                dialog.show();
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment = manager.findFragmentByTag("dialog");
                if (fragment != null) {
                    manager.beginTransaction().remove(fragment).commit();
                }
                DialogFrag dialogFrag = new DialogFrag();
                dialogFrag.show(getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        dialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
